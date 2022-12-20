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

    public boolean trySimpleMove(Assignment target) {
        Container c=target.getContainer();
        if (c.getCurrentSlot()==target.getSlot()){
            return true;
        }
        if (c.getId()==90||c.getId()==96){
            System.out.println("checkpoint");
        }
        int currentX=c.getCurrentSlot().getX();
        int currentY=c.getCurrentSlot().getY();

        if (map[currentX][currentY].peek()!=c){
            return false;
        }
        int targetX=target.getSlot().getX();
        int targetY=target.getSlot().getY();
        int lengte=c.getLength();
        if (checkIfPlacable(targetX,targetY,lengte)){
            for (int i = 0; i < lengte; i++) {
                map[currentX+i][currentY].pop();
                map[targetX+i][targetY].push(c);
            }
            c.setCurrentSlot(c.getTarget());
            System.out.println("Moved container "+c.getId()+ " from: "+currentX+","+currentY+" to "+targetX+","+targetY);
            return true;
        }
        return false;
    }
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
            for (int i = 0; i < lengte; i++) {
                map[currentX+i][currentY].pop();
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
    public boolean liftFromTarget(Assignment target, int cranes) {
        Container c=target.getContainer();
        if (c.getCurrentSlot()==target.getSlot()){
            return true;
        }
        int currentX=c.getCurrentSlot().getX();
        int currentY=c.getCurrentSlot().getY();
        Stack<Container> currentColumn=map[currentX][currentY];
        if (currentColumn.size()-cranes>currentColumn.indexOf(c)){
            return false;
        }

        return false;
    }
    public boolean checkIfPlacable(int x, int y, int lengte){
        Stack<Container> containerStack=map[x][y];
        int size=containerStack.size();

        if (size>0){
            if (containerStack.peek().getLength()>lengte) return false;
        }
        for (int i = x; i < x+lengte-1; i++) {
            if (map[i][y].size()!=size) return false;
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
