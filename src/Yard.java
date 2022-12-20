import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.*;

public class Yard {
    private final String name;
    private final int length;
    private final int width;
    private final int maxHeight;
    private final HashMap<Integer, Crane> cranes;
    private final HashMap<Integer,Slot> slots;
    private final HashMap<Integer,Container> containers;
    private final Field field;
    private List<Assignment> assignments;

    public Yard(String path) throws IOException {
        String string= Files.readString(Path.of(path));
        JSONObject obj=new JSONObject(string);
        this.name=(String)obj.get("name");
        this.length=(int) obj.get("length");
        this.width=(int) obj.get("width");
        this.maxHeight=(int) obj.get("maxheight");

        JSONArray slotsJSON=obj.getJSONArray("slots");
        JSONArray cranesJSON=obj.getJSONArray("cranes");
        JSONArray containersJSON=obj.getJSONArray("containers");
        JSONArray assignmentsJSON=obj.getJSONArray("assignments");

        this.cranes= new HashMap<>();
        for (int i = 0; i < cranesJSON.length(); i++) {
            JSONObject o=cranesJSON.getJSONObject(i);
            Crane crane=new Crane(
                    (int) o.get("id"),
                    (double) (o.get("x")),
                    (double) (o.get("y")),
                    (int) o.get("xmin"),
                    (int) o.get("ymin"),
                    (int) o.get("xmax"),
                    (int) o.get("ymax"),
                    (int) o.get("xspeed"),
                    (int) o.get("yspeed")
            );
            cranes.put(crane.getId(),crane);

        }

        this.slots=new HashMap<>();
        for (int i = 0; i < slotsJSON.length(); i++) {
            JSONObject o=slotsJSON.getJSONObject(i);
            Slot slot=new Slot(
                    (int) o.get("id"),
                    (int) o.get("x"),
                    (int) o.get("y")

            );
            slots.put(slot.getId(),slot);
        }

        this.containers=new HashMap<>();
        for (int i = 0; i < containersJSON.length(); i++) {
            JSONObject o=containersJSON.getJSONObject(i);
            Container container=new Container(
                    (int) o.get("id"),
                    (int) o.get("length")
            );
            containers.put(container.getId(),container);
        }

        this.assignments=new ArrayList<>();
        for (int i = 0; i < assignmentsJSON.length(); i++) {
            JSONObject o=assignmentsJSON.getJSONObject(i);
            Container container=containers.get((int) o.get("container_id"));
            Slot slot= slots.get((int) o.get("container_id"));
            assignments.add(new Assignment(slot,container));
        }
        this.field=new Field(length,width,maxHeight);
        field.placeInitialContainers(this.assignments);

    }
    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public HashMap<Integer, Crane> getCranes() {
        return cranes;
    }

    public HashMap<Integer, Slot> getSlots() {
        return slots;
    }

    public HashMap<Integer, Container> getContainers() {
        return containers;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }
    public int[] getDimentions(){
        return new int[]{length, width};
    }



}
