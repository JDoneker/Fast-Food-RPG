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
		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				play.createFile();
				play.readFile();
			}

			@Override
			public void windowClosing(WindowEvent e) {
				play.writeToFile();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				
			}
			
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
	}
	public static void main(String[] args) {
		Main run = new Main();
	}
}
