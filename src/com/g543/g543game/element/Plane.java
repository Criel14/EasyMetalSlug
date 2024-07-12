package com.g543.g543game.element;

import com.g543.g543game.manager.ElementManager;
import com.g543.g543game.manager.GameElement;

import javax.swing.*;
import java.awt.*;

public class Plane extends ElementObj{

    // 飞机的生存时间
    private int lifeTime = 200;
    // 飞机的飞行速度
    private int moveSpeed = 4;
    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getImageIcon().getImage(), (this.getX() - getMap().newX) * 2, this.getY(), this.getWidth(), this.getHeight(), null);
    }

    @Override
    public ElementObj createElement(String str){
//        System.out.println("创建飞机");
        this.setX(getMap().newX);
        this.setY(30);
        this.setImageIcon(new ImageIcon("image/planeImage/plane.png"));
        this.setWidth(this.getImageIcon().getIconWidth());
        this.setHeight(this.getImageIcon().getIconHeight());
        return this;
    }

    @Override
    public void move(long gameTime){
        this.setX(this.getX() + moveSpeed);
        lifeTime--;
        if(lifeTime == 0){
            this.setAlive(false);
        }
    }

    private long localTime= 0;
    @Override
    public void attack(long gameTime){
        if(gameTime > localTime + 30){
            localTime = gameTime;
            ElementObj bullet = new PlaneBullet().createElement(this.toString());
            ElementManager.getManager().addElement(GameElement.PLANE_BULLET, bullet);
        }
    }

    @Override
    public String toString() {
        return this.getX() + "," + this.getY();
    }
}
