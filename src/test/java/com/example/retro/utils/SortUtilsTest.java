package com.example.retro.utils;

import com.example.retro.HelloApplication;
import com.example.retro.MyNeatList;
import com.example.retro.models.GameSystem;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SortUtilsTest {

    @Test
    void testInsertionSortAscending() {
        // Test case for insertionSortAscending

        // Create an unsorted list
        MyNeatList<Integer> unsortedList = new MyNeatList<>();
        unsortedList.add(5);
        unsortedList.add(2);
        unsortedList.add(8);
        unsortedList.add(1);
        unsortedList.add(7);


        // Verify the sorted order (added backwards)
        MyNeatList<Integer> expectedSorted = new MyNeatList<>();
        expectedSorted.add(8);
        expectedSorted.add(7);
        expectedSorted.add(5);
        expectedSorted.add(2);
        expectedSorted.add(1);

        assertNotEquals(expectedSorted.head.getContents(), unsortedList.head.getContents());
        assertNotEquals(expectedSorted.head.getContents(), unsortedList.head.getContents());
        assertNotEquals(expectedSorted.head.next.getContents(), unsortedList.head.next.getContents());
        assertNotEquals(expectedSorted.head.next.next.getContents(), unsortedList.head.next.next.getContents());
        assertNotEquals(expectedSorted.head.next.next.next.getContents(), unsortedList.head.next.next.next.getContents());

        // Call the method to be tested
        SortUtils.insertionSortAscending(unsortedList) ;


        assertEquals(expectedSorted.head.getContents(), unsortedList.head.getContents());
        assertEquals(expectedSorted.head.next.getContents(), unsortedList.head.next.getContents());
        assertEquals(expectedSorted.head.next.next.getContents(), unsortedList.head.next.next.getContents());
        assertEquals(expectedSorted.head.next.next.next.getContents(), unsortedList.head.next.next.next.getContents());


    }

    @Test
    void testSortByGameSystemNameAscending() {
        HelloApplication.gameSystems.clear();
        // Create a list of game systems to be sorted
        HelloApplication.gameSystems.add(new GameSystem("Sega Genesis", "", "", "", "", "", 1980, 499.99));
        HelloApplication.gameSystems.add(new GameSystem("Atari 5200", "", "", "", "", "", 1980, 399.99));
        HelloApplication.gameSystems.add(new GameSystem("Atari 3200", "", "", "", "", "", 1980, 200));
        HelloApplication.gameSystems.add(new GameSystem("Nintendo Entertainment System", "", "", "", "", "", 1980, 599.99));

        // Call the method to be tested
        SortUtils.sortByGameSystemNameAscending();

        // Verify the sorted order by checking the names using the hash function
        assertEquals("Atari 3200", findGameSystemByName("Atari 3200").getName());
        assertEquals("Atari 5200", findGameSystemByName("Atari 5200").getName());
        assertEquals("Nintendo Entertainment System", findGameSystemByName("Nintendo Entertainment System").getName());
        assertEquals("Sega Genesis", findGameSystemByName("Sega Genesis").getName());

    }

    private GameSystem findGameSystemByName(String name) {
        int hash = HelloApplication.gameSystems.hashFunction(name);
        for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
            GameSystem system = HelloApplication.gameSystems.getElementFromPosition((hash + i) % HelloApplication.gameSystems.size());
            if (system != null && name.equals(system.getName())) {
                return system;
            }
        }
        return null; // Game system not found
    }


    @Test
    void testSortByGameSystemPriceDescending() {
        HelloApplication.gameSystems.clear();
        // Create a list of game systems to be sorted
        HelloApplication.gameSystems.add(new GameSystem("Atari 5200", "", "", "", "", "", 1980, 499.99));
        HelloApplication.gameSystems.add(new GameSystem("Nintendo Entertainment System", "", "", "", "", "", 1980, 600));
        HelloApplication.gameSystems.add(new GameSystem("Sega Genesis", "", "", "", "", "", 1980, 100));
        HelloApplication.gameSystems.add(new GameSystem("Atari 3200", "", "", "", "", "", 1980, 200));

        // Call the method to be tested
        SortUtils.sortByGameSystemPriceDescending();

        // Verify the sorted order by checking the prices using the hash function
        assertEquals(600, findGameSystemByName("Nintendo Entertainment System").getPrice());
        assertEquals(499.99, findGameSystemByName("Atari 5200").getPrice());
        assertEquals(200, findGameSystemByName("Atari 3200").getPrice());
        assertEquals(100, findGameSystemByName("Sega Genesis").getPrice());
    }





}
