package com.g543.g543game.controller;

import com.g543.g543game.element.ElementObj;
import com.g543.g543game.manager.ElementManager;
import com.g543.g543game.manager.GameElement;
import com.g543.g543game.manager.GameLoader;

import java.util.*;

// 游戏主线程：控制游戏加载、判定、地图切换、资源释放、重新读取等
public class GameThread extends Thread {
    private ElementManager elementManager;

    // 游戏时间：实际上是while循环计数，可以看成帧
    private long gameTime = 0L;

    public GameThread() {
        elementManager = ElementManager.getManager();
    }

    @Override
    public void run() {
        while (true) { // true可以换成变量来控制
            // 加载
            gameLoad();
            // 进行
            gameRun();
            // 结束当前场景，释放资源
            gameEnd();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 游戏加载
    private void gameLoad() {
        // 加载图片
        GameLoader.loadImage();
        // 加载玩家
        GameLoader.loadPlayer();
        // 加载敌人
        GameLoader.loadEnemy();

    }

    // 游戏进行
    private void gameRun() {
        while (true) {
            // 获取所有元素
            Map<GameElement, List<ElementObj>> all = new HashMap<>(elementManager.getGameElements());
            // 执行模板模式
            elementExecuteModel(all,gameTime);
            // 执行碰撞检测
            elementCollision(all);
            // 控制时间流逝
            gameTime++;
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 结束场景、资源回收
    private void gameEnd() {

    }

    // 对map中的元素执行executeModel
    private void elementExecuteModel(Map<GameElement, List<ElementObj>> all, long gameTime) {
        // 对所有元素执行模板模式
        Set<GameElement> set = all.keySet();
        for (GameElement gameElement : set) {
            List<ElementObj> elementList = new ArrayList<>(all.get(gameElement));
            Iterator<ElementObj> iterator = elementList.iterator();
            // 用迭代器，方便做删除操作
            while (iterator.hasNext()) {
                ElementObj elementObj = iterator.next();
                // 判断元素的生存状态
                // 如果已经死亡，则执行destroy方法，并移除该元素
                if (!elementObj.isAlive()) {
                    elementObj.destroy(gameTime);
                    iterator.remove();
                } else {
                    elementObj.executeModel(gameTime);
                }
            }
        }
    }


    // 碰撞检测方法，传入两个元素列表
    public void checkCollision(List<ElementObj> listA, List<ElementObj> listB) {
        for (ElementObj elementA : listA) {
            for (ElementObj elementB : listB) {
                if (elementA.isCollided(elementB)) {
                    // 用instanceof 判断类型后执行对应逻辑

                    break;
                }
            }
        }
    }

    // 对所有元素中必要的元素执行碰撞检测
    private void elementCollision(Map<GameElement, List<ElementObj>> all) {
        // 获取元素集合
        List<ElementObj> player = all.get(GameElement.PLAYER);

        // 执行碰撞检测
        checkCollision(player, player); // 用法示例

    }

}
