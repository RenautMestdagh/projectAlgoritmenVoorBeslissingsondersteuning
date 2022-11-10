import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {

        readJson input = new readJson("start.json");

        Map<Integer, Container> containers = input.returnContainers();

        int[] maxDimentions = input.getDimentions();
        int maxStackHeight = 10;
        Field field = new Field(maxDimentions[0]+1, maxDimentions[1]+1, maxStackHeight);

        input.placeInitialContainers(field, containers);






        //---------------------------------------------------------------
        Kraan k1=new Kraan(0.75,0.75,0,0);
        Kraan k2=new Kraan(0.70,0.70,100,0);

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