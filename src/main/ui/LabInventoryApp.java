package ui;

import model.Item;
import model.Type;
import model.TypeList;

import java.util.*;

public class LabInventoryApp {
    private Scanner input;
    private Item itemA;
    private Type type;
    private TypeList typeList;

    // EFFECTS: runs the LabInventory application
    public LabInventoryApp() {
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
        System.out.println("\tv -> view list of items from selected type");
        System.out.println("\tg -> get details for one items");
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
        String command = input.next();
        command = command.toLowerCase();

        if (command.equals("t")) {
            doTypeAdd();
        } else if (command.equals("i")) {
            doItemAdd();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to select to delete a new type or item and conducts the action
    public void doDelete() {
        System.out.println("\nSelect from:");
        System.out.println("\tt -> delete a new type");
        System.out.println("\ti -> delete a new item");
        String command = input.next();
        command = command.toLowerCase();

        if (command.equals("t")) {
            doTypeDelete();
        } else if (command.equals("i")) {
            doItemDelete();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: prompts user to select a type and view all the items in the list
    public void doView() {
        System.out.println("Enter the type name : ");
        String typeName = input.next();
        for (Type i : typeList.getTypes()) {
            if (i.getItemsForType(typeName).isEmpty()) {
                System.out.println("There are no items in type " + i.getTypeName());
            } else {
                for (Item j : i.getItemsForType(typeName)) {
                    System.out.println(j.getItemName());
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
                for (Item j : i.getItemsForType(i.getTypeName())) {
                    if (j.getItemName() == itemName) {
                        System.out.println(j.toString());
                    } else {
                        System.out.println(itemName + " is not in " + i.getTypeName() + " type");
                    }
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts the action of adding a type
    public void doTypeAdd() {
        System.out.println("Enter the type name: ");
        String typeName = input.next();
        if (typeList.addType(typeName)) {
            System.out.println(typeName + " is added to the typelist.");
        } else {
            System.out.println("Can not be added! The name is in the list already.");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts the action of deleting a type
    public void doTypeDelete() {
        System.out.println("Enter the type name: ");
        String typeName = input.next();
        if (typeList.removeType(typeName)) {
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
        type = new Type(typeName);
        if (type.addItemToType(typeName, itemA)) {
            System.out.println(itemA.getItemName() + " is added to the type " + typeName);
        } else {
            System.out.println("Can not be added! The item is already in the list.");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts the action of deleting an item
    public void doItemDelete() {
        collectItemInformation();
        System.out.println("Enter the type name for this item : ");
        String typeName = input.next();
        type = new Type(typeName);
        if (type.removeItemFromType(typeName, itemA)) {
            System.out.println(itemA.getItemName() + " is removed from the type " + typeName);
        } else {
            System.out.println("Can not be deleted! The item is not in the list.");
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
