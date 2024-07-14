package com.g543.g543game.show;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameEnd extends JFrame {
    public GameEnd(String str) {
        init(str);
    }

    // 初始化方法
    private void init(String str) {
        // 设置窗口大小
        this.setSize(800, 900);
        // 设置窗口关闭时自动关闭
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口标题
        this.setTitle("G543Game - Start Page");
        // 设置窗口居中
        this.setLocationRelativeTo(null);
        // 设置布局为null，方便自定义布局
        this.setLayout(null);

        // 创建一个背景面板
        JPanel bgPanel = new BackgroundPanel("image/playersImg/"+ str + ".png");
        bgPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        // 设置背景面板布局为null
        bgPanel.setLayout(null);
        this.add(bgPanel);

        // 创建开始游戏按钮
        JButton startButton = new JButton("BACK TO MENU");
        startButton.setFont(new Font("Arial", Font.BOLD, 32));
        // 设置按钮位置和大小
        startButton.setBounds(230, 650, 310, 48); // 中心位置
        JButton endButton = new JButton("EXIT GAME");
        endButton.setFont(new Font("Arial", Font.BOLD, 32));
        // 设置按钮位置和大小
        endButton.setBounds(230, 750, 310, 48); // 中心位置
        bgPanel.add(startButton);
        bgPanel.add(endButton);

        // 添加按钮点击事件
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取并启动游戏主窗口
//                GameJFrame gameJFrame = GameJFrame.getInstance();
//                gameJFrame.start(); // 启动游戏主窗口
//                GameStartFrame.this.dispose(); // 关闭启动页面
                new LevelSelectionPage();
                dispose();
            }
        });


        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // 显示启动页面
        this.setVisible(true);
    }
}
