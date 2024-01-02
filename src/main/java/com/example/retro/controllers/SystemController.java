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
            gameName,gamePublisher,gameDesc,gameDev,gameRelease,gameCover;



    @FXML
    private TreeView<String> systemDetails;


    public TreeView<String> getSystemDetails() {
        return systemDetails;
    }

    private GameSystem gs;

    public GameSystem getGs() {
        return gs;
    }

    public void setGs(GameSystem gs) {
        this.gs = gs;
    }

    private TreeItem<String> games;

    public TreeItem<String> getGames() {
        return games;
    }

    public void setGames(TreeItem<String> games) {
        this.games = games;
    }

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

            for (int i = 0; i<HelloApplication.games.size();i++){
                if (HelloApplication.games.getElementFromPosition(i)!=null) {
                    if (HelloApplication.games.getElementFromPosition(i).getTitle().equalsIgnoreCase(gameToAdd.getTitle())) {//check for unique name across all games
                        uniqueName = false;
                        break;
                    }
                }
            }






            if (validYear && Utilities.isValidURL(url) && uniqueName) {
                gameToAdd.setPosition(HelloApplication.games.add(gameToAdd));
                gs.getGames().add(gameToAdd);
                games.getChildren().add(new TreeItem<>(gameToAdd.getTitle() + " (Original Game)"));
            } else Utilities.showWarningAlert("WARNING", "Enter valid details");

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
                            gameTI.getChildren().add(new TreeItem<>("| PORT |  "+ port.getTitle() + "  | " + port.getGsPortedTo() + " |"));
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
