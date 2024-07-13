package com.g543.g543game.show;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 启动页面窗体
public class GameStartFrame extends JFrame {

    // 构造方法
    public GameStartFrame() {
        init();
    }

    // 初始化方法
    private void init() {
        // 设置窗口大小
        this.setSize(580, 818);
        // 设置窗口关闭时自动关闭
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口标题
        this.setTitle("G543Game - Start Page");
        // 设置窗口居中
        this.setLocationRelativeTo(null);
        // 设置布局为null，方便自定义布局
        this.setLayout(null);

        // 创建一个背景面板
        JPanel bgPanel = new BackgroundPanel("image/background/cover.jpg");
        bgPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        // 设置背景面板布局为null
        bgPanel.setLayout(null);
        this.add(bgPanel);

        // 创建开始游戏按钮
        JButton startButton = new JButton("START GAME");
        startButton.setFont(new Font("Arial", Font.BOLD, 32));
        // 设置按钮位置和大小
        startButton.setBounds(150, 510, 280, 48); // 中心位置
        bgPanel.add(startButton);

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

        // 显示启动页面
        this.setVisible(true);
    }
}