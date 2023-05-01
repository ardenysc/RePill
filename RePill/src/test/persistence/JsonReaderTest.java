package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Collection wr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCollection() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCollection.json");
        try {
            Collection collection = reader.read();
            assertEquals("My collection", collection.getName());
            assertEquals(0, collection.getCollectionSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCollection() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCollection.json");
        try {
            Collection collection = reader.read();
            assertEquals("My collection", collection.getName());
            List<Pill> pillList = collection.getPillList();
            assertEquals(2, pillList.size());
            checkPill("mypill", "My Pill", 10, 2, "headache", pillList.get(0).scheduleToJason(), pillList.get(0));
            checkPill("anotherpill", "Another Pill", 20, 1, "stomachache", pillList.get(1).scheduleToJason(), pillList.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}