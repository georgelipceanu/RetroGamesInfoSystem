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

import java.lang.reflect.InvocationTargetException;
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

            for (int i = 0; i<HelloApplication.gameSystems.size()-1;i++){
                if (HelloApplication.gameSystems.getElementFromPosition(i)!=null) {
                    if (HelloApplication.gameSystems.getElementFromPosition(i).getName().equalsIgnoreCase(gsToAdd.getName())) {
                        uniqueName = false;
                        break;
                    }
                }
            }


            if (validPrice&&validYear&&Utilities.isValidURL(url)&&uniqueName && !gsToAdd.getName().contains("| SYSTEM |")){
                gsToAdd.setPosition(HelloApplication.gameSystems.add(gsToAdd));
                root.getChildren().add(new TreeItem<>("| SYSTEM |  "+gsToAdd.getName()));
                gameSystems.getItems().add(gsToAdd);
                System.out.println(gsToAdd.getPosition());
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
                year = Integer.parseInt(gameRelease.getText());
            } catch (NumberFormatException e){
                validYear=false;
            }

            Game gameToAdd = new Game(name,publisher,desc,dev,url,year);
            boolean uniqueName=true;

            for (int i = 0; i<HelloApplication.games.size()-1;i++){
                if (HelloApplication.games.getElementFromPosition(i)!=null) {
                    if (HelloApplication.games.getElementFromPosition(i).getTitle().equalsIgnoreCase(gameToAdd.getTitle())) {
                        uniqueName = false;
                        break;
                    }
                }
            }

            TreeItem<String> gsToAddToTI=system.getSelectionModel().getSelectedItem();

            if (system.getSelectionModel().getSelectedItem()!=null && gsToAddToTI!=root) {
                GameSystem gsToAddTo = null;
                String gsToAddToName = gsToAddToTI.getValue();
                for (int i = 0; i<HelloApplication.gameSystems.size()-1;i++){
                    if (HelloApplication.gameSystems.getElementFromPosition(i)!=null) {
                        if (HelloApplication.gameSystems.getElementFromPosition(i).getName().equalsIgnoreCase(gsToAddToName.substring(12))) {
                            gsToAddTo=HelloApplication.gameSystems.getElementFromPosition(i);
                            break;
                        }
                    }
                }
                if (validYear && Utilities.isValidURL(url) && uniqueName && gsToAddTo!=null && !gameToAdd.getTitle().contains("| GAME |")) {
                    gameToAdd.setPosition(HelloApplication.games.add(gameToAdd));
                    gsToAddTo.getGames().add(gameToAdd);
                    gsToAddToTI.getChildren().add(new TreeItem<>("| GAME |  "+gameToAdd.getTitle()));
                } else Utilities.showWarningAlert("WARNING", "Enter valid details");
            } else Utilities.showWarningAlert("WARNING", "Select a Game System to add to");
        }else Utilities.showWarningAlert("WARNING", "Fill all boxes");
    }

    @FXML
    public void addPort() {
//        if (!portCover.getText().isEmpty() && !portRelease.getText().isEmpty() && !portDev.getText().isEmpty()) { //checking if each text box is filled
//            String dev = portDev.getText();
//            String url = portCover.getText();
//
//            int year = 0;
//            boolean validYear=true;
//            try {
//                year = Integer.parseInt(portRelease.getText());//getting each attribute of port
//            } catch (NumberFormatException e){
//                validYear=false;
//            }
//
//
////            Port portToAdd = new GamePort(name,publisher,desc,dev,url,year);
////            boolean uniqueName=true;
////
////
////            TreeItem<String> gsToAddToTI=system.getSelectionModel().getSelectedItem();
////
////            if (system.getSelectionModel().getSelectedItem()==null) {
////                if (validYear && Utilities.isValidURL(url) && uniqueName) {
////                    HelloApplication.games.add(gameToAdd);
////                    gsToAddToTI.getChildren().add(new TreeItem<>(gameToAdd.getTitle()));
////                } else Utilities.showWarningAlert("WARNING", "Enter valid details");
////            } else Utilities.showWarningAlert("WARNING", "Select a Game System to add to");
//        }else Utilities.showWarningAlert("WARNING", "Fill all boxes");
    }

    public void view(){
        int option = 0;
        if (system.getSelectionModel().getSelectedItem() != null)
            option = (system.getSelectionModel().getSelectedItem().getValue().contains("| SYSTEM |")) ? 1 :
                system.getSelectionModel().getSelectedItem().getValue().contains("| GAME |") ? 2 :
                        system.getSelectionModel().getSelectedItem().getValue().contains("| PORT |") ? 3 : 0;

        switch (option){
            case 1 -> {
                GameSystem gs=null;
                String gsName = system.getSelectionModel().getSelectedItem().getValue().substring(12);//getting rid of "| SYSTEM |  "
                for (int i = 0; i<HelloApplication.gameSystems.size()-1;i++){
                    if (HelloApplication.gameSystems.getElementFromPosition(i)!=null) {
                        if (HelloApplication.gameSystems.getElementFromPosition(i).getName().equalsIgnoreCase(gsName)) {
                            gs = HelloApplication.gameSystems.getElementFromPosition(i);
                            break;
                        }
                    }
                }
                SystemController.getSystemController().getSystemDetails().setRoot(new TreeItem<>(gs.getName()));
                SystemController.getSystemController().getSystemDetails().getRoot().getChildren().add(new TreeItem<>("Description: "+gs.getDescription()));
                SystemController.getSystemController().getSystemDetails().getRoot().getChildren().add(new TreeItem<>("Type: "+gs.getType()));
                SystemController.getSystemController().getSystemDetails().getRoot().getChildren().add(new TreeItem<>("Media: "+gs.getMedia()));
                SystemController.getSystemController().getSystemDetails().getRoot().getChildren().add(new TreeItem<>("Manufacturer: "+gs.getManufacturer()));
                SystemController.getSystemController().getSystemDetails().getRoot().getChildren().add(new TreeItem<>("Launch Year: "+gs.getLaunchYear()));
                SystemController.getSystemController().getSystemDetails().getRoot().getChildren().add(new TreeItem<>("Price: €"+gs.getPrice()));
                SystemController.getSystemController().getSystemDetails().getRoot().getChildren().add(new TreeItem<>("Image: "+gs.getImageURL()));//adding details to treeview
                TreeItem<String> games = new TreeItem<>("GAMES:");
                SystemController.getSystemController().getSystemDetails().getRoot().getChildren().add(games);
                for (Game game : gs.getGames()) {
                    if (game instanceof GamePort)
                        games.getChildren().add(new TreeItem<>(game.getTitle() + " (Port)"));
                    else games.getChildren().add(new TreeItem<>(game.getTitle()));
                }

                SystemController.getSystemController().gameSysName.setText(gs.getName());

                HelloApplication.mainStage.setScene(HelloApplication.systemS);
            }

            case 2 ->{
                System.out.println("to be completed");
            }
            case 3 -> System.out.println("to be completed");
            case 0 -> Utilities.showWarningAlert("WARNING!", "Select a valid option");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        system.setRoot(root);
        mainController = this;
    }
}
