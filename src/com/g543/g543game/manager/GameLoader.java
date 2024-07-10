package com.g543.g543game.manager;

import com.g543.g543game.element.ElementObj;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

// 资源加载器
public class GameLoader {
    // 元素管理器
    private static ElementManager elementManager = ElementManager.getManager();
    // 图片集合
    public static Map<String, ImageIcon> imageMap = new HashMap<>();
    // 读取配置文件
    private static Properties properties = new Properties();
    // 对象集合
    private static Map<String, Class<?>> objectMap = new HashMap<>();

    // 加载对象
    public static void loadObject() {
        // 得到文件路径，由于maven的特性，这里路径只需要从resources目录开始就可以了
        String propertiesUrl = "properties/ObjectData.properties";
        // 用io流来获取文件对象 得到类加载器
        ClassLoader classLoader = GameLoader.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(propertiesUrl);
        properties.clear();
        try {
            // 读取文件
            properties.load(inputStream);
            // 获取键集合
            Set<Object> keySet = properties.keySet();
            for (Object o : keySet) {
                // 根据键获取值
                String classUrl = properties.getProperty(o.toString());
                // 获取类
                Class<?> forName = Class.forName(classUrl);
                objectMap.put(o.toString(), forName);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 通过键获取对象
    public static ElementObj getObject(String objName) {
        try {
            Class<?> element = objectMap.get(objName);
            Object newInstance = element.newInstance();
            if (newInstance instanceof ElementObj) {
                return (ElementObj) newInstance;
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 加载玩家
    public static void loadPlayer() {
        loadObject();
        String playData = "100,220,test_image";
        ElementObj obj = getObject("player");
        ElementObj player = obj.createElement(playData);
        elementManager.addElement(GameElement.PLAYER, player);
    }

    // 加载敌人
    public static void loadEnemy() {
        loadObject();
        String enemyData = "100,220,enemyRPG";
        ElementObj obj = getObject("enemy");
        ElementObj enemy = obj.createElement(enemyData);
        elementManager.addElement(GameElement.ENEMY, enemy);
    }


    //加载地图
    public static void loadBackground() {
        String BackgroundData = "0,0,background";
        ElementObj obj = getObject("backgroundMap");
        ElementObj background = obj.createElement(BackgroundData);
        elementManager.addElement(GameElement.BACKGROUND_MAP, background);
    }

    // 加载图片方法
    public static void loadImage() {
        String imageProperties = "properties/ImageData.properties";
        ClassLoader classLoader = GameLoader.class.getClassLoader();
        InputStream is = classLoader.getResourceAsStream(imageProperties);
        properties.clear();
        try {
            properties.load(is);
            Set<Object> set = properties.keySet();
            for (Object o : set) {
                String url = properties.getProperty(o.toString());
                imageMap.put(o.toString(), new ImageIcon(url));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
