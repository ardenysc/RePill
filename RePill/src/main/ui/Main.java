package ui;

import java.io.FileNotFoundException;

// Main class to instantiate GUI
public class Main {
    // EFFECTS: instantiates GUI
    public static void main(String[] args) {
//        try {
//            new Repill();
//        } catch (FileNotFoundException e) {
//            System.out.println("file not found");
//        }
        new CollectionTable().createUI();
    }
}
