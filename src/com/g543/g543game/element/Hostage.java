package com.g543.g543game.element;

import com.g543.g543game.manager.ElementManager;
import com.g543.g543game.manager.GameElement;
import com.g543.g543game.manager.GameLoader;
import com.g543.g543game.show.GameJFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Hostage extends ElementObj{

    //状态：stand，die
    private String status = "stand";

    private int moveSpeed = 4;
    private boolean isDying = false;
    private int dyingFrameCounter = 0;

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getImageIcon().getImage(),(this.getX() - getMap().newX) * 2,this.getY(),this.getWidth(),this.getHeight(),null);
        showBloodBar(g);
    }
    @Override
    public ElementObj createElement(String str){
        String [] strs = str.split(",");
        this.setX(Integer.parseInt(strs[0]));
        this.setY(Integer.parseInt(strs[1]));
        this.setImageIcon(GameLoader.imageMap.get(strs[2]));
        this.setHeight(this.getImageIcon().getIconHeight());
        this.setWidth(this.getImageIcon().getIconWidth());
//        System.out.println(this.getWidth()+" "+this.getHeight());
        return this;
    }

    @Override
    protected void updateImage(long gameTime) {
        String url = String.valueOf(GameLoader.imageMap.get("hostage_" + this.status));
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

        if (isDying) {
            icon = new ImageIcon(imageList.get((int) (dyingFrameCounter / 8 % imageList.size())).toString());
            if(dyingFrameCounter >= 7 * 8 && dyingFrameCounter <= 13 * 8) this.setX(this.getX() - 1);
            else if(dyingFrameCounter >= 19 * 8 && dyingFrameCounter <= 25 * 8) this.setX(this.getX() - 2);
            dyingFrameCounter++;

            if(dyingFrameCounter / 8 == imageList.size() && this.getX() - getMap().newX > 0) dyingFrameCounter -= 6 * 8;
            if (dyingFrameCounter / 8 == imageList.size()) {
                this.setAlive(false);
//                System.out.println("hostage die");
            }
        }

        this.setWidth(icon.getIconWidth());
        this.setHeight(icon.getIconHeight());

        // 更新图片
        this.setImageIcon(icon);
    }

    @Override
    public void die(long gameTime) {
        status = "die";
        isDying = true;
    }


}
