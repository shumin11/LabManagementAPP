package persistence;

import model.Item;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JsonTest {
    protected void checkItem(String itemName, int initialAmount,
                             String location, String vendor, String updated, int cutoff, Item item) {
        assertEquals(itemName, item.getItemName());
        assertEquals(initialAmount, item.getAmount());
        assertEquals(location, item.getLocation());
        assertEquals(vendor, item.getVendor());
        assertEquals(updated, item.getUpdated());
        assertEquals(cutoff, item.getCutOff());
    }
}
