import javax.swing.ImageIcon;

public class BigMac extends Weapon{
    public BigMac(int x1, int y1) {
        super(x1, y1, 50, 50, 100, 100, 100, new ImageIcon("BigMac.png"));
        
    }
    public String toString(){
        return "Big Mac";
    }
}
