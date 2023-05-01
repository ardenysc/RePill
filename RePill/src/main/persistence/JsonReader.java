package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Collection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCollection(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    // throws IOException if an error occurs reading data from file
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses collection from JSON object and returns it
    private Collection parseCollection(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int id = jsonObject.getInt("id");
        Collection collection = new Collection(name);
        collection.setID(id);
        addPills(collection, jsonObject);
        return collection;
    }

    // MODIFIES: collection
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addPills(Collection c, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("pillList");
        for (Object json : jsonArray) {
            JSONObject nextPill = (JSONObject) json;
            addPill(c, nextPill);
        }
    }

    // MODIFIES: collection
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addPill(Collection collection, JSONObject jsonObject) {
        int id = jsonObject.getInt("id");
        String name = jsonObject.getString("name");
        String displayName = jsonObject.getString("displayName");
        int count = jsonObject.getInt("count");
        int dosage = jsonObject.getInt("dosage");
        String description = jsonObject.getString("description");

        Pill pill = new Pill(name, count, dosage, description);
        pill.setID(id);
        pill.setName(displayName);

        JSONArray jsonArray = jsonObject.getJSONArray("schedule");
        addSchedule(pill, jsonArray);

        collection.addPill(pill);
    }

    // MODIFIES: pill
    // EFFECTS: parses schedule from JSON object and adds it to pill
    private void addSchedule(Pill pill, JSONArray jsonArray) {
        for (Object json : jsonArray) {
            String timeString = (String) json;
            String[] hourMinute = timeString.split(":");
            int hour = Integer.parseInt(hourMinute[0]);
            int minute = Integer.parseInt(hourMinute[1]);
            LocalTime nextSchedule = LocalTime.of(hour, minute);
            pill.addSchedule(nextSchedule);
        }
    }
}
