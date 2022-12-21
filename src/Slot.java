public class Slot {
    private final int id;
    private final int x;
    private final int y;

    public Slot(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Slot XYtoSlot(int x, int y) {
        return Yard.getSlots().get(y*Yard.getLength()+x);
    }
}
