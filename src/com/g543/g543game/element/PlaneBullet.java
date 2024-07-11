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

public class PlaneBullet extends ElementObj{
    // 子弹的生存时间
    private int lifeTime = 100;
    // 子弹的飞行速度
    private int moveSpeed = 4;

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getImageIcon().getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
    }

//    str的格式是：x,y
//    其中x是发射点的x坐标，y是发射点的y坐标
    @Override
    public ElementObj createElement(String str){
        String [] arr = str.split(",");
        this.setX(Integer.parseInt(arr[0]));
        this.setY(Integer.parseInt(arr[1]));
        this.setImageIcon(new ImageIcon("image/planeImage/plane_bullet/plane_bomb0.png"));
        return this;
    }

    @Override
    protected void updateImage(long gameTime) {
        String url = String.valueOf(GameLoader.imageMap.get("plane_bullet"));
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

    @Override
    public void move(long gameTime){
        this.setY(this.getY() + moveSpeed);
        lifeTime--;
        if(lifeTime == 0){
            this.setAlive(false);
        }
    }

    @Override
    public void destroy(long gameTime) {
        ElementManager.getManager().addElement(GameElement.DESTROYED_ELEMENT_EFFECT,
                new PlaneBulletDestroyEffect().createElement(this.getX() + "," + this.getY()));
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }


    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }
}
