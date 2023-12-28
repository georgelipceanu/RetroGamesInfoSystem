package com.example.retro.controllers;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    private GameController gameController;

    public GameController getGameController() {
        return gameController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        gameController=this;
    }
}
