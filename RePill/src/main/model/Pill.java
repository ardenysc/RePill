package model;

import org.json.JSONArray;
import persistence.Writable;
import org.json.JSONObject;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// Represents a pill that has an id, name, display name, count, dosage, description, and schedule list
public class Pill implements Writable {
    private static int nextID = 1;
    protected int id;
    protected String name;
    protected String displayName;
    protected int count;
    protected int dosage;
    protected String description;
    protected List<LocalTime> schedule;
    protected boolean select;

    /*
     * REQUIRES: length of pill name > 0;
     *           count of pill is a non-negative integer;
     *           dosage of pill is a non-negative integer
     * EFFECTS: sets pill id to be a unique positive integer;
     *          sets name of pill to given name;
     *          sets count of pill to given count;
     *          sets dosage of pill to given dosage;
     *          sets description of pill to given description; description can be null
     *          sets empty schedule of pill
     */
    public Pill(String pillName, int count, int dosage, String description) {
        id = nextID++;
        displayName = pillName;
        name = pillName.toLowerCase().replaceAll("\\s+","");
        this.count = count;
        this.dosage = dosage;
        this.description = description;
        this.schedule = new ArrayList<>();
    }

    /*
     * REQUIRES: amount > 0
     * MODIFIES: this
     * EFFECTS: increments count of pills by given amount
     */
    public void incrementCount(int amount) {
        count += amount;
    }

    /*
     * REQUIRES: amount > 0 and amount <= count of pills
     * MODIFIES: this
     * EFFECTS: decrements count of pills by given amount
     */
    public void decrementCount(int amount) {
        count -= amount;
    }

    /*
     * REQUIRES: id > 0
     * MODIFIES: this
     * EFFECTS: sets id of pill to given id
     */
    public void setID(int id) {
        this.id = id;
    }

    /*
     * REQUIRES: name length > 0
     * MODIFIES: this
     * EFFECTS: sets name of pill to given name
     */
    public void setName(String name) {
        this.displayName = name;
        this.name = name.toLowerCase().replaceAll("\\s+","");
    }

    /*
     * REQUIRES: count is a non-negative integer
     * MODIFIES: this
     * EFFECTS: sets count of pill to given count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /*
     * REQUIRES: dosage is a non-negative integer
     * MODIFIES: this
     * EFFECTS: sets dosage of pill to given dosage
     */
    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets description of pill to given description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /*
     * MODIFIES: this
     * EFFECTS: if given time is not already in schedule, adds given time to schedule;
     *          otherwise, does nothing
     */
    public void addSchedule(LocalTime time) {
        if (!schedule.contains(time)) {
            schedule.add(time);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: if given time is already in schedule, removes given time from schedule;
     *          otherwise, does nothing
     */
    public void removeSchedule(LocalTime time) {
        schedule.remove(time);
    }

    /*
     * MODIFIES: this
     * EFFECTS: changes original time from schedule to new time
     */
    public void changeSchedule(LocalTime originalTime, LocalTime newTime) {
        int index = schedule.indexOf(originalTime);
        schedule.set(index, newTime);
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getCount() {
        return count;
    }

    public int getDosage() {
        return dosage;
    }

    public String getDescription() {
        return description;
    }

    public List<LocalTime> getSchedule() {
        return schedule;
    }

    public boolean isSelect() {
        return select;
    }

    // EFFECTS: sets select to given boolean value
    public void setSelect(boolean select) {
        this.select = select;
    }

    // EFFECTS: returns this as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("name", name);
        json.put("displayName", displayName);
        json.put("count", count);
        json.put("dosage", dosage);
        json.put("description", description);
        json.put("schedule", scheduleToJason());
        return json;
    }

    // EFFECTS: returns times in schedule as a JSON array
    public JSONArray scheduleToJason() {
        JSONArray jsonArray = new JSONArray();

        for (LocalTime s : schedule) {
            jsonArray.put(s);
        }

        return jsonArray;
    }
}
