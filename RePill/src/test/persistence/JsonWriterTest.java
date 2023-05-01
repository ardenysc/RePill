package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Collection collection = new Collection("My collection");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCollection() {
        try {
            Collection collection = new Collection("My collection");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCollection.json");
            writer.open();
            writer.write(collection);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCollection.json");
            collection = reader.read();
            assertEquals("My collection", collection.getName());
            assertEquals(0, collection.getCollectionSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCollection() {
        try {
            Collection collection = new Collection("My collection");
            collection.addPill(new Pill("My Pill", 10, 2, "headache"));
            collection.addPill(new Pill("Another Pill", 20, 1, "stomachache"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCollection.json");
            writer.open();
            writer.write(collection);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCollection.json");
            collection = reader.read();
            assertEquals("My collection", collection.getName());
            List<Pill> pillList = collection.getPillList();
            assertEquals(2, pillList.size());
            checkPill("mypill", "My Pill", 10, 2, "headache", pillList.get(0).scheduleToJason(), pillList.get(0));
            checkPill("anotherpill", "Another Pill", 20, 1, "stomachache", pillList.get(1).scheduleToJason(), pillList.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}