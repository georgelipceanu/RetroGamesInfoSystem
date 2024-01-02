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
    public void deleteSystem(){
        for (Game game : gs.getGames()){
            if (game instanceof GamePort) {
                for (TreeItem<String> portTI : games.getChildren()) {
                    boolean portRemoved=false;

                    String gsPortedToName = portTI.getValue().substring(game.getTitle().length()+12,portTI.getValue().length()-1);
                    int keyGSOfPortToRemoveFrom = HelloApplication.gameSystems.hashFunction(gsPortedToName);
                    GameSystem gameSystemToRemovePortFrom = HelloApplication.gameSystems.getElementFromPosition(keyGSOfPortToRemoveFrom);
                    boolean gsOfPortToRemoveFrom = (gameSystemToRemovePortFrom) == null;
                    if (gsOfPortToRemoveFrom)
                        for (int i = 0; i < HelloApplication.gameSystems.size(); i++)
                            if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                                gameSystemToRemovePortFrom = HelloApplication.gameSystems.getElementFromPosition(i);//assigning dummy system to avoid null pointer exception if gsToAddTo is initially null
                                break;
                            }

                    if (!gameSystemToRemovePortFrom.getName().equals(gsPortedToName)) {//finding game system in backend hash map stored at diff location
                        int home = keyGSOfPortToRemoveFrom;
                        do {
                            keyGSOfPortToRemoveFrom = (keyGSOfPortToRemoveFrom + 1) % (HelloApplication.gameSystems.size());
                            if (HelloApplication.gameSystems.getElementFromPosition(keyGSOfPortToRemoveFrom) != null) {
                                if (HelloApplication.gameSystems.getElementFromPosition(keyGSOfPortToRemoveFrom).getName().equalsIgnoreCase(gsPortedToName)) {
                                    gameSystemToRemovePortFrom = HelloApplication.gameSystems.getElementFromPosition(keyGSOfPortToRemoveFrom);
                                    break;
                                }
                            }
                        } while (home != keyGSOfPortToRemoveFrom);
                    }

                    for (Game game1 : gameSystemToRemovePortFrom.getGames()) { //removing port from ported to system
                        if (game1 instanceof GamePort) {
                            if (game1.getTitle().equals(game.getTitle())) {
                                gameSystemToRemovePortFrom.getGames().remove(game1);
                                portRemoved=true;
                                break;
                            }
                        }
                    }

                    if (portRemoved) break;
                }

                HelloApplication.ports.delete(((GamePort) game).getPortPosition());
            } else {
                for (GamePort port : game.getPorts()){

                }
                HelloApplication.games.delete(game.getPosition());
            }
        }
        HelloApplication.gameSystems.delete(gs.getPosition());
        MainController.getMainController().refresh();
        HelloApplication.mainStage.setScene(HelloApplication.mainS);//brought back to main page after delete
    }

    @FXML
    public void goBack(){
        MainController.getMainController().clear();//refreshing main page with changes made on view pages
        MainController.getMainController().refresh();

        HelloApplication.mainStage.setScene(HelloApplication.mainS);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        systemController=this;
    }
}
