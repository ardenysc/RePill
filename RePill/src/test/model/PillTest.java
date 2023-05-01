package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PillTest {
    private Pill testPill;

    @BeforeEach
    void runBefore() {
        testPill = new Pill("My Pill", 10, 2, "headache");
    }

    @Test
    void testConstructor() {
        assertEquals("My Pill", testPill.getDisplayName());
        assertEquals("mypill", testPill.getName());
        assertEquals(10, testPill.getCount());
        assertEquals(2, testPill.getDosage());
        assertTrue(testPill.getID() > 0);
    }

    @Test
    void testSetID() {
        testPill.setID(2022);
        assertEquals(2022, testPill.getID());
    }

    @Test
    void testSetName() {
        testPill.setName("Some other name");
        assertEquals("someothername", testPill.getName());
        assertEquals("Some other name", testPill.getDisplayName());
    }

    @Test
    void testSetCount() {
        testPill.setCount(10);
        assertEquals(10, testPill.getCount());
    }

    @Test
    void testSetDosage() {
        testPill.setDosage(10);
        assertEquals(10, testPill.getDosage());
    }

    @Test
    void testSetSelect() {
        testPill.setSelect(true);
        assertTrue(testPill.isSelect());
    }

    @Test
    void testSetDescription() {
        testPill.setDescription("some other description");
        assertEquals("some other description", testPill.getDescription());
    }

    @Test
    void testAddScheduleOnce() {
        LocalTime schedule = LocalTime.of(10, 10);
        testPill.addSchedule(schedule);
        assertEquals(1, testPill.getSchedule().size());
        assertEquals(LocalTime.of(10, 10), testPill.getSchedule().get(0));
    }

    @Test
    void testAddScheduleMultipleTimes() {
        LocalTime schedule0 = LocalTime.of(10, 10);
        LocalTime schedule1 = LocalTime.of(20, 30);
        LocalTime schedule2 = LocalTime.of(20, 30);
        testPill.addSchedule(schedule0);
        testPill.addSchedule(schedule1);
        testPill.addSchedule(schedule2);
        assertEquals(2, testPill.getSchedule().size());
        assertEquals(LocalTime.of(10, 10), testPill.getSchedule().get(0));
        assertEquals(LocalTime.of(20, 30), testPill.getSchedule().get(1));
    }

    @Test
    void testRemoveScheduleOnce() {
        LocalTime schedule0 = LocalTime.of(10, 10);
        testPill.addSchedule(schedule0);
        testPill.removeSchedule(schedule0);
        assertEquals(0, testPill.getSchedule().size());
    }

    @Test
    void testRemoveScheduleMultipleTimes() {
        LocalTime schedule0 = LocalTime.of(10, 10);
        LocalTime schedule1 = LocalTime.of(20, 30);
        testPill.addSchedule(schedule0);
        testPill.addSchedule(schedule1);
        testPill.removeSchedule(schedule0);
        testPill.removeSchedule(schedule1);
        assertEquals(0, testPill.getSchedule().size());
    }

    @Test
    void testChangeScheduleOnce() {
        LocalTime schedule0 = LocalTime.of(10, 10);
        LocalTime newTime = LocalTime.of(0, 0);
        testPill.addSchedule(schedule0);
        testPill.changeSchedule(schedule0, newTime);
        assertEquals(newTime, testPill.getSchedule().get(0));
    }

    @Test
    void testChangeScheduleMultipleTimes() {
        LocalTime schedule0 = LocalTime.of(10, 10);
        LocalTime schedule1 = LocalTime.of(20, 30);
        LocalTime newTime = LocalTime.of(0, 0);
        testPill.addSchedule(schedule0);
        testPill.addSchedule(schedule1);
        testPill.changeSchedule(schedule0, newTime);
        testPill.changeSchedule(schedule1, newTime);
        assertEquals(newTime, testPill.getSchedule().get(0));
        assertEquals(newTime, testPill.getSchedule().get(1));
    }

    @Test
    void testIncrementCountOnce() {
        testPill.incrementCount(10);
        assertEquals(20, testPill.getCount());
    }

    @Test
    void testIncrementCountMultipleTimes() {
        testPill.incrementCount(10);
        testPill.incrementCount(20);
        assertEquals(40, testPill.getCount());
    }

    @Test
    void testDecrementCountOnce() {
        testPill.decrementCount(2);
        assertEquals(8, testPill.getCount());
    }

    @Test
    void testDecrementCountMultipleTimes() {
        testPill.decrementCount(2);
        testPill.decrementCount(3);
        assertEquals(5, testPill.getCount());
    }

    @Test
    void testToJson() {
        JSONObject testJson = testPill.toJson();
        assertEquals(testPill.getID(), testJson.getInt("id"));
        assertEquals(testPill.getName(), testJson.getString("name"));
        assertEquals(testPill.getDisplayName(), testJson.getString("displayName"));
        assertEquals(testPill.getCount(), testJson.getInt("count"));
        assertEquals(testPill.getDosage(), testJson.getInt("dosage"));
        assertEquals(testPill.getDescription(), testJson.getString("description"));
    }
}