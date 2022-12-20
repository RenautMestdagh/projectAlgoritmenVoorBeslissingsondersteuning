import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Field {

    private int[][][] map; //is zyx
    public void placeInitialContainers(List<Assignment> assignments){
        List<Container>[][] containers=new ArrayList[map[0][0].length][map[0].length];//hier xy structuur
        for (int i = 0; i < containers.length; i++) {
            for (int j = 0; j < containers[0].length; j++) {
                containers[i][j]=new ArrayList<Container>();
            }
        }
//        System.out.println(containers.getClass().getSimpleName());
//        System.out.println(containers[0].getClass().getSimpleName());
//        System.out.println(containers[0][0].getClass().getName());
        for (Assignment a:assignments) {
            Container c=a.getContainer();
            Slot s=a.getSlot();
            int lengte=c.getLength();
            containers[s.getX()][s.getY()].add(c);
            if (c.getLength()>1)containers[s.getX()+1][s.getY()].add(c);
        }
        for (int i = 0; i < containers.length; i++) {
            for (int j = 0; j < containers[0].length; j++) {
                if (containers[i][j].size()>1) System.out.println("meer dan 1 container moet algo voor schikken");
                else if (containers[i][j].size()>0)map[0][j][i]=containers[i][j].get(0).getId();
            }
        }
        System.out.println(map.toString());
    }

    public Field(int maxX, int maxY, int maxZ) {
        
        this.map = new int[maxZ][maxY][maxX];
        for (int[][] i:this.map) {
            for (int [] j:i) {
                Arrays.fill(j,0);
            }

        }
    }

    public boolean placeContainer(int id, int slot) {      //plaats container met lengte 1
        int[] xy = this.translateSlotToXY(slot);
        for (int i = 0; i < map[0][0].length; i++) {    //telkens lijn verhogen (verticaal)
            if(i>0){
                if(xy[1]>0)
                    if(map[xy[0]][xy[1]-1][i-1] == map[xy[0]][xy[1]][i-1])  //langs linkerkant checken als container eronder niet langer uitsteekt
                        return false;
                if(xy[1]<map[0].length-1)
                    if(map[xy[0]][xy[1]][i-1] == map[xy[0]][xy[1]+1][i-1])  //langs rechterkant checken als container eronder niet langer uitsteekt
                        return false;
            }
            if (map[xy[0]][xy[1]][i] == 0) {    //als er zich geen container bevindt
                map[xy[0]][xy[1]][i] = id;
                return true;
            }
        }
        return false;
    }

    public boolean placeContainer(int id, int slot1, int slot2) {
        //plaats container met lengte langer dan 1.
        //containers liggen langs Y-as
        if(slot1>slot2){            //zorgen dat slot1<slot2
            int tmp=slot1;
            slot1=slot2;
            slot2=tmp;
        }
        int[] start = this.translateSlotToXY(slot1);
        int[] end = this.translateSlotToXY(slot2);

        for (int i = 0; i < map[0][0].length; i++) {        //een lijn verhogen (verticaal)
            int lengthOke=0;
            for (int j = 0; j >= start[1] - end[1]; j--) {      //de lijn aflopen (horizontaal)
                if(i>0){
                    if(start[1]>0)
                        if(map[start[0]][start[1]][i-1] == map[start[0]][start[1]-1][i-1])
                            return false;
                    if(end[1]<map[0].length-1)
                        if(map[start[0]][end[1]][i-1] == map[start[0]][end[1]+1][i-1])
                            return false;
                }
                if (map[start[0]][end[1]+j][i] == 0) {          //kijken als er zich geen container bevindt
                    if(i>0)
                        if(map[start[0]][end[1]+j][i-1] == 0)   //de container wordt niet ondersteund langs onder
                            return false;
                    lengthOke++;
                } else
                    break;
                if(lengthOke==end[1]-start[1]+1){ //found a spot where container can be placed
                    for (int k = 0; k >= start[1] - end[1]; k--) {
                        map[start[0]][end[1]+k][i] = id;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private int[] translateSlotToXY (int slot){
        int y = (int) Math.floor((float)(slot-1)/ map.length);
        int x = slot -1 - (slot-1)/ map.length;
        return new int[]{x, y};
    }
}
