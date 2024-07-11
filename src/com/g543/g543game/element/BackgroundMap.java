package com.g543.g543game.element;

import com.g543.g543game.manager.GameLoader;
import com.g543.g543game.show.GameJFrame;
import com.g543.g543game.util.KeyboardCode;

import java.awt.*;

public class BackgroundMap extends ElementObj{
    //地图改变的速度
    public int ChangeSpeed = 3;
    //局部地图的x坐标
    public int newX = 0;
    //局部地图的y坐标
    public int newY = 0;
    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getImageIcon().getImage(),0,0,this.getWidth(),this.getHeight()+1400,
                newX,newY,newX+500,this.getHeight(),null);
    }

    @Override
    public void keyboardListener(boolean isPressed,int keyCode){
        if(isPressed){
//            System.out.println("keycode"+keyCode);
            if(keyCode == KeyboardCode.D && newX+500<=this.getWidth()){
                changeWay = 2;
            }
            else if (keyCode == KeyboardCode.A && newX>=0){
                changeWay = 1;
            }
        }
        else{
            changeWay = 0;
        }
    }

    @Override
    public void updateImage(long gameTime){
        if(changeWay == 2 && newX+500<this.getWidth() && this.isChange){

            newX += ChangeSpeed;
        }
        else if (changeWay == 1 && newX>0 && this.isChange){
            newX -= ChangeSpeed;
        }
    }

    @Override
    public ElementObj createElement(String str){
        String [] strs = str.split(",");
        this.setX(Integer.parseInt(strs[0]));
        this.setY(Integer.parseInt(strs[1]));
        this.setImageIcon(GameLoader.imageMap.get(strs[2]));
        this.setHeight(GameJFrame.GAME_HEIGHT);
        this.setWidth(this.getImageIcon().getIconWidth());
//        System.out.println(this.getWidth()+" "+this.getHeight());
        return this;
    }

    public int getChangeSpeed() {
        return ChangeSpeed;
    }

    public void setChangeSpeed(int changeSpeed) {
        ChangeSpeed = changeSpeed;
    }

    public int getNewX() {
        return newX;
    }

    public void setNewX(int newX) {
        this.newX = newX;
    }

    public int getNewY() {
        return newY;
    }

    public void setNewY(int newY) {
        this.newY = newY;
    }

}
