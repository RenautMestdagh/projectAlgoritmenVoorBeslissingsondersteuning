import java.io.File;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException{
        File folder=new File("initial");
        for (File filentry: folder.listFiles()){
            Yard yard=new Yard(filentry.getPath());
        }






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