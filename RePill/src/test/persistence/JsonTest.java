package persistence;

import model.*;
import org.json.JSONArray;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonTest {
    protected void checkPill(String name, String displayName, int count, int dosage, String description, JSONArray schedule, Pill pill) {
        assertEquals(name, pill.getName());
        assertEquals(displayName, pill.getDisplayName());
        assertEquals(count, pill.getCount());
        assertEquals(dosage, pill.getDosage());
        assertEquals(description, pill.getDescription());
        assertTrue(schedule.similar(pill.scheduleToJason()));
    }
}