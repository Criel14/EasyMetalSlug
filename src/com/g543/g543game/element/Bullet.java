package com.g543.g543game.element;

import com.g543.g543game.manager.GameLoader;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Bullet extends ElementObj {

    // 移动速度
    private int moveSpeed = 6;

    //移动方向
    private boolean isMovingRight = true;

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getImageIcon().getImage(), (this.getX() - getMap().newX) * 2, this.getY(), this.getWidth(), this.getHeight(), null);
    }


    @Override
    public ElementObj createElement(String data) {
        String[] split = data.split(",");
        this.setX(Integer.parseInt(split[0]));
        this.setY(Integer.parseInt(split[1]));
        ImageIcon icon = GameLoader.imageMap.get(split[2]);
        this.setWidth(icon.getIconWidth());
        this.setHeight(icon.getIconHeight());
        this.setImageIcon(icon);
        isMovingRight = Objects.equals(split[3], "1");
        setAttackDamage(Integer.parseInt(split[4]));
        return this;
    }

    @Override
    protected void move(long gameTime) {
        if (isMovingRight)
            this.setX(this.getX() + moveSpeed);
        else this.setX(this.getX() - moveSpeed);
    }

    @Override
    public void die(long gameTime) {
        setAlive(false);
    }
}
