package com.example.retro.utils;

import com.example.retro.HelloApplication;
import com.example.retro.MyNeatList;
import com.example.retro.models.Game;
import com.example.retro.models.GamePort;
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

    public static void insertionSortAscending(int[] array) {
        int n = array.length;

        for (int i = 1; i < n; ++i) {
            int key = array[i];
            int j = i - 1;

            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
        }
    }

    public static void insertionSortAscending(String[] array) {
        int n = array.length;

        for (int i = 1; i < n; ++i) {
            String key = array[i];
            int j = i - 1;

            while (j >= 0 && array[j].compareTo(key) > 0) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
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

    public static void insertionSortDescending(int[] array) {
        int n = array.length;

        for (int i = 1; i < n; ++i) {
            int key = array[i];
            int j = i - 1;

            while (j >= 0 && array[j] < key) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
        }
    }




    public static MyNeatList<GameSystem> sortByGameSystemNameAscending() {
        MyNeatList<String> nameList = new MyNeatList<>();

        // Loop through all game systems and extract names
        for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
            if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                nameList.add(HelloApplication.gameSystems.getElementFromPosition(i).getName());
            }
        }

        // Use insertion sort to sort the names
        SortUtils.insertionSortAscending(nameList);

        MyNeatList<GameSystem> nameListObjects = new MyNeatList<>();

        // Loop through all game systems and add corresponding objects to nameListObjects
        for (String sortedName : nameList) {
            for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
                GameSystem system = HelloApplication.gameSystems.getElementFromPosition(i);
                if (system != null && sortedName.equals(system.getName())) {
                    nameListObjects.add(system);
                    break;  // Break to the next iteration of the outer loop
                }
            }
        }

        // Return the list of found GameSystem objects
        return nameListObjects;
    }

    public static MyNeatList<GameSystem> sortByGameSystemNameDescending() {
        MyNeatList<String> nameList = new MyNeatList<>();

        // Loop through all game systems and extract names
        for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
            if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                nameList.add(HelloApplication.gameSystems.getElementFromPosition(i).getName());
            }
        }

        // Use insertion sort to sort the names in descending order
        SortUtils.insertionSortDescending(nameList);

        MyNeatList<GameSystem> nameListObjects = new MyNeatList<>();

        // Loop through all game systems and add corresponding objects to nameListObjects
        for (String sortedName : nameList) {
            for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
                GameSystem system = HelloApplication.gameSystems.getElementFromPosition(i);
                if (system != null && sortedName.equals(system.getName())) {
                    nameListObjects.add(system);
                    break;  // Break to the next iteration of the outer loop
                }
            }
        }


        // Return the list of found GameSystem objects in descending order
        return nameListObjects;
    }
