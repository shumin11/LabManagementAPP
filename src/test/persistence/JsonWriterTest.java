package persistence;

import model.Item;
import model.Type;
import model.TypeList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            TypeList typeList = new TypeList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTypeList() {
        try {
            TypeList typeList = new TypeList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTypeList.json");
            writer.open();
            writer.write(typeList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTypeList.json");
            typeList = reader.read();
            assertTrue(typeList.typesIsEmpty());
            assertEquals(0, typeList.typesLength());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralTypeList() {
        try {
            TypeList typeList = new TypeList();
            Type type = new Type("GeneralSupply");
            Item itemA = new Item("PipetteTips", 80, "Room101Cabinet2", "Fisher",
                    "2022-07-24", 10);
            itemA.addNote("none");
            Item itemB = new Item("PetriDish", 120, "Room101Cabinet3", "Fisher",
                    "2022-07-24", 20);
            itemB.addNote("testing");
            type.addItemToType("GeneralSupply", itemA);
            type.addItemToType("GeneralSupply", itemB);
            typeList.getTypes().add(type);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTypeList.json");
            writer.open();
            writer.write(typeList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTypeList.json");
            typeList = reader.read();
            assertFalse(typeList.typesIsEmpty());
            assertEquals(1, typeList.typesLength());
            type = typeList.getTypes().get(0);

            assertEquals(2, type.getItemsForType("GeneralSupply").size());
            checkItem("PipetteTips", 80, "Room101Cabinet2", "Fisher",
                    "2022-07-24", 10, type.getItemsForType("GeneralSupply").get(0));
            assertEquals("none", type.getItemsForType("GeneralSupply").get(0).getNotes().get(0));
            checkItem("PetriDish", 120, "Room101Cabinet3", "Fisher",
                    "2022-07-24", 20, type.getItemsForType("GeneralSupply").get(1));
            assertEquals("testing", type.getItemsForType("GeneralSupply").get(1).getNotes().get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}



