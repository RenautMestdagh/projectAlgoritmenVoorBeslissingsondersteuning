public class Field {

    private int[][][] map;

    public Field(int maxX, int maxY, int maxZ) {
        this.map = new int[maxX][maxY][maxZ];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                for (int k = 0; k < map[i][j].length; k++) {
                    map[i][j][k] = 0;
                }
            }
        }
    }

    public void placeContainer(int id, int slot) {      //plaats container met lengte 1
        int[] xy = this.translateSlotToXY(slot);
        for (int i = 0; i < map[0][0].length; i++) {
            if (map[xy[0]][xy[1]][i] == 0) {
                map[xy[0]][xy[1]][i] = id;
                break;
            }
        }
    }

    public boolean placeContainer(int id, int slot1, int slot2) {            //plaats container met lengte langer dan 1.
                                                                            //containers liggen langs Y-as
        if(slot1>slot2){            //zorgen dat slot1<slot2
            int tmp=slot1;
            slot1=slot2;
            slot2=tmp;
        }
        int[] start = this.translateSlotToXY(slot1);
        int[] end = this.translateSlotToXY(slot2);

        for (int i = 0; i < map[0][0].length; i++) {
            int lengthOke=0;
            boolean possiblyNotOk = false;                                   //als container langs onder niet ondersteund wordt
            for (int j = 0; j >= start[1] - end[1]; j--) {
                if (map[start[0]][end[j+1]][i] == 0) {
                    if(i>0)
                        if(map[start[0]][end[j+1]][i-1] == 0)
                            possiblyNotOk=true;
                    lengthOke++;
                } else
                    break;
                if(lengthOke==end[1]-start[1]+1){ //found a spot where container can be placed
                    if(possiblyNotOk)
                        return false;
                    for (int k = 0; k >= start[1] - end[1]; k--) {
                        map[start[0]][end[k+1]][i] = id;
                    }
                    return true;
                }
            }
        }
        return true;
    }

    private int[] translateSlotToXY (int slot){
        int y = (int) Math.floor((slot-1)/ map.length);
        int x = slot -1 - (slot-1)/ map.length;
        return new int[]{x, y};
    }
}
