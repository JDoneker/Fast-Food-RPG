import java.awt.Color;
import java.awt.Graphics;
public class Projectile {
    double x,y,dx,dy;
    int[] projectileCoord;
    int width,height;
    private Color c;
    public Projectile(int x1,int y1,int x2,int y2,int w, int h, double s, Color c1){        
        double speed = s;
        x = x1;
        y = y1;
        width = w;
        height = h;
        c = c1;
        double diffx = x2-x1;
        double diffy = y2-y1;
        double theta = Math.atan(diffy/diffx);
        if(x2>x1){
            dx = speed * Math.cos(theta);
            dy = speed * Math.sin(theta);
        }
        if(x1>x2){
            dx = -speed * Math.cos(theta);
            dy = -speed * Math.sin(theta);
        }
        if(dx == 0||dy==0){
            dx = Math.random();
            dy = Math.random();
        }
        projectileCoord = new int[] {-1,-1};
    }
    public boolean collidesWith(int[] coord) {
        return coord[0]==projectileCoord[0] && coord[1] == projectileCoord[1];
    }
    public int getX() {
        return projectileCoord[0];
    }
    public int getY() {
        return projectileCoord[1];
    }
    public void move() {
        x+=dx;
        y+=dy;
    }
    public void draw(Graphics g2d) {
        g2d.setColor(c);
        g2d.fillRect((int)(x-width/2), (int)(y-width/2),width,height);
    }
    public void updateCoord(int[] playerCoord) {
        int tempx = (int)x +25;
        int tempy = (int)y +25;
        int indexx = Math.floorDiv(tempx, 50)-8;
        int indexy = Math.floorDiv(tempy, 50)-6;
        projectileCoord = new int[]{playerCoord[0]+indexx,playerCoord[1]+indexy};
    }
    public void setX(int xmove) {x+=xmove;}
    public void setY(int ymove) {y+=ymove;}
    public int[] getCoord() {return projectileCoord;}


}
