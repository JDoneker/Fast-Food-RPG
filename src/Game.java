
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage; 
import java.awt.event.*; 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;


public class Game  extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener{
	private BufferedImage back; 
	private int key, xmouse, ymouse,xoffset,yoffset,mousebutton; 
	private String screen;
	private ArrayList<Character> characterList;
	private ArrayList<Projectile> projectileList;
	private Queue <Enemy> enemyList;
	private int characterIndex;
	private static final int WIDTH =800;
	private static final int HEIGHT=600;

	public Game() {
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		new Thread(this).start();	
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		key =-1; 
		xmouse=0;
		ymouse=0;
		mousebutton = 0;
		xoffset=-350;
		yoffset=-450;
		screen = "start";
		characterList = setCharacterList();
		enemyList = setEnemyList();
		projectileList = new ArrayList<>();
		for(Character c: characterList){
			System.out.println(c);
		}
	}
	private Queue<Enemy> setEnemyList() {
		Queue<Enemy> temp = new LinkedList<>();
		temp.add(new SaladMonster(100,100));
		temp.add(new SaladMonster(200,100));
		temp.add(new SaladMonster(300,100));
		temp.add(new SaladMonster(400,100));
		return temp;
	}
	private ArrayList<Character> setCharacterList() {
		ArrayList<Character> temp = new ArrayList<>();
		temp.add(new McDonalds(100,100));
		temp.add(new Whataburger(210,100));
		temp.add(new Dominos(320,100));
		temp.add(new TacoBell(430,100));
		return temp;
	}
	public void run()
	   {
	   	try
	   	{
	   		while(true)
	   		{
	   		   Thread.currentThread().sleep(5);
	            repaint();
	         }
	      }
	   		catch(Exception e)
	      {
	      }
	  	}
	

	
	
	
	public void paint(Graphics g){
		
		Graphics2D twoDgraph = (Graphics2D) g; 
		if( back ==null)
			back=(BufferedImage)( (createImage(getWidth(), getHeight())));
		

		Graphics g2d = back.createGraphics();

		g2d.clearRect(0, 0, WIDTH, HEIGHT);
		g2d.setFont(new Font("Arial",0,32));
		drawScreen(g2d);

		twoDgraph.drawImage(back, null, 0, 0);

	}
	private void drawScreen(Graphics g2d) {
		switch (screen) {
			case "start":
				drawStartScreen(g2d);
				break;
			case "selection":
				drawSelectionScreen(g2d);
				break;
			case "gameplay":
				drawGameplayScreen(g2d);
				break;
			default:
				break;
		}
	}
	private void drawGameplayScreen(Graphics g2d) {
		g2d.drawImage(new ImageIcon("DefaultBackground.png").getImage(), xoffset, yoffset, 1500,1500,null);
		Character c = characterList.get(characterIndex);
		g2d.drawString(Integer.toString(c.getHealth()),0,32);
		c.drawChar(g2d);
		c.drawWeapon(g2d);
		Iterator<Projectile> iterator = projectileList.iterator();
		while(iterator.hasNext()){
			Projectile p = iterator.next();
			p.drawProjectile(g2d,xoffset,yoffset);
			
			if(p instanceof PlayerProjectile&&enemyList.peek()!=null&&p.collidesWith(enemyList.peek(),xoffset,yoffset)){
				enemyList.remove();
				iterator.remove();
				
			}
			if(p instanceof EnemyProjectile&&p.collidesWith(c,xoffset,yoffset)){
				iterator.remove();
				c.setHealth(c.getHealth()-1);
				
			}
			
		}
		if(enemyList.peek()!=null){
			enemyList.peek().drawCharOffset(g2d,xoffset,yoffset);
			if(Math.random()<0.4){
				projectileList.add(new EnemyProjectile(enemyList.peek().getX()+xoffset, enemyList.peek().getY()+yoffset,c.getX()+c.getWidth()/2, c.getY()+c.getHeight()/2,xoffset,yoffset));

			}
		}
		
	}
	private void drawStartScreen(Graphics g2d) {
		g2d.drawString("Character Selection press 1,2,3, or 4 to begin", 0, 32);
		for(Character c: characterList){
			c.drawChar(g2d);
			c.drawWeapon(g2d);
		}
	}
	private void drawSelectionScreen(Graphics g2d) {
		g2d.drawString("Press space to go back and enter to confirm", 0, 32);
		Character c = characterList.get(characterIndex);
		c.drawChar(g2d);
		c.drawWeapon(g2d);
		g2d.drawString(c.toString(),100,232);
		g2d.drawString("Speed: "+Integer.toString(c.getSpeed()), 100, 264);
		g2d.drawString("Health: "+Integer.toString(c.getHealth()), 100, 296);
		g2d.drawString("Damage: "+Integer.toString(c.getDamage()), 100, 328);
		g2d.drawString("Stamina: "+Integer.toString(c.getStamina()), 100, 360);
		g2d.drawString("Weapon: "+c.getWeapon().toString(),100,392);
	}
	//DO NOT DELETE
	@Override
	public void keyTyped(KeyEvent e) {
	}
	//DO NOT DELETE
	@Override
	public void keyPressed(KeyEvent e) {
		key= e.getKeyCode();
		System.out.println(key);
		if(key == 49 &&(screen=="selection"|| screen == "start")){
			characterIndex = 0;
			screen = "selection";
		}
		if(key == 50 && (screen=="selection"|| screen == "start")){
			characterIndex = 1;
			screen = "selection";
		}
		if(key == 51&& (screen=="selection"|| screen == "start")){
			characterIndex = 2;
			screen = "selection";
		}
		if(key == 52&& (screen=="selection"|| screen == "start")){
			characterIndex = 3;
			screen = "selection";
		}
		if(key==32 && screen == "selection"){
			screen = "start";
		}
		if(key == 10 && screen == "selection"){
			characterList.get(characterIndex).moveCenter(WIDTH,HEIGHT);
			screen = "gameplay";
		}
		/* 
		if(key == 37){ //Right
			xoffset+=100;
		}
		if(key == 38){ //Up
			yoffset+=100;
		}
		if(key == 39){ //Left
			xoffset-=100;
		}
		if(key == 40){ //Down
			yoffset-=100;
		}
		*/
	}
	//DO NOT DELETE
	@Override
	public void keyReleased(KeyEvent e) {
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		xmouse=arg0.getX();
		ymouse=arg0.getY();
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {

	}
	@Override
	public void mouseEntered(MouseEvent arg0) {

	}
	@Override
	public void mouseExited(MouseEvent arg0) {

	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		System.out.println("you clicked at ("+ arg0.getX() + ","+arg0.getY()+") or (" + Integer.toString((arg0.getX()+xoffset))+ ","+ Integer.toString((arg0.getY()+yoffset)) + ").");
		xmouse = arg0.getX();
		ymouse = arg0.getY(); 
		mousebutton = arg0.getButton();
		if(mousebutton == 1 && screen == "gameplay"){
			if(ymouse > 0.75*xmouse && ymouse < -0.75*xmouse+600){
			xoffset+=100;
			}
			if(ymouse < 0.75*xmouse && ymouse < -0.75*xmouse+600){
				yoffset+=100;
			}
			if(ymouse < 0.75*xmouse && ymouse > -0.75*xmouse+600){
				xoffset-=100;
			}
			if(ymouse > 0.75*xmouse && ymouse > -0.75*xmouse+600){
				yoffset-=100;
			}
		}
		if(mousebutton == 3){
			if(characterList.get(characterIndex).getWeapon().toString()=="Baja Blast"){
				projectileList.add(new PlayerProjectile(characterList.get(characterIndex).getX(), characterList.get(characterIndex).getY(), xmouse,ymouse,xoffset,yoffset));

			}
		}
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
