import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        File folder=new File("initial");
        for (File filentry: folder.listFiles()){
            Yard yard=new Yard(filentry.getPath());
        }
        readJson input = new readJson("start.json");

        Map<Integer, Container> containers = input.returnContainers();

        int[] maxDimentions = input.getDimentions();
        int maxStackHeight = 10;
        Field field = new Field(maxDimentions[0]+1, maxDimentions[1]+1, maxStackHeight);

        input.placeInitialContainers(field, containers);





        //---------------------------------------------------------------


//        Trajectory t1 = new Trajectory(k1, 100,100, 3);
//        Trajectory t2 = new Trajectory(k1, 0,100, 3);
//
//        System.out.println(t1.isSafe(t2));
//
//        Trajectory t3 = new Trajectory(k1, 100,100, 3);
//        Trajectory t4 = new Trajectory(k1, 0,100, 5000);
//
//        System.out.println(t3.isSafe(t4));

        Trajectory t5 = new Trajectory(k1, 49,100, 3);
        Trajectory t6 = new Trajectory(k2, 51,100, 3);

        //System.out.println(t5.isSafe(t6));
    }
}