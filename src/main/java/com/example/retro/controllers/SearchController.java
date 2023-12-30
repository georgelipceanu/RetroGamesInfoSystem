package com.example.retro.controllers;

import com.example.retro.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    private static SearchController searchController;

    public static SearchController getSearchController() {
        return searchController;
    }

    @FXML
    public void goBack(){
        HelloApplication.mainStage.setScene(HelloApplication.mainS);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchController=this;
    }
}
