import javax.swing.ImageIcon;

public class PattyMelt extends Weapon{
    public PattyMelt(int x1, int y1) {
        super(x1, y1, 50, 50, 100, 100, 100, new ImageIcon("PattyMelt.png"));
        
    }
    public String toString(){
        return "Patty Melt";
    }
}
