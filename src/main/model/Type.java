package model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

// Represents a type having a name, and a list of items associated with it

public class Type {
    String typeName;
    private LinkedList<Item> items;


    // EFFECTS: construct a type with no type name or items added
    public Type(String typeName) {
        this.typeName = typeName;
        items = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: if the list of items are associated with typeName does not contain this item,
    //          then adds the item to the list associated with typeName, and return true.
    //          otherwise, do not add item and return false
    public boolean addItemToType(String typeName, Item item) {
        if (this.typeName.equals(typeName) && (!items.contains(item))) {
            this.items.add(item);
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: if the list of items are associated with typeName does contain this item,
    //          then removes the item from the list associated with typeName, and return true.
    //          otherwise, do nothing and return false
    public boolean removeItemFromType(String typeName, Item item) {
        if (this.typeName.equals(typeName) && (this.items.contains(item))) {
            this.items.remove(item);
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: returns a list of items associated with the typeName;
    //          returns null if name is not matching
    public List<Item> getItemsForType(String typeName) {
        if (this.typeName.equals(typeName)) {
            return this.items;
        } else {
            return null;
        }
    }

    // EFFECTS: returns the name of this type
    public String getTypeName() {
        return this.typeName;
    }

    // EFFECTS: returns the number of items currently in the list
    public int itemsLength() {
        return this.items.size();
    }

    // EFFECTS: returns ture if the item list is empty, false otherwise
    public boolean itemsIsEmpty() {
        return (this.items.size() == 0);
    }

}
