package com.g543.g543game.show;

import javax.swing.*;
import java.awt.event.*;

// 游戏窗体，使用swing awt
public class GameJFrame extends JFrame {
    // 窗体大小
    public static int GAME_WIDTH = 1280;
    public static int GAME_HEIGHT = 700;

    // 单例模式的静态实例
    private static GameJFrame instance = null;

    // 返回单例的静态方法
    public static GameJFrame getInstance() {
        if (instance == null) {
            instance = new GameJFrame();
        }
        return instance;
    }

    // 正在显示的面板
    private JPanel jPanel = null;

    // 输入监听
    private KeyListener keyListener = null;
    private MouseMotionListener mouseMotionListener = null;
    private MouseListener mouseListener = null;

    // 游戏主线程
    private Thread gameThread = null;

    // 构造方法
    public GameJFrame() {
        init();
    }

    // 初始化
    public void init() {
        // 设置窗口大小
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        // 设置窗口关闭时自动关闭
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口标题
        this.setTitle("G543Game");
        // 设置窗口居中
        this.setLocationRelativeTo(null);
    }

    // 用于set注入：将配置文件中的数据复制为类的属性（跟Spring里的ioc差不多）
    public void setjPanel(JPanel jPanel) {
        this.jPanel = jPanel;
    }

    public void setKeyListener(KeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
        this.mouseMotionListener = mouseMotionListener;
    }

    public void setMouseListener(MouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    public void setGameThread(Thread gameThread) {
        this.gameThread = gameThread;
    }

    // 启动
    public void start() {
        if (jPanel != null) {
            this.add(jPanel);
        }
        if (keyListener != null) {
            this.addKeyListener(keyListener);
        }
        if (mouseMotionListener != null) {
            this.addMouseMotionListener(mouseMotionListener);
        }
        if (mouseListener != null) {
            this.addMouseListener(mouseListener);
        }
        if (gameThread != null) {
            gameThread.start();
        }

        // 界面刷新
        // 判断类型，用于后面线程里的强转
        if (this.jPanel instanceof Runnable) {
            new Thread((Runnable) this.jPanel).start();
        }


        // 界面显示
        this.setVisible(true);

    }

    public void removeJFrame(){
        dispose();
    }

}
