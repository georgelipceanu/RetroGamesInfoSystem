package com.example.retro.controllers;

import com.example.retro.HelloApplication;
import com.example.retro.models.Game;
import com.example.retro.models.GamePort;
import com.example.retro.models.GameSystem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
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

    public GameSystem gs;

    public void setGs(GameSystem gs) {
        this.gs = gs;
    }

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
        portController=this;
    }
}
