public class Crane {
    private final int id;
    private double x;
    private double y;
    private final int xmin;
    private final int ymin;
    private final int xmax;
    private final int ymax;
    private final int xspeed;
    private final int yspeed;

    public Crane(int id, double x, double y, int xmin, int ymin, int xmax, int ymax, int xspeed, int yspeed) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;
        this.xspeed = xspeed;
        this.yspeed = yspeed;
    }
    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getXmin() {
        return xmin;
    }

    public int getYmin() {
        return ymin;
    }

    public int getXmax() {
        return xmax;
    }

    public int getYmax() {
        return ymax;
    }

    public int getXspeed() {
        return xspeed;
    }

    public int getYspeed() {
        return yspeed;
    }

    public void setPosXY(double posX, double posY) {
        this.x = posX;
        this.y = posY;
    }
    public double distX(int x){
        return x-this.x;
    }

    public double distY(int y){
        return y-this.y;
    }

    public double travelTime(int x, int y){
        double timeX=Math.abs(this.x-x)/this.xspeed;
        double timeY=Math.abs(this.y-y)/this.xspeed;
        return Math.max(timeX, timeY);
    }
}
