package com.g543.g543game.game;

import com.g543.g543game.controller.GameListener;
import com.g543.g543game.controller.GameThread;
import com.g543.g543game.show.GameJFrame;
import com.g543.g543game.show.GameMainJPanel;
import com.g543.g543game.show.GameStartFrame;

// 程序入口
public class GameStart {
    public static <GameFrame> void main(String[] args) {

        GameJFrame gameJFrame = GameJFrame.getInstance();

        // 实例化主线程写在了LevelSelectionPage里
        // 实例化面板，并注入GameJFrame
        GameMainJPanel gameMainJPanel = new GameMainJPanel();
        gameJFrame.setjPanel(gameMainJPanel);

        // 实例化监听，并注入GameJFrame
        GameListener gameListener = new GameListener();
        gameJFrame.setKeyListener(gameListener);
        // 进入启动页
        new GameStartFrame();
    }
}
