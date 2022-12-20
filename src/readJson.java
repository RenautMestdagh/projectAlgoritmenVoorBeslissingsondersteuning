//import org.json.*;
//
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//public class readJson {
//    private final JSONObject jsonObject;
//    public readJson(String filename) throws IOException, ParseException {
//
//        //JSON parser object to parse read file
//        JSONParser jsonParser = new JSONParser();
//
//        try (FileReader reader = new FileReader(filename)) {
//            this.jsonObject = (JSONObject) jsonParser.parse(reader);
//        }
//    }
//
//    public Map<Integer, Container> returnContainers(){
//
//        Map<Integer, Container> containers = new HashMap<Integer, Container>();
//
//
//
//        for (Object o : (JSONArray)jsonObject.get("containers")) {
//            JSONObject container = (JSONObject) o;
//            Container temp = new Container(Math.toIntExact((Long) container.get("length")));
//            containers.put(Math.toIntExact((Long) container.get("id")), temp);
//        }
//
//        return containers;
//    }
//
//
//
//    public void placeInitialContainers(Field field, Map<Integer, Container> containers){
//
//        for (Object o : (JSONArray)jsonObject.get("assignments")) {
//            JSONObject assignment = (JSONObject) o;
//            JSONArray array = (JSONArray) assignment.get("slot_id");
//            if(array.size()==1)
//                field.placeContainer(Math.toIntExact((Long) assignment.get("container_id")), Math.toIntExact((Long) array.get(0)));
//            else
//                field.placeContainer(Math.toIntExact((Long) assignment.get("container_id")),Math.toIntExact((Long) array.get(0)),Math.toIntExact((Long) array.get(array.size()-1)));
//        }
//    }
//}
