import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.awt.event.*; 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
public class Game  extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener{
	private BufferedImage back; 
	private int key, xmouse, ymouse; 
	private int[] playerCoord;
	private String screen;
	private ArrayList<Player> playerList;
	private ArrayList<Projectile> projectileList;
	private ArrayList<Enemy> enemyList;
	private Queue <Level> levelQueue;
	private int playerIndex;
	private static final int WIDTH =800;
	private static final int HEIGHT=600;
	private File saveFile;
	private double startTime;
	private double curTime;
	private double highScore;
	private int typingStringIndex;
	private boolean[] currentWASD;
	private int movementCooldown;
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		new Thread(this).start();	
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		currentWASD = new boolean[]{false,false,false,false};
		saveFile = new File("savefile.txt");
		key =-1; 
		xmouse=0;
		ymouse=0;
		screen = "start";
		levelQueue = setLevelQueue();
		playerList = setPlayerList();
		enemyList = setEnemyList(levelQueue.peek().getNumEnemy());
		playerCoord = levelQueue.peek().getValidPosition(2);
		projectileList = new ArrayList<>();
		for(Player c: playerList){
			System.out.println(c);
		}
		movementCooldown = 0;
	}
	private Queue<Level> setLevelQueue() {
		Queue<Level> temp = new LinkedList<>();
		temp.add(new Level(11,11,10,3,5,0.2,10));
		temp.add(new Level(65,65,10,11,25,0.2,100));
		return temp;
	}
	public void createFile(){
		try {
			if(saveFile.createNewFile()){
				System.out.println("cool");
			}else{
				System.out.println("already exist");
			}
		} catch (Exception e) {
			
		}
		
	}	
	public void writeToFile(){
		try {
			FileWriter myFileWriter = new FileWriter(saveFile);
			if(curTime>highScore){
				myFileWriter.write(Double.toString(curTime));
			}else{
				myFileWriter.write(Double.toString(highScore));
				System.out.println("not highscore");
			}
			myFileWriter.close();
			System.out.println("yay file :)");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void readFile(){
		try {
			Scanner sc = new Scanner(saveFile);
			while(sc.hasNext()){
				highScore = Double.parseDouble(sc.next());
			}
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private ArrayList<Enemy> setEnemyList(int numEnemy) {
		ArrayList<Enemy> temp = new ArrayList<>();
		for(int i = 0; i < numEnemy; i++){
			int[] monStart = levelQueue.peek().getValidPosition(1);
			temp.add(new SaladMonster(monStart[0], monStart[1]));
		}
		return temp;
	}
	private ArrayList<Player> setPlayerList() {
		ArrayList<Player> temp = new ArrayList<>();
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
			case "lose":
				drawLoseScreen(g2d);
				break;
			default:
				break;
		}
	}
	private void drawLoseScreen(Graphics g2d) {
		g2d.drawString("Score "+ new DecimalFormat("#0.00").format(curTime),300,300);
		g2d.drawString("High Score "+ new DecimalFormat("#0.00").format(highScore),300,332);
		if(curTime > highScore){
			g2d.drawString("you got a new high score !!!",300,364);
		}else{
			g2d.drawString("no new high score :(",400,364);
		}
	}
	private void drawGameplayScreen(Graphics g2d) {
		if(enemyList.size()==0){
			levelQueue.remove();
			playerCoord =levelQueue.peek().getValidPosition(2);
			enemyList = setEnemyList(levelQueue.peek().getNumEnemy());
		}
		levelQueue.peek().drawLevelBackground(g2d,playerCoord);
		Player c = playerList.get(playerIndex);
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, 200, 78);
		g2d.setColor(Color.BLACK);
		g2d.drawString(Integer.toString(c.getHealth()),0,32);
		curTime = (System.currentTimeMillis()-startTime)/1000;
		g2d.drawString("Timer: "+ new DecimalFormat("#0.00").format(curTime),0,64);
		if(c.getHealth()<=0){
			screen = "lose";
		}
		c.drawChar(g2d,0.5);
		c.drawWeapon(g2d,0.5);
		characterMove();
		Iterator<Projectile> iterator = projectileList.iterator();
		while(iterator.hasNext()){
			Projectile p = iterator.next();
			p.updateCoord(playerCoord);
			p.draw(g2d);
			p.move();
			if(levelQueue.peek().getValueAt(p.getCoord())==0){
				iterator.remove();
			}
			if(p instanceof EnemyProjectile&&p.collidesWith(playerCoord)){
				iterator.remove();
				if(Math.random()>playerList.get(playerIndex).getResistance()/200.0){
					c.setHealth(c.getHealth()-1);
				}
			}
			if(p instanceof PlayerProjectile){
				Iterator<Enemy> iterator2 = enemyList.iterator();
				while(iterator2.hasNext()){
					Enemy e = iterator2.next();
					if(p.collidesWith(new int[]{e.getX(),e.getY()})){
						iterator.remove();
						iterator2.remove();
					}
				}
			}
		}
		for(Enemy e: enemyList){
			int[] enemeyPos = levelQueue.peek().indexToScreen(e.getX(),e.getY(),playerCoord);
			e.drawCharAbsolute(g2d,enemeyPos[0],enemeyPos[1]);
			if(Math.random()<0.04 && enemeyPos[0]>0 && enemeyPos[0]<WIDTH&& enemeyPos[1]>0&&enemeyPos[1]<HEIGHT){
				projectileList.add(new EnemyProjectile(enemeyPos[0]+25,enemeyPos[1]+25,WIDTH/2,HEIGHT/2,5,5,1.2,Color.GREEN));
			}
		}
	}
	private void characterMove() {

		if(movementCooldown>2000/playerList.get(playerIndex).getSpeed()){
			ArrayList<Character> possibleDirctions = levelQueue.peek().getPosDir(playerCoord[0],playerCoord[1]);
			if(currentWASD[0] && possibleDirctions.contains('u')){//W
				playerCoord[1] = playerCoord[1] -1;
				moveAllProjectiles(0,50);
				movementCooldown = 0;
			}else if(currentWASD[1]  && possibleDirctions.contains('l')){//A
				playerCoord[0] = playerCoord[0] -1;
				moveAllProjectiles(50,0);
				movementCooldown = 0;
			}else if(currentWASD[2]  && possibleDirctions.contains('d')){//S
				playerCoord[1] = playerCoord[1] +1;
				moveAllProjectiles(0,-50);
				movementCooldown = 0;
			}else if(currentWASD[3]  && possibleDirctions.contains('r')){//D
				playerCoord[0] = playerCoord[0] +1;
				moveAllProjectiles(-50,0);
				movementCooldown = 0;
			}
		}else{
			movementCooldown++;
		}
	}
	private void drawStartScreen(Graphics g2d) {
		g2d.drawString("Character Selection press 1,2,3, or 4 to begin".substring(0, typingStringIndex), 0, 32);
		if(typingStringIndex<"Character Selection press 1,2,3, or 4 to begin".length()&&Math.random()<0.05){
			typingStringIndex++;
		}
		for(Player c: playerList){
			c.drawChar(g2d,1.0);
			c.drawWeapon(g2d,1.0);
		}
	}
	private void drawSelectionScreen(Graphics g2d) {
		g2d.drawString("Press space to go back and enter to confirm", 0, 32);
		Player c = playerList.get(playerIndex);
		c.drawChar(g2d,1.0);
		c.drawWeapon(g2d,1.0);
		g2d.drawString(c.toString(),100,232);
		g2d.drawString("Speed: "+Integer.toString(c.getSpeed()), 100, 264);
		g2d.drawString("Health: "+Integer.toString(c.getHealth()), 100, 296);
		g2d.drawString("Bullet Speed: "+Double.toString(c.getBulletSpeed()), 100, 328);
		g2d.drawString("Resistance: "+Integer.toString(c.getResistance()), 100, 360);
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
			playerIndex = 0;
			screen = "selection";
		}
		if(key == 50 && (screen=="selection"|| screen == "start")){
			playerIndex = 1;
			screen = "selection";
		}
		if(key == 51&& (screen=="selection"|| screen == "start")){
			playerIndex = 2;
			screen = "selection";
		}
		if(key == 52&& (screen=="selection"|| screen == "start")){
			playerIndex = 3;
			screen = "selection";
		}
		if(key==32 && screen == "selection"){
			typingStringIndex = 0;
			screen = "start";
		}
		if(screen =="gameplay"){
			if(key == 87){
				currentWASD[0] = true;
			}
			if(key == 65){
				currentWASD[1] = true;
			}
			if(key == 83){
				currentWASD[2] = true;
			}
			if(key == 68){
				currentWASD[3] = true;
			}
		}
		if(key == 10 && screen == "selection"){
			playerList.get(playerIndex).moveCenter(WIDTH,HEIGHT,0.5);
			screen = "gameplay";
			startTime = System.currentTimeMillis();
		}
	}
	private void moveAllProjectiles(int xmove, int ymove) {
		for(Projectile p: projectileList){
			p.setX(xmove);
			p.setY(ymove);
		}
	}
	//DO NOT DELETE
	@Override
	public void keyReleased(KeyEvent e) {
		key= e.getKeyCode();
		if(screen =="gameplay"){
			if(key == 87){
				currentWASD[0] = false;
			}
			if(key == 65){
				currentWASD[1] = false;
			}
			if(key == 83){
				currentWASD[2] = false;
			}
			if(key == 68){
				currentWASD[3] = false;
			}
		}
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
	public void mouseClicked(MouseEvent arg0) {}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {
		xmouse = arg0.getX();
		ymouse = arg0.getY(); 
		Color c = Color.BLACK;
		String weapString = playerList.get(playerIndex).getWeapon().toString();
		if(weapString =="Baja Blast"){
			c = Color.CYAN;
		}else if(weapString =="Patty Melt"){
			c = new Color(150,75,0);
		}else if(weapString =="Large Pizza"){
			c = Color.RED;
		}else if(weapString =="Big Mac"){
			c = Color.YELLOW;
		}
		projectileList.add(new PlayerProjectile(WIDTH/2,HEIGHT/2,xmouse,ymouse,25,25,playerList.get(playerIndex).getBulletSpeed()/100,c));
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}