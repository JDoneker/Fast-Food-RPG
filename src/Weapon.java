import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Weapon {
    private int x,y,width,height,damage,durability,attackspeed,dx,dy;
    private ImageIcon pic;
    public Weapon(int x1, int y1, int w1, int h1, int damage1, int durability1, int attackspeed1, ImageIcon p){
        x =x1;
        y=y1;
        width = w1;
        height = w1;
        damage = damage1;
        durability = durability1;
        attackspeed = attackspeed1;
        dx = 0;
        dy = 0;
        pic = p;
    }
    public void drawChar(Graphics g2d, int x2, int y2, double scaleFactor){
        g2d.drawImage(pic.getImage(),x2+(int)(scaleFactor*50),y2+(int)(scaleFactor*50),(int)(scaleFactor*width),(int)(scaleFactor*height),null);
    }
   
}
