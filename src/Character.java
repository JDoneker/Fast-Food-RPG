
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage; 
import java.awt.event.*; 
import java.util.ArrayList;

public class Character {
    private int x,y,width,height,speed,health,damage,stamina,dx,dy;
    private ImageIcon pic;
    private Weapon weapon;
    public Character(){
        x =0;
        y=0;
        width = 0;
        height = 0;
        speed = 0;
        health = 0;
        damage = 0;
        stamina = 0;
        dx = 0;
        dy = 0;
        pic = new ImageIcon();
    }
    public Character(int x1, int y1, int w1, int h1, int speed1, int health1, int damage1, int stamina1, ImageIcon p){
        x = x1;
        y = y1;
        width = w1;
        height = w1;
        speed = speed1;
        health = health1;
        damage = damage1;
        stamina = stamina1;
        dx = 0;
        dy = 0;
        pic = p;
    }
    public Character(int x1, int y1, int w1, int h1, int speed1, int health1, int damage1, int stamina1, Weapon weapon1, ImageIcon p){
        x =x1;
        y=y1;
        width = w1;
        height = w1;
        speed = speed1;
        health = health1;
        damage = damage1;
        stamina = stamina1;
        dx = 0;
        dy = 0;
        weapon = weapon1;
        pic = p;
    }
    public int getHealth() {
        return health;
    }
    public void drawChar(Graphics g2d){
        g2d.drawImage(pic.getImage(),x,y,width,height,null);
    }
    public void drawCharOffset(Graphics g2d, int xoff,int yoff){
        g2d.drawImage(pic.getImage(),x+xoff,y+yoff,width,height,null);
    }
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
    public int getDamage() {
        return damage;
    }
    public int getStamina() {
       return stamina;
    }
    public Object getWeapon() {
        return weapon;
    }
    public void moveCenter(int width2, int height2) {
        x = width2/2-width/2;
        y = height2/2-width/2;
    }
    public void drawWeapon(Graphics g2d) {
        weapon.drawChar(g2d,x,y);
    }
    public void setHealth(int i) {
        health = i;
    }
}