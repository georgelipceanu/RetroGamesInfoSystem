package com.example.retro.controllers;

import com.example.retro.HelloApplication;
import com.example.retro.models.Game;
import com.example.retro.models.GamePort;
import com.example.retro.models.GameSystem;
import com.example.retro.utils.Utilities;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class SystemController implements Initializable {

    private static SystemController systemController;


    public static SystemController getSystemController() {
        return systemController;
    }

    @FXML
    public TextField gameSysName,gameSysManufacturer,gameSysDesc,gameSysType,gameSysMedia,gameSysYear,gameSysPrice,gameSysImage,
            gameName,gamePublisher,gameDesc,gameDev,gameRelease,gameCover,
            portDev,portRelease,portCover;


    @FXML
    private TreeView<String> systemDetails;


    public TreeView<String> getSystemDetails() {
        return systemDetails;
    }

    private GameSystem gs;

    @FXML
    public void addGame() {
        if (!gameName.getText().isEmpty() && !gamePublisher.getText().isEmpty() && !gameDesc.getText().isEmpty() && !gameDev.getText().isEmpty() &&
                !gameCover.getText().isEmpty() && !gameRelease.getText().isEmpty()) { //checking if each text box is filled
            String name = gameName.getText();
            String desc = gameDesc.getText();
            String publisher = gamePublisher.getText();
            String dev = gameDev.getText();
            String url = gameCover.getText();

            int year = 0;
            boolean validYear=true;
            try {
                year = Integer.parseInt(gameRelease.getText());
            } catch (NumberFormatException e){
                validYear=false;
            }


            Game gameToAdd = new Game(name,publisher,desc,dev,url,year);
            boolean uniqueName=true;


            TreeItem<String> gsToAddToTI=systemDetails.getSelectionModel().getSelectedItem();



            if (systemDetails.getSelectionModel().getSelectedItem()!=null && gsToAddToTI!=systemDetails.getRoot()) {
                if (validYear && Utilities.isValidURL(url) && uniqueName) {
                    gameToAdd.setPosition(HelloApplication.games.add(gameToAdd));
                    gsToAddToTI.getChildren().add(new TreeItem<>(gameToAdd.getTitle()));
                } else Utilities.showWarningAlert("WARNING", "Enter valid details");
            } else Utilities.showWarningAlert("WARNING", "Select a Game System to add to");
        }else Utilities.showWarningAlert("WARNING", "Fill all boxes");
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
                            gameTI.getChildren().add(new TreeItem<>("| PORT |  "+ port.getTitle() + "  | " + port.getGsPortedTo()));
                    } else gs.getChildren().add(new TreeItem<>("| PORT |  " + game.getTitle()));
                }
            }
        }

        HelloApplication.mainStage.setScene(HelloApplication.mainS);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        systemController=this;
    }
}
