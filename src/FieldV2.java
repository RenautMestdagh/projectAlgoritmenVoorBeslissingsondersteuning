import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class FieldV2 {
    private final Stack<Container>[][] map;
    private final int X;
    private final int Y;
    private final int Z;
    public FieldV2(int X,int Y,int Z){
        this.X=X;
        this.Y=Y;
        this.Z=Z;
        this.map=new Stack[X][Y];
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                map[i][j]= new Stack<>();
            }
        }
    }
    //hier gewoon proberen van a naar b
    public boolean trySimpleMove(Assignment target) {
        Container c=target.getContainer();
        if (c.getCurrentSlot()==target.getSlot()){
            return true;
        }
        int currentX=c.getCurrentSlot().getX();
        int currentY=c.getCurrentSlot().getY();

        if (map[currentX][currentY].peek()!=c){
            return false;
        }
        int targetX=target.getSlot().getX();
        int targetY=target.getSlot().getY();
        int lengte=c.getLength();
        //controleer of mogelijk
        if (checkIfPlacable(targetX,targetY,lengte)){
            for (int i = 0; i < lengte; i++) {
                map[currentX+i][currentY].pop();
                map[targetX+i][targetY].push(c);
            }
            //System.out.println("Moved container "+c.getId()+ " from: "+c.getCurrentSlot().getId()+" to "+c.getTarget().getId());
            c.setCurrentSlot(c.getTarget());
            System.out.println("Moved container "+c.getId()+ " from: "+currentX+","+currentY+" to "+targetX+","+targetY);
            return true;
        }
        return false;
    }
    //hier gewoon van a naar b maar met container erboven vastgehouden door kraan
    public boolean liftFromCurrent(Assignment target, int cranes) {
        Container c=target.getContainer();
        if (c.getCurrentSlot()==target.getSlot()){
            return true;
        }
        int currentX=c.getCurrentSlot().getX();
        int currentY=c.getCurrentSlot().getY();

        Stack<Container> containerStack=map[currentX][currentY];
        int grootteStack=containerStack.size();
        int niveauContainer=containerStack.indexOf(c);

        if (grootteStack-cranes>niveauContainer){
            return false;
        }

        //verplaatsing
        int targetX=target.getSlot().getX();
        int targetY=target.getSlot().getY();
        int lengte=c.getLength();

        if(checkIfPlacable(targetX,targetY,lengte)){
            for (int i = 1; i < grootteStack-niveauContainer; i++) {
                System.out.println("while holding: "+map[currentX][currentY].get(niveauContainer+i).getId());
            }
            for (int i = 0; i < lengte; i++) {
                map[currentX+i][currentY].remove(c);
                map[targetX+i][targetY].push(c);
            }
            System.out.println("Moved "+c.getId()+ " from: "+c.getCurrentSlot().getId()+" to "+c.getTarget().getId());
            c.setCurrentSlot(target.getSlot());
            return true;
        }


        return false;
    }
    //nog niet nodig geweest
    public boolean liftFromTarget(Assignment target, int cranes) {
        //todo
        Container c=target.getContainer();
//        if (c.getCurrentSlot()==target.getSlot()){
//            return true;
//        }
//        int currentX=c.getCurrentSlot().getX();
//        int currentY=c.getCurrentSlot().getY();
//        Stack<Container> currentColumn=map[currentX][currentY];
//        if (currentColumn.size()-cranes>currentColumn.indexOf(c)){
//            return false;
//        }

        return false;
    }
    //verplaatst container uit de weg om dan terug te plaatsen
    //kan fouten bevatten omdat nog niet gebruikt deze code
    public boolean moveContainerOutOfWay(Assignment target, HashMap<Integer,Slot> slots){
        Container c=target.getContainer();
        if (c.getCurrentSlot()==target.getSlot()){
            return true;
        }
        int currentX=c.getCurrentSlot().getX();
        int currentY=c.getCurrentSlot().getY();
        int targetX=target.getSlot().getX();
        int targetY=target.getSlot().getY();
        int lengte=c.getLength();

        Stack<Container> containerStack=map[currentX][currentY];
        int grootteStack=containerStack.size();
        int niveauContainer=containerStack.indexOf(c);

        //verplaats alle containers in de weg
        for (int aantalPops = 0; aantalPops<grootteStack-niveauContainer-1; aantalPops++) {
            //eerste container krijgen
            Container tomove=containerStack.peek();
            for (int x = 0; x <= X-tomove.getLength(); x++) {
                for (int y = 0; y < Y; y++) {
                    if (!(x<targetX+lengte && x>=targetX && y==targetY)) {
                        if (checkIfPlacable(x,y,tomove.getLength())){

                            for (int i = 0; i < tomove.getLength(); i++) {
                                map[x+i][y].push(tomove);
                                map[tomove.getCurrentSlot().getX()+i]
                                        [tomove.getCurrentSlot().getY()].pop();
                            }
                            Slot slot=slots.get(x+y*Y);
                            if (slot.getX()!=x||slot.getY()!=y) System.out.println("foutje herberken key");
                            System.out.println("Moved container sdfsdf"+tomove.getId()+ " from: "+tomove.getCurrentSlot().getX()+","+tomove.getCurrentSlot().getY()+" to "+slot.getX()+","+slot.getY());
                            tomove.setCurrentSlot(slot);
                        }
                    }
                }
            }
        }
        //check of containers wel weg
        if (containerStack.peek()!=c) {
            System.out.println("foutje met poppen");
            return false;
        }

        //verplaatsing
        if (checkIfPlacable(targetX,targetY,lengte)){
            for (int i = 0; i < lengte; i++) {
                map[currentX+i][currentY].remove(c);
                map[targetX+i][targetY].push(c);
            }
            System.out.println("Moved container "+c.getId()+ " from: "+currentX+","+currentY+" to "+targetX+","+targetY);
            for (int i = 1; i < grootteStack-niveauContainer; i++) {
                System.out.println("while holding: "+map[currentX][currentY].get(niveauContainer+i).getId());
            }
            return true;
        }


        return false;
    }
    public boolean checkIfPlacable(int x, int y, int lengte){
        Stack<Container> containerStack=map[x][y];
        int size=containerStack.size();

        for (int i = x; i < x+lengte; i++) {
            if (map[i][y].size()!=size) return false;
        }
        if (size>0){
            if (containerStack.peek().getLength()>lengte) return false;
            Container first=map[x][y].peek();
            Container last=map[x+lengte-1][y].peek();
            if (first.getCurrentSlot().getX()<x) return false;
            if (last.getCurrentSlot().getX()+last.getLength()>x+lengte) return false;
        }


        return true;
    }



    public void placeInitialContainers(List<Assignment> assignments) {
        for (Assignment a : assignments) {
            Container c = a.getContainer();
            Slot s = a.getSlot();
            for (int i = s.getX(); i <= s.getX()+c.getLength()-1; i++) {
                map[i][s.getY()].add(c);
            }
        }
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                checkContainers(i,j);
            }
        }

    }
    private void checkContainers(int X, int Y) {
        int minX=Integer.MAX_VALUE;
        int maxX=Integer.MIN_VALUE;
        Stack<Container> check=map[X][Y];
        //check intervl maken
        for (Container c:check) {
            if (minX>c.getCurrentSlot().getX()) minX=c.getCurrentSlot().getX();
            if (maxX<c.getCurrentSlot().getX()+c.getLength()-1) maxX=c.getCurrentSlot().getX()+c.getLength()-1;

        }
        //juiste volgorde gestapeld?
        for (int i = 0; i < check.size()-1; i++) {
            if (check.get(i).getLength()>check.get(i+1).getLength()) System.out.println("fuck2");
        }
        //zit er een container die buiten interval valt?
        for (int i = minX; i <= maxX; i++) {
            if (i!=X){
                for (Container elders:map[i][Y]) {
                    if (!check.contains(elders)) {
                        if (elders.getCurrentSlot().getX()<minX||
                                elders.getCurrentSlot().getX()+elders.getLength()-1>maxX){
                            System.out.println("fuck");
                        }
                    }
                }
            }
        }
    }

    public void printField(int size){
        int numberDigits=Integer.toString(size).length();
        for (int Z = 0; Z <this.Z; Z++) {
            System.out.println("Z = " + Z + " =========================");
            for (int i = 0; i < Y; i++) {
                System.out.print(i+": ");
                for (int j = 0; j < X; j++) {
                    if (map[j][i].size() > Z) System.out.printf("%0"+numberDigits+"d",map[j][i].get(Z).getId());
                    else {
                        for (int k = 0; k < numberDigits; k++) {
                            System.out.print(" ");
                        }
                    }
                    System.out.print(",");
                }
                System.out.println();
            }
        }
    }


}
