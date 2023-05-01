package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

// Represents a functioning table model to display Pills in Collection
public class PillTableModel extends AbstractTableModel {
    private static final String JSON_STORE = "./data/collection.json";
    private static Collection collection;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static List<Pill> pillList;
    private List<String> columnNames;

    // EFFECTS: set up table with column names, collection, json reader, and json writer
    public PillTableModel() {
        collection = new Collection("My Collection");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        pillList = collection.getPillList();
        columnNames = getColumnNamesList();
    }

    // MODIFIES: this
    // EFFECTS: adds new row to table
    public void addRow(Pill pill) {
        collection.addPill(pill);
        pillList = collection.getPillList();
        fireTableRowsInserted(getRowCount(), getRowCount());
    }

    // MODIFIES: this
    // EFFECTS: deletes selected rows from table
    public void deleteRow() {
        for (int rowIndex = pillList.size() - 1; rowIndex >= 0; rowIndex--) {
            if (pillList.get(rowIndex).isSelect()) {
                collection.removePill(pillList.get(rowIndex));
                pillList = collection.getPillList();
//                pillList.remove(rowIndex);
            }
        }
        fireTableDataChanged();
    }

    // MODIFIES: this
    // EFFECTS: loads collection to table
    public void loadCollecitonToTable() {
        loadCollection();
        pillList = collection.getPillList();
        fireTableDataChanged();
    }

    // MODIFIES: this
    // EFFECTS: loads collection from file
    public void loadCollection() {
        try {
            collection = jsonReader.read();
//            System.out.println("Loaded " + collection.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves collection to file
    public void saveCollection() {
        try {
            jsonWriter.open();
            jsonWriter.write(collection);
            jsonWriter.close();
//            System.out.println("Saved " + collection.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: gets column names from Collection fields
    private List<String> getColumnNamesList() {
        List<String> names = new ArrayList<>();
        names.add("Select");
        names.add("Name");
        names.add("Count");
        names.add("Dosage");
        names.add("Description");
        names.add("Schedule");
        return names;
    }

    // EFFECTS: gets value at given index
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return pillList.get(rowIndex).isSelect();
            case 1:
                return pillList.get(rowIndex).getDisplayName();
            case 2:
                return pillList.get(rowIndex).getCount();
            case 3:
                return pillList.get(rowIndex).getDosage();
            case 4:
                return pillList.get(rowIndex).getDescription();
            default:
                return null;
        }
    }

    // MODIFIES: this
    // EFFECTS: changes values at given index to given value
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                pillList.get(rowIndex).setSelect((Boolean) value);
                break;
            case 1:
                pillList.get(rowIndex).setName(value != null ? value.toString() : null);
                break;
            case 2:
                pillList.get(rowIndex).setCount(value != null ? Integer.parseInt(value.toString()) : null);
                break;
            case 3:
                pillList.get(rowIndex).setDosage(value != null ? Integer.parseInt(value.toString()) : null);
                break;
            case 4:
                pillList.get(rowIndex).setDescription(value != null ? value.toString() : null);
                break;
            default:
                break;
        }
    }

    // EFFECTS: returns Class of column
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Boolean.class;
            case 1:
                return String.class;
            case 2:
                return Integer.class;
            case 3:
                return Integer.class;
            default:
                return String.class;
        }
    }

    // EFFECTS: tells if cell is editable
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames.get(column);
    }

    @Override
    public int getRowCount() {
        return pillList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    public String getCollectionName() {
        return collection.getName();
    }
}
