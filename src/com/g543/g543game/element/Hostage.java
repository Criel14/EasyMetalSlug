package com.g543.g543game.element;

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
        this.setWidth(icon.getIconWidth());
        this.setHeight(icon.getIconHeight());

        // 更新图片
        this.setImageIcon(icon);
    }
}
