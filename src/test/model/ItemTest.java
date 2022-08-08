package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    private Item testItem;

    @BeforeEach
    public void runBefore() {
        testItem = new Item("PipetteTips", 80, "Room101Cabinet2", "Fisher",
                "2022-07-24", 10);
    }

    @Test
    public void testConstructor() {
        assertEquals("PipetteTips", testItem.getItemName());
        assertEquals(80, testItem.getAmount());
        assertEquals("Room101Cabinet2", testItem.getLocation());
        assertEquals("Fisher", testItem.getVendor());
        assertEquals("2022-07-24", testItem.getUpdated());
        assertEquals(10, testItem.getCutOff());
        assertFalse(testItem.toOrder());
        assertEquals(0, testItem.getNotes().size());
    }

    @Test
    public void testConstructorNegAmount() {
        testItem = new Item("PipetteTips", -10, "Room101Cabinet2", "Fisher",
                "2022-07-24", 10);
        assertEquals("PipetteTips", testItem.getItemName());
        assertEquals(0, testItem.getAmount());
        assertEquals("Room101Cabinet2", testItem.getLocation());
        assertEquals("Fisher", testItem.getVendor());
        assertEquals("2022-07-24", testItem.getUpdated());
        assertEquals(10, testItem.getCutOff());
        assertTrue(testItem.toOrder());
        assertEquals(0, testItem.getNotes().size());
    }

    @Test
    public void testConstructorNegCutoff() {
        testItem = new Item("PipetteTips", 80, "Room101Cabinet2", "Fisher",
                "2022-07-24", -5);
        assertEquals("PipetteTips", testItem.getItemName());
        assertEquals(80, testItem.getAmount());
        assertEquals("Room101Cabinet2", testItem.getLocation());
        assertEquals("Fisher", testItem.getVendor());
        assertEquals("2022-07-24", testItem.getUpdated());
        assertEquals(0, testItem.getCutOff());
        assertFalse(testItem.toOrder());
        assertEquals(0, testItem.getNotes().size());
    }

    @Test
    public void testAddNoteDiff() {
        testItem.addNote("VWR also has the same product");
        assertEquals(1, testItem.getNotes().size());
        testItem.addNote("catalog number");
        testItem.addNote("Everyone needs them");
        assertEquals(3, testItem.getNotes().size());
    }

    @Test
    public void testAddNoteSame() {
        testItem.addNote("VWR also has the same product");
        assertEquals(1, testItem.getNotes().size());
        testItem.addNote("VWR also has the same product");
        assertEquals(1, testItem.getNotes().size());
    }

    @Test
    public void testRemoveNoteHave() {
        testItem.addNote("VWR also has the same product");
        testItem.addNote("catalog number");
        testItem.addNote("Everyone needs them");
        assertEquals(3, testItem.getNotes().size());
        testItem.removeNote("VWR also has the same product");
        assertEquals(2, testItem.getNotes().size());
        testItem.removeNote("catalog number");
        assertEquals(1, testItem.getNotes().size());
        testItem.removeNote("Everyone needs them");
        assertEquals(0, testItem.getNotes().size());
    }

    @Test
    public void testRemoveNoteNotHave() {
        testItem.addNote("VWR also has the same product");
        assertEquals(1, testItem.getNotes().size());
        testItem.removeNote("catalog number");
        assertEquals(1, testItem.getNotes().size());
    }

    @Test
    public void testAddAmount() {
        testItem.addAmount(10);
        assertEquals(90, testItem.getAmount());
        testItem.addAmount(10);
        testItem.addAmount(10);
        testItem.addAmount(10);
        assertEquals(120, testItem.getAmount());
    }

    @Test
    public void testDecreaseAmount() {
        testItem.decreaseAmount(10);
        assertEquals(70, testItem.getAmount());
        testItem.decreaseAmount(10);
        testItem.decreaseAmount(10);
        assertEquals(50, testItem.getAmount());
    }

    @Test
    public void testIsToOrder() {
        testItem.decreaseAmount(50);
        assertEquals(30, testItem.getAmount());
        assertFalse(testItem.isToOrder());
        assertFalse(testItem.toOrder());
        testItem.decreaseAmount(20);
        assertEquals(10, testItem.getAmount());
        assertTrue(testItem.isToOrder());
        assertTrue(testItem.toOrder());
        testItem.decreaseAmount(5);
        assertEquals(5, testItem.getAmount());
        assertTrue(testItem.isToOrder());
        assertTrue(testItem.toOrder());
    }

    @Test
    public void testSetOrder() {
        assertEquals(80, testItem.getAmount());
        assertFalse(testItem.toOrder());
        testItem.setOder();
        assertTrue(testItem.toOrder());
    }

    @Test
    public void testToString() {
        assertTrue(testItem.toString().contains("item name: PipetteTips, amount: 80, " +
                "location: Room101Cabinet2, vendor: Fisher, updated: 2022-07-24, toOrder: false"));
    }

    @Test
    public void testEquals_Symmetric() {
        assertTrue(testItem.equals(testItem));
        assertTrue(testItem.hashCode() == testItem.hashCode());
        Item otherItem = new Item("PipetteTips", 80, "Room101Cabinet2", "Fisher",
                "2022-07-24", 10);
        assertTrue(testItem.equals(otherItem) && otherItem.equals(testItem));
        assertTrue(testItem.hashCode() == otherItem.hashCode());
        Item secondItem = null;
        assertFalse(testItem.equals(secondItem) && secondItem.equals(testItem));
    }

    @Test
    public void testNameDifferentItem() {
        Item nameDifferentItem = new Item("gloves", 80, "Room101Cabinet2",
                "Fisher", "2022-07-24", 10);
        assertFalse(testItem.equals(nameDifferentItem) && nameDifferentItem.equals(testItem));
        assertFalse(testItem.hashCode() == nameDifferentItem.hashCode());
    }

    @Test
    public void testAmountDifferentItem() {
        Item amountDifferentItem = new Item("PipetteTips", 100, "Room101Cabinet2",
                "Fisher", "2022-07-24", 10);
        assertFalse(testItem.equals(amountDifferentItem) && amountDifferentItem.equals(testItem));
        assertFalse(testItem.hashCode() == amountDifferentItem.hashCode());
    }

    @Test
    public void testLocationDifferentItem() {
        Item locationDifferentItem = new Item("PipetteTips", 80, "Room",
                "Fisher", "2022-07-24", 10);
        assertFalse(testItem.equals(locationDifferentItem) && locationDifferentItem.equals(testItem));
        assertFalse(testItem.hashCode() == locationDifferentItem.hashCode());

    }

    @Test
    public void testVendorDifferentItem() {
        Item vendorDifferentItem = new Item("PipetteTips", 80, "Room101Cabinet2",
                "vwr", "2022-07-24", 10);
        assertFalse(testItem.equals(vendorDifferentItem) && vendorDifferentItem.equals(testItem));
        assertFalse(testItem.hashCode() == vendorDifferentItem.hashCode());
    }

    @Test
    public void testUpdatedDifferentItem() {
        Item updatedDifferentItem = new Item("PipetteTips", 80, "Room101Cabinet2",
                "Fisher", "2022", 10);
        assertFalse(testItem.equals(updatedDifferentItem) && updatedDifferentItem.equals(testItem));
        assertFalse(testItem.hashCode() == updatedDifferentItem.hashCode());
    }

    @Test
    public void testCutoffDifferentItem() {
        Item cutoffDifferentItem = new Item("PipetteTips", 80, "Room101Cabinet2",
                "Fisher", "2022-07-24", 50);
        assertFalse(testItem.equals(cutoffDifferentItem) && cutoffDifferentItem.equals(testItem));
        assertFalse(testItem.hashCode() == cutoffDifferentItem.hashCode());
    }


}











