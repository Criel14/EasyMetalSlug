package com.g543.g543game.element;

import com.g543.g543game.manager.ElementManager;
import com.g543.g543game.manager.GameElement;
import com.g543.g543game.manager.GameLoader;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class RPGBullet extends ElementObj {

    // 移动速度
    private int moveSpeed = 6;

    //下坠速度
    private int gravity = 2;

    //移动方向
    private boolean isMovingRight = true;

    private boolean isBooming = false;

    private boolean isFromPlayer = true;

    public boolean isFromPlayer() {
        return isFromPlayer;
    }

    public void setFromPlayer(boolean fromPlayer) {
        isFromPlayer = fromPlayer;
    }

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getImageIcon().getImage(), (this.getX() - getMap().newX) * 2, this.getY(), this.getWidth(), this.getHeight(), null);
//        g.drawRect(this.getRectangle().x, this.getRectangle().y, this.getRectangle().width, this.getRectangle().height);
    }

    @Override
    public ElementObj createElement(String data) {
        String[] split = data.split(",");
        this.setX(Integer.parseInt(split[0]));
        this.setY(Integer.parseInt(split[1]));
        isMovingRight = split[3].equals("1");
        isFromPlayer = split[4].equals("1");
        ImageIcon icon = GameLoader.imageMap.get(split[2]+(isMovingRight ? "_right" : "_left"));
        this.setWidth(icon.getIconWidth());
        this.setHeight(icon.getIconHeight());
        this.setImageIcon(icon);
        setAttackDamage(0);
        return this;
    }

    @Override
    protected void move(long gameTime) {
        if (isMovingRight)
            this.setX(this.getX() + moveSpeed);
        else this.setX(this.getX() - moveSpeed);

        this.setY(this.getY() + gravity);
    }

    @Override
    protected void stateSwitch(long gameTime) {
        if (getY() >= 570) die(gameTime);
    }

    @Override
    public void die(long gameTime) {
        if (isBooming) return;
        isBooming = true;
        if (isMovingRight) {
            if (isFromPlayer) {
                ElementManager.getManager().addElement(GameElement.PLAYER_DESTROYED_ELEMENT_EFFECT,
                        new PlaneBulletDestroyedEffect().createElement((this.getX() + 20) + "," + (this.getY() - 30)));
            } else {
                ElementManager.getManager().addElement(GameElement.ENEMY_DESTROYED_ELEMENT_EFFECT,
                        new PlaneBulletDestroyedEffect().createElement((this.getX() + 20) + "," + (this.getY() - 30)));
            }
        } else {
            if (isFromPlayer) {
                ElementManager.getManager().addElement(GameElement.PLAYER_DESTROYED_ELEMENT_EFFECT,
                        new PlaneBulletDestroyedEffect().createElement((this.getX() - 20) + "," + (this.getY() - 30)));
            } else {
                ElementManager.getManager().addElement(GameElement.ENEMY_DESTROYED_ELEMENT_EFFECT,
                        new PlaneBulletDestroyedEffect().createElement((this.getX() - 20) + "," + (this.getY() - 30)));
            }
        }
        setAlive(false);
    }
}
