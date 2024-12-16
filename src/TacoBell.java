import javax.swing.ImageIcon;

public class TacoBell extends Player{
    public TacoBell() {
        super();
    }
    public TacoBell(int x, int y){
        super(x,y,100,100,125,75,200,50, new BajaBlast(x+50,y+50),new ImageIcon("TacoBell.png"));
    }
    public String toString(){
        return "TacoBell";
    }
}
