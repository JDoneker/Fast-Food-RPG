import javax.swing.ImageIcon;

public class Dominos extends Character{
    public Dominos() {
        super();
    }
    public Dominos(int x, int y){
        super(x,y,100,100,200,125,50,150, new LargePizza(x+50,y+50), new ImageIcon("Dominos.png"));
    }
    public String toString(){
        return "Dominos";

    }
}

