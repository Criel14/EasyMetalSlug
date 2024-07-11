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

public class Enemy extends ElementObj {

    // 敌人种类：EnemyGun,EnemyRPG
    private String enemyType = "EnemyGun";
    // 方向：right,left
    private String direction = "left";
    // 状态：attack，run，stand，die
    private String status = "stand";


    public Enemy() {
    }

    @Override
    public void showElement(Graphics g) {
        try{
            g.drawImage(this.getImageIcon().getImage(), (this.getX() - getMap().newX) * 2, this.getY(), this.getWidth(), this.getHeight(), null);
        } catch (Exception e){
            System.out.println("Enemy showElement error" + this.getImageIcon());
        }
    }

    // data格式：X坐标，Y坐标，种类（EnemyGun或EnemyRPG）
    @Override
    public ElementObj createElement(String data) {
        String[] split = data.split(",");
        this.setX(Integer.parseInt(split[0]));
        this.setY(Integer.parseInt(split[1]));
        this.setEnemyType(split[2]);
        // 测试
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

        ImageIcon icon = new ImageIcon(imageList.get((int) (gameTime / 20 % imageList.size())).toString());
        this.setWidth(icon.getIconWidth());
        this.setHeight(icon.getIconHeight());

        // 更新图片
        this.setImageIcon(icon);
    }


    // get和set方法
    public void setEnemyType(String enemyType) {
        this.enemyType = enemyType;
    }

    private BackgroundMap getMap() {
        BackgroundMap map = null;
        ElementManager elementManager = ElementManager.getManager();
        List<ElementObj> elementObjList = elementManager.getElement(GameElement.BACKGROUND_MAP);
        for (ElementObj m : elementObjList) {
            map = (BackgroundMap) m;
        }
        return map;
    }
}
