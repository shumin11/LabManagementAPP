package model;

import java.util.LinkedList;

// Represents a list of types

public class TypeList {

    private LinkedList<Type> types;

    public TypeList() {
        types = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: if the list does not already contain a type with typeName same as given name,
    //          adds the type to the list and return true; otherwise, returns false.
    public boolean addType(String typeName) {
        for (Type i : types) {
            if (i.getTypeName() == typeName) {
                return false;
            }
        }
        types.add(new Type(typeName));
        return true;
    }

    // MODIFIES: this
    // EFFECTS: if the list has already contained a type with typeName same as given name,
    //          removes the type from the list and return true; otherwise, returns false.
    public boolean removeType(String typeName) {
        for (Type i : types) {
            if (i.getTypeName() == typeName) {
                types.remove(i);
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns the number of types currently in the list
    public int typesLength() {
        return types.size();
    }

    // EFFECTS: returns ture if the list is empty, false otherwise
    public boolean typesIsEmpty() {
        return (types.size() == 0);
    }

}






