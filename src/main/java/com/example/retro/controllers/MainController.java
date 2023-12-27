package com.example.retro.controllers;

import com.example.retro.models.GameSystem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private static MainController mainController;

    @FXML
    private TextField gameSysName,gameSysManufacturer,gameSysDesc,gameSysType,gameSysMedia,gameSysYear,gameSysPrice,gameSysImage,
            gameName,gamePublisher,gameDesc,gameDev,gameRelease,gameCover,
            portDev,portRelease,portCover;

    @FXML
    private TreeView system;

    @FXML
    private ChoiceBox<GameSystem> gameSystems;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainController = this;

    }
}
