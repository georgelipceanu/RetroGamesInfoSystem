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

    private Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    private GameSystem gs;

    public GameSystem getGs() {
        return gs;
    }

    public void setGs(GameSystem gs) {
        this.gs = gs;
    }

    public void edit(){
        int key = game.getPosition();
        if (!gameName.getText().isEmpty() && !gamePublisher.getText().isEmpty() && !gameDesc.getText().isEmpty() && !gameDev.getText().isEmpty() &&
                !gameCover.getText().isEmpty() && !gameRelease.getText().isEmpty()) { //checking if each text box is filled
            String name = gameName.getText();
            String desc = gameDesc.getText();
            String publisher = gamePublisher.getText();
            String dev = gameDev.getText();
            String url = gameCover.getText();//getting details from boxes

            int year = 0;
            boolean validYear=true;
            try {//checking if number is entered
                year = Integer.parseInt(gameRelease.getText());
            } catch (NumberFormatException e){
                validYear=false;
            }

            Game gameToAdd = new Game(name,publisher,desc,dev,url,year);
            boolean uniqueName=true;

            if (!gameToAdd.getTitle().equals(name)) {//only checks name if it has been changed


                for (int i = 0; i < HelloApplication.games.size(); i++) {
                    if (HelloApplication.games.getElementFromPosition(i) != null) {
                        if (HelloApplication.games.getElementFromPosition(i).getTitle().equalsIgnoreCase(gameToAdd.getTitle())) {//checking if name is unique
                            uniqueName = false;
                            break;
                        }
                    }
                }
            }



            if (validYear&& Utilities.isValidURL(url)&&uniqueName && !game.getTitle().contains("| GAME |")){//all valid details
                for (GamePort port : game.getPorts()){
                    gameToAdd.getPorts().add(port);//adding all games back to new gs
                }

                gameToAdd.setPosition(key);
                HelloApplication.games.replace(gameToAdd,key);
                int index = 0;
                for (Game game1 : gs.getGames()){
                    if (game1==game){
                        index=gs.getGames().indexOf(game1);//getting index for set() method in MyNeatList<>
                        break;
                    }

                }

                gs.getGames().set(index,gameToAdd);//updating game in games list
                HelloApplication.gameSystems.replace(gs,gs.getPosition());//updating gs with new game in games list
                gameToAdd.setPosition(game.getPosition());
                HelloApplication.games.replace(gameToAdd,game.getPosition());

                game=gameToAdd;

                gameDetails.setRoot(new TreeItem<>(game.getTitle() + "  | " + gs.getName() + " |"));
                gameDetails.getRoot().getChildren().add(new TreeItem<>("Description: "+game.getDescription()));
                gameDetails.getRoot().getChildren().add(new TreeItem<>("Publisher: "+game.getPublisher()));
                gameDetails.getRoot().getChildren().add(new TreeItem<>("Developer: "+game.getOgDeveloper()));
                gameDetails.getRoot().getChildren().add(new TreeItem<>("Year of release: "+game.getYearOfRelease()));
                gameDetails.getRoot().getChildren().add(new TreeItem<>("Cover Art (URL): "+game.getCoverArtURL()));
                TreeItem<String> ports =new TreeItem<>("PORTS: ");
                gameDetails.getRoot().getChildren().add(ports);

                for (GamePort port : game.getPorts()) {
                    ports.getChildren().add(new TreeItem<>("| " + port.getGsPortedTo() + " |"));
                }



            } else Utilities.showWarningAlert("WARNING", "Enter valid details");
        }else Utilities.showWarningAlert("WARNING", "Fill all boxes");
    }

    @FXML
    public void delete(){
        gs.getGames().remove(game);
        HelloApplication.gameSystems.replace(gs,gs.getPosition());//updating with gs without game

        for (GamePort port : game.getPorts()){
            String gsToRemoveFromName = port.getGsPortedTo();
            int keyForGSToRemoveFrom = HelloApplication.gameSystems.hashFunction(gsToRemoveFromName);
            GameSystem gsToRemoveFrom=HelloApplication.gameSystems.getElementFromPosition(keyForGSToRemoveFrom);

            boolean gsToRemoveFromEmpty=(gsToRemoveFrom)==null;
            if (gsToRemoveFromEmpty)
                for (int i=0;i<HelloApplication.gameSystems.size();i++)
                    if (HelloApplication.gameSystems.getElementFromPosition(i)!=null){
                        gsToRemoveFrom=HelloApplication.gameSystems.getElementFromPosition(i);//assigning dummy system to avoid null pointer exception if gsToAddTo is initially null
                        break;
                    }


            if (!gsToRemoveFrom.getName().equals(gsToRemoveFromName)) {//finding game system in backend hash map stored at diff location
                int home = keyForGSToRemoveFrom;
                do {
                    keyForGSToRemoveFrom = (keyForGSToRemoveFrom + 1) % (HelloApplication.gameSystems.size());
                    if (HelloApplication.gameSystems.getElementFromPosition(keyForGSToRemoveFrom) != null) {
                        if (HelloApplication.gameSystems.getElementFromPosition(keyForGSToRemoveFrom).getName().equalsIgnoreCase(gsToRemoveFromName)) {
                            gsToRemoveFrom = HelloApplication.gameSystems.getElementFromPosition(keyForGSToRemoveFrom);
                            break;
                        }
                    }
                } while (home != keyForGSToRemoveFrom);
            }
            gsToRemoveFrom.getGames().remove(port);
            HelloApplication.gameSystems.replace(gsToRemoveFrom,gsToRemoveFrom.getPosition());
        }

        HelloApplication.games.delete(game.getPosition());

        MainController.getMainController().clear();//refreshing main page with changes made on view pages
        MainController.getMainController().refresh();
        HelloApplication.mainStage.setScene(HelloApplication.mainS);

    }

    @FXML
    public void view(){

    }

    @FXML
    public void goBack(){
        MainController.getMainController().clear();//refreshing main page with changes made on view pages
        MainController.getMainController().refresh();
        HelloApplication.mainStage.setScene(HelloApplication.mainS);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        gameController=this;
    }
}
