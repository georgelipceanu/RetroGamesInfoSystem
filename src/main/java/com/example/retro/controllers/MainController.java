package com.example.retro.controllers;

import com.example.retro.HelloApplication;
import com.example.retro.models.Game;
import com.example.retro.models.GamePort;
import com.example.retro.models.GameSystem;
import com.example.retro.utils.Utilities;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
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
    private TreeView<String> system;
    private static final TreeItem<String> root = new TreeItem<>("Game Systems");
    public TreeView<String> getSystem() {
        return system;
    }

    public static TreeItem<String> getRoot() {
        return root;
    }

    @FXML
    private ChoiceBox<GameSystem> gameSystems;


    public ChoiceBox<GameSystem> getGameSystems() {
        return gameSystems;
    }

    @FXML
    public void addGameSystem() {
        if (!gameSysName.getText().isEmpty() && !gameSysPrice.getText().isEmpty() && !gameSysDesc.getText().isEmpty() && !gameSysManufacturer.getText().isEmpty() &&
                !gameSysType.getText().isEmpty() && !gameSysDesc.getText().isEmpty() && !gameSysYear.getText().isEmpty()&& !gameSysImage.getText().isEmpty() &&
                !gameSysMedia.getText().isEmpty()) { //checking if each text box is filled
            String name = gameSysName.getText();
            String desc = gameSysDesc.getText();
            String manufacturer = gameSysManufacturer.getText();
            String type = gameSysType.getText();
            String media = gameSysMedia.getText();
            String url = gameSysImage.getText();

            int year = 0;
            boolean validYear=true;
            try {
                year = Integer.parseInt(gameSysYear.getText());//getting each attribute of port
            } catch (NumberFormatException e){
                validYear=false;
            }

            double price=0;
            boolean validPrice=true;
            try {
                price = Double.parseDouble(gameSysPrice.getText());
            } catch (NumberFormatException e){
                validPrice=false;
            }

            GameSystem gsToAdd = new GameSystem(name,manufacturer,desc,type,media,url,year,price);
            boolean uniqueName=true;


            if (validPrice&&validYear&&Utilities.isValidURL(url)&&uniqueName){
                HelloApplication.gameSystems.add(gsToAdd);;
                root.getChildren().add(new TreeItem<>(gsToAdd.getName()));
                gameSystems.getItems().add(gsToAdd);
            } else Utilities.showWarningAlert("WARNING", "Enter valid details");

        }else Utilities.showWarningAlert("WARNING", "Fill all boxes");
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
                year = Integer.parseInt(gameRelease.getText());//getting each attribute of port
            } catch (NumberFormatException e){
                validYear=false;
            }


            Game gameToAdd = new Game(name,publisher,desc,dev,url,year);
            boolean uniqueName=true;


            TreeItem<String> gsToAddToTI=system.getSelectionModel().getSelectedItem();

            if (system.getSelectionModel().getSelectedItem()!=null && gsToAddToTI!=root) {
                if (validYear && Utilities.isValidURL(url) && uniqueName) {
                    HelloApplication.games.add(gameToAdd);
                    gsToAddToTI.getChildren().add(new TreeItem<>(gameToAdd.getTitle()));
                } else Utilities.showWarningAlert("WARNING", "Enter valid details");
            } else Utilities.showWarningAlert("WARNING", "Select a Game System to add to");
        }else Utilities.showWarningAlert("WARNING", "Fill all boxes");
    }

    @FXML
    public void addPort() {
        if (!portCover.getText().isEmpty() && !portRelease.getText().isEmpty() && !portDev.getText().isEmpty()) { //checking if each text box is filled
            String dev = portDev.getText();
            String url = portCover.getText();

            int year = 0;
            boolean validYear=true;
            try {
                year = Integer.parseInt(portRelease.getText());//getting each attribute of port
            } catch (NumberFormatException e){
                validYear=false;
            }


//            Port portToAdd = new GamePort(name,publisher,desc,dev,url,year);
//            boolean uniqueName=true;
//
//
//            TreeItem<String> gsToAddToTI=system.getSelectionModel().getSelectedItem();
//
//            if (system.getSelectionModel().getSelectedItem()==null) {
//                if (validYear && Utilities.isValidURL(url) && uniqueName) {
//                    HelloApplication.games.add(gameToAdd);
//                    gsToAddToTI.getChildren().add(new TreeItem<>(gameToAdd.getTitle()));
//                } else Utilities.showWarningAlert("WARNING", "Enter valid details");
//            } else Utilities.showWarningAlert("WARNING", "Select a Game System to add to");
        }else Utilities.showWarningAlert("WARNING", "Fill all boxes");
    }

    public void view(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        system.setRoot(root);
        mainController = this;
    }
}
