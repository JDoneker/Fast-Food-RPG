import javax.swing.ImageIcon;

public class Whataburger extends Character{
    public Whataburger(){
        super();
    }
    public Whataburger(int x, int y){
        super(x,y,100,100,25,250,250,25,new PattyMelt(x+50,y+50), new ImageIcon("Whataburger.png"));
    }
    public String toString(){
        return "Whataburger";
    }
}
