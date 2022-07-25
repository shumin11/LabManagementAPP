package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TypeListTest {
    TypeList testTypeList;

    @BeforeEach
    public void runBefore() {
        this.testTypeList = new TypeList();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, testTypeList.typesLength());
        assertTrue(testTypeList.typesIsEmpty());
    }

    @Test
    public void testAddTypeSuccess() {
        assertTrue(testTypeList.addType("GeneralSupply"));
        assertEquals(1, testTypeList.typesLength());
        assertFalse(testTypeList.typesIsEmpty());

        assertTrue(testTypeList.addType("Chemicals"));
        assertEquals(2, testTypeList.typesLength());
    }

    @Test
    public void testAddTypeInSuccess() {
        assertTrue(testTypeList.addType("GeneralSupply"));
        assertEquals(1, testTypeList.typesLength());

        assertFalse(testTypeList.addType("GeneralSupply"));
        assertEquals(1, testTypeList.typesLength());
    }

    @Test
    public void testRemoveTypeSuccess() {
        assertTrue(testTypeList.addType("GeneralSupply"));
        assertTrue(testTypeList.addType("Chemicals"));
        assertEquals(2, testTypeList.typesLength());

        assertTrue(testTypeList.removeType("Chemicals"));
        assertEquals(1, testTypeList.typesLength());
        assertTrue(testTypeList.removeType("GeneralSupply"));
        assertEquals(0, testTypeList.typesLength());
        assertTrue(testTypeList.typesIsEmpty());
    }

    @Test
    public void testRemoveTypeInSuccess() {
        assertTrue(testTypeList.addType("GeneralSupply"));
        assertTrue(testTypeList.addType("Chemicals"));
        assertEquals(2, testTypeList.typesLength());
        assertTrue(testTypeList.removeType("Chemicals"));
        assertEquals(1, testTypeList.typesLength());

        assertFalse(testTypeList.removeType("Chemicals"));
        assertEquals(1, testTypeList.typesLength());
        assertFalse(testTypeList.typesIsEmpty());
    }
}
