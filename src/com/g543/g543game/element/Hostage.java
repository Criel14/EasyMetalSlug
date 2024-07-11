package com.g543.g543game.element;

import com.g543.g543game.manager.GameLoader;
import com.g543.g543game.show.GameJFrame;

import java.awt.*;

public class Hostage extends ElementObj{

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getImageIcon().getImage(),this.getX(),this.getY(),this.getWidth(),this.getHeight(),null);
    }
    @Override
    public ElementObj createElement(String str){
        String [] strs = str.split(",");
        this.setX(Integer.parseInt(strs[0]));
        this.setY(Integer.parseInt(strs[1]));
        this.setImageIcon(GameLoader.imageMap.get(strs[2]));
        this.setHeight(this.getImageIcon().getIconHeight());
        this.setWidth(this.getImageIcon().getIconWidth());
        System.out.println(this.getWidth()+" "+this.getHeight());
        return this;
    }
}
