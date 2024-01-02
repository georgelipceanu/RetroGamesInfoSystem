package com.example.retro;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UltimateHashTest {

    @Test
    public void testAddAndGetElement() {

        UltimateHash<Integer> ultimateHash = new UltimateHash<>(5);
        ultimateHash.add(1);
        ultimateHash.add(7);
        ultimateHash.add(14);


        // Use the find method to validate the existence of the elements
        assertEquals(1, ultimateHash.find(1));
        assertEquals(7, ultimateHash.find(7));
        assertEquals(14, ultimateHash.find(14));
        assertNull(ultimateHash.find(4)); // Element not in the hash table
    }


    @Test
    public void testReplace() {
        UltimateHash<String> ultimateHash = new UltimateHash<>(5);
        ultimateHash.add("One");
        ultimateHash.add("Two");
        ultimateHash.replaceElement("Updated", ultimateHash.find("One"));

        assertNull(ultimateHash.find("One"));
        assertEquals("Updated", ultimateHash.find("Updated"));
    }

    @Test
    public void testDelete() {
        UltimateHash<String> ultimateHash = new UltimateHash<>(5);
        ultimateHash.add("One");
        ultimateHash.add("Two");
        ultimateHash.deleteElement("one");

        assertNull(ultimateHash.find("one")); // Deleted position
        assertEquals("Two", ultimateHash.find("Two"));
    }


    @Test
    public void testDisplayHashTable() {
        UltimateHash<String> ultimateHash = new UltimateHash<>(3);
        ultimateHash.add("One");
        ultimateHash.add("Two");
        ultimateHash.add("Three");

        // Note: This test just prints to the console; manual inspection is required
        ultimateHash.displayHashTable();
    }


}
