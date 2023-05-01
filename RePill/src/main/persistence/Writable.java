package persistence;

import org.json.JSONObject;

// Contains method that enables writing data
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
