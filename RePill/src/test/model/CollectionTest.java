package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CollectionTest {
    private Collection testCollection;
    private Pill testPill0;

    @BeforeEach
    void runBefore() {
        testCollection = new Collection("My Pills");
        testPill0 = new Pill("My Pill", 10, 2, "stomachache");
    }

    @Test
    void testConstructor() {
        assertEquals("My Pills", testCollection.getName());
        assertTrue(testCollection.getID() > 0);
    }

    @Test
    void testSetID() {
        testCollection.setID(2022);
        assertEquals(2022, testCollection.getID());
    }

    @Test
    void testIsInPillList() {
        boolean result;
        result = testCollection.isInPillList(testPill0);
        assertFalse(result);
        testCollection.addPill(testPill0);
        result = testCollection.isInPillList(testPill0);
        assertTrue(result);
    }

    @Test
    void testGetOnePillNameList() {
        testCollection.addPill(testPill0);
        String result = testCollection.getPillNameList();
        assertEquals("[My Pill]", result);
    }

    @Test
    void testGetMultiplePillNameList() {
        Pill testPill1 = new Pill("Another Pill", 30, 1, "headache");
        testCollection.addPill(testPill0);
        testCollection.addPill(testPill1);

        String result = testCollection.getPillNameList();
        assertEquals("[My Pill, Another Pill]", result);
    }

    @Test
    void testAddPillOnce() {
        testCollection.addPill(testPill0);
        assertEquals(1, testCollection.getCollectionSize());
        assertTrue(testCollection.getPillList().contains(testPill0));
    }

    @Test
    void testAddPillMultipleTimes() {
        Pill testPill1 = new Pill("Another Pill", 30, 1, "headache");
        testCollection.addPill(testPill0);
        testCollection.addPill(testPill1);
        assertEquals(2, testCollection.getCollectionSize());
        assertTrue(testCollection.getPillList().contains(testPill0));
        assertTrue(testCollection.getPillList().contains(testPill1));
    }

    @Test
    void testAddDuplicatePill() {
        Pill testPill1 = new Pill("My Pill", 30, 1, "headache");
        testCollection.addPill(testPill0);
        testCollection.addPill(testPill1);
        assertEquals(1, testCollection.getCollectionSize());
        assertTrue(testCollection.getPillList().contains(testPill0));
        assertFalse(testCollection.getPillList().contains(testPill1));
    }

    @Test
    void testRemovePillOnce() {
        testCollection.addPill(testPill0);
        testCollection.removePill(testPill0);
        assertEquals(0, testCollection.getCollectionSize());
        assertFalse(testCollection.getPillList().contains(testPill0));
    }

    @Test
    void testRemovePillMultipleTimes() {
        Pill testPill1 = new Pill("Another Pill", 30, 1, "headache");
        testCollection.addPill(testPill0);
        testCollection.addPill(testPill1);
        testCollection.removePill(testPill0);
        testCollection.removePill(testPill1);
        assertEquals(0, testCollection.getCollectionSize());
    }

    @Test
    void testRemovePillNotInList() {
        Pill testPill1 = new Pill("Another Pill", 30, 1, "headache");
        testCollection.addPill(testPill0);
        testCollection.removePill(testPill1);
        assertEquals(1, testCollection.getCollectionSize());
        assertTrue(testCollection.getPillList().contains(testPill0));
    }

    @Test
    void testToJson() {
        JSONObject testJson = testCollection.toJson();
        assertEquals(testCollection.getID(), testJson.getInt("id"));
        assertEquals(testCollection.getName(), testJson.getString("name"));
    }
}
