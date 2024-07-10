package com.g543.g543game.show;

import com.g543.g543game.element.ElementObj;
import com.g543.g543game.element.Player;
import com.g543.g543game.manager.ElementManager;
import com.g543.g543game.manager.GameElement;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

// 主要面板：显示元素，同时界面刷新
public class GameMainJPanel extends JPanel implements Runnable{
    // 元素管理器
    private ElementManager elementManager;

    // 构造方法
    public GameMainJPanel() {
        init();
    }

    // 初始化
    private void init() {
        // 获取ElementManager对象
        elementManager = ElementManager.getManager();
    }

    // 绘制界面
    // 这个方法只执行一次，实时刷新需要使用多线程
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Map<GameElement, List<ElementObj>> all = new HashMap<>(elementManager.getGameElements());
        Set<GameElement> set = all.keySet();
        for (GameElement gameElement : set) {
            List<ElementObj> list = new ArrayList<>(all.get(gameElement));
            for (ElementObj elementObj : list) {
                elementObj.showElement(g);
            }
        }
    }


    // 多线程Runnable接口的方法实现
    @Override
    public void run() {
        while (true) {
            // 不断刷新
            this.repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
