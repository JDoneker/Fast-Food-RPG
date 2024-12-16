import java.awt.Color;

public class PlayerProjectile extends Projectile{
    
    public PlayerProjectile(int x1,int y1,int x2,int y2,int w, int h, double s, Color c1){ 
        super(x1,y1,x2, y2, w, h, s, c1);
    }
    public boolean collidesWith(Enemy e){
        return(projectileCoord[0]==e.getX()&&projectileCoord[1]==e.getY());
    }
}
