import javax.swing.ImageIcon;

public class LargePizza extends Weapon{
    public LargePizza(int x1, int y1) {
        super(x1, y1, 50, 50, 100, 100, 100, new ImageIcon("LargePizza.png"));
        
    }
    public String toString(){
        return "Large Pizza";
    }
}
