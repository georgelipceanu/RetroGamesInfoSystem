package com.example.retro.controllers;

import com.example.retro.HelloApplication;
import com.example.retro.MyNeatList;
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

    public GamePort gamePort;

    public void setGamePort(GamePort gamePort) {
        this.gamePort = gamePort;
    }

    public void setGs(GameSystem gs) {
        this.gs = gs;
    }

    public void edit(){
        if (!portCover.getText().isEmpty() && !portRelease.getText().isEmpty() && !portDev.getText().isEmpty()) { //checking if each text box is filled
            String dev = portDev.getText();
            String url = portCover.getText();//getting details from boxes

            int year = 0;
            boolean validYear = true;
            try {
                year = Integer.parseInt(portRelease.getText());//getting each attribute of port
            } catch (NumberFormatException e) {
                validYear = false;
            }


            String gameName = gamePort.getTitle(); //cutting out "| GAME |  "
            int key = HelloApplication.games.hashFunction(gameName);
            Game gameToPort = HelloApplication.games.getElementFromPosition(key);
            boolean gameEmpty = (gameToPort == null);
            if (gameEmpty)
                for (int i = 0; i < HelloApplication.games.size(); i++)
                    if (HelloApplication.games.getElementFromPosition(i) != null) {
                        gameToPort = HelloApplication.games.getElementFromPosition(i);//assigning dummy game to avoid null pointer exception if gsToAddTo is initially null
                        break;
                    }
            if (!gameToPort.getTitle().equals(gameName)) {
                int home = key;
                do {
                    key = (key + 1) % (HelloApplication.games.size());
                    if (HelloApplication.games.getElementFromPosition(key) != null) {
                        if (HelloApplication.games.getElementFromPosition(key).getTitle().equals(gameName)) {
                            gameToPort = HelloApplication.games.getElementFromPosition(key);
                            break;
                        }
                    }

                } while (home != key);
            }

            GamePort portToAdd = new GamePort(gameToPort.getTitle(), gameToPort.getPublisher(), gameToPort.getDescription(), gameToPort.getOgDeveloper()
                    , gameToPort.getCoverArtURL(), gameToPort.getYearOfRelease(), dev, url, year);


            if (validYear && Utilities.isValidURL(url) && year >= gameToPort.getYearOfRelease()) {
                portToAdd.setGsPortedTo(gs.getName());
                portToAdd.setPortPosition(HelloApplication.ports.add(portToAdd));
                gameToPort.getPorts().add(portToAdd);
                portToAdd.setPortPosition(portToAdd.getPortPosition());
                gs.getGames().add(portToAdd);

                HelloApplication.ports.replace(portToAdd,portToAdd.getPortPosition());

                HelloApplication.gameSystems.replace(gs, gs.getPosition());//update
                HelloApplication.games.replace(gameToPort, gameToPort.getPosition());

                TreeItem<String> portTI=portDetails.getRoot();
                MyNeatList<TreeItem<String>> portsTI=new MyNeatList<>();
                for (TreeItem<String> child:portDetails.getRoot().getChildren()){
                    for (TreeItem<String> portItem:child.getChildren()){
                        portsTI.add(portItem);
                    }

                }

                portDetails.getRoot().getChildren().clear();
                PortController.getPortController().getPortDetails().setRoot(portTI);
                PortController.getPortController().getPortDetails().getRoot().getChildren().add(new TreeItem<>("Description: "+portToAdd.getDescription()));
                PortController.getPortController().getPortDetails().getRoot().getChildren().add(new TreeItem<>("Publisher: "+portToAdd.getPublisher()));
                PortController.getPortController().getPortDetails().getRoot().getChildren().add(new TreeItem<>("Original Developer: "+portToAdd.getOgDeveloper()));
                PortController.getPortController().getPortDetails().getRoot().getChildren().add(new TreeItem<>("Initial Year of release: "+portToAdd.getYearOfRelease()));
                PortController.getPortController().getPortDetails().getRoot().getChildren().add(new TreeItem<>("Original Cover Art (URL): "+portToAdd.getCoverArtURL()));
                PortController.getPortController().getPortDetails().getRoot().getChildren().add(new TreeItem<>("Port Developer: "+portToAdd.getPortDev()));
                PortController.getPortController().getPortDetails().getRoot().getChildren().add(new TreeItem<>("Year of Port release: "+portToAdd.getNewYear()));
                PortController.getPortController().getPortDetails().getRoot().getChildren().add(new TreeItem<>("Port Cover Art (URL): "+portToAdd.getNewCoverArt()));
                TreeItem<String> portsSystems = new TreeItem<>("Systems this game is on: ");
                PortController.getPortController().getPortDetails().getRoot().getChildren().add(portsSystems);
                portsSystems.getChildren().addAll(portsTI);

            }
        }

    }


    @FXML
    public void goBack(){
        MainController.getMainController().clear();//refreshing main page with changes made on view pages
        MainController.getMainController().refresh();

        HelloApplication.mainStage.setScene(HelloApplication.mainS);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        portController=this;
    }
}
