package com.example.retro.utils;

import com.example.retro.HelloApplication;
import com.example.retro.MyNeatList;
import com.example.retro.models.GameSystem;

public class SortUtils {

    public static <T extends Comparable<T>> void insertionSortAscending(MyNeatList<T> list) {
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

    public static <T extends Comparable<T>> void insertionSortDescending(MyNeatList<T> list) {
        int n = list.size();

        for (int i = 1; i < n; ++i) {
            T key = list.get(i);
            int j = i - 1;

            while (j >= 0 && list.get(j).compareTo(key) < 0) {
                list.set(j + 1, list.get(j));
                j = j - 1;
            }
            list.set(j + 1, key);
        }
    }



    public static void main(String[] args) {
        HelloApplication.gameSystems.add(new GameSystem("xbox", "", "", "", "", "", 80, 20));
        HelloApplication.gameSystems.add(new GameSystem("ps4", "", "", "", "", "", 80, 20));
        HelloApplication.gameSystems.add(new GameSystem("nintendo", "", "", "", "", "", 80, 20));
        HelloApplication.gameSystems.add(new GameSystem("c", "", "", "", "", "", 80, 20));
        HelloApplication.gameSystems.add(new GameSystem("z--", "", "", "", "", "", 80, 20));


        sortByGameSystemNameAscending();
    }

    public static void sortByGameSystemNameAscending() {
        MyNeatList<String> nameList = new MyNeatList<>();

        // Loop through all game systems and extract names
        for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
            if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                nameList.add(HelloApplication.gameSystems.getElementFromPosition(i).getName());
            }
        }

        // Use insertion sort to sort the names
        SortUtils.insertionSortAscending(nameList);

        // Now, you have the sorted names in the 'nameList'
        // You can use this sorted list as needed
        // For example, print the sorted names
        System.out.println("Sorted Names: " + nameList.stream().toList());

    }

    public static MyNeatList<String> sortByGameSystemNameAscendingReturn() {//for returning
        MyNeatList<String> nameList = new MyNeatList<>();

        // Loop through all game systems and extract names
        for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
            if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                nameList.add(HelloApplication.gameSystems.getElementFromPosition(i).getName());
            }
        }

        // Use insertion sort to sort the names
        SortUtils.insertionSortAscending(nameList);

        // Now, you have the sorted names in the 'nameList'
        // You can use this sorted list as needed
        // For example, print the sorted names
        System.out.println("Sorted Names: " + nameList.stream().toList());

        return nameList;
    }

    public static void sortByGameSystemNameDescending() {
        MyNeatList<String> nameList = new MyNeatList<>();

        // Loop through all game systems and extract names
        for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
            if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                nameList.add(HelloApplication.gameSystems.getElementFromPosition(i).getName());
            }
        }

        // Use insertion sort to sort the names
        SortUtils.insertionSortDescending(nameList);

        // Now, you have the sorted names in the 'nameList'
        // You can use this sorted list as needed
        // For example, print the sorted names
        System.out.println("Sorted Names: " + nameList.stream().toList());

    }

    public static MyNeatList<String> sortByGameSystemNameDescendingReturn() {
        MyNeatList<String> nameList = new MyNeatList<>();

        // Loop through all game systems and extract names
        for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
            if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                nameList.add(HelloApplication.gameSystems.getElementFromPosition(i).getName());
            }
        }

        // Use insertion sort to sort the names
        SortUtils.insertionSortDescending(nameList);

        // Now, you have the sorted names in the 'nameList'
        // You can use this sorted list as needed
        // For example, print the sorted names
        return nameList;

    }
////////////////////////////////////////////////////////////////////////


    public static void sortByGameSystemLaunchYearAscending() {
        MyNeatList<Integer> launchYearList = new MyNeatList<>();

        // Loop through all game systems and extract names
        for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
            if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                launchYearList.add(HelloApplication.gameSystems.getElementFromPosition(i).getLaunchYear());
            }
        }

        // Use insertion sort to sort the names
        SortUtils.insertionSortAscending(launchYearList);

        // Now, you have the sorted names in the 'nameList'
        // You can use this sorted list as needed
        // For example, print the sorted names
        System.out.println("Sorted Names: " + launchYearList.stream().toList());

    }

    public static void sortByGameSystemLaunchYearDescending() {
        MyNeatList<Integer> launchYearList = new MyNeatList<>();

        // Loop through all game systems and extract names
        for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
            if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                launchYearList.add(HelloApplication.gameSystems.getElementFromPosition(i).getLaunchYear());
            }
        }

        // Use insertion sort to sort the names
        SortUtils.insertionSortDescending(launchYearList);

        // Now, you have the sorted names in the 'nameList'
        // You can use this sorted list as needed
        // For example, print the sorted names
        System.out.println("Sorted Names: " + launchYearList.stream().toList());

    }

