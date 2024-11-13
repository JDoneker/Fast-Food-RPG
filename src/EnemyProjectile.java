import java.awt.Color;

public class EnemyProjectile extends Projectile{
    public EnemyProjectile(int x1, int y1, int x2, int y2, int xoffset, int yoffset){
        super(x1, y1, 15,15,2, Color.GREEN, x2, y2, xoffset, yoffset);
    }
}
