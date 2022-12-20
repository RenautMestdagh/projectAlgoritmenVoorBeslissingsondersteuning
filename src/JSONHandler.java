import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

public class JSONHandler {
    public static Yard getYard(File instance,File targetFile) throws IOException {
        Yard yard=getYard(instance);

        String stringTarget= Files.readString(targetFile.toPath());
        JSONObject objTarget=new JSONObject(stringTarget);
        JSONArray targetJSON=objTarget.getJSONArray("assignments");

        ArrayList<Assignment> target= new ArrayList<>();
        HashMap<Integer,Container>containers=yard.getContainers();
        HashMap<Integer,Slot>slots=yard.getSlots();

        for (int i = 0; i < targetJSON.length(); i++) {
            JSONObject o=targetJSON.getJSONObject(i);
            Container container=containers.get(o.getInt("container_id"));
            Slot slot= slots.get(o.getInt("slot_id"));
            target.add(new Assignment(slot,container));
        }
        yard.setTarget(target);
        for (Assignment a:target) {
            a.getContainer().setTarget(a.getSlot());
        }
        return yard;
    }
    public static Yard getYard(File instance) throws IOException {
        Yard yard=new Yard();
        String string= Files.readString(instance.toPath());
        JSONObject obj=new JSONObject(string);


        yard.setName(obj.getString("name"));
        yard.setLength(obj.getInt("length"));
        yard.setWidth(obj.getInt("width"));
        yard.setMaxHeight(obj.getInt("maxheight"));

        JSONArray slotsJSON=obj.getJSONArray("slots");
        JSONArray cranesJSON=obj.getJSONArray("cranes");
        JSONArray containersJSON=obj.getJSONArray("containers");
        JSONArray assignmentsJSON=obj.getJSONArray("assignments");

        HashMap<Integer,Crane> cranes= new HashMap<>();
        for (int i = 0; i < cranesJSON.length(); i++) {
            JSONObject o=cranesJSON.getJSONObject(i);
            Crane crane=new Crane(
                    o.getInt("id"),
                    o.getDouble("x"),
                    o.getDouble("y"),
                    o.getInt("xmin"),
                    o.getInt("ymin"),
                    o.getInt("xmax"),
                    o.getInt("ymax"),
                    o.getInt("xspeed"),
                    o.getInt("yspeed")
            );
            cranes.put(crane.getId(),crane);
        }
        yard.setCranes(cranes);

        HashMap<Integer,Slot> slots=new HashMap<>();
        for (int i = 0; i < slotsJSON.length(); i++) {
            JSONObject o=slotsJSON.getJSONObject(i);
            Slot slot=new Slot(
                    o.getInt("id"),
                    o.getInt("x"),
                    o.getInt("y")

            );
            slots.put(slot.getId(),slot);
        }
        yard.setSlots(slots);

        HashMap<Integer,Container>containers=new HashMap<>();
        for (int i = 0; i < containersJSON.length(); i++) {
            JSONObject o=containersJSON.getJSONObject(i);
            Container container=new Container(
                    o.getInt("id"),
                    o.getInt("length")
            );
            containers.put(container.getId(),container);
        }
        yard.setContainers(containers);

        ArrayList<Assignment> assignments=new ArrayList<>();
        for (int i = 0; i < assignmentsJSON.length(); i++) {
            JSONObject o=assignmentsJSON.getJSONObject(i);
            Container container=containers.get(o.getInt("container_id"));
            Slot slot= slots.get(o.getInt("slot_id"));
            container.setCurrentSlot(slot);
            assignments.add(new Assignment(slot,container));
        }
        yard.setAssignments(assignments);

        yard.setField(new FieldV2(yard.getLength(), yard.getWidth(), yard.getMaxHeight()));
        yard.initialize();

        return yard;
    }
}
