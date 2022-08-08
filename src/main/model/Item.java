package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Represents an item having a name, amount, location, type, updated, and notes

public class Item implements Writable {
    private String itemName;
    private int amount;
    private String location;
    private String vendor;
    private String updated;
    private int cutoff;
    private List<String> notes;
    private boolean toOrder;

    // REQUIRES: itemName has a non-zero length
    // EFFECTS: constructs an item with given name, amount, location, vendor, updated, empty notes, no need to order
    //          if initialAmount >=0 then amount is set to initialAmount, otherwise amount is 0;
    //          the same case as cutoff, it has to be >=0, or it will be set to 0.
    public Item(String itemName, int initialAmount, String location, String vendor, String updated, int cutoff) {
        this.itemName = itemName;
        if (initialAmount >= 0) {
            this.amount = initialAmount;
        } else {
            this.amount = 0;
        }
        this.location = location;
        this.vendor = vendor;
        this.updated = updated;
        if (cutoff >= 0) {
            this.cutoff = cutoff;
        } else {
            this.cutoff = 0;
        }
        notes = new ArrayList<>();
        toOrder = isToOrder();
    }


    // MODIFIES: this
    // EFFECTS: adds note to the list unless it's already there, in which case do nothing
    public void addNote(String note) {
        if (!this.notes.contains(note)) {
            this.notes.add(note);
        }
    }

    // MODIFIES: this
    // EFFECTS: if the note is in the array list, remove it; otherwise, do nothing
    public void removeNote(String note) {
        if (this.notes.contains(note)) {
            this.notes.remove(note);
        }
    }

    // REQUIRES: num >= 0
    // MODIFIES: this
    // EFFECTS: adds num to amount and updated amount is returned
    public int addAmount(int num) {
        this.amount = this.amount + num;
        return this.amount;
    }

    // REQUIRES: amount - num >= 0
    // MODIFIES: this
    // EFFECTS: decreases amount by num and updated amount is returned
    public int decreaseAmount(int num) {
        this.amount = this.amount - num;
        return this.amount;
    }

    // EFFECTS: returns true if the amount is less or equal than cutoff
    public boolean isToOrder() {
        if (this.amount <= this.cutoff) {
            return this.toOrder = true;
        } else {
            return this.toOrder = false;
        }
    }

    // MODIFIES: this
    // EFFECTS: sets to order this item
    public void setOder() {
        this.toOrder = true;
    }

    public String getItemName() {
        return this.itemName;
    }

    public int getAmount() {
        return this.amount;
    }

    public String getLocation() {
        return this.location;
    }

    public String getVendor() {
        return this.vendor;
    }

    public String getUpdated() {
        return this.updated;
    }

    public int getCutOff() {
        return this.cutoff;
    }

    public List<String> getNotes() {
        return this.notes;
    }

    public boolean toOrder() {
        return this.toOrder;
    }


    // EFFECTS: returns a string representation of an item
    public String toString() {
        String amountStr = String.valueOf(amount);
        return String.format(
                "[item name: %s, amount: %s, location: %s, vendor: %s, updated: %s, toOrder: %s, notes: %s]",
                itemName, amountStr, location, vendor, updated, toOrder, notes);
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("item name", itemName);
        json.put("amount", amount);
        json.put("location", location);
        json.put("vendor", vendor);
        json.put("updated", updated);
        json.put("cutoff", cutoff);
        json.put("toOrder", toOrder);
        json.put("notes", notesToJson());
        return json;
    }


    // EFFECTS: returns notes as a Json array
    private JSONArray notesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (String i : notes) {
            jsonArray.put(i);
        }
        return jsonArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return amount == item.amount && cutoff == item.cutoff && itemName.equals(item.itemName)
                && location.equals(item.location) && vendor.equals(item.vendor) && updated.equals(item.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName, amount, location, vendor, updated, cutoff);
    }
}

