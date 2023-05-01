package ui;

import model.Collection;
import model.Pill;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Scanner;

// represents rePill (name of project) console application
public class Repill {
    private static final String JSON_STORE = "./data/collection.json";
    private Scanner input;
    private Collection collection;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the rePill application
    //          if data load fails, throws FileNotFoundException
    public Repill() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runRepill();
    }

    /*
     * MODIFIES: this
     * EFFECTS: starts the application with main menu
     *          if data load fails, throws FileNotFoundException
     */
    private void runRepill() throws FileNotFoundException {
        input = new Scanner(System.in);
        input.useDelimiter("\n");

        chooseMainMenu();

        System.out.println("\nEnd");
    }

    /*
     * EFFECTS: prompts user input to choose from main menu and returns it
     */
    private int promptMainMenuChoice() {
        viewMainMenu();
        int command = input.nextInt();
        input.nextLine();
        return command;
    }

    /*
     * EFFECTS: displays main menu
     */
    private void viewMainMenu() {
        System.out.println("\nWhat would you like to do? ");
        System.out.println("\t1: New collection");
        System.out.println("\t2: Load collection");
        System.out.println("\t3: Quit");
    }

    /*
     * MODIFIES: this
     * EFFECTS: processes choice from main menu
     *          if data load fails, throws FileNotFoundException
     */
    private void chooseMainMenu() throws FileNotFoundException {
        int command = promptMainMenuChoice();
        switch (command) {
            case 1:
                newCollection();
                chooseCollectionMenu();
                break;
            case 2:
                loadCollection();
                if (collection == null) {
                    throw new FileNotFoundException();
                }
                chooseCollectionMenu();
                break;
            case 3:
                break;
            default:
                System.out.println("choose from menu");
                break;
        }
    }

    /*
     * REQUIRES: collection name length > 0
     * MODIFIES: this
     * EFFECTS: prompts user to input new collection information
     *          and displays the new collection
     */
    private void newCollection() {
        System.out.println("\nEnter your collection's name: ");
        String collectionName = input.nextLine();
        collection = new Collection(collectionName);
        System.out.println("Your collection's name is " + collection.getName());
    }

    /*
     * REQUIRES: pill name length > 0
     * MODIFIES: this
     * EFFECTS: prompts user to input new pill information
     *          and returns the new pill
     */
    private Pill newPill() {
        System.out.println("\nEnter name of pill: ");
        String pillName = input.nextLine();
        System.out.println("Enter count of pill: ");
        int pillCount = input.nextInt();
        input.nextLine();
        System.out.println("Enter dosage of pill: ");
        int pillDosage = input.nextInt();
        input.nextLine();
        System.out.println("Enter description of pill: ");
        String pillDescription = input.nextLine();

        return new Pill(pillName, pillCount, pillDosage, pillDescription);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds new pill to collection and lets the user know
     */
    private void addPillToCollection() {
        Pill pillToAdd = newPill();
        collection.addPill(pillToAdd);
        System.out.println(pillToAdd.getDisplayName() + " added");
    }

    /*
     * MODIFIES: this
     * EFFECTS: prompts for pill name
     *          if collection is empty lets user know and does nothing
     *          if pill is in collection, removes it and prints out that it has been removed;
     *          if not, prints out that pill is not in collection
     */
    private void removePillFromCollection() {
        if (collection.getCollectionSize() > 0) {
            viewCollection();
            System.out.println("\nEnter name of pill you wish to remove: ");
            String pillNameToRemove = input.nextLine();
            Pill pillToRemove = searchPill(pillNameToRemove.toLowerCase().replaceAll("\\s+",""));
            if (pillToRemove != null) {
                collection.removePill(pillToRemove);
                System.out.println(pillNameToRemove + " removed");
            } else {
                System.out.println(pillNameToRemove + " is not in collection");
            }
        } else {
            System.out.println("Collection is empty");
        }
    }

    /*
     * EFFECTS: prints out pills in collection
     */
    private void viewCollection() {
        System.out.println("\n-----" + collection.getName() + "-----");
        System.out.println(collection.getPillNameList());
        System.out.println("---------------");
    }

    /*
     * EFFECTS: prompts user input to choose from collection menu and returns it
     */
    private int promptCollectionMenuChoice() {
        viewCollection();
        viewCollectionMenu();
        int command = input.nextInt();
        input.nextLine();
        return command;
    }

    /*
     * MODIFIES: this
     * EFFECTS: processes choice from collection menu
     */
    @SuppressWarnings("methodlength")
    private void chooseCollectionMenu() {
        boolean keepGoing = true;
        while (keepGoing) {
            switch (promptCollectionMenuChoice()) {
                case 1:
                    addPillToCollection();
                    break;
                case 2:
                    removePillFromCollection();
                    break;
                case 3:
                    if (collection.getCollectionSize() > 0) {
                        choosePillMenu();
                    } else {
                        System.out.println("Collection is empty");
                    }
                    break;
                case 4:
                    saveCollection();
                    break;
                case 5:
                    keepGoing = false;
                    break;
                default:
                    System.out.println("Choose from the menu");
            }
        }
    }

    /*
     * EFFECTS: searches pill from collection;
     *          if pill in collection, returns it;
     *          if not, returns null
     */
    private Pill searchPill(String pillName) {
        for (Pill p : collection.getPillList()) {
            if (p.getName().equalsIgnoreCase(pillName)) {
                return p;
            }
        }
        return null;
    }

    /*
     * EFFECTS: prompts user to chooses pill from collection
     *          and returns it
     */
    private Pill choosePill() {
        boolean pillIsNull = true;
        String chosenPillName = null;
        while (pillIsNull) {
            System.out.println("\nChoose pill from: ");
            viewCollection();
            chosenPillName = input.nextLine();
            chosenPillName = chosenPillName.replaceAll("\\s+", "");
            if (searchPill(chosenPillName) == null) {
                System.out.println("Enter correct name");
            } else {
                pillIsNull = false;
            }
        }
        return searchPill(chosenPillName);
    }

    /*
     * EFFECTS: displays information about pill
     */
    private void viewPill(Pill chosenPill) {
        System.out.println("\nName: " + chosenPill.getDisplayName());
        System.out.println("Count: " + chosenPill.getCount());
        System.out.println("Dosage: " + chosenPill.getDosage());
        System.out.println("Description: " + chosenPill.getDescription());
        System.out.println("Schedule: " + chosenPill.getSchedule());
    }

    /*
     * EFFECTS: displays menu for collection
     */
    private void viewCollectionMenu() {
        System.out.println("\nWhat would you like to do? ");
        System.out.println("\t1: Add new pill to collection");
        System.out.println("\t2: Remove pill from collection");
        System.out.println("\t3: View pill from collection");
        System.out.println("\t4: Save collection");
        System.out.println("\t5: Quit");
    }


    /*
     * EFFECTS: displays menu for pill
     */
    private void viewPillMenu() {
        System.out.println("\nWhat would you like to do? ");
        System.out.println("\t1: Increment count");
        System.out.println("\t2: Decrement count");
        System.out.println("\t3: Change name");
        System.out.println("\t4: Change dosage");
        System.out.println("\t5: Change description");
        System.out.println("\t6: Manage schedule");
        System.out.println("\t7: Quit");
    }

    /*
     * EFFECTS: manages schedule change and displays result
     */
    private void manageSchedule(Pill chosenPill) {
        chooseScheduleMenu(chosenPill);
        viewPill(chosenPill);
    }

    /*
     * EFFECTS: displays menu for schedule
     */
    private void viewScheduleMenu() {
        System.out.println("\nWhat would you like to do? ");
        System.out.println("\t1: Add schedule");
        System.out.println("\t2: Remove schedule");
        System.out.println("\t3: Change schedule");
        System.out.println("\t4: Quit");
    }

    /*
     * MODIFIES: this
     * EFFECTS: processes choice from schedule menu
     */
    private void chooseScheduleMenu(Pill chosenPill) {
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.println(chosenPill.getSchedule());
            viewScheduleMenu();
            int command = input.nextInt();
            input.nextLine();
            switch (command) {
                case 1:
                    addSchedule(chosenPill);
                    break;
                case 2:
                    removeSchedule(chosenPill);
                    break;
                case 3:
                    changeSchedule(chosenPill);
                    break;
                case 4:
                    keepGoing = false;
                    break;
                default:
                    System.out.println("Choose from the menu");
            }
        }
    }

    // EFFECTS: gives list of schedule and lets user choose one
    private int chooseFromSchedule(Pill chosenPill) {
        int option = 1;
        for (LocalTime t : chosenPill.getSchedule()) {
            System.out.println("\n" + option + ". " + t);
            option++;
        }
        int command = input.nextInt();
        command--;
        return command;
    }

    // EFFECTS: prompts user for new time
    private LocalTime promptNewTime() {
        System.out.println("\nEnter new time: ");
        System.out.println("\tHour: ");
        int newHour = input.nextInt();
        input.nextLine();
        System.out.println("\tMinute: ");
        int newMinute = input.nextInt();
        input.nextLine();

        return LocalTime.of(newHour, newMinute);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds new schedule to given pill
     */
    private void addSchedule(Pill chosenPill) {
        LocalTime newSchedule = promptNewTime();
        chosenPill.addSchedule(newSchedule);
        System.out.println(newSchedule + " added");
    }

    /*
     * MODIFIES: this
     * EFFECTS: removes schedule from given pill
     */
    private void removeSchedule(Pill chosenPill) {
        if (chosenPill.getSchedule().size() > 0) {
            System.out.println("\nWhich one would you like to remove? ");
            int command = chooseFromSchedule(chosenPill);
            chosenPill.removeSchedule(chosenPill.getSchedule().get(command));
            System.out.println("Removed");
        } else {
            System.out.println("Schedule is empty");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: changes schedule of given pill
     */
    private void changeSchedule(Pill chosenPill) {
        if (chosenPill.getSchedule().size() > 0) {
            System.out.println("\nWhich one would you like to change? ");
            int command = chooseFromSchedule(chosenPill);
            LocalTime newSchedule = promptNewTime();
            chosenPill.changeSchedule(chosenPill.getSchedule().get(command), newSchedule);
            System.out.println("Changed");
        } else {
            System.out.println("Schedule is empty");
        }
    }

    /*
     * EFFECTS: prompts user to choose from pill menu and returns it
     */
    private int promptPillMenuChoice() {
        viewPillMenu();
        int command = input.nextInt();
        input.nextLine();
        return command;
    }

    /*
     * MODIFIES: this
     * EFFECTS: processes choice from pill menu
     */
    private void choosePillMenu() {
        boolean keepGoing = true;
        Pill chosenPill = choosePill();
        viewPill(chosenPill);
        while (keepGoing) {
            int command = promptPillMenuChoice();
            if (command == 1) {
                incrementPillCount(chosenPill);
            } else if (command == 2) {
                decrementPillCount(chosenPill);
            } else if (command == 3) {
                changeName(chosenPill);
            } else if (command == 4) {
                changeDosage(chosenPill);
            } else if (command == 5) {
                changeDescription(chosenPill);
            } else if (command == 6) {
                manageSchedule(chosenPill);
            } else if (command == 7) {
                keepGoing = false;
            } else {
                System.out.println("Choose from the menu");
            }
        }
    }

    /*
     * REQUIRES: name length > 0
     * MODIFIES: this
     * EFFECTS: prompts user for new name;
     *          changes pill name to given name and displays result
     */
    private void changeName(Pill chosenPill) {
        System.out.println("\nEnter new name: ");
        String newName = input.nextLine();
        chosenPill.setName(newName);
        viewPill(chosenPill);
    }

    /*
     * REQUIRES: dosage > 0
     * MODIFIES: this
     * EFFECTS: prompts user for new dosage;
     *          changes pill dosage to given dosage and displays result
     */
    private void changeDosage(Pill chosenPill) {
        System.out.println("\nEnter new dosage: ");
        int newDosage = input.nextInt();
        input.nextLine();
        chosenPill.setDosage(newDosage);
        viewPill(chosenPill);
    }

    /*
     * MODIFIES: this
     * EFFECTS: prompts user for new description;
     *          changes pill description to given description and displays result
     */
    private void changeDescription(Pill chosenPill) {
        System.out.println("\nEnter new description: ");
        String newDescription = input.nextLine();
        chosenPill.setDescription(newDescription);
        viewPill(chosenPill);
    }

    /*
     * REQUIRES: amount > 0
     * MODIFIES: this
     * EFFECTS: prompts user for amount to increment;
     *          increments pill count by given amount and displays result
     */
    private void incrementPillCount(Pill chosenPill) {
        System.out.println("\nEnter number to increment: ");
        int amount = input.nextInt();
        input.nextLine();
        chosenPill.incrementCount(amount);
        viewPill(chosenPill);
    }

    /*
     * MODIFIES: this
     * EFFECTS: prompts user for amount to decrement;
     *          decrements pill count by given amount and displays result
     */
    private void decrementPillCount(Pill chosenPill) {
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.println("\nEnter number to decrement: ");
            int amount = input.nextInt();
            input.nextLine();
            if (amount <= chosenPill.getCount()) {
                chosenPill.decrementCount(amount);
                keepGoing = false;
            } else {
                System.out.println("number must be smaller than current count: " + chosenPill.getCount());
            }
        }
        viewPill(chosenPill);
    }

    // EFFECTS: saves collection to file
    private void saveCollection() {
        try {
            jsonWriter.open();
            jsonWriter.write(collection);
            jsonWriter.close();
//            System.out.println("Saved " + collection.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads collection from file
    private void loadCollection() {
        try {
            collection = jsonReader.read();
//            System.out.println("Loaded " + collection.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
