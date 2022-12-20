import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.json.*;

public class Yard {
    private List<Crane> cranes;
    private List<Slot> slots;
    private final List<Container> containers;
//    private final List<Assignment> assignments;

    public Yard(String path) throws IOException {
        String string= Files.readString(Path.of(path));
        JSONObject obj=new JSONObject(string);
        JSONArray slotsJSON=obj.getJSONArray("slots");
        JSONArray cranesJSON=obj.getJSONArray("cranes");
        JSONArray containersJSON=obj.getJSONArray("containers");
        JSONArray assignmentsJSON=obj.getJSONArray("assignments");
        System.out.println("assignments = " + assignmentsJSON);
        System.out.println("containers = " + containersJSON);
        System.out.println("cranes = " + cranesJSON);
        System.out.println("slots = " + slotsJSON);
        this.cranes=new ArrayList<>();
        for (int i = 0; i < cranesJSON.length(); i++) {
            JSONObject o=cranesJSON.getJSONObject(i);
            cranes.add(new Crane(
                    (int) o.get("id"),
                    (double) o.get("x"),
                    (double) o.get("y"),
                    (int) o.get("xmin"),
                    (int) o.get("ymin"),
                    (int) o.get("xmax"),
                    (int) o.get("ymax"),
                    (int) o.get("xspeed"),
                    (int) o.get("yspeed")
            ));
        }

        this.slots=new ArrayList<>();
        for (int i = 0; i < slotsJSON.length(); i++) {
            JSONObject o=slotsJSON.getJSONObject(i);
            slots.add(new Slot(
                    (int) o.get("id"),
                    (int) o.get("x"),
                    (int) o.get("y")

            ));
        }

        this.containers=new ArrayList<>();
        for (int i = 0; i < containersJSON.length(); i++) {
            JSONObject o=containersJSON.getJSONObject(i);
            containers.add(new Container(
                    (int) o.get("id"),
                    (int) o.get("length")
            ));
        }
    }

}
