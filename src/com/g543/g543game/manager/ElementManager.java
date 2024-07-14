package com.g543.g543game.manager;

import com.g543.g543game.element.ElementObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 元素管理器：存储元素、提供方法给View和Controller获取数据
// 单例模式
public class ElementManager {
    // 用集合存放数据
    // 用MAP获取，用枚举类型做key
    private Map<GameElement, List<ElementObj>> gameElements;

    // 编写单例模式
    private static ElementManager EM = null;

    // 私有化构造方法
    private ElementManager() {
        // 构造方法不能被重写，子类可以重写init方法
        init();
    }

    // 初始化/实例化
    public void init() {
        gameElements = new HashMap<>();
        // 将element集合放入map中
        for (GameElement ge : GameElement.values()) {
            gameElements.put(ge, new ArrayList<>());
        }
    }

    // 获取Map
    public Map<GameElement, List<ElementObj>> getGameElements() {
        return gameElements;
    }

    // 添加元素(类型、内容)
    public void addElement(GameElement gameElement, ElementObj elementObj) {
        gameElements.get(gameElement).add(elementObj);
    }

    // 取出元素(类型)
    public List<ElementObj> getElement(GameElement gameElement) {
        return gameElements.get(gameElement);
    }

    // 加线程锁的get方法，获取元素管理器ElementManager
    public static synchronized ElementManager getManager() {
        // 使用的时候才加载实例：“饱汉模式”
        // 若“饿汉模式”，则在静态代码块中new
        if (EM == null) {
            EM = new ElementManager();
        }
        return EM;
    }

    public static void removeAll(){
        for( GameElement ge : GameElement.values()){
            EM.getGameElements().get(ge).clear();
        }
    }

}