////////////////////////////////////////////////////////////////


    public static void sortByGameSystemPriceAscending() {
        MyNeatList<Double> priceList = new MyNeatList<>();

        // Loop through all game systems and extract names
        for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
            if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                priceList.add(HelloApplication.gameSystems.getElementFromPosition(i).getPrice());
            }
        }

        // Use insertion sort to sort the names
        SortUtils.insertionSortAscending(priceList);

        // Now, you have the sorted names in the 'nameList'
        // You can use this sorted list as needed
        // For example, print the sorted names
        System.out.println("Sorted Names: " + priceList.stream().toList());

    }

    public static void sortByGameSystemPriceDescending() {
        MyNeatList<Double> priceList = new MyNeatList<>();

        // Loop through all game systems and extract names
        for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
            if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                priceList.add(HelloApplication.gameSystems.getElementFromPosition(i).getPrice());
            }
        }

        // Use insertion sort to sort the names
        SortUtils.insertionSortDescending(priceList);

        // Now, you have the sorted names in the 'nameList'
        // You can use this sorted list as needed
        // For example, print the sorted names
        System.out.println("Sorted Names: " + priceList.stream().toList());

    }

    ////////////////////////////////////////////////////////////////
    // sort for games/game ports
    //
    //
    //
    /////////////////////////////////////////////////////////////////


    public static void sortByGameDescriptionAscending() {
        MyNeatList<String> descriptionList = new MyNeatList<>();

        // Loop through all game systems and extract names
        for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
            if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                descriptionList.add(HelloApplication.games.getElementFromPosition(i).getDescription());
            }
        }

        // Use insertion sort to sort the names
        SortUtils.insertionSortAscending(descriptionList);

        // Now, you have the sorted names in the 'nameList'
        // You can use this sorted list as needed
        // For example, print the sorted names
        System.out.println("Sorted Names: " + descriptionList.stream().toList());

    }

    public static void sortByGameDescriptionDescending() {
        MyNeatList<String> descriptionList = new MyNeatList<>();

        // Loop through all game systems and extract names
        for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
            if (HelloApplication.games.getElementFromPosition(i) != null) {
                descriptionList.add(HelloApplication.games.getElementFromPosition(i).getDescription());
            }
        }

        // Use insertion sort to sort the names
        SortUtils.insertionSortDescending(descriptionList);

        // Now, you have the sorted names in the 'nameList'
        // You can use this sorted list as needed
        // For example, print the sorted names
        System.out.println("Sorted Names: " + descriptionList.stream().toList());

    }

    //////////////////////////////////////////////////////////////////////////////////


    public static void sortByGameNameAscending() {
        MyNeatList<String> NameList = new MyNeatList<>();

        // Loop through all game systems and extract names
        for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
            if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                NameList.add(HelloApplication.games.getElementFromPosition(i).getTitle());
            }
        }

        // Use insertion sort to sort the names
        SortUtils.insertionSortAscending(NameList);

        // Now, you have the sorted names in the 'nameList'
        // You can use this sorted list as needed
        // For example, print the sorted names
        System.out.println("Sorted Names: " + NameList.stream().toList());

    }

    public static MyNeatList<String> sortByGameNameAscendingReturn() {
        MyNeatList<String> NameList = new MyNeatList<>();

        // Loop through all game systems and extract names
        for (int i = 0; i < HelloApplication.games.size(); i++) {
            if (HelloApplication.games.getElementFromPosition(i) != null) {
                NameList.add(HelloApplication.games.getElementFromPosition(i).getTitle());
            }
        }

        // Use insertion sort to sort the names
        SortUtils.insertionSortAscending(NameList);

        // Now, you have the sorted names in the 'nameList'
        // You can use this sorted list as needed
        // For example, print the sorted names

        return NameList;

    }

    public static void sortByGameNameDescending() {
        MyNeatList<String> NameList = new MyNeatList<>();

        // Loop through all game systems and extract names
        for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
            if (HelloApplication.games.getElementFromPosition(i) != null) {
                NameList.add(HelloApplication.games.getElementFromPosition(i).getTitle());
            }
        }

        // Use insertion sort to sort the names
        SortUtils.insertionSortDescending(NameList);

        // Now, you have the sorted names in the 'nameList'
        // You can use this sorted list as needed
        // For example, print the sorted names
        System.out.println("Sorted Names: " + NameList.stream().toList());

    }

    public static MyNeatList<String> sortByGameNameDescendingReturn() {
        MyNeatList<String> NameList = new MyNeatList<>();

        // Loop through all game systems and extract names
        for (int i = 0; i < HelloApplication.games.size(); i++) {
            if (HelloApplication.games.getElementFromPosition(i) != null) {
                NameList.add(HelloApplication.games.getElementFromPosition(i).getTitle());
            }
        }

        // Use insertion sort to sort the names
        SortUtils.insertionSortDescending(NameList);

        // Now, you have the sorted names in the 'nameList'
        // You can use this sorted list as needed
        // For example, print the sorted names
        return NameList;

    }

//////////////////////////////////////////////////////////////////////////////////


    public static void sortByGamePortYearAscending() {
        MyNeatList<Integer> portYearList = new MyNeatList<>();

        // Loop through all game systems and extract names
        for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
            if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                portYearList.add(HelloApplication.ports.getElementFromPosition(i).getNewYear());
            }
        }

        // Use insertion sort to sort the names
        SortUtils.insertionSortAscending(portYearList);

        // Now, you have the sorted names in the 'nameList'
        // You can use this sorted list as needed
        // For example, print the sorted names
        System.out.println("Sorted Names: " + portYearList.stream().toList());

    }

    public static void sortByGamePortYearDescending() {
        MyNeatList<Integer> portYearList = new MyNeatList<>();

        // Loop through all game systems and extract names
        for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
            if (HelloApplication.games.getElementFromPosition(i) != null) {
                portYearList.add(HelloApplication.ports.getElementFromPosition(i).getNewYear());
            }

            // Use insertion sort to sort the names
            SortUtils.insertionSortDescending(portYearList);

            // Now, you have the sorted names in the 'nameList'
            // You can use this sorted list as needed
            // For example, print the sorted names
            System.out.println("Sorted Names: " + portYearList.stream().toList());

        }
    }

}



