
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage; 
import java.awt.event.*; 
import java.util.ArrayList;

public class Player {
    private int x,y,width,height,speed,health,resistance,dx,dy;
    private double bulletSpeed;
    private ImageIcon pic;
    private Weapon weapon;
    public Player(){
        x =0;
        y =0;
        width = 0;
        height = 0;
        speed = 0;
        health = 0;
        bulletSpeed = 0;
        resistance = 0;
        dx = 0;
        dy = 0;
        pic = new ImageIcon();
    }
    public Player(int x1, int y1, int w1, int h1, int speed1, int health1, int bulletSpeed1, int resistance1, ImageIcon p){
        x = x1;
        y = y1;
        width = w1;
        height = w1;
        speed = speed1;
        health = health1;
        bulletSpeed = bulletSpeed1;
        resistance = resistance1;
        dx = 0;
        dy = 0;
        pic = p;
    }
    public Player(int x1, int y1, int w1, int h1, int speed1, int health1, int bulletSpeed1, int resistance1, Weapon weapon1, ImageIcon p){
        x =x1;
        y=y1;
        width = w1;
        height = w1;
        speed = speed1;
        health = health1;
        bulletSpeed = bulletSpeed1;
        resistance = resistance1;
        dx = 0;
        dy = 0;
        weapon = weapon1;
        pic = p;
    }
    public int getHealth() {
        return health;
    }
    public void drawChar(Graphics g2d,double scaleFactor){
        g2d.drawImage(pic.getImage(),x,y,(int)(scaleFactor*width),(int)(scaleFactor*height),null);
    }
    public void drawCharAbsolute(Graphics g2d, int absx, int absy) {
        g2d.drawImage(pic.getImage(), absx, absy,width,height, null);
    }
    /* 
    public void drawCharOffset(Graphics g2d, int playerX,int playerY){
        g2d.drawImage(pic.getImage(),x+xoff,y+yoff,width,height,null);
    }
    */
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
    public int getSpeed() {
        return speed;
    }
    public double getBulletSpeed() {
        return bulletSpeed;
    }
    public int getResistance() {
       return resistance;
    }
    public Object getWeapon() {
        return weapon;
    }
    public void moveCenter(int width2, int height2,double scaleFactor) {
        x = (int)(width2/2-scaleFactor*width/2);
        y = (int)(height2/2-scaleFactor*height/2);
    }
    public void drawWeapon(Graphics g2d,double scaleFactor) {
        weapon.drawChar(g2d,x,y,scaleFactor);
    }
    public void setHealth(int i) {
        health = i;
    }
}