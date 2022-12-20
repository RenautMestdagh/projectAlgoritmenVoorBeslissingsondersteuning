public class Container {
    private final int id;
    private final int length;

    private Slot target;
    private Slot currentSLot;

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

    public void setTarget(Slot slot) {
        this.target=slot;
    }
    public Slot getTarget() {
        return this.target;
    }

    public void setCurrentSlot(Slot slot) {
        this.currentSLot=slot;
    }
    public Slot getCurrentSlot(){
        return this.currentSLot;
    }
}
