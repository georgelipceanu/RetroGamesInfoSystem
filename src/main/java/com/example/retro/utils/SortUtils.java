package com.example.retro.utils;

import com.example.retro.MyNeatList;
import com.example.retro.models.Game;
import com.example.retro.models.GameSystem;

import java.util.List;

public class SortUtils {

    public static <T extends Comparable<T>> void insertionSort(MyNeatList<T> list) {
        int n = list.size();

        for (int i = 1; i < n; ++i) {
            T key = list.get(i);
            int j = i - 1;

            while (j >= 0 && list.get(j).compareTo(key) > 0) {
                list.set(j + 1, list.get(j));
                j = j - 1;
            }
            list.set(j + 1, key);
        }
    }

    public static void main(String[] args) {
        // Example usage
        MyNeatList<String> integerList = new MyNeatList<>();
        GameSystem xbox = new GameSystem("xbox","microsoft","goodig","console","console","console",2021,50);
        GameSystem xbox1 = new GameSystem("apple","microsoft","goodig","console","console","console",2021,20);
        GameSystem xbox2 = new GameSystem("delta","microsoft","goodig","console","console","console",2021,540);



        integerList.add(xbox.getName());
        integerList.add(xbox1.getName());
        integerList.add(xbox2.getName());


        System.out.println("Original List: " + integerList.stream().toList());

        insertionSort(integerList);

        System.out.println("Sorted List: " + integerList.stream().toList());
    }
}
