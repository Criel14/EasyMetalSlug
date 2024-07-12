package com.g543.g543game.element;

import com.g543.g543game.manager.ElementManager;
import com.g543.g543game.manager.GameElement;
import com.g543.g543game.manager.GameLoader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class Enemy extends ElementObj {

    // 敌人种类：EnemyGun,EnemyRPG
    private String enemyType = "EnemyGun";
    // 方向：right,left
    private String direction = "left";
    // 状态：attack，run，stand，die
    private String status = "stand";

    //正在播放死亡动画
    private boolean isDying = false;

    // 死亡动画帧计数器
    private int dyingFrameCounter = 0;

    // 移动速度
    private int moveSpeed = 2;

    // 随机数生成器
    private Random random = new Random();

    // 攻击间隔时间
    private long lastAttackTime = 0;
    private long attackInterval = 1000; // 每1秒攻击一次
    private long attackDuration = 200;
    private long attackStartTime = 0;
    // 站立间隔时间
    private long lastStandTime = 0;
    private long standInterval = 3000; // 每3秒站立一次

    // 站立持续时间
    private long standDuration = 500; // 站立持续0.5秒
    private long standStartTime = 0; // 记录站立开始的时间

    public Enemy() {
    }

    @Override
    public void showElement(Graphics g) {
        try {
            g.drawImage(this.getImageIcon().getImage(), (this.getX() - getMap().newX) * 2, this.getY(), this.getWidth(), this.getHeight(), null);
        } catch (Exception e) {
            System.out.println("Enemy showElement error" + this.getImageIcon());
        }
        showBloodBar(g);
    }

    // data格式：X坐标，Y坐标，种类（EnemyGun或EnemyRPG）
    @Override
    public ElementObj createElement(String data) {
        String[] split = data.split(",");
        this.setX(Integer.parseInt(split[0]));
        this.setY(Integer.parseInt(split[1]));
        this.setEnemyType(split[2]);
        this.setWidth(100);
        this.setHeight(93);

        return this;
    }

    @Override
    protected void updateImage(long gameTime) {
        String url = String.valueOf(GameLoader.imageMap.get(this.enemyType + "_" + direction + "_" + status));
        Path dir = Paths.get(url);
        // 存储每个状态下的所有图片文件的路径
        List<Path> imageList = new ArrayList<>();
        try {
            Files.walk(dir)
                    .filter(Files::isRegularFile)
                    .forEach(imageList::add);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageIcon icon = new ImageIcon(imageList.get((int) (gameTime / 8 % imageList.size())).toString());
        if (isDying) {
            icon = new ImageIcon(imageList.get((int) (dyingFrameCounter / 15 % imageList.size())).toString());
            dyingFrameCounter++;
            if (dyingFrameCounter % 15 == 0) setY(getY() + 8);
            if (dyingFrameCounter / 15 == imageList.size()) this.setAlive(false);
        }

        this.setWidth(icon.getIconWidth());
        this.setHeight(icon.getIconHeight());

        // 更新图片
        this.setImageIcon(icon);
    }

    @Override
    public void die(long gameTime) {
        status = "die";
        isDying = true;
    }

    // get和set方法
    public void setEnemyType(String enemyType) {
        this.enemyType = enemyType;
    }

    // 更新敌人AI
    @Override
    public void stateSwitch(long gameTime) {
        if (isDying) {
            return; // 如果敌人正在死亡，不执行AI逻辑
        }

        int playerX = getPlayer().getX();
        long currentTime = System.currentTimeMillis();

        // 随机站立逻辑
        if (status.equals("stand") && currentTime - standStartTime < standDuration) {
            return; // 继续站立直到站立持续时间结束
        } else if (currentTime - lastStandTime > standInterval) {
            status = "stand";
            standStartTime = currentTime;
            lastStandTime = currentTime + random.nextInt((int) standInterval);
            return;
        }

        // 攻击逻辑
        if (status.equals("attack") && currentTime - attackStartTime < attackDuration) {
            return;
        } else if (currentTime - lastAttackTime > attackInterval) {
            status = "attack";
            attackStartTime = currentTime;
            lastAttackTime = currentTime + random.nextInt((int) attackInterval);
            shoot();
            return;
        }

        // 移动逻辑
        if (abs(playerX - this.getX()) < 200)
        {
            if (!status.equals("stand")) status = "stand";
            return;
        }
        if (playerX > this.getX()) {
            direction = "right";
            status = "run";
            this.setX(this.getX() + moveSpeed);
        } else if (playerX < this.getX()) {
            direction = "left";
            status = "run";
            this.setX(this.getX() - moveSpeed);
        } else {
            status = "stand";
        }
    }

    private void shoot() {
        // 发射子弹逻辑
        ElementObj obj = GameLoader.getObject("gunBullet");
        int x = this.getX();
        int y = this.getY();
        int isMovingRight = 0;
        if (direction.equals("right")) isMovingRight = 1;
        if (isMovingRight == 1) x += 50;
        y += 15;
        String data = Integer.toString(x) + "," + Integer.toString(y) + ",gunBullet," + Integer.toString(isMovingRight);

        obj.createElement(data);
        ElementManager.getManager().addElement(GameElement.ENEMY_BULLET, obj);
    }

    private Player getPlayer() {
        Player player = null;
        ElementManager elementManager = ElementManager.getManager();
        List<ElementObj> elementObjList = elementManager.getElement(GameElement.PLAYER);
        for (ElementObj p : elementObjList) {
            player = (Player) p;
        }
        return player;
    }
}
