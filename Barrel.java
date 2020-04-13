
public class Barrel {

    private int x;
    private int y;
    private int rum;

    //Constructor
    //creates a barrel given its (x,y) location
    public Barrel(int x, int y, int rum){
        this.x = x;
        this.y = y;
        this.rum = rum;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getRum(){
        return this.rum;
    }

}