////////////////////////////////////////////////////////////////////////


    public static MyNeatList<GameSystem> sortByGameSystemLaunchYearAscending() {
        MyNeatList<Integer> launchYearList = new MyNeatList<>();

        // Loop through all game systems and extract launch years
        for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
            if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                launchYearList.add(HelloApplication.gameSystems.getElementFromPosition(i).getLaunchYear());
            }
        }

        // Use insertion sort to sort the launch years in ascending order
        SortUtils.insertionSortAscending(launchYearList);

        MyNeatList<GameSystem> launchYearListObjects = new MyNeatList<>();

        // Loop through all game systems and add corresponding objects to launchYearListObjects
        for (Integer sortedLaunchYear : launchYearList) {
            for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
                GameSystem system = HelloApplication.gameSystems.getElementFromPosition(i);
                if (system != null && sortedLaunchYear.equals(system.getLaunchYear())) {
                    if (!launchYearListObjects.contains(system)) {
                        launchYearListObjects.add(system);
                        break;  // Break to the next iteration of the outer loop
                    }
                }
            }
        }


        // Return the list of found GameSystem objects sorted by launch year
        return launchYearListObjects;
    }

    public static MyNeatList<GameSystem> sortByGameSystemLaunchYearAscendingInt() {
        int[] launchYearList = new int[HelloApplication.gameSystems.size()];

        // Loop through all game systems and extract launch years
        for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
            if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                launchYearList[i]=HelloApplication.gameSystems.getElementFromPosition(i).getLaunchYear();
            }
        }

        // Use insertion sort to sort the launch years in ascending order
        SortUtils.insertionSortAscending(launchYearList);

        MyNeatList<GameSystem> launchYearListObjects = new MyNeatList<>();

        // Loop through all game systems and add corresponding objects to launchYearListObjects
        for (int sortedLaunchYear : launchYearList) {
            for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
                GameSystem system = HelloApplication.gameSystems.getElementFromPosition(i);
                if (system != null && sortedLaunchYear==system.getLaunchYear()) {
                    if (!launchYearListObjects.contains(system)) {
                        launchYearListObjects.add(system);
                        break;  // Break to the next iteration of the outer loop
                    }
                }
            }
        }


        // Return the list of found GameSystem objects sorted by launch year
        return launchYearListObjects;
    }


    public static MyNeatList<GameSystem> sortByGameSystemLaunchYearDescending() {
        MyNeatList<Integer> launchYearList = new MyNeatList<>();

        // Loop through all game systems and extract launch years
        for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
            if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                launchYearList.add(HelloApplication.gameSystems.getElementFromPosition(i).getLaunchYear());
            }
        }

        // Use insertion sort to sort the launch years in descending order
        SortUtils.insertionSortDescending(launchYearList);

        MyNeatList<GameSystem> launchYearListObjects = new MyNeatList<>();

        // Loop through all game systems and add corresponding objects to launchYearListObjects
        for (Integer sortedLaunchYear : launchYearList) {
            for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
                GameSystem system = HelloApplication.gameSystems.getElementFromPosition(i);
                if (system != null && sortedLaunchYear.equals(system.getLaunchYear())) {
                    if (!launchYearListObjects.contains(system)) {
                        launchYearListObjects.add(system);
                        break;  // Break to the next iteration of the outer loop
                    }
                }
            }
        }


        // Return the list of found GameSystem objects sorted by launch year in descending order
        return launchYearListObjects;
    }

    public static MyNeatList<GameSystem> sortByGameSystemLaunchYearDescendingInt() {
        int[] launchYearList = new int[HelloApplication.gameSystems.size()];

        // Loop through all game systems and extract launch years
        for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
            if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                launchYearList[i]=HelloApplication.gameSystems.getElementFromPosition(i).getLaunchYear();
            }
        }

        // Use insertion sort to sort the launch years in descending order
        SortUtils.insertionSortDescending(launchYearList);

        MyNeatList<GameSystem> launchYearListObjects = new MyNeatList<>();

        // Loop through all game systems and add corresponding objects to launchYearListObjects
        for (Integer sortedLaunchYear : launchYearList) {
            for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
                GameSystem system = HelloApplication.gameSystems.getElementFromPosition(i);
                if (system != null && sortedLaunchYear.equals(system.getLaunchYear())) {
                    if (!launchYearListObjects.contains(system)) {
                        launchYearListObjects.add(system);
                        break;  // Break to the next iteration of the outer loop
                    }
                }
            }
        }


        // Return the list of found GameSystem objects sorted by launch year in descending order
        return launchYearListObjects;
    }


