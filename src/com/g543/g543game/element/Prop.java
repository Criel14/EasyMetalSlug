package com.g543.g543game.element;

import com.g543.g543game.manager.GameLoader;

import javax.swing.*;
import java.awt.*;

public class Prop extends ElementObj{

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
        return this;
    }
}
