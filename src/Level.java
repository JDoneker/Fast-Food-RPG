import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;

public class Level {
    private static char[] DIRECTIONS = {'u','d','r','l'};
    private int[][] grid;
    private int numEnemy;
    public Level(int width, int height,int attempts,int minSize,int maxSize, double doorRate, int numEnemy1){
        grid = new int[height][width];
        addRooms(grid,attempts,minSize,maxSize);
        growMaze(grid);
        addDoors(grid,doorRate);
        removeDeadEnds(grid);
        randomizeWalls(grid);
        print2DArray(grid);
        numEnemy = numEnemy1;
    }
    private void randomizeWalls(int[][] grid) {
        for(var i = 0; i < grid.length; i++){
            for(var j = 0; j<grid[0].length; j++){
                if(getDir(grid,j,i,'u',0)[2]==0){
                    int ranNum = (int)Math.floor(3*Math.random()+1);
                    switch (ranNum) {
                        case 1:
                            setDir(grid, j, i, 'u', 0, 4);
                            break;
                        case 2:
                            setDir(grid, j, i, 'u', 0, 5);
                            break;
                        case 3:
                            setDir(grid, j, i, 'u', 0, 6);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }
    public int[][] getGrid(){
        return grid;
    }
    private static void removeDeadEnds(int[][] grid) {
        ArrayList<int[]> deadEnds = new ArrayList<>();
        deadEnds = findDeadEnds(grid);
        while(!deadEnds.isEmpty()){
            int[] cell = deadEnds.get(deadEnds.size()-1);
            grid[cell[1]][cell[0]]=0;
            deadEnds.remove(deadEnds.size() - 1);
            deadEnds = findDeadEnds(grid);
        }
    }
    private static ArrayList<int[]> findDeadEnds(int[][] grid) {
        ArrayList<int[]> temp = new ArrayList<>();
        for(var i = 1; i < grid.length-1; i++){
            for(var j = 1; j<grid[0].length-1; j++){
                if(isDeadEnd(grid,j,i)){
                    temp.add(new int[]{j,i});
                }
            }
        }
        return temp;
    }
    private static boolean isDeadEnd(int[][] grid, int x, int y) {
        if(grid[y][x]==2){
            int wallCount = 0;
            for(char dir: DIRECTIONS){
                if(getDir(grid, x, y, dir, 1)[2]==0){
                    wallCount++;
                }
            }
            if(wallCount == 3){
                return true;
            }else{return false;}
        }else{
            return false;
        }
    }
    private static void addDoors(int[][] grid,double doorRate) {
        ArrayList<int[]> doorCapables = new ArrayList<>();
        for(var i = 1; i < grid.length-1; i++){
            for(var j = 1; j<grid[0].length-1; j++){
                if(canBeDoor(grid,j,i)){
                    doorCapables.add(new int[]{j,i});
                }
            }
        }

        for(int[] cell: doorCapables){
            if(Math.abs(Math.random())<doorRate){
                grid[cell[1]][cell[0]]=3;
            }
        }
    }
    private static boolean canBeDoor(int[][] grid, int x, int y) {
        if(grid[y][x]==0){
            int corridorCount = 0;
            int roomCount = 0;
            for(char dir: DIRECTIONS){
                if(getDir(grid, x, y, dir, 1)[2]==1){
                    roomCount++;
                }
                if(getDir(grid, x, y, dir, 1)[2]==2){
                    corridorCount++;
                }
            }
            if(corridorCount == 1 && roomCount == 1){
                return true;
            }else{return false;}
        }else{
            return false;
        }
    }
    private static void growMaze(int[][] grid) {
        int startX = 0;
        int startY = 0;
        boolean validStart = false;
        while(!validStart){
            startX = getRandomOddInt(1,grid[0].length-1);
            startY = getRandomOddInt(1,grid.length-1);
            if(grid[startY][startX]==1){
                System.out.println("on other box");
            }else{
                validStart=true;
            }
        }
        ArrayList<int[]> cells = new ArrayList<>();
        setDir(grid, startX, startY, 'u', 0, 2);
        cells.add(new int[] {startX, startY});
        while (!cells.isEmpty()) {
            int[] cell = cells.get(cells.size() - 1);
            int curX = cell[0];
            int curY = cell[1];
            ArrayList<Character> unmadeCells = new ArrayList<>();
            for (char dir : DIRECTIONS) {
                int[] dirCell = getDir(grid, curX, curY, dir,  2);
                if(isInBounds(grid, dirCell[0], dirCell[1])&&dirCell[2]==0){
                    unmadeCells.add(dir);
                }   
            }
            if (!unmadeCells.isEmpty()) {
                char dir = unmadeCells.get((int) (Math.random() * unmadeCells.size()));
                setDir(grid,curX,curY,dir,1,2);
                setDir(grid,curX,curY,dir,2,2);
                cells.add(getDir(grid,curX,curY,dir,2));
            } else {
                cells.remove(cells.size() - 1);
            }
        }
    }
    private static void setDir(int[][] grid, int x, int y, char dir, int distance, int val) {
        int[] cell;
        if(dir  == 'u'){
            cell = new int[] {x,y-distance};
        }else if(dir == 'd'){
            cell = new int[] {x,y+distance};
        }else if(dir == 'r'){
            cell = new int[] {x+distance,y};
        }else if(dir == 'l'){
            cell = new int[] {x-distance,y};
        }else{
            cell = new int[]{-1,-1};
        } 
        if(isInBounds(grid, cell[0], cell[1])){
            grid[cell[1]][cell[0]] = val;
        }
    }
    public static int[] getDir(int[][] grid, int x, int y, char dir, int distance){
        int[] cell;
        if(dir  == 'u'){
            cell = new int[] {x,y-distance,0};
        }else if(dir == 'd'){
            cell = new int[] {x,y+distance,0};
        }else if(dir == 'r'){
            cell = new int[] {x+distance,y,0};
        }else if(dir == 'l'){
            cell = new int[] {x-distance,y,0};
        }else{
            cell = new int[]{-1,-1,-1};
        } 
        if(isInBounds(grid, cell[0], cell[1])){
            cell[2] = grid[cell[1]][cell[0]];
        }else{
            cell[2]=-1;
        }
        return cell;
    }
    private static boolean isInBounds(int[][] grid, int x, int y) {
        return x >= 0 && x < grid[0].length && y >= 0 && y < grid.length;
    }
    private static void addRooms(int[][] grid,int attempts,int minSize, int maxSize) {
        int width,height,x,y;
        ArrayList<Rect> rooms = new ArrayList<Rect>();
        for(int i = 0; i < attempts; i++){
            width = getRandomOddInt(minSize,maxSize);
            height = getRandomOddInt(minSize, maxSize);
            x = getRandomOddInt(1,grid[0].length-1-width);
            y = getRandomOddInt(1,grid.length-1-height);
            Rect temp = new Rect(x, y, width, height);
            boolean collides = false;
            for(Rect r: rooms){
                if(temp.collidesWith(r)){
                    collides = true;
                }
            }
            if(!collides) rooms.add(temp);
        }
        for(Rect r:rooms){
            for(int j = r.getY(); j<r.getY()+r.getHeight(); j ++){
                Arrays.fill(grid[j], r.getX(),r.getX()+r.getWidth(),1);
            }
        }     
    }
    private static void print2DArray(int[][] a){
        for( int[] i :a){
            System.out.println(Arrays.toString(i));
        }
    }
    public static int getRandomOddInt(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min should not be greater than max");
        }
        if (min % 2 == 0) {
            min++;
        }
        if (max % 2 == 0) {
            max--;
        }
        int range = (max - min) / 2 + 1;
        return min + 2 * (int) (Math.random() * range);
    }
    public void drawLevelBackground(Graphics g2d, int[] playerCoord) {
        for(int i = playerCoord[1]-6; i<=playerCoord[1]+6; i++ ){
            for(int j = playerCoord[0]-8;j<=playerCoord[0]+8;j++){
                int xoffset = j-playerCoord[0];
                int yoffset = i-playerCoord[1];
                int[] cell = getDir(grid, j,i, 'u', 0);
                int screenX = 50*((xoffset)+8)-25;
                int screenY = 50*((yoffset)+6)-25;
                if(cell[2]==2||cell[2]==3||cell[2]==1){
                    g2d.drawImage(new ImageIcon("FloorTile.png").getImage(), screenX, screenY,50,50, null);
                }else if(cell[2]==-1){
                    g2d.setColor(Color.BLACK);
                    g2d.fillRect(screenX,screenY,50,50);
                }else if(cell[2]==4){
                    g2d.drawImage(new ImageIcon("WallTile1.png").getImage(), screenX, screenY,50,50, null);
                }else if(cell[2]==5){
                    g2d.drawImage(new ImageIcon("WallTile2.png").getImage(), screenX, screenY,50,50, null);
                }else if(cell[2]==6){
                    g2d.drawImage(new ImageIcon("WallTile3.png").getImage(), screenX, screenY,50,50, null);
                }else{
                    System.out.println(cell[2]);
                    g2d.setColor(Color.DARK_GRAY);
                }
            }
        }
    }
    public ArrayList<Character> getPosDir(int x, int y) {
        ArrayList<Character> temp = new ArrayList<>();
        for (char dir : DIRECTIONS) {
            int[] dirCell = getDir(grid, x, y, dir,  1);
            if(isInBounds(grid, dirCell[0], dirCell[1])&&dirCell[2]!=4&&dirCell[2]!=5&&dirCell[2]!=6){
                temp.add(dir);
            }   
        }
        return temp;
    }
    public int[] getValidPosition(int val) {
        int startX = 0;
        int startY = 0;
        boolean validStart = false;
        while(!validStart){
            startX = getRandomOddInt(1,grid[0].length-1);
            startY = getRandomOddInt(1,grid.length-1);
            if(grid[startY][startX]==val){
                validStart=true;
            }else{
                validStart=false;
            }
        }
        return new int[]{startX,startY};
    }
    public int[] indexToScreen(int x, int y, int[] playerCoord) {
        int xoffset = x-playerCoord[0];
        int yoffset = y-playerCoord[1];
        int screenX = 50*((xoffset)+8)-25;
        int screenY = 50*((yoffset)+6)-25;
        return new int[]{screenX,screenY};
    }
    public int getValueAt(int[] projectilePos) {
        return getDir(grid,projectilePos[0],projectilePos[1],'u',0)[2];
    }
    public int getNumEnemy() {
        return numEnemy;
    }
}