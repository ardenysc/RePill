package model;

import org.json.JSONArray;
import persistence.Writable;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents a collection of pills with an id, a name for the collection and a list of pills
public class Collection implements Writable {
    private static int nextID = 1;
    private int id;
    private String name;
    private List<Pill> pillList;

    /*
     * REQUIRES: length of given name is longer than zero
     * EFFECTS: sets collection id to be a unique positive integer;
     *          sets collection name to given name
     */
    public Collection(String collectionName) {
        id = nextID++;
        name = collectionName;
        pillList = new ArrayList<>();
    }

    /*
     * MODIFIES: this
     * EFFECTS: if given pill is not a duplicate, adds pill to pill list and logs it;
     *          otherwise do nothing
     */
    public void addPill(Pill pillToAdd) {
        if (!isInPillList(pillToAdd)) {
            pillList.add(pillToAdd);
            EventLog.getInstance().logEvent(new Event("New pill added to collection: " + pillToAdd.getDisplayName()));
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: if given pill is in pill list, removes pill from pill list and logs it;
     *          otherwise do nothing
     */
    public void removePill(Pill pillToRemove) {
        if (isInPillList(pillToRemove)) {
            Pill temp = pillToRemove;
            pillList.remove(pillToRemove);
            EventLog.getInstance().logEvent(new Event("Pill removed from collection: " + temp.getDisplayName()));
        }
    }

     /*
      * EFFECTS: if given pill is in pill list, returns true;
      *          otherwise returns false
      */
    public boolean isInPillList(Pill pill) {
        for (Pill p : pillList) {
            if (pill.getName().equals(p.getName())) {
                return true;
            }
        }
        return false;
    }

    /*
     * REQUIRES: id > 0
     * MODIFIES: this
     * EFFECTS: sets id of collection to given id
     */
    public void setID(int id) {
        this.id = id;
    }

    // EFFECTS: returns the size of pill list
    public int getCollectionSize() {
        return pillList.size();
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Pill> getPillList() {
        return pillList;
    }

    // EFFECTS: returns all pill names in pill list
    public String getPillNameList() {
        ArrayList<String> pillNameList = new ArrayList<>();
        for (Pill p : pillList) {
            pillNameList.add(p.getDisplayName());
        }
        return pillNameList.toString();
    }

    // EFFECTS: returns this as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("name", name);
        json.put("pillList", pillListToJson());
        return json;
    }

    // EFFECTS: returns pills in this collection as a JSON array
    private JSONArray pillListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Pill p : pillList) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }
}
