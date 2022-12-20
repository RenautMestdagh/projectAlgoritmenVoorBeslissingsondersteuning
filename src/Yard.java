import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Yard {
    private String name=null;
    private int length=0;
    private int width=0;
    private int maxHeight=0;
    private int targetHeigt=0;
    private HashMap<Integer, Crane> cranes=null;
    private HashMap<Integer,Slot> slots=null;
    private HashMap<Integer,Container> containers=null;
    private FieldV2 field=null;
    private ArrayList<Assignment> target=null;
    private ArrayList<Assignment> assignments=null;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }

    public int getMaxHeight() {
        return maxHeight;
    }
    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public int getTargetHeigt() {
        return targetHeigt;
    }
    public void setTargetHeigt(int targetHeigt) {
        this.targetHeigt = targetHeigt;
    }

    public HashMap<Integer, Crane> getCranes() {
        return cranes;
    }
    public void setCranes(HashMap<Integer, Crane> cranes) {
        this.cranes = cranes;
    }

    public HashMap<Integer, Slot> getSlots() {
        return slots;
    }
    public void setSlots(HashMap<Integer, Slot> slots) {
        this.slots = slots;
    }

    public HashMap<Integer, Container> getContainers() {
        return containers;
    }
    public void setContainers(HashMap<Integer, Container> containers) {
        this.containers = containers;
    }

    public FieldV2 getField() {
        return field;
    }
    public void setField(FieldV2 field) {
        this.field = field;
    }

    public ArrayList<Assignment> getTarget() {
        return target;
    }
    public void setTarget(ArrayList<Assignment> target) {
        this.target = target;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }
    public void setAssignments(ArrayList<Assignment> assignments) {
        this.assignments = assignments;
    }
    public Yard() {
        
    }
    public void findSolution() {
        if (target==null){
            List<Container> toMove=field.getTooHigh(targetHeigt);
            int size= toMove.size();
            for (int i = 0; i < size; i++) {
                if (field.moveContainer(toMove.get(i),slots,targetHeigt)){
                    toMove.remove(i);
                    size--;
                }
            }
        }else{
            List<Assignment> toMove= new ArrayList<>(target);
            while(!toMove.isEmpty()){
                //al plek?
                int size= toMove.size();
                for (int i = 0; i < size; i++) {
                    if (field.trySimpleMove(toMove.get(i))){
                        toMove.remove(i);
                        size--;
                    }
                }
                //kan kraan als backup gebruikt worden
                for (int i = 0; i < size; i++) {
                    if (field.liftFromCurrent(toMove.get(i),cranes.size())){
                        toMove.remove(i);
                        size--;
                    }
                }
                //kan targetstack opheffen
                for (int i = 0; i < size; i++) {
                    if (field.liftFromTarget(toMove.get(i),cranes.size())){
                        toMove.remove(i);
                        size--;
                    }
                }
                boolean found=false;
                for (int i = 0; i < size; i++) {
                    if (field.liftFromTarget(toMove.get(i),cranes.size())){
                        toMove.remove(i);
                        size--;
                        found=true;
                    }
                }
                if (found){
                    toMove=new ArrayList<>(target);
                }
            }
        }
    }

    public void print() {
        field.printField(slots.size());
    }

    public void initialize() {
        field.placeInitialContainers(this.assignments);
    }
}
