public class Assignment {
    public Slot getSlot() {
        return slot;
    }
    public Container getContainer(){
        return container;
    }

    private final Slot slot;
    private final Container container;

    public Assignment(Slot slot_id, Container container_id) {
        this.slot = slot_id;
        this.container = container_id;
    }
}
