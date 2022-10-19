public class Main {
    public static void main(String[] args) {

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

        System.out.println(t5.isSafe(t6));
    }
}