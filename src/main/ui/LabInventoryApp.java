package ui;

import model.Item;
import model.Type;
import model.TypeList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

// Lab Inventory application
public class LabInventoryApp {
    private static final String JSON_STORE = "./data/labInventory.json";
    private Scanner input;
    private Item itemA;
    private TypeList typeList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the LabInventory application
    public LabInventoryApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runLabInventory();
    }

    // MODIFIES: this
    // EFFECTS: run the program, process with the inputs
    // CITATION: https://github.students.cs.ubc.ca/CPSC210/TellerApp
    public void runLabInventory() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            mainMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
    }

    // EFFECTS: display menu of options to user
    public void mainMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add a new type or item");
        System.out.println("\td -> delete a type or an item");
        System.out.println("\tv -> view list of types or view list of items from selected type");
        System.out.println("\tg -> get details for one item");
        System.out.println("\ts -> save item information to file");
        System.out.println("\tl -> load data from file");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: process based on the user input command
    public void processCommand(String command) {
        if (command.equals("a")) {
            doAdd();
        } else if (command.equals("d")) {
            doDelete();
        } else if (command.equals("v")) {
            doView();
        } else if (command.equals("g")) {
            doGet();
        } else if (command.equals("s")) {
            doSave();
        } else if (command.equals("l")) {
            doLoad();
        } else {
            System.out.println("Selection not valid...");
        }
    }


    // MODIFIES: this
    // EFFECTS: prompts user to select to add a new type or item and conducts the action
    public void doAdd() {
        System.out.println("\nSelect from:");
        System.out.println("\tt -> add a new type");
        System.out.println("\ti -> add a new item");
        String command = null;
        command = input.next();
        command = command.toLowerCase();

        if (command.equals("t")) {
            doTypeAdd();
        } else if (command.equals("i")) {
            doItemAdd();
        } else {
            System.out.println("Selection not valid...");
            doAdd();
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to select to delete a new type or item and conducts the action
    public void doDelete() {
        System.out.println("\nSelect from:");
        System.out.println("\tt -> delete a new type");
        System.out.println("\ti -> delete a new item");
        String command = null;
        command = input.next();
        command = command.toLowerCase();

        if (command.equals("t")) {
            doTypeDelete();
        } else if (command.equals("i")) {
            doItemDelete();
        } else {
            System.out.println("Selection not valid...");
            doDelete();
        }
    }

    // EFFECTS: prompts user to select to view types or items on specific type
    public void doView() {
        System.out.println("\nSelect from:");
        System.out.println("\tt -> view the type list");
        System.out.println("\ti -> select a type and view all items");
        String command = null;
        command = input.next();
        command = command.toLowerCase();

        if (command.equals("t")) {
            doViewTypes();
        } else if (command.equals("i")) {
            doViewItems();
        } else {
            System.out.println("Selection not valid...");
            doView();
        }
    }

    // EFFECTS: conducts an action to view types
    public void doViewTypes() {
        if (typeList.getTypes().isEmpty()) {
            System.out.println("This is an empty inventory!");
        } else {
            for (Type i : typeList.getTypes()) {
                System.out.println(i.getTypeName());
            }
        }
    }


    // EFFECTS: prompts user to select a type and view all the items in the list
    public void doViewItems() {
        System.out.println("Enter the type name : ");
        String typeName = input.next();
        if (typeList.getTypes().isEmpty()) {
            System.out.println("This is an empty inventory!");
        } else {
            for (Type i : typeList.getTypes()) {
                if (i.getItemsForType(typeName) == null) {
                    System.out.println(
                            "No such item in type named " + i.getTypeName());
                } else if (i.getItemsForType(typeName).isEmpty()) {
                    System.out.println("There are no items in type " + i.getTypeName());
                } else {
                    int index = 1;
                    for (Item j : i.getItemsForType(typeName)) {
                        System.out.println("Items in selected type: " + index + "-" + j.getItemName());
                        index++;
                    }
                }
            }
        }
    }

    // EFFECTS: prompts user to select an item and view all the details of this item
    public void doGet() {
        System.out.println("Enter the item name you are looking for: ");
        String itemName = input.next();
        if (typeList.typesIsEmpty()) {
            System.out.println("This lab inventory is empty!");
        } else {
            for (Type i : typeList.getTypes()) {
                if (i.getItemsForType(i.getTypeName()).isEmpty() || i.getItemsForType(i.getTypeName()) == null) {
                    System.out.println("no items in the type " + i.getTypeName());
                } else {
                    for (Item j : i.getItemsForType(i.getTypeName())) {
                        if (j.getItemName().equals(itemName)) {
                            System.out.println(j.toString());
                        }
                    }
                }
            }
        }
    }

    // EFFECTS: saves lab inventory information to file
    public void doSave() {
        try {
            jsonWriter.open();
            jsonWriter.write(typeList);
            jsonWriter.close();
            System.out.println("Saved lab inventory information to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads lab inventory information form file
    public void doLoad() {
        try {
            typeList = jsonReader.read();
            System.out.println("Loaded lab inventory information from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


    // MODIFIES: this
    // EFFECTS: conducts the action of adding a type
    public void doTypeAdd() {
        System.out.println("Enter the type name: ");
        String typeName = input.next();
        boolean result = typeList.addType(typeName);
        if (result) {
            System.out.println(typeName + " is added to the type list.");
        } else {
            System.out.println("Can not be added! The name is in the list already.");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts the action of deleting a type
    public void doTypeDelete() {
        System.out.println("Enter the type name: ");
        String typeName = input.next();
        boolean result = typeList.removeType(typeName);
        if (result) {
            System.out.println(typeName + " is removed from the list.");
        } else {
            System.out.println("Can not be deleted! This type is not in the list.");
        }
    }


    // MODIFIES: this
    // EFFECTS: conducts the action of adding an item
    public void doItemAdd() {
        collectItemInformation();
        System.out.println("Enter the type name for this item : ");
        String typeName = input.next();
        typeList.addType(typeName);
        for (Type i : typeList.getTypes()) {
            if (i.getTypeName().equals(typeName)) {
                if (i.addItemToType(typeName, itemA)) {
                    System.out.println(itemA.getItemName() + " is added to the type " + typeName);
                } else {
                    System.out.println("Can not be added! this item is already in the type");
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts the action of deleting an item
    public void doItemDelete() {
        collectItemInformation();
        System.out.println("Enter the type name for this item : ");
        String typeName = input.next();
        if (typeList.getTypes().isEmpty()) {
            System.out.println("There are no types in inventory. Nothing to delete!");
        } else {
            for (Type i : typeList.getTypes()) {
                if (i.getTypeName().equals(typeName)) {
                    if (i.removeItemFromType(typeName, itemA)) {
                        System.out.println(itemA.getItemName() + " is removed from the type " + typeName);
                    } else {
                        System.out.println("Can not be deleted! This item is not in the type");
                    }
                } else {
                    System.out.println("can not be deleted! This type is not existing, need to have the type first");
                }
            }
        }
    }


    // EFFECTS: prompts user to input the item data
    public void collectItemInformation() {
        System.out.println("Enter item name: ");
        String itemName = input.next();
        System.out.println("Enter item amount currently in the lab: ");
        int initialAmount = input.nextInt();
        System.out.println("Enter location of this item: ");
        String location = input.next();
        System.out.println("Enter vendor Name of this item: ");
        String vendor = input.next();
        System.out.println("Enter updated time: ");
        String updated = input.next();
        System.out.println("Enter the cutoff value of this item stored in the lab: ");
        int cutoff = input.nextInt();
        System.out.println("Enter other notes: ");
        String note = input.next();
        itemA = new Item(itemName, initialAmount, location, vendor,
                updated, cutoff);
        itemA.addNote(note);
    }

    // MODIFIES: this
    // EFFECTS: initializes type list and scanner
    public void init() {
        typeList = new TypeList();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

}
