package persistence;

import model.Type;
import model.TypeList;
import org.junit.jupiter.api.Test;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            TypeList typeList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTypeList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTypeList.json");
        try {
            TypeList typeList = reader.read();
            assertEquals(0, typeList.typesLength());
            assertTrue(typeList.typesIsEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTypeList.json");
        try {
            TypeList typeList = reader.read();
            assertFalse(typeList.typesIsEmpty());
            assertEquals(1, typeList.typesLength());
            Type type = typeList.getTypes().get(0);

            assertEquals(2, type.getItemsForType("GeneralSupply").size());
            checkItem("PipetteTips", 80, "Room101Cabinet2", "Fisher",
                    "2022-07-24", 10, type.getItemsForType("GeneralSupply").get(0));
            checkItem("PetriDish", 120, "Room101Cabinet3", "Fisher",
                    "2022-07-24", 20, type.getItemsForType("GeneralSupply").get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
