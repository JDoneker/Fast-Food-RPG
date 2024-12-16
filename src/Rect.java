public class Rect {
    private int x,y,width,height;
    public Rect(int x1, int y1, int width1, int height1) {
        this.x = x1;
        this.y = y1;
        this.width = width1;
        this.height = height1;
    }
    public boolean collidesWith(Rect other) {
        // AABB collision detection
        return this.x < other.x + other.width && 
               this.x + this.width > other.x &&
               this.y < other.y + other.height &&
               this.y + this.height > other.y;
    }
    public int getY() {
        return this.y;
    }
    public int getX() {
       return this.x;
    }
    public int getHeight() {
        return this.height;
    }
    public int getWidth() {
        return this.width;
    }
}
