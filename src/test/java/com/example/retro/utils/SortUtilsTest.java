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
    void insertionSortAscending() {
        // Test case for insertionSortAscending can be added here if needed
    }

    @Test
    void sortByGameSystemNameAscending() {
        // Create a list of game systems to be sorted
        HelloApplication.gameSystems.add(new GameSystem("Atari 5200","","","","","", 1980,499.99));
        HelloApplication.gameSystems.add(new GameSystem("Nintendo Entertainment System","","","","","", 1980,499.99));
        HelloApplication.gameSystems.add(new GameSystem("Sega Genesis","","","","","", 1980,499.99));

        // Call the method to be tested
        SortUtils.sortByGameSystemNameAscending();

        // Verify the sorted order
        assertEquals("Atari 5200", HelloApplication.gameSystems.getElementFromPosition(HelloApplication.gameSystems.hashFunction("Atari 5200")).getName());
        assertEquals("Nintendo Entertainment System", HelloApplication.gameSystems.getElementFromPosition(HelloApplication.gameSystems.hashFunction("Nintendo Entertainment System")).getName());
        assertEquals("Sega Genesis", HelloApplication.gameSystems.getElementFromPosition(HelloApplication.gameSystems.hashFunction("Sega Genesis")).getName());
    }

  //  @Test
//    void sortByGameSystemPriceDescending() {
//        // Create a list of game systems to be sorted
//        MyNeatList<GameSystem> gameSystems = new MyNeatList<>();
//        gameSystems.add(new GameSystem("PS5", 499.99));
//        gameSystems.add(new GameSystem("Xbox Series X", 449.99));
//        gameSystems.add(new GameSystem("Nintendo Switch", 299.99));
//
//        // Call the method to be tested
//        SortUtils.sortByGameSystemPriceDescending();
//
//        // Verify the sorted order
//        assertEquals("PS5", gameSystems.get(0).getName());
//        assertEquals("Xbox Series X", gameSystems.get(1).getName());
//        assertEquals("Nintendo Switch", gameSystems.get(2).getName());
//    }

    // Define a simple GameSystem class for testing

}
