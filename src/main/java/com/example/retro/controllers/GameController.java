package com.example.retro.controllers;

import com.example.retro.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    private static GameController gameController;

    public static GameController getGameController() {
        return gameController;
    }

    @FXML
    private TreeView<String> gameDetails;

    public TreeView<String> getGameDetails() {
        return gameDetails;
    }

    @FXML
    public TextField gameName,gamePublisher,gameDesc,gameDev,gameRelease,gameCover,
            portDev,portRelease,portCover;

    @FXML
    public void goBack(){
        HelloApplication.mainStage.setScene(HelloApplication.mainS);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        gameController=this;
    }
}
