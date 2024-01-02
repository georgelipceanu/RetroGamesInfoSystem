package com.example.retro.controllers;

import com.example.retro.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class PortController implements Initializable {

    private static PortController portController;

    public static PortController getPortController() {
        return portController;
    }

    @FXML
    private TreeView<String> portDetails;

    public TreeView<String> getPortDetails() {
        return portDetails;
    }

    @FXML
    public TextField portDev,portRelease,portCover;

    @FXML
    public void goBack(){
        HelloApplication.mainStage.setScene(HelloApplication.mainS);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        portController=this;
    }
}
