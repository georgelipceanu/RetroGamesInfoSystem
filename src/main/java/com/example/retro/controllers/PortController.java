package com.example.retro.controllers;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class PortController implements Initializable {

    private PortController portController;

    public PortController getPortController() {
        return portController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        portController=this;
    }
}
