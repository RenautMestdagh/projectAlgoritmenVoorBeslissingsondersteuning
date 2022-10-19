public class Kraan {

    private double vx;
    private double vy;
    private int posX;
    private int posY;

    public Kraan(double vx, double vy, int posX, int posY) {
        this.vx = vx;
        this.vy = vy;
        this.posX = posX;
        this.posY = posY;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setPosXY(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int distX(int x){
        return x-this.posX;
    }

    public int distY(int y){
        return y-this.posY;
    }

    public double travelTime(int x, int y){
        double timeX=Math.abs(posX-x)/vx;
        double timeY=Math.abs(posY-y)/vy;
        return Math.max(timeX, timeY);
    }
}
