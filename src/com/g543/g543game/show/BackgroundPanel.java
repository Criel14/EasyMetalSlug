package com.g543.g543game.show;

import javax.swing.*;
import java.awt.*;

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        // 设置背景图片路径
        backgroundImage = new ImageIcon(imagePath).getImage();
//        System.out.println(imagePath);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 绘制背景图片
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
