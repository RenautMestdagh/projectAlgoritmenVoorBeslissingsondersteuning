import java.util.ArrayList;
import java.util.HashMap;

public class Trajectory {
    HashMap<Double, Punt> trajectory = new HashMap<>();       //key: time      value: Punt

    public Trajectory(Kraan k, int toX, int toY, double startTime){
        int time = (int)Math.ceil(k.travelTime(toX, toY));
        double vx= (double)k.distX(toX)/time;
        double vY= (double)k.distY(toY)/time;

        for(int i=0;i<=time;i++){
            Punt p = new Punt(k.getPosX()+vx*i,k.getPosY()+vY*i);
            trajectory.put(startTime+i, p);
        }
        System.out.println(3);

    }
}
