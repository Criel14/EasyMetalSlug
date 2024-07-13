package com.g543.g543game.element;

import com.g543.g543game.manager.GameLoader;
import com.g543.g543game.show.GameJFrame;
import com.g543.g543game.util.KeyboardCode;

import java.awt.*;

public class BackgroundMap extends ElementObj{
    //局部地图的x坐标
    public int newX = 0;
    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getImageIcon().getImage(),0,0,this.getWidth(),this.getHeight()+1400,
                newX,0,newX+740,this.getHeight(),null);
    }

    @Override
    public ElementObj createElement(String str){
        String [] strs = str.split(",");
        this.setX(Integer.parseInt(strs[0]));
        this.setY(Integer.parseInt(strs[1]));
        this.setImageIcon(GameLoader.imageMap.get(strs[2]));
        System.out.println("加载的背景图是：" + GameLoader.imageMap.get(strs[2]));
        this.setHeight(GameJFrame.GAME_HEIGHT);
        this.setWidth(this.getImageIcon().getIconWidth());
        return this;
    }

}
