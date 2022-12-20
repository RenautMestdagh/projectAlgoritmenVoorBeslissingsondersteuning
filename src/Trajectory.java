import java.util.*;

public class Trajectory {
    HashMap<Integer, Punt> trajectory = new HashMap<>();       //key: time      value: Punt
    final int safety_value = 5;

    public Trajectory(Crane k, int toX, int toY, int startTime){
        int time = (int)Math.ceil(k.travelTime(toX, toY));
        double vx= (double)k.distX(toX)/time;
        double vY= (double)k.distY(toY)/time;

        for(int i=0;i<=time;i++){
            Punt p = new Punt(k.getX()+vx*i,k.getY()+vY*i);
            trajectory.put(startTime+i, p);
        }
    }

    public boolean isSafe(Trajectory t) {
        int min1 = Collections.min(this.trajectory.keySet());
        int min2 = Collections.min(t.trajectory.keySet());

        int max1 = Collections.max(this.trajectory.keySet());
        int max2 = Collections.max(t.trajectory.keySet());

        if (max1 < min2 || max2 < min1)
            return true; //kraan kan nog in de weg staan
        else {
            if (this.trajectory.size() >= t.trajectory.size()) {
                for (Object tt : t.trajectory.keySet().toArray()) {
                    int tInt = (int) tt;
                    double otherPos = this.trajectory.get(tInt).getX();
                    double thisPos = t.trajectory.get(tInt).getX();
                    if (Math.abs(otherPos - thisPos) < safety_value)
                        return false;
                }
            } else {
                for (Object tt : this.trajectory.keySet().toArray()) {
                    int tInt = (int) tt;
                    double otherPos = t.trajectory.get(tInt).getX();
                    double thisPos = this.trajectory.get(tInt).getX();
                    if (Math.abs(otherPos - thisPos) < safety_value)
                        return false;
                }
            }
            return true;
        }
    }
}
