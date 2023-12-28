package com.example.retro.controllers;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    private static SearchController searchController;

    public static SearchController getSearchController() {
        return searchController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchController=this;
    }
}
