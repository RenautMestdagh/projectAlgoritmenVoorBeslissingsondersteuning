import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Yard {
    private String name=null;
    private static int length=0;
    private static int width=0;
    private int maxHeight=0;
    private int targetHeigt=0;
    private static HashMap<Integer, Crane> cranes=null;
    private static double time=0;
    private static HashMap<Integer,Slot> slots=null;
    private static HashMap<Integer,Container> containers=null;
    private FieldV2 field=null;
    private ArrayList<Assignment> target=null;
    private static ArrayList<Assignment> assignments=null;
    static List<Assignment> toMove=null;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public static int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }

    public static int getWidth() {
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

    public static HashMap<Integer, Crane> getCranes() {
        return cranes;
    }
    public void setCranes(HashMap<Integer, Crane> cranes) {
        this.cranes = cranes;
    }
    public static double getTime() {
        return time;
    }

    public static void setTime(double time) {
        Yard.time = time;
    }
    public static HashMap<Integer, Slot> getSlots() {
        return slots;
    }
    public void setSlots(HashMap<Integer, Slot> slots) {
        this.slots = slots;
    }

    public static HashMap<Integer, Container> getContainers() {
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
        int tries = 0;
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
            toMove= new ArrayList<>(target);
            while(!toMove.isEmpty()){
                if(tries>5){
                    Set<Integer> toDoContainers = new HashSet<>();
                    for(Assignment a :toMove){
                        toDoContainers.add(a.getContainer().getId());
                    }
                    toMove.clear();
                    for(int container:toDoContainers){
                        target.get(container).getContainer().setTarget(target.get(container).getSlot());
                        toMove.add(new Assignment(target.get(container).getSlot(), target.get(container).getContainer()));
                    }

                }
                tries++;
                //al plek?
                int size= toMove.size();
                boolean changed=true;
                while(changed){
                    changed= false;
                    for (int i = 0; i < size; i++) {
                        if (field.trySimpleMove(toMove.get(i))){
                            toMove.remove(i);
                            size--;
                            changed=true;
                        }
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

    public static boolean getCraneForOperation(Slot from, Slot to, int containerId){
        int fromX = from.getX()+Yard.getContainers().get(containerId).getLength()/2;
        double fromY = from.getY()+0.5;
        int toX = to.getX()+Yard.getContainers().get(containerId).getLength()/2;
        double toY = to.getY()+0.5;

        HashMap<Integer, Crane> cranes = Yard.getCranes();
        ArrayList<Crane> possible = new ArrayList<>();

        for (Crane c : cranes.values()) {
            if (c.getXmin() <= Math.min(fromX, toX) && Math.max(fromX, toX) <= c.getXmax()){  // crane heeft gewenste range
                possible.add(c);
            }
        }

        Crane best = null;
        int minTotTime=Integer.MAX_VALUE;
        int travelTime = -1;
        int travelTimeSave = -1;
        int operationTime = -1;

        if(possible.size()==0){
            //kijken welke kraan eraan kan
            for (Crane c : cranes.values()) {
                if(c.getXmin() <= fromX || c.getXmax() >= fromX){
                    //gemeenschappelijke slots:
                    int smallestX = Math.max(Yard.getCranes().get(0).getXmin(),Yard.getCranes().get(1).getXmin());
                    int largestX = Math.min(Yard.getCranes().get(0).getXmax(),Yard.getCranes().get(1).getXmax());
                    int randomX;
                    int randomY;
                    while (true) {
                        randomX = ThreadLocalRandom.current().nextInt(smallestX, largestX + 1);
                        randomY = ThreadLocalRandom.current().nextInt(0, Yard.getWidth());
                        try{
                            if(FieldV2.checkIfPlacable(randomX, randomY, Yard.getContainers().get(containerId).getLength()))
                                break;
                        } catch (Error ignored){}
                    }

                    Slot slot = Slot.XYtoSlot(randomX, randomY);
                    //c moet container dichter brengen naar gemeenschappelijk slot (assignment?)
                    toMove.add(new Assignment(slot,Yard.getContainers().get(containerId)));
                    Yard.getContainers().get(containerId).setTarget(slot);
                    toMove.add(new Assignment(to,Yard.getContainers().get(containerId)));
                    //nieuwe assignment om verplaatste container juist te zetten
                    return false;
                }
            }
        } else if (possible.size()>1){
            //find best crane
            for (Crane c : possible) {
                travelTime = c.travelTime(fromX, fromY);
                operationTime = c.travelTime(fromX, fromY, toX, toY);
                if(travelTime+operationTime<minTotTime) {
                    travelTimeSave = travelTime;
                    minTotTime=travelTime+operationTime;
                    best = c;
                }
            }

        } else {
            best = possible.get(0);
            travelTimeSave = best.travelTime(fromX, fromY);
            minTotTime = travelTimeSave + best.travelTime(fromX, fromY, toX, toY);
        }

        // kijken als kranen in de weg staan
        int minX = (int) Math.min(Math.min(fromX, toX), best.getX());
        int maxX = (int) Math.max(Math.max(fromX, toX), best.getX());

        for (Crane c : cranes.values()) {
            if(c!=best && c.getX()>=minX && c.getX()<=maxX) {
                // andere kraan sta in de weg
                if(c.getY()<best.getY())
                    toX = minX-1;
                else
                    toX = maxX+1;

                travelTimeSave = c.travelTime(toX, c.getY());
                c.setX(toX, 0, travelTimeSave);
            }
        }

        best.setPosXY(toX, toY, containerId, travelTimeSave, minTotTime);
        return true;
    }

    public void print() {
        field.printField(slots.size());
    }

    public void initialize() {
        field.placeInitialContainers(this.assignments);
    }
}
