package com.g543.g543game.element;

import com.g543.g543game.manager.ElementManager;
import com.g543.g543game.manager.GameElement;
import com.g543.g543game.manager.GameLoader;
import com.g543.g543game.util.KeyboardCode;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Player extends ElementObj {

    // 是否在移动
    private boolean isMoving = false;
    // 是否向右移动（true为向右，false为向左）
    private boolean isMovingRight = true;

    // 移动速度
    private int moveSpeed = 3;

    // 跳跃相关变量
    private boolean isJumping = false;
    private int jumpSpeed = 10;
    private int gravity = 1;
    private int jumpHeight = 20;
    private int initialY;

    // 移动方向
    private String direction = "right";

    // 状态（跑、跳、站立等）
    private String upperStatus = "attackhandgun";
    private String lowerStatus = "run";

    // 是否在发射
    private boolean isShooting = false;

    // 是否可以发射
    private boolean canShoot = true;

    // 子弹发射间隔
    private int bulletInterval = 400;

    // 射击动画帧计数器
    private int shootingFrameCounter = 0;

    // 构造方法
    public Player() {
    }

    public Player(int x, int y, int width, int height, ImageIcon imageIcon) {
        super(x, y, width, height, imageIcon);
    }

    // data格式：X坐标，Y坐标，图片名称（对应配置文件的图片）
    @Override
    public ElementObj createElement(String data) {
        String[] split = data.split(",");
        this.setX(Integer.parseInt(split[0]));
        this.setY(Integer.parseInt(split[1]));
        ImageIcon icon = GameLoader.imageMap.get(split[2]);
        this.setWidth(100);
        this.setHeight(93);
        this.setImageIcon(icon);
        initialY = this.getY(); // 初始化初始Y坐标
        return this;
    }

    // 重写显示方法
    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getImageIcon().getImage(), (this.getX() - getMap().newX) * 2, this.getY(), this.getWidth(), this.getHeight(), null);
    }

    @Override
    public void updateImage(long gameTime) {
        String imgLower = direction + "_lower_" + lowerStatus;
        String imgUpper = direction + "_upper_" + upperStatus;
        String url1 = String.valueOf(GameLoader.imageMap.get(imgLower));
        String url2 = String.valueOf(GameLoader.imageMap.get(imgUpper));

        Path dir1 = Paths.get(url1);
        Path dir2 = Paths.get(url2);
        //存储图片文件的路径
        List<Path> imgLowerList = new ArrayList<>();
        List<Path> imgUpperList = new ArrayList<>();
        try {
            Files.walk(dir1)
                    .filter(Files::isRegularFile)
                    .forEach(imgLowerList::add);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.walk(dir2)
                    .filter(Files::isRegularFile)
                    .forEach(imgUpperList::add);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int dx;
        if ("left".equals(direction)) dx = 45;
        else dx = 0;

        // 根据是否在发射状态选择上半身图片
        Path upperImagePath;
        if (isShooting) {
            upperImagePath = imgUpperList.get(shootingFrameCounter / 5 % imgUpperList.size());
            shootingFrameCounter++;
        } else {
            upperImagePath = imgUpperList.get(0); // 保持第一张图片
            shootingFrameCounter = 0; // 重置射击动画帧计数器
        }

        ImageIcon icon = createCombinedIcon(upperImagePath,
                imgLowerList.get((int) (gameTime / 5 % imgLowerList.size())), dx);
        this.setImageIcon(icon);
    }

    //合并两张图片
    public static ImageIcon createCombinedIcon(Path pathOne, Path pathTwo, int dx) {
        try {
            // 读取第一张图片
            BufferedImage imageOne = ImageIO.read(pathOne.toFile());

            // 读取第二张图片
            BufferedImage imageTwo = ImageIO.read(pathTwo.toFile());

            // 创建一个新的 BufferedImage，高度是两张图片的高度之和，宽度是两张图片中较大的宽度
            int width = Math.max(imageOne.getWidth(), imageTwo.getWidth());
            int height = imageOne.getHeight() + imageTwo.getHeight();
            BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            // 将第一张图片画到 combined 上
            combined.getGraphics().drawImage(imageOne, 0, 0, null);
            // 将第二张图片画到 combined 上，y 坐标为第一张图片的高度
            combined.getGraphics().drawImage(imageTwo, dx, imageOne.getHeight() - 10, null);

            // 创建一个新的 ImageIcon
            return new ImageIcon(combined);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void keyboardListener(boolean isPressed, int keyCode) {
        if (isPressed) {
            switch (keyCode) {
                case KeyboardCode.A:
                    this.isMoving = true;
                    this.isMovingRight = false;
                    this.direction = "left";
                    break;
                case KeyboardCode.D:
                    this.isMoving = true;
                    this.isMovingRight = true;
                    this.direction = "right";
                    break;
                case KeyboardCode.SPACE:
                    if (!isJumping) {
                        isJumping = true;
                        lowerStatus = "jump"; // 更新跳跃状态
                    }
                    break;
                case KeyboardCode.J:
                    if (!isShooting && canShoot) {
                        isShooting = true;
                        shootingFrameCounter = 0; // 重置射击动画帧计数器
                        Timer timer = new Timer(bulletInterval, e -> {
                            isShooting = false;
                            canShoot = true; // 重置射击状态
                        });
                        canShoot = false; // 禁止连续射击
                        timer.setRepeats(false); // 只执行一次
                        timer.start();
                        addBullet(); // 发射子弹
                    }
                    break;
                case KeyboardCode.K:
                    ElementManager.getManager().addElement(GameElement.PLANE,new Plane().createElement(""));
                    break;
                default:
                    break;
            }
        } else {
            isMoving = false;
        }
    }

    // 移动方法
    @Override
    public void move(long gameTime) {
        if (this.isMoving) {
            BackgroundMap map = getMap();
            if (this.isMovingRight && this.getX() <= 1400) {
                if (this.getX() - map.newX >= 550)
                {
                    map.newX += moveSpeed;
                }
                this.setX(this.getX() + moveSpeed);
            } else if (!this.isMovingRight && this.getX() >= 50) {
                if (this.getX() - map.newX <= 25){
                    map.newX -= moveSpeed;
                }
                this.setX(this.getX() - moveSpeed);
            }
        }

        // 跳跃逻辑
        if (isJumping) {
            this.setY(this.getY() - jumpSpeed);
            jumpSpeed -= gravity;
            if (this.getY() >= initialY) {
                this.setY(initialY);
                isJumping = false;
                jumpSpeed = 10; // 重置跳跃速度
                lowerStatus = "run"; // 恢复跑步状态
            }
        }
    }

    // 添加道具
    @Override
    protected void addProp(long gameTime) {
        // 添加子弹
        if (isShooting && canShoot) {
            // 发射子弹逻辑
            addBullet();
        }
    }

    private void addBullet() {
        ElementObj obj = GameLoader.getObject("gunBullet");
        int x = this.getX();
        int y = this.getY();
        int isMovingRight = 0;
        if (this.isMovingRight) isMovingRight = 1;
        if (isMovingRight == 1) x += 50;
        y += 15;
        String data = Integer.toString(x) + "," + Integer.toString(y) + ",gunBullet," + Integer.toString(isMovingRight);

        obj.createElement(data);
        ElementManager.getManager().addElement(GameElement.PLAYER_BULLET, obj);
    }
}