////////////////////////////////////////////////////////////////


    public static MyNeatList<GameSystem> sortByGameSystemPriceAscending() {
        MyNeatList<Double> priceList = new MyNeatList<>();

        // Loop through all game systems and extract prices
        for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
            if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                priceList.add(HelloApplication.gameSystems.getElementFromPosition(i).getPrice());
            }
        }

        // Use insertion sort to sort the prices in ascending order
        SortUtils.insertionSortAscending(priceList);

        MyNeatList<GameSystem> priceListObjects = new MyNeatList<>();

        // Loop through all game systems and add corresponding objects to priceListObjects
        for (Double sortedPrice : priceList) {
            for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
                GameSystem system = HelloApplication.gameSystems.getElementFromPosition(i);
                if (system != null && sortedPrice.equals(system.getPrice())) {
                    if (!priceListObjects.contains(system)) {
                        priceListObjects.add(system);
                        break;  // Break to the next iteration of the outer loop
                    }
                }
            }
        }

        // Now, you have the sorted prices in 'priceList' and corresponding objects in 'priceListObjects'
        // You can use these lists as needed
        // For example, print the sorted prices and corresponding objects
        for (Double sortedPrice : priceList) {
            System.out.println("Sorted Price: " + sortedPrice);
        }

        for (GameSystem system : priceListObjects) {
            System.out.println("Corresponding Object: " + system);
        }

        // Return the list of found GameSystem objects sorted by price in ascending order
        return priceListObjects;
    }


    public static MyNeatList<GameSystem> sortByGameSystemPriceDescending() {
        MyNeatList<Double> priceList = new MyNeatList<>();

        // Loop through all game systems and extract prices
        for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
            if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                priceList.add(HelloApplication.gameSystems.getElementFromPosition(i).getPrice());
            }
        }

        // Use insertion sort to sort the prices in descending order
        SortUtils.insertionSortDescending(priceList);

        MyNeatList<GameSystem> priceListObjects = new MyNeatList<>();

        // Loop through all game systems and add corresponding objects to priceListObjects
        for (Double sortedPrice : priceList) {
            for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
                GameSystem system = HelloApplication.gameSystems.getElementFromPosition(i);
                if (system != null && sortedPrice.equals(system.getPrice())) {
                    if (!priceListObjects.contains(system)) {
                        priceListObjects.add(system);
                        break;  // Break to the next iteration of the outer loop
                    }
                }
            }
        }

        // Now, you have the sorted prices in 'priceList' and corresponding objects in 'priceListObjects'
        // You can use these lists as needed
        // For example, print the sorted prices and corresponding objects
        for (Double sortedPrice : priceList) {
            System.out.println("Sorted Price: " + sortedPrice);
        }

        for (GameSystem system : priceListObjects) {
            System.out.println("Corresponding Object: " + system);
        }

        // Return the list of found GameSystem objects sorted by price in descending order
        return priceListObjects;
    }


    ////////////////////////////////////////////////////////////////
    // sort for games/game ports
    //
    //
    //
    /////////////////////////////////////////////////////////////////


    public static MyNeatList<Game> sortByGameDescriptionAscending() {
        MyNeatList<String> descriptionList = new MyNeatList<>();

        // Loop through all game systems and extract game descriptions
        for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
            if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                descriptionList.add(HelloApplication.gameSystems.getElementFromPosition(i).getDescription());
            }
        }

        // Use insertion sort to sort the game descriptions in ascending order
        SortUtils.insertionSortAscending(descriptionList);

        MyNeatList<Game> descriptionListObjects = new MyNeatList<>();

        // Loop through all game systems and add corresponding objects to descriptionListObjects
        for (String sortedDescription : descriptionList) {
            for (int i = 0; i < HelloApplication.games.size(); i++) {
                Game system = HelloApplication.games.getElementFromPosition(i);
                if (system != null && sortedDescription.equals(system.getDescription())) {
                    descriptionListObjects.add(system);
                    break;  // Break to the next iteration of the outer loop
                }
            }
        }

        // Return the list of found GameSystem objects sorted by game description in ascending order
        return descriptionListObjects;
    }

    public static MyNeatList<Game> sortByGameDescriptionDescending() {
        MyNeatList<String> descriptionList = new MyNeatList<>();

        // Loop through all game systems and extract game descriptions
        for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
            if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                descriptionList.add(HelloApplication.gameSystems.getElementFromPosition(i).getDescription());
            }
        }

        // Use insertion sort to sort the game descriptions in descending order
        SortUtils.insertionSortDescending(descriptionList);

        MyNeatList<Game> descriptionListObjects = new MyNeatList<>();

        // Loop through all games and add corresponding objects to descriptionListObjects
        for (String sortedDescription : descriptionList) {
            for (int i = 0; i < HelloApplication.games.size(); i++) {
                Game game = HelloApplication.games.getElementFromPosition(i);
                if (game != null && sortedDescription.equals(game.getDescription())) {
                    descriptionListObjects.add(game);
                    break;  // Break to the next iteration of the outer loop
                }
            }
        }

        // Return the list of found Game objects sorted by game description in descending order
        return descriptionListObjects;
    }


    //////////////////////////////////////////////////////////////////////////////////


    public static MyNeatList<Game> sortByGameNameAscending() {
        MyNeatList<String> nameList = new MyNeatList<>();

        // Loop through all game systems and extract game names
        for (int i = 0; i < HelloApplication.games.size(); i++) {
            if (HelloApplication.games.getElementFromPosition(i) != null) {
                nameList.add(HelloApplication.games.getElementFromPosition(i).getTitle());
            }
        }

        // Use insertion sort to sort the game names in ascending order
        SortUtils.insertionSortAscending(nameList);

        MyNeatList<Game> nameListObjects = new MyNeatList<>();

        // Loop through all game systems and add corresponding objects to nameListObjects
        for (String sortedName : nameList) {
            for (int i = 0; i < HelloApplication.games.size(); i++) {
                Game game = HelloApplication.games.getElementFromPosition(i);
                if (game != null && sortedName.equals(game.getTitle())) {
                    nameListObjects.add(game);
                    break;  // Break to the next iteration of the outer loop
                }
            }
        }

        // Return the list of found Game objects sorted by game name in ascending order
        return nameListObjects;
    }

    public static MyNeatList<Game> sortByGameNameDescending() {
        MyNeatList<String> nameList = new MyNeatList<>();

        // Loop through all game systems and extract game names
        for (int i = 0; i < HelloApplication.games.size(); i++) {
            if (HelloApplication.games.getElementFromPosition(i) != null) {
                nameList.add(HelloApplication.games.getElementFromPosition(i).getTitle());
            }
        }

        // Use insertion sort to sort the game names in descending order
        SortUtils.insertionSortDescending(nameList);

        MyNeatList<Game> nameListObjects = new MyNeatList<>();

        // Loop through all game systems and add corresponding objects to nameListObjects
        for (String sortedName : nameList) {
            for (int i = 0; i < HelloApplication.games.size(); i++) {
                Game game = HelloApplication.games.getElementFromPosition(i);
                if (game != null && sortedName.equals(game.getTitle())) {
                    nameListObjects.add(game);
                    break;  // Break to the next iteration of the outer loop
                }
            }
        }

        // Return the list of found Game objects sorted by game name in descending order
        return nameListObjects;
    }


    //////////////////////////////////////////////////////////////////////////////////////////////



    public static MyNeatList<Game> sortByGameReleaseYearAscending() {
        MyNeatList<Integer> releaseYearList = new MyNeatList<>();

        // Loop through all games and extract release years
        for (int i = 0; i < HelloApplication.games.size(); i++) {
            if (HelloApplication.games.getElementFromPosition(i) != null) {
                releaseYearList.add(HelloApplication.games.getElementFromPosition(i).getYearOfRelease());
            }
        }

        // Use insertion sort to sort the release years in ascending order
        SortUtils.insertionSortAscending(releaseYearList);

        MyNeatList<Game> sortedGames = new MyNeatList<>();

        // Loop through all games and add corresponding objects to sortedGames
        for (Integer sortedReleaseYear : releaseYearList) {
            for (int i = 0; i < HelloApplication.games.size(); i++) {
                Game game = HelloApplication.games.getElementFromPosition(i);
                if (game != null && sortedReleaseYear.equals(game.getYearOfRelease())) {
                    sortedGames.add(game);
                    break;  // Break to the next iteration of the outer loop
                }
            }
        }

        // Return the list of found Game objects sorted by release year in ascending order
        return sortedGames;
    }

    public static MyNeatList<Game> sortByGameReleaseYearDescending() {
        MyNeatList<Integer> releaseYearList = new MyNeatList<>();

        // Loop through all games and extract release years
        for (int i = 0; i < HelloApplication.games.size(); i++) {
            if (HelloApplication.games.getElementFromPosition(i) != null) {
                releaseYearList.add(HelloApplication.games.getElementFromPosition(i).getYearOfRelease());
            }
        }

        // Use insertion sort to sort the release years in ascending order
        SortUtils.insertionSortDescending(releaseYearList);

        MyNeatList<Game> sortedGames = new MyNeatList<>();

        // Loop through all games and add corresponding objects to sortedGames
        for (Integer sortedReleaseYear : releaseYearList) {
            for (int i = 0; i < HelloApplication.games.size(); i++) {
                Game game = HelloApplication.games.getElementFromPosition(i);
                if (game != null && sortedReleaseYear.equals(game.getYearOfRelease())) {
                    sortedGames.add(game);
                    break;  // Break to the next iteration of the outer loop
                }
            }
        }

        // Return the list of found Game objects sorted by release year in ascending order
        return sortedGames;
    }

    public static MyNeatList<Game> sortByGameReleaseYearAscendingInt() {
        int[] releaseYearList = new int[HelloApplication.games.size()];

        // Loop through all game systems and extract launch years
        for (int i = 0; i < HelloApplication.games.size(); i++) {
            if (HelloApplication.games.getElementFromPosition(i) != null) {
                releaseYearList[i]=HelloApplication.games.getElementFromPosition(i).getYearOfRelease();
            }
        }

        // Use insertion sort to sort the release years in ascending order
        SortUtils.insertionSortAscending(releaseYearList);

        MyNeatList<Game> sortedGames = new MyNeatList<>();

        // Loop through all games and add corresponding objects to sortedGames
        for (Integer sortedReleaseYear : releaseYearList) {
            for (int i = 0; i < HelloApplication.games.size(); i++) {
                Game game = HelloApplication.games.getElementFromPosition(i);
                if (game != null && sortedReleaseYear.equals(game.getYearOfRelease())) {
                    if (!sortedGames.contains(game)) {
                        sortedGames.add(game);
                        break;  // Break to the next iteration of the outer loop
                    }
                }
            }
        }

        // Return the list of found Game objects sorted by release year in ascending order
        return sortedGames;
    }

    public static MyNeatList<Game> sortByGameReleaseYearDescendingInt() {
        int[] releaseYearList = new int[HelloApplication.games.size()];

        // Loop through all game systems and extract launch years
        for (int i = 0; i < HelloApplication.games.size(); i++) {
            if (HelloApplication.games.getElementFromPosition(i) != null) {
                releaseYearList[i]=HelloApplication.games.getElementFromPosition(i).getYearOfRelease();
            }
        }

        // Use insertion sort to sort the release years in descending order
        SortUtils.insertionSortDescending(releaseYearList);

        MyNeatList<Game> sortedGames = new MyNeatList<>();

        // Loop through all games and add corresponding objects to sortedGames
        for (Integer sortedReleaseYear : releaseYearList) {
            for (int i = 0; i < HelloApplication.games.size(); i++) {
                Game game = HelloApplication.games.getElementFromPosition(i);
                if (game != null && sortedReleaseYear.equals(game.getYearOfRelease())) {
                    if (!sortedGames.contains(game))
                    sortedGames.add(game);
                    break;  // Break to the next iteration of the outer loop
                }
            }
        }

        // Return the list of found Game objects sorted by release year in descending order
        return sortedGames;
    }




    //////////////////////////////////////////////////////////////////////////////////////////////


    public static MyNeatList<GamePort> sortByGamePortYearAscending() {
        MyNeatList<Integer> portYearList = new MyNeatList<>();

        // Loop through all game systems and extract port years
        for (int i = 0; i < HelloApplication.ports.size(); i++) {
            if (HelloApplication.ports.getElementFromPosition(i) != null) {
                portYearList.add(HelloApplication.ports.getElementFromPosition(i).getNewYear());
            }
        }

        // Use insertion sort to sort the port years in ascending order
        SortUtils.insertionSortAscending(portYearList);

        MyNeatList<GamePort> portYearListObjects = new MyNeatList<>();

        // Loop through all game systems and add corresponding objects to portYearListObjects
        for (Integer sortedPortYear : portYearList) {
            for (int i = 0; i < HelloApplication.ports.size(); i++) {
                GamePort port = HelloApplication.ports.getElementFromPosition(i);
                if (port != null && sortedPortYear.equals(port.getNewYear())) {
                    portYearListObjects.add(port);
                    break;  // Break to the next iteration of the outer loop
                }
            }
        }

        // Return the list of found Game objects sorted by port year in ascending order
        return portYearListObjects;
    }

    public static MyNeatList<GamePort> sortByGamePortYearAscendingInt() {
        int[] portYearList = new int[HelloApplication.ports.size()];

        // Loop through all game systems and extract port years
        for (int i = 0; i < HelloApplication.ports.size(); i++) {
            if (HelloApplication.ports.getElementFromPosition(i) != null) {
                portYearList[i]=HelloApplication.ports.getElementFromPosition(i).getNewYear();
            }
        }

        // Use insertion sort to sort the port years in ascending order
        SortUtils.insertionSortAscending(portYearList);

        MyNeatList<GamePort> portYearListObjects = new MyNeatList<>();

        // Loop through all game systems and add corresponding objects to portYearListObjects
        for (Integer sortedPortYear : portYearList) {
            for (int i = 0; i < HelloApplication.ports.size(); i++) {
                GamePort port = HelloApplication.ports.getElementFromPosition(i);
                if (port != null && sortedPortYear.equals(port.getNewYear())) {
                    if (!portYearListObjects.contains(port)) {
                        portYearListObjects.add(port);
                        break;  // Break to the next iteration of the outer loop
                    }
                }
            }
        }

        // Return the list of found Game objects sorted by port year in ascending order
        return portYearListObjects;
    }



    public static MyNeatList<GamePort> sortByGamePortYearDescending() {
        MyNeatList<Integer> portYearList = new MyNeatList<>();

        // Loop through all game systems and extract port years
        for (int i = 0; i < HelloApplication.ports.size(); i++) {
            if (HelloApplication.ports.getElementFromPosition(i) != null) {
                portYearList.add(HelloApplication.ports.getElementFromPosition(i).getNewYear());
            }
        }

        // Use insertion sort to sort the port years in descending order
        SortUtils.insertionSortDescending(portYearList);

        MyNeatList<GamePort> portYearListObjects = new MyNeatList<>();

        // Loop through all game systems and add corresponding objects to portYearListObjects
        for (int sortedReleaseYear : portYearList) {
            for (int i = 0; i < HelloApplication.ports.size(); i++) {
                GamePort port = HelloApplication.ports.getElementFromPosition(i);
                if (port != null && sortedReleaseYear==(port.getYearOfRelease())) {
                    if (!portYearList.contains(port))
                        portYearListObjects.add(port);
                    break;  // Break to the next iteration of the outer loop
                }
            }
        }

        // Return the list of found Game objects sorted by port year in descending order
        return portYearListObjects;
    }


    public static MyNeatList<GamePort> sortByGamePortYearDescendingInt() {
        int[] portYearList = new int[HelloApplication.ports.size()];

        // Loop through all game systems and extract port years
        for (int i = 0; i < HelloApplication.ports.size(); i++) {
            if (HelloApplication.ports.getElementFromPosition(i) != null) {
                portYearList[i]=HelloApplication.ports.getElementFromPosition(i).getNewYear();
            }
        }

        // Use insertion sort to sort the port years in ascending order
        SortUtils.insertionSortDescending(portYearList);

        MyNeatList<GamePort> portYearListObjects = new MyNeatList<>();

        // Loop through all game systems and add corresponding objects to portYearListObjects
        for (Integer sortedPortYear : portYearList) {
            for (int i = 0; i < HelloApplication.ports.size(); i++) {
                GamePort port = HelloApplication.ports.getElementFromPosition(i);
                if (port != null && sortedPortYear.equals(port.getNewYear())) {
                    if (!portYearListObjects.contains(port)) {
                        portYearListObjects.add(port);
                        break;  // Break to the next iteration of the outer loop
                    }
                }
            }
        }

        // Return the list of found Game objects sorted by port year in ascending order
        return portYearListObjects;
    }









    public static MyNeatList<GamePort> sortByGamePortNameAscending() {
        MyNeatList<String> portNameList = new MyNeatList<>();

        // Loop through all game ports and extract port titles
        for (int i = 0; i < HelloApplication.ports.size(); i++) {
            if (HelloApplication.ports.getElementFromPosition(i) != null) {
                portNameList.add(HelloApplication.ports.getElementFromPosition(i).getTitle());
            }
        }

        // Use insertion sort to sort the port titles in ascending order
        SortUtils.insertionSortAscending(portNameList);

        MyNeatList<GamePort> sortedPorts = new MyNeatList<>();

        // Loop through all game ports and add corresponding objects to sortedPorts
        for (String sortedPortName : portNameList) {
            for (int i = 0; i < HelloApplication.ports.size(); i++) {
                GamePort port = HelloApplication.ports.getElementFromPosition(i);
                if (port != null && sortedPortName.equals(port.getTitle())) {
                    sortedPorts.add(port);
                    break;  // Break to the next iteration of the outer loop
                }
            }
        }

        // Return the list of found GamePort objects sorted by port title in ascending order
        return sortedPorts;
    }

    public static MyNeatList<GamePort> sortByGamePortNameAscendingString() {
        String[] portNameList = new String[HelloApplication.ports.population()];

        // Loop through all game ports and extract port titles
        for (int i = 0; i < HelloApplication.ports.size(); i++) {
            for (int j = 0 ; j<HelloApplication.ports.population();j++) {
                if (HelloApplication.ports.getElementFromPosition(i) != null) {
                    portNameList[j] = (HelloApplication.ports.getElementFromPosition(i).getTitle());
                    break;
                }
            }
        }

        // Use insertion sort to sort the port titles in ascending order
        SortUtils.insertionSortAscending(portNameList);

        MyNeatList<GamePort> sortedPorts = new MyNeatList<>();

        // Loop through all game ports and add corresponding objects to sortedPorts
        for (String sortedPortName : portNameList) {
            for (int i = 0; i < HelloApplication.ports.size(); i++) {
                GamePort port = HelloApplication.ports.getElementFromPosition(i);
                if (port != null && sortedPortName.equals(port.getTitle())) {
                    if (!sortedPorts.contains(port)) {
                        sortedPorts.add(port);
                        break;  // Break to the next iteration of the outer loop
                    }
                }
            }
        }

        // Return the list of found GamePort objects sorted by port title in ascending order
        return sortedPorts;
    }




    public static MyNeatList<GamePort> sortByGamePortNameDescending() {
        MyNeatList<String> portNameList = new MyNeatList<>();

        // Loop through all game ports and extract port titles
        for (int i = 0; i < HelloApplication.ports.size(); i++) {
            if (HelloApplication.ports.getElementFromPosition(i) != null) {
                portNameList.add(HelloApplication.ports.getElementFromPosition(i).getTitle());
            }
        }

        // Use insertion sort to sort the port titles in descending order
        SortUtils.insertionSortDescending(portNameList);

        MyNeatList<GamePort> sortedPorts = new MyNeatList<>();

        // Loop through all game ports and add corresponding objects to sortedPorts
        for (String sortedPortName : portNameList) {
            for (int i = 0; i < HelloApplication.ports.size(); i++) {
                GamePort port = HelloApplication.ports.getElementFromPosition(i);
                if (port != null && sortedPortName.equals(port.getTitle())) {
                    if (!sortedPorts.contains(port)) {
                        sortedPorts.add(port);
                        break;  // Break to the next iteration of the outer loop
                    }
                }
            }
        }

        // Return the list of found GamePort objects sorted by port title in descending order
        return sortedPorts;
    }








}



