import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame{
	
	
	public Main () {
		super("Fast Food RPG");
		Game play = new Game();
		((Component) play).setFocusable(true); 
		setBackground(Color.WHITE);
		setResizable(false);
		add(play);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
	}
	public static void main(String[] args) {
		Main run = new Main();
	}
}
