package com.g543.g543game.controller;

import com.g543.g543game.element.ElementObj;
import com.g543.g543game.manager.ElementManager;
import com.g543.g543game.manager.GameElement;

import java.awt.event.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 监听类：监听用户的操作
public class GameListener implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    // 管理器对象
    private ElementManager elementManager = ElementManager.getManager();

    // 存放按键（第一次按下的时候记录，按完删掉）
    private Set<Integer> pressedKeySet = new HashSet<>();


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // 如果按下了已经按下的按键，中断输入（相当于优化了长按）
        int key = e.getKeyCode();
        if (pressedKeySet.contains(key)) {
            return;
        } else {
            pressedKeySet.add(key);
        }
        // 获取玩家集合
        List<ElementObj> players = elementManager.getElement(GameElement.PLAYER);
        for (ElementObj player : players) {
            player.keyboardListener(true, e.getKeyCode());// 测试
        }
        List<ElementObj> maps = elementManager.getElement(GameElement.BACKGROUND_MAP);
        for (ElementObj map : maps) {
            map.keyboardListener(true, e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // 如果松开了的按键之前没按过，则不执行松开的效果（可能是防止bug）
        if (!pressedKeySet.contains(e.getKeyCode())) {
            return;
        }
        // 松开时移除按键集合
        pressedKeySet.remove(e.getKeyCode());
        // 获取玩家集合
        List<ElementObj> players = elementManager.getElement(GameElement.PLAYER);
        for (ElementObj player : players) {
            player.keyboardListener(false, e.getKeyCode());// 测试
        }
        List<ElementObj> maps = elementManager.getElement(GameElement.BACKGROUND_MAP);
        for (ElementObj map : maps) {
            map.keyboardListener(false, e.getKeyCode());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }
}
