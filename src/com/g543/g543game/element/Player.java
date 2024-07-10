package com.g543.g543game.element;

import com.g543.g543game.manager.ElementManager;
import com.g543.g543game.manager.GameElement;
import com.g543.g543game.manager.GameLoader;
import com.g543.g543game.util.KeyboardCode;

import javax.swing.*;
import java.awt.*;

public class Player extends ElementObj {

    // 是否在移动
    private boolean isMoving = false;
    // 是否向右移动（true为向右，false为向左）
    private boolean isMovingRight = true;

    // 移动速度
    private int moveSpeed = 6;

    // 移动方向
    private String direction = "right";

    // 是否在发射
    private boolean isShooting = false;

    // 子弹发射间隔
    private long bulletInterval = 0;


    // 构造方法
    public Player() {
    }
    public Player(int x, int y, int width, int height, ImageIcon imageIcon) {
        super(x, y, width, height, imageIcon);
    }

    // data格式：X坐标，Y坐标，图片名称（对应配置文件的图片）
    @Override
    public ElementObj createElement(String data) {
        String[] split = data.split(",");
        this.setX(Integer.parseInt(split[0]));
        this.setY(Integer.parseInt(split[1]));
        ImageIcon icon = GameLoader.imageMap.get(split[2]);
        this.setWidth(icon.getIconWidth());
        this.setHeight(icon.getIconHeight());
        this.setImageIcon(icon);
        return this;
    }

    // 重写显示方法
    @Override
    public void showElement(Graphics g) {
        // 测试显示图片
        g.drawImage(this.getImageIcon().getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
    }

    @Override
    public void keyboardListener(boolean isPressed, int keyCode) {
        if (isPressed) {
            switch (keyCode) {
                case KeyboardCode.A:
                    this.isMoving = true;
                    this.isMovingRight = false;
                    this.direction = "left";
                    break;
                case KeyboardCode.D:
                    this.isMoving = true;
                    this.isMovingRight = true;
                    this.direction = "right";
                    break;
                case KeyboardCode.W:
                    this.direction = "up";
                    break;
                case KeyboardCode.S:
                    this.direction = "down";
                    break;
                case KeyboardCode.SPACE:
                    break;
                case KeyboardCode.J:
                    isShooting = true;
                    break;
                default:
                    break;
            }
        } else {
            isMoving = false;
            isShooting = false;
        }
    }

    // 移动方法
    @Override
    public void move(long gameTime) {
        if (this.isMoving) {
            if (this.isMovingRight) {
                this.setX(this.getX() + moveSpeed);
            } else {
                this.setX(this.getX() - moveSpeed);
            }
        }
    }

    // 添加道具
    @Override
    protected void addProp(long gameTime) {
        // 添加子弹
        if (isShooting) {
            // 发射子弹逻辑

        }
    }
}
