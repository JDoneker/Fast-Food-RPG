import javax.swing.ImageIcon;

public class McDonalds extends Player{
    public McDonalds(){
        super();
    }

    public McDonalds(int x,int y) {
        super(x,y,100,100,75,150,125,175,new BigMac(x+50,y+50),new ImageIcon("McDonalds.png"));
    }
    public String toString(){
        return "McDonalds";
    }
}
