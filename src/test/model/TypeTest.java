package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class TypeTest {
    private Type testType;
    private Item itemA;
    private Item itemB;

    @BeforeEach
    public void runBefore() {
        testType = new Type("GeneralSupply");
        this.itemA = new Item("PipetteTips", 80, "Room101Cabinet2", "Fisher",
                "2022-07-24", 10);
        this.itemB = new Item("PetriDish", 120, "Room101Cabinet3", "Fisher",
                "2022-07-24", 20);
    }

    @Test
    public void testConstructor() {
        assertEquals("GeneralSupply", testType.getTypeName());
        assertEquals(0, testType.getItemsForType("GeneralSupply").size());
    }

    @Test
    public void testAddOneItemToOneTypeWithBothNameConditions() {
        assertTrue(testType.addItemToType("GeneralSupply", itemA));
        assertEquals(1, testType.getItemsForType("GeneralSupply").size());
        assertEquals(this.itemA, testType.getItemsForType("GeneralSupply").get(0));

        assertFalse(testType.addItemToType("Chemicals", itemB));
        assertEquals(1, testType.getItemsForType("GeneralSupply").size());
        assertEquals(this.itemA, testType.getItemsForType("GeneralSupply").get(0));

    }

    @Test
    public void testAddOneItemTwiceToOneAccount() {
        assertTrue(testType.addItemToType("GeneralSupply", itemA));
        assertEquals(1, testType.getItemsForType("GeneralSupply").size());
        assertEquals(this.itemA, testType.getItemsForType("GeneralSupply").get(0));
        assertFalse(testType.addItemToType("GeneralSupply", itemA));
        assertEquals(1, testType.getItemsForType("GeneralSupply").size());
        assertEquals(this.itemA, testType.getItemsForType("GeneralSupply").get(0));
    }

    @Test
    public void testAddTwoDifferentItemsToOneType() {
        assertTrue(testType.addItemToType("GeneralSupply", itemA));
        assertTrue(testType.addItemToType("GeneralSupply", itemB));

        assertEquals(2, testType.getItemsForType("GeneralSupply").size());
        assertEquals(this.itemA, testType.getItemsForType("GeneralSupply").get(0));
        assertEquals(this.itemB, testType.getItemsForType("GeneralSupply").get(1));
    }


    @Test
    public void testRemoveOneItemFromOneType() {
        assertTrue(testType.addItemToType("GeneralSupply", itemA));
        assertEquals(1, testType.getItemsForType("GeneralSupply").size());
        assertEquals(this.itemA, testType.getItemsForType("GeneralSupply").get(0));

        assertTrue(testType.removeItemFromType("GeneralSupply", itemA));
        assertEquals(0, testType.getItemsForType("GeneralSupply").size());

    }

    @Test
    public void testRemoveOneItemFromOneTypeWithDifferentName() {
        assertTrue(testType.addItemToType("GeneralSupply", itemA));
        assertEquals(1, testType.getItemsForType("GeneralSupply").size());
        assertEquals(this.itemA, testType.getItemsForType("GeneralSupply").get(0));

        assertFalse(testType.removeItemFromType("Chemicals", itemA));
        assertEquals(1, testType.getItemsForType("GeneralSupply").size());
        assertEquals(this.itemA, testType.getItemsForType("GeneralSupply").get(0));
    }

    @Test
    public void testRemoveOneItemTwiceFromOneType() {
        assertTrue(testType.addItemToType("GeneralSupply", itemA));
        assertTrue(testType.addItemToType("GeneralSupply", itemB));
        assertEquals(2, testType.getItemsForType("GeneralSupply").size());

        assertTrue(testType.removeItemFromType("GeneralSupply", itemA));
        assertEquals(1, testType.getItemsForType("GeneralSupply").size());
        assertEquals(this.itemB, testType.getItemsForType("GeneralSupply").get(0));

        assertFalse(testType.removeItemFromType("GeneralSupply", itemA));
        assertEquals(1, testType.getItemsForType("GeneralSupply").size());
        assertEquals(this.itemB, testType.getItemsForType("GeneralSupply").get(0));

    }

    @Test
    public void testRemoveTwoDifferentItemsFromOneType() {
        assertTrue(testType.addItemToType("GeneralSupply", itemA));
        assertTrue(testType.addItemToType("GeneralSupply", itemB));
        assertEquals(2, testType.getItemsForType("GeneralSupply").size());

        assertTrue(testType.removeItemFromType("GeneralSupply", itemA));
        assertTrue(testType.removeItemFromType("GeneralSupply", itemB));
        assertEquals(0, testType.getItemsForType("GeneralSupply").size());
    }

    @Test
    public void testItemsLength() {
        assertEquals(0, testType.itemsLength());

        assertTrue(testType.addItemToType("GeneralSupply", itemA));
        assertTrue(testType.addItemToType("GeneralSupply", itemB));
        assertEquals(2, testType.itemsLength());
    }

    @Test
    public void testItemIsEmpty() {
        assertTrue(testType.itemsIsEmpty());
        assertTrue(testType.addItemToType("GeneralSupply", itemA));
        assertFalse(testType.itemsIsEmpty());
    }



}
