package com.g543.g543game.element;

import com.g543.g543game.manager.GameLoader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlaneBulletDestroyEffect extends ElementObj{

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getImageIcon().getImage(),this.getX()+20,this.getY()+20,this.getWidth(), this.getHeight(),null);
    }


    @Override
    public ElementObj createElement(String str){
        String[] strs= str.split(",");
        this.setX(Integer.parseInt(strs[0]));
        this.setY(Integer.parseInt(strs[1]));
        this.setImageIcon(new ImageIcon("image/boom/bomb.bang0.png"));
        return this;
    }


    private int i = 0;
    private long localTime = 0;
    @Override
    protected void updateImage(long gameTime) {
        if(gameTime > localTime + 3){
            localTime = gameTime;

            String url = String.valueOf(GameLoader.imageMap.get("boom"));
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

            //提取文件名中的数字部分,并按照数字的大小进行升序排序。
            try {
                imageList = Files.list(dir)
                        .filter(path -> path.toString().endsWith(".png"))
                        .sorted((path1, path2) -> {
                            String fileName1 = path1.getFileName().toString();
                            String fileName2 = path2.getFileName().toString();
                            return extractNumericPart(fileName1) - extractNumericPart(fileName2);
                        })
                        .collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 更新图片
            ImageIcon icon = new ImageIcon(imageList.get(i++).toString());
            this.setWidth(icon.getIconWidth());
            this.setHeight(icon.getIconHeight());
            // 更新图片
            this.setImageIcon(icon);
            if(i == imageList.size() - 1){
                this.setAlive(false);
            }
        }

    }

    private static int extractNumericPart(String fileName) {
        // 移除"bomb_bang"前缀和".png"扩展名
        String numericPart = fileName.replaceAll("^bomb_bang|.png$", "");

        // 如果numericPart不为空,则返回数字值
        if (!numericPart.isEmpty()) {
            return Integer.parseInt(numericPart);
        }

        // 如果numericPart为空,返回0(或其他默认值)
        return 0;
    }
}
