import javax.swing.ImageIcon;

public class BajaBlast extends Weapon{
    public BajaBlast(int x1, int y1) {
        super(x1, y1, 50, 50, 100, 100, 100, new ImageIcon("BajaBlast.png"));
        
    }
    public String toString(){
        return "Baja Blast";
    }
}
