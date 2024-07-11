package com.g543.g543game.element;


import javax.swing.*;
import java.awt.*;

// 所有element的基类
public abstract class ElementObj {
    // 元素左上角的x坐标（横，从左往右）
    private int x;
    // 元素左上角的y坐标（纵，从上往下）
    private int y;
    // 元素的宽度
    private int width;
    // 元素的高度
    private int height;
    // 图片
    private ImageIcon imageIcon;
    // 生存状态
    private boolean isAlive = true;
    // 血量（默认值为100）
    private int hp = 100;
    //地图类使用，根据玩家在地图上的相对位置决定是否移动地图，例如地图左右侧的1/3是允许地图移动的范围
    public Boolean isChange = false;
    // 地图类专用，0为地图不动，1为地图向左运动，2为地图向右运动
    public int changeWay = 0;

    // getter和setter
    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    //getter和setter
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }

    public int getChangeWay() {
        return changeWay;
    }

    public void setChangeWay(int changeWay) {
        this.changeWay = changeWay;
    }

    public Boolean getChange() {
        return isChange;
    }

    public void setChange(Boolean change) {
        isChange = change;
    }

    // 构造方法
    public ElementObj() {
    }

    public ElementObj(int x, int y, int width, int height, ImageIcon imageIcon) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imageIcon = imageIcon;
    }

    // 显示元素(参数：画笔——用于绘制)
    public abstract void showElement(Graphics g);

    // 运行模板
    // 模板模式：定义对象执行方法的先后顺序
    public final void executeModel(long gameTime) {
        // 更新图像（更换装备）
        updateImage(gameTime);
        // 移动
        move(gameTime);
        // 添加道具
        addProp(gameTime);
        // 攻击
        attack(gameTime);
        // 站立
        stand(gameTime);
        // 状态切换
        stateSwitch(gameTime);
    }

    // 这些没有abstract的方法， 子类根据需要选择性继承
    // 更新图像（更换装备）
    protected void updateImage(long gameTime) {
    }

    // 移动
    protected void move(long gameTime) {
    }

    // 添加道具
    protected void addProp(long gameTime) {
    }

    //攻击
    protected void attack(long gameTime) {
    }

    // 站立
    protected void stand(long gameTime) {
    }

    // 状态切换
    protected void stateSwitch(long gameTime) {
    }

    // 销毁/死亡
    // 死亡的动画、销毁效果之列的
    public void destroy(long gameTime) {

    }

    // 创建元素
    // 字符串形式为 x,y,direction
    public ElementObj createElement(String data) {
        return null;
    }

    // 键盘监听
    public void keyboardListener(boolean isPressed, int keyCode) {
    }


    // 获取元素的碰撞体积——矩形对象
    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);
    }

    // 碰撞检测（检测对象）
    // 返回true：碰撞；返回false：未碰撞
    public boolean isCollided(ElementObj elementObj) {
        // 返回两个矩形是否相交
        return getRectangle().intersects(elementObj.getRectangle());
    }

    // 受到攻击，子类可以重写逻辑
    public void attacked(int damage) {
        this.hp -= damage;
        // 血量为0则设置生存状态为false
        if (this.hp <= 0) {
            this.setAlive(false);
        }
    }


}
