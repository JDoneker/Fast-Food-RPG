import java.awt.Color;
import java.awt.Graphics;

public class Projectile {
    double x,y,theta,dx,dy;
    int width,height;
    private Color c;
    public Projectile(int x1,int y1,int w, int h, double s, Color c1, int x2,int y2, int xoffset, int yoffset ){
        double speed = s;
        x = x1;
        y = y1;
        width = w;
        height = h;
        c = c1;
        double diffx = x2-x1;
        double diffy = y2-y1;
        theta = Math.atan(diffy/diffx);
        if(x2>x1){
            dx = speed * Math.cos(theta);
            dy = speed * Math.sin(theta);
        }
        if(x1>x2){
            dx = -speed * Math.cos(theta);
            dy = -speed * Math.sin(theta);
        }
        x-=xoffset;
        y-=yoffset;
    }
    public void drawProjectile(Graphics g2d, int xoffset, int yoffset){
        g2d.setColor(c);
        g2d.fillRect((int)x+xoffset,(int)y+yoffset,width,height);
        x+=dx;
        y+=dy;
    }
    public boolean collidesWith(Character character, int xoffset, int yoffset) {
        int cx;
        int cy;
        if(character instanceof Enemy){
            cx = character.getX() + xoffset;
            cy = character.getY() + yoffset;
        }else{
            cx = character.getX();
            cy = character.getY();
        }
        double px = x + xoffset;
        double py = y + yoffset;
        int cw = character.getWidth();
        int ch = character.getHeight();
        boolean temp = (px< cx+cw && px+width>cx &&py<cy+ch&&py+height>cy);
        return temp;
    }


}
