package com.g543.g543game.show;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 关卡选择页面
// 关卡选择页面
class LevelSelectionPage extends JFrame {
    public LevelSelectionPage() {
        // 设置窗口标题
        setTitle("G543Game - Selection Page");
        // 设置窗口大小
        setSize(1280, 720);
        // 设置窗口居中
        setLocationRelativeTo(null);
        // 设置关闭操作
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置布局管理器
        setLayout(new GridBagLayout());

        // 创建自定义背景面板
        BackgroundPanel backgroundPanel = new BackgroundPanel("image/background/levelSelection.jpg");
        backgroundPanel.setLayout(new GridBagLayout());
        setContentPane(backgroundPanel);

        // 使用 GridBagConstraints 布局按钮
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.insets = new Insets(30, 30, 30, 30); // 设置内边距

        // 创建关卡按钮
        for (int i = 1; i <= 4; i++) {
            JButton levelButton = new JButton();
            ImageIcon icon = new ImageIcon("image/button/level" + i + ".png"); // 创建图标
            levelButton.setIcon(icon); // 设置按钮的图标
            Dimension size = new Dimension(150, 150);
            levelButton.setPreferredSize(size); // 调整按钮大小为正方形
            levelButton.setMinimumSize(size);   // 设置最小尺寸
            levelButton.setMaximumSize(size);   // 设置最大尺寸
            levelButton.setOpaque(false); // 设置按钮为透明
            levelButton.setContentAreaFilled(false); // 设置按钮内容区域不填充
            levelButton.setBorderPainted(false);
            levelButton.addActionListener(new LevelButtonActionListener(i));
            gbc.gridx = i - 1; // 设置按钮的横坐标
            add(levelButton, gbc);
        }

        // 显示窗口
        setVisible(true);
    }

    // 关卡按钮的事件监听器
    private class LevelButtonActionListener implements ActionListener {
        private final int level;

        public LevelButtonActionListener(int level) {
            this.level = level;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // 在此处添加进入相应关卡的逻辑
            System.out.println("进入关卡 " + level);
            GameJFrame gameJFrame = GameJFrame.getInstance();
            gameJFrame.start(); // 启动游戏主窗口
            LevelSelectionPage.this.dispose();
        }
    }
}

