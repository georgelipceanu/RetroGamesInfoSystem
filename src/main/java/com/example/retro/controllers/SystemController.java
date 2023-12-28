package com.example.retro.controllers;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SystemController implements Initializable {

    private SystemController systemController;

    public SystemController getSystemController() {
        return systemController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        systemController=this;
    }
}
