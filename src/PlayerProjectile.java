import java.awt.Color;

public class PlayerProjectile extends Projectile{
    public PlayerProjectile(int x1, int y1, int x2, int y2, int xoffset, int yoffset){
        super(x1, y1, 25,25,2.5, Color.CYAN, x2, y2, xoffset, yoffset);
    }
}
