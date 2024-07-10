package com.g543.g543game.element;

import com.g543.g543game.manager.GameLoader;

import java.awt.*;

public class BackgroundMap extends ElementObj{

    public int ChangeSpeed = 6; //地图行动的速度
    public int newX;            //
    @Override
    public void showElement(Graphics g) {
//        g.drawImage(this.getImageIcon().getImage(),)
    }

    @Override
    public ElementObj createElement(String str){
        String [] strs = str.split(",");
        this.setX(Integer.parseInt(strs[0]));
        this.setY(Integer.parseInt(strs[1]));
        this.setImageIcon(GameLoader.imageMap.get(strs[2]));
        this.setHeight(this.getImageIcon().getIconHeight());
        this.setWidth(this.getImageIcon().getIconWidth());
        return this;
    }
}
