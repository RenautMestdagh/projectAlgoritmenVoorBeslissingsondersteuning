import java.io.File;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException{
        File file1 = new File("instances/1t/TerminalA_20_10_3_2_100.json");
        File file2 = new File("instances/1t/targetTerminalA_20_10_3_2_100.json");
        Yard yard=JSONHandler.getYard(file1,file2);
        yard.print();
        System.out.println("%CraneId\tContainerId\tPickupTime\tEndTime\tPickupPosX\tPickupPosY\tEndPosX\tEndPosY;");
        yard.findSolution();
        yard.print();
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
//
//        Trajectory t5 = new Trajectory(k1, 49,100, 3);
//        Trajectory t6 = new Trajectory(k2, 51,100, 3);

        //System.out.println(t5.isSafe(t6));
    }
}