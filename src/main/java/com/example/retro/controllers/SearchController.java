package com.example.retro.controllers;

import com.example.retro.HelloApplication;
import com.example.retro.MyNeatList;
import com.example.retro.models.GameSystem;
import com.example.retro.utils.SortUtils;
import com.example.retro.utils.Utilities;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    private static SearchController searchController;

    public static SearchController getSearchController() {
        return searchController;
    }


    public void goBack(){
        HelloApplication.mainStage.setScene(HelloApplication.mainS);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchController=this;
    }




//    public void searchGameSystemsByName(){
//        if (!searchBar.getText().isEmpty()) {
//            searchedListOfGameSystems.getItems().clear();
//            String goods = searchBar.getText();
//            MyNeatList<String> strSearchedGameSystems = new MyNeatList<>();
//
//            for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
//                    if (HelloApplication.gameSystems.getElementFromPosition(i).getName().toLowerCase().contains(goods.toLowerCase())) {//searches through all containers and pallets to find goods
//                        strSearchedGameSystems.add(HelloApplication.gameSystems.getElementFromPosition(i).getName());
//                    }
//                }
//            SortUtils.insertionSortAscending(strSearchedGameSystems);
//            }
//
//            if (!strSearchedGameSystems.isEmpty()) {//checks if any goods were found
//                searchedListOfGameSystems.getItems().addAll(strSearchedGameSystems);//adds them to listview
//            }else Utilities.showWarningAlert("WARNING!","No goods of this description found");
//        }else Utilities.showWarningAlert("WARNING!","Search bar is empty");
//
    }



