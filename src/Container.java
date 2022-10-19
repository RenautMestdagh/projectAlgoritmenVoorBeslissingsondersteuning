public class Container {

    public Container(int length, int[] center) {
        this.length = length;
        this.center = center;
    }

    private int length;

    private int[] center;

    public int getLength() {
        return length;
    }

    public int[] getcenter() {
        return center;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setCenter(int[] center) {
        this.center = center;
    }

}
