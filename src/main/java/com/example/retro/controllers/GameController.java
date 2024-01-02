package com.example.retro.controllers;

import com.example.retro.HelloApplication;
import com.example.retro.models.Game;
import com.example.retro.models.GamePort;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
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
        MainController.getMainController().clear();//refreshing main page with changes made on view pages
        for (int i = 0 ; i<HelloApplication.gameSystems.size();i++){//adding everything back into fxml elements
            if (HelloApplication.gameSystems.getElementFromPosition(i)!=null) {
                TreeItem<String> gs = new TreeItem<>("| SYSTEM |  " + HelloApplication.gameSystems.getElementFromPosition(i).getName());
                MainController.getRoot().getChildren().add(gs);
                MainController.getMainController().getGameSystems().getItems().add(HelloApplication.gameSystems.getElementFromPosition(i));
                for (Game game : HelloApplication.gameSystems.getElementFromPosition(i).getGames()) {
                    if (!(game instanceof GamePort)){
                        TreeItem<String> gameTI = new TreeItem<>("| GAME |  " + game.getTitle());
                        gs.getChildren().add(gameTI);
                        for (GamePort port : game.getPorts())
                            gameTI.getChildren().add(new TreeItem<>("| PORT |  "+ port.getTitle() + "  | " + port.getGsPortedTo() + " |"));
                    } else gs.getChildren().add(new TreeItem<>("| PORT |  " + game.getTitle()));
                }
            }
        }

        HelloApplication.mainStage.setScene(HelloApplication.mainS);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        gameController=this;
    }
}
