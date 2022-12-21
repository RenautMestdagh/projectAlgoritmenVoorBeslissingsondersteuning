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
    private int busyTill=0;

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

    public void setX(double toX, int toTime, int totalTime) {
        System.out.println(this.getId()+";;"+(busyTill+toTime)+";"+(busyTill+totalTime)+";"+x+";"+y+";"+toX+";"+y+";");
        busyTill+=totalTime;
        this.x = toX;
    }

    public double getY() {
        return y;
    }

    public void setY(double toY, int containerId, int toTime, int totalTime) {
        this.y = toY;
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

    public int getBusyTill() {
        return busyTill;
    }

    public void setBusyTill(int busyTill) {
        this.busyTill = busyTill;
    }
    public void setPosXY(double toX, double toY, int containerId, int toTime, int totalTime) {
        busyTill += totalTime;

        String containerIdS;
        if(containerId==-1)
            containerIdS="";
        else
            containerIdS = String.valueOf(containerId);

        int[] containerCoords = Container.idToXY(containerId);
        double fromY = containerCoords[1]+0.5;

        System.out.println(this.getId()+"\t"+containerIdS+"\t"+(busyTill+toTime)+"\t"+(busyTill+totalTime)+"\t"+containerCoords[0]+"\t"+fromY+"\t"+toX+"\t"+toY+";");

        this.x = toX;
        this.y = toY;
    }
    public double distX(int x){
        return x-this.x;
    }

    public double distY(int y){
        return y-this.y;
    }

    public int travelTime(int x, double y){
        double timeX=Math.abs(this.x-x)/this.xspeed;
        double timeY=Math.abs(this.y-y)/this.yspeed;
        return (int) Math.round(Math.max(timeX, timeY));
    }
    public int travelTime(int fromX, double fromY, int toX, double toY){
        double timeX=Math.abs(toX-fromX)/this.xspeed;
        double timeY=Math.abs(toY-fromY)/this.yspeed;
        return (int) Math.round(Math.max(timeX, timeY));
    }
}
