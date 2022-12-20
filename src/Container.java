public class Container {
    private final int id;
    private final int length;

    private int[] center;

    public Container(int id, int length) {
        this.id=id;
        this.length=length;
    }
    public int getId() {
        return id;
    }

    public int getLength() {
        return length;
    }

    public int[] getCenter(){
        return this.center;
    }
}
