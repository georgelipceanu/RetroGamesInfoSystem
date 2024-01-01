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
            String url = gameSysImage.getText();//getting details from boxes

            int year = 0;
            boolean validYear=true;
            try {//checking if number is entered
                year = Integer.parseInt(gameSysYear.getText());
            } catch (NumberFormatException e){
                validYear=false;
            }

            double price=0;
            boolean validPrice=true;
            try {//checking if number is entered
                price = Double.parseDouble(gameSysPrice.getText());
            } catch (NumberFormatException e){
                validPrice=false;
            }

            GameSystem gsToAdd = new GameSystem(name,manufacturer,desc,type,media,url,year,price);
            boolean uniqueName=true;

            for (int i = 0; i<HelloApplication.gameSystems.size();i++){
                if (HelloApplication.gameSystems.getElementFromPosition(i)!=null) {
                    if (HelloApplication.gameSystems.getElementFromPosition(i).getName().equalsIgnoreCase(gsToAdd.getName())) {//checking if name is unique
                        uniqueName = false;
                        break;
                    }
                }
            }


            if (validPrice&&validYear&&Utilities.isValidURL(url)&&uniqueName && !gsToAdd.getName().contains("| SYSTEM |")){//all valid details
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

            for (int i = 0; i<HelloApplication.games.size();i++){
                if (HelloApplication.games.getElementFromPosition(i)!=null) {
                    if (HelloApplication.games.getElementFromPosition(i).getTitle().equalsIgnoreCase(gameToAdd.getTitle())) {//check for unique name across all games
                        uniqueName = false;
                        break;
                    }
                }
            }

            TreeItem<String> gsToAddToTI=system.getSelectionModel().getSelectedItem();

            if (system.getSelectionModel().getSelectedItem()!=null && gsToAddToTI!=root && gsToAddToTI.getValue().contains("| SYSTEM |")) {//check if a system is selected

                String gsToAddToName = gsToAddToTI.getValue().substring(12);
                int key = HelloApplication.gameSystems.hashFunction(gsToAddToName);
                GameSystem gsToAddTo=HelloApplication.gameSystems.getElementFromPosition(key);//getting system from selected item based on key from hash function
                boolean gsEmpty = (gsToAddTo==null);

                if (gsEmpty)
                    for (int i=0;i<HelloApplication.gameSystems.size();i++)
                        if (HelloApplication.gameSystems.getElementFromPosition(i)!=null){
                            gsToAddTo=HelloApplication.gameSystems.getElementFromPosition(i);//assigning dummy system to avoid null pointer exception if gsToAddTo is initially null
                            break;
                        }

                if (!gsToAddTo.getName().equals(gsToAddToName)) {//searching through map if system has been probed from where it should be
                    int home=key;
                    do {
                        key=(key+1)%(HelloApplication.gameSystems.size());

                        if (HelloApplication.gameSystems.getElementFromPosition(key) != null) {
                            if (HelloApplication.gameSystems.getElementFromPosition(key).getName().equalsIgnoreCase(gsToAddToName)) {
                                gsToAddTo = HelloApplication.gameSystems.getElementFromPosition(key);//assigns system when it is found
                                break;
                            }
                        }

                    } while (home!=key);
                }

                if (validYear && Utilities.isValidURL(url) && uniqueName && gsToAddTo!=null && !gameToAdd.getTitle().contains("| GAME |")) {//all details valid
                    gameToAdd.setPosition(HelloApplication.games.add(gameToAdd));
                    gsToAddTo.getGames().add(gameToAdd);
                    gsToAddToTI.getChildren().add(new TreeItem<>("| GAME |  "+gameToAdd.getTitle()));
                } else Utilities.showWarningAlert("WARNING", "Enter valid details");
            } else Utilities.showWarningAlert("WARNING", "Select a Game System to add to");
        }else Utilities.showWarningAlert("WARNING", "Fill all boxes");
    }

    @FXML
    public void addPort() {
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

            TreeItem<String> gameToAddToTI = system.getSelectionModel().getSelectedItem();
            if (gameToAddToTI != null && gameToAddToTI.getValue().contains("| GAME |")) {

                String gameName = gameToAddToTI.getValue().substring(10); //cutting out "| GAME |  "
                int key = HelloApplication.games.hashFunction(gameName);
                Game gameToPort = HelloApplication.games.getElementFromPosition(key);
                boolean gameEmpty=(gameToPort==null);

                if (gameEmpty)
                    for (int i=0;i<HelloApplication.games.size();i++)
                        if (HelloApplication.games.getElementFromPosition(i)!=null){
                            gameToPort=HelloApplication.games.getElementFromPosition(i);//assigning dummy game to avoid null pointer exception if gsToAddTo is initially null
                            break;
                        }

                if (!gameToPort.getTitle().equals(gameName)) {
                    int home = key;
                    do {
                        key = (key + 1) % (HelloApplication.games.size());

                        if (HelloApplication.games.getElementFromPosition(key) != null) {
                            if (HelloApplication.games.getElementFromPosition(key).getTitle().equalsIgnoreCase(gameName)) {
                                gameToPort = HelloApplication.games.getElementFromPosition(key);
                                break;
                            }
                        }

                    } while (home != key);
                }

                GameSystem gsToAddTo = gameSystems.getSelectionModel().getSelectedItem();//getting system from choicebox
                GameSystem realGS = HelloApplication.gameSystems.getElementFromPosition(gsToAddTo.getPosition());//getting actual system from backend
                if (gsToAddTo != null) {

                    GamePort portToAdd = new GamePort(gameToPort.getTitle(), gameToPort.getPublisher(), gameToPort.getDescription(), gameToPort.getOgDeveloper()
                            , gameToPort.getCoverArtURL(), gameToPort.getYearOfRelease(), dev, url, year);

                    boolean uniquePort=true;

                    for (Game game : realGS.getGames()) {
                        if (game instanceof GamePort)
                            if (game == portToAdd) {
                                uniquePort = false;
                                break;
                            }
                        if (portToAdd.getTitle().equals(game.getTitle())){
                            uniquePort=false;
                            break;
                        }
                    }

                    if (validYear && Utilities.isValidURL(url)&&uniquePort) {
                        portToAdd.setPortPosition(HelloApplication.ports.add(portToAdd));
                        gameToPort.getPorts().add(portToAdd);
                        realGS.getGames().add(portToAdd);
                        gameToAddToTI.getChildren().add(new TreeItem<>("| PORT |  "+portToAdd.getTitle() + "  | " + realGS.getName() + " |" ));

                        for (TreeItem<String> child : root.getChildren()) {//looking for system in treeview from root
                            if (child.getValue().equals("| SYSTEM |  "+ realGS.getName())) {
                                child.getChildren().add(new TreeItem<>("| PORT |  "+portToAdd.getTitle()));
                                break;
                            }
                        }


                    } else Utilities.showWarningAlert("WARNING", "Enter valid details");
                } else Utilities.showWarningAlert("WARNING", "Select a Game System to add to");
            } else Utilities.showWarningAlert("WARNING", "Select a game to port");
        }else Utilities.showWarningAlert("WARNING", "Fill all boxes");
    }

    public void view(){
        int option = 0;
        if (system.getSelectionModel().getSelectedItem() != null)
            option = (system.getSelectionModel().getSelectedItem().getValue().contains("| SYSTEM |")) ? 1 :
                system.getSelectionModel().getSelectedItem().getValue().contains("| GAME |") ? 2 :
                        system.getSelectionModel().getSelectedItem().getValue().contains("| PORT |") ? 3 : 0;

        switch (option){
            case 1 -> {

                String gsName = system.getSelectionModel().getSelectedItem().getValue().substring(12);//getting rid of "| SYSTEM |  "

                int key = HelloApplication.gameSystems.hashFunction(gsName);
                GameSystem gs=HelloApplication.gameSystems.getElementFromPosition(key);
                boolean gsEmpty=(gs==null);
                if (gsEmpty)
                    for (int i=0;i<HelloApplication.gameSystems.size();i++)
                        if (HelloApplication.gameSystems.getElementFromPosition(i)!=null){
                            gs=HelloApplication.gameSystems.getElementFromPosition(i);//assigning dummy system to avoid null pointer exception if gsToAddTo is initially null
                            break;
                        }

                if (!gs.getName().equals(gsName)) {
                    int home=key;
                    do {
                        key=(key+1)%(HelloApplication.gameSystems.size());

                        if (HelloApplication.gameSystems.getElementFromPosition(key) != null) {
                            if (HelloApplication.gameSystems.getElementFromPosition(key).getName().equalsIgnoreCase(gsName)) {
                                gs = HelloApplication.gameSystems.getElementFromPosition(key);
                                break;
                            }
                        }

                    } while (home!=key);
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
                    else games.getChildren().add(new TreeItem<>(game.getTitle()+ " (Original game)"));
                }

                SystemController.getSystemController().gameSysName.setText(gs.getName());
                SystemController.getSystemController().gameSysDesc.setText(gs.getDescription());
                SystemController.getSystemController().gameSysManufacturer.setText(gs.getManufacturer());
                SystemController.getSystemController().gameSysImage.setText(gs.getImageURL());
                SystemController.getSystemController().gameSysPrice.setText(String.valueOf(gs.getPrice()));
                SystemController.getSystemController().gameSysYear.setText(String.valueOf(gs.getLaunchYear()));
                SystemController.getSystemController().gameSysMedia.setText(String.valueOf(gs.getLaunchYear()));
                SystemController.getSystemController().gameSysType.setText(String.valueOf(gs.getLaunchYear()));//filling textboxes with data for editing

                System.out.println(gs.getPosition());
                HelloApplication.mainStage.setScene(HelloApplication.systemS);
            }

            case 2 ->{
                TreeItem<String> gameTI = system.getSelectionModel().getSelectedItem();
                String gameName = gameTI.getValue().substring(10);
                int key = HelloApplication.games.hashFunction(gameName);
                Game game = HelloApplication.games.getElementFromPosition(key);
                boolean gameEmpty= (game==null);

                if (gameEmpty)
                    for (int i=0;i<HelloApplication.games.size();i++)
                        if (HelloApplication.games.getElementFromPosition(i)!=null){
                            game=HelloApplication.games.getElementFromPosition(i);//assigning dummy system to avoid null pointer exception if gsToAddTo is initially null
                            break;
                        }

                if (!game.getTitle().equals(gameName)) {
                    int home=key;
                    do {
                        key=(key+1)%(HelloApplication.games.size());

                        if (HelloApplication.games.getElementFromPosition(key) != null) {
                            if (HelloApplication.games.getElementFromPosition(key).getTitle().equalsIgnoreCase(gameName)) {
                                game = HelloApplication.games.getElementFromPosition(key);
                                break;
                            }
                        }

                    } while (home!=key);
                }


                GameController.getGameController().getGameDetails().setRoot(new TreeItem<>(game.getTitle()));
                GameController.getGameController().getGameDetails().getRoot().getChildren().add(new TreeItem<>("Description: "+game.getDescription()));
                GameController.getGameController().getGameDetails().getRoot().getChildren().add(new TreeItem<>("Publisher: "+game.getPublisher()));
                GameController.getGameController().getGameDetails().getRoot().getChildren().add(new TreeItem<>("Developer: "+game.getOgDeveloper()));
                GameController.getGameController().getGameDetails().getRoot().getChildren().add(new TreeItem<>("Year of release: "+game.getYearOfRelease()));
                GameController.getGameController().getGameDetails().getRoot().getChildren().add(new TreeItem<>("Cover Art (URL): "+game.getCoverArtURL()));
                TreeItem<String> ports =new TreeItem<>("PORTS: ");
                GameController.getGameController().getGameDetails().getRoot().getChildren().add(ports);


                for (TreeItem<String> child: gameTI.getChildren()) {
                    String portSystem=child.getValue().substring(10+gameName.length()+2);//getting system name of each port
                    ports.getChildren().add(new TreeItem<>(portSystem));
                }

                GameController.getGameController().gameName.setText(gameName);
                GameController.getGameController().gameDesc.setText(game.getDescription());
                GameController.getGameController().gamePublisher.setText(game.getPublisher());
                GameController.getGameController().gameCover.setText(game.getCoverArtURL());
                GameController.getGameController().gameRelease.setText(String.valueOf(game.getYearOfRelease()));
                GameController.getGameController().gameDev.setText(game.getOgDeveloper());
                HelloApplication.mainStage.setScene(HelloApplication.gameS);
            }
            case 3 -> System.out.println("to be completed");
            case 0 -> Utilities.showWarningAlert("WARNING!", "Select a valid option");
        }

    }

    @FXML
    public void search(){HelloApplication.mainStage.setScene(HelloApplication.searchS);}

    @FXML
    public void delete() {
        int option = 0;
        if (system.getSelectionModel().getSelectedItem() != null)
            option = (system.getSelectionModel().getSelectedItem().getValue().contains("| SYSTEM |")) ? 1 :
                    system.getSelectionModel().getSelectedItem().getValue().contains("| GAME |") ? 2 :
                            system.getSelectionModel().getSelectedItem().getValue().contains("| PORT |") ? 3 : 0;

        switch (option) {
            case 1 -> {
                TreeItem<String> gsToRemoveTI = system.getSelectionModel().getSelectedItem();
                String gsName = system.getSelectionModel().getSelectedItem().getValue().substring(12);//getting rid of "| SYSTEM |  "

                int key = HelloApplication.gameSystems.hashFunction(gsName);//finding game system in backend hash map
                GameSystem gs = HelloApplication.gameSystems.getElementFromPosition(key);
                boolean gsEmpty=(gs==null);
                if (gsEmpty)
                    for (int i=0;i<HelloApplication.gameSystems.size();i++)
                        if (HelloApplication.gameSystems.getElementFromPosition(i)!=null){
                            gs=HelloApplication.gameSystems.getElementFromPosition(i);//assigning dummy system to avoid null pointer exception if gsToAddTo is initially null
                            break;
                        }

                if (!gs.getName().equals(gsName)) {//finding game system in backend hash map stored at diff location
                    int home = key;
                    do {
                        key = (key + 1) % (HelloApplication.gameSystems.size());
                        if (HelloApplication.gameSystems.getElementFromPosition(key) != null) {
                            if (HelloApplication.gameSystems.getElementFromPosition(key).getName().equalsIgnoreCase(gsName)) {
                                gs = HelloApplication.gameSystems.getElementFromPosition(key);
                                break;
                            }
                        }
                    } while (home != key);
                }

                for (Game game : gs.getGames()) {//removing games on system
                    if (game instanceof GamePort) {
                        HelloApplication.ports.delete(((GamePort) game).getPortPosition());
                    }
                    else {
                        for (GamePort port : game.getPorts()){
                            for (TreeItem<String> system : root.getChildren()){
                                String gsNameToRemovePortFrom = system.getValue().substring(12);//removing "| SYSTEM |  "
                                int keyGSOfPortToRemoveFrom = HelloApplication.gameSystems.hashFunction(gsNameToRemovePortFrom);
                                GameSystem gameSystemToRemovePortFrom = HelloApplication.gameSystems.getElementFromPosition(keyGSOfPortToRemoveFrom);
                                boolean gsOfPortToRemoveFrom=(gameSystemToRemovePortFrom)==null;
                                if (gsOfPortToRemoveFrom)
                                    for (int i=0;i<HelloApplication.gameSystems.size();i++)
                                        if (HelloApplication.gameSystems.getElementFromPosition(i)!=null){
                                            gameSystemToRemovePortFrom=HelloApplication.gameSystems.getElementFromPosition(i);//assigning dummy system to avoid null pointer exception if gsToAddTo is initially null
                                            break;
                                        }

                                if (!gameSystemToRemovePortFrom.getName().equals(gsName)) {//finding game system in backend hash map stored at diff location
                                    int home = keyGSOfPortToRemoveFrom;
                                    do {
                                        keyGSOfPortToRemoveFrom = (keyGSOfPortToRemoveFrom + 1) % (HelloApplication.gameSystems.size());
                                        if (HelloApplication.gameSystems.getElementFromPosition(keyGSOfPortToRemoveFrom) != null) {
                                            if (HelloApplication.gameSystems.getElementFromPosition(keyGSOfPortToRemoveFrom).getName().equalsIgnoreCase(gsNameToRemovePortFrom)) {
                                                gameSystemToRemovePortFrom = HelloApplication.gameSystems.getElementFromPosition(keyGSOfPortToRemoveFrom);
                                                break;
                                            }
                                        }
                                    } while (home != keyGSOfPortToRemoveFrom);
                                }

                                for (Game game1:gameSystemToRemovePortFrom.getGames())
                                    if (port.getTitle().equals(game1.getTitle())) {
                                        gameSystemToRemovePortFrom.getGames().remove(game1);//removing port from systems game list
                                        break;
                                    }

                                for (TreeItem<String> child : system.getChildren()){
                                    if (child.getValue().contains("| PORT |  "+port.getTitle())){
                                        child.getParent().getChildren().remove(child);
                                        break;
                                    }
                                }
                            }

                            HelloApplication.ports.delete(port.getPortPosition());
                        }
                        HelloApplication.games.delete(game.getPosition());
                    }
                }
                HelloApplication.ports.displayHashTable();


                HelloApplication.gameSystems.delete(key);
                gameSystems.getItems().remove(gs);
                gsToRemoveTI.getParent().getChildren().remove(gsToRemoveTI);

            }

            case 2 -> {
                TreeItem<String> gameToRemoveTI = system.getSelectionModel().getSelectedItem();
                String gameName = system.getSelectionModel().getSelectedItem().getValue().substring(10);//getting rid of "| GAME |  "
                int key = HelloApplication.games.hashFunction(gameName);
                Game game = HelloApplication.games.getElementFromPosition(key);
                boolean gameEmpty=(game==null);
                if (gameEmpty)
                    for (int i=0;i<HelloApplication.games.size();i++)
                        if (HelloApplication.games.getElementFromPosition(i)!=null){
                            game=HelloApplication.games.getElementFromPosition(i);//assigning dummy game to avoid null pointer exception if gsToAddTo is initially null
                            break;
                        }

                if (!game.getTitle().equals(gameName)){
                    int home = key;
                    do {
                        key = (key + 1) % (HelloApplication.games.size());
                        if (HelloApplication.games.getElementFromPosition(key) != null) {
                            if (HelloApplication.games.getElementFromPosition(key).getTitle().equalsIgnoreCase(gameName)) {
                                game = HelloApplication.games.getElementFromPosition(key);
                                break;
                            }
                        }
                    } while (home!=key);
                }

                for (TreeItem<String> system : root.getChildren()) {//looking through systems in treeview from root

                    String gsName = system.getValue().substring(12);//getting rid of "| SYSTEM |  "

                    int keyForGS = HelloApplication.gameSystems.hashFunction(gsName);//finding game system in backend hash map
                    GameSystem gs = HelloApplication.gameSystems.getElementFromPosition(keyForGS);

                    boolean gsEmpty=(gs)==null;
                    if (gsEmpty)
                        for (int i=0;i<HelloApplication.gameSystems.size();i++)
                            if (HelloApplication.gameSystems.getElementFromPosition(i)!=null){
                                gs=HelloApplication.gameSystems.getElementFromPosition(i);//assigning dummy system to avoid null pointer exception if gsToAddTo is initially null
                                break;
                            }

                    if (!gs.getName().equals(gsName)) {//finding game system in backend hash map stored at diff location
                        int home = keyForGS;
                        do {
                            keyForGS = (keyForGS + 1) % (HelloApplication.gameSystems.size());
                            if (HelloApplication.gameSystems.getElementFromPosition(keyForGS) != null) {
                                if (HelloApplication.gameSystems.getElementFromPosition(keyForGS).getName().equalsIgnoreCase(gsName)) {
                                    gs = HelloApplication.gameSystems.getElementFromPosition(keyForGS);
                                    break;
                                }
                            }
                        } while (home != keyForGS);
                    }

                    for (Game port : gs.getGames()){//removing port from system in backend
                        if (port instanceof GamePort)
                            if (port.getTitle().equals(game.getTitle())){
                                gs.getGames().remove(port);
                                break;
                            }
                    }

                    for (TreeItem<String> child : system.getChildren()) {
                        String childS = child.getValue();//getting text only from treeitem
                        System.out.println(childS);
                        if (childS.equalsIgnoreCase("| PORT |  " + game.getTitle())) {
                            child.getParent().getChildren().remove(child);
                            break;
                        }
                    }
                }



                for (GamePort port : game.getPorts()){//removed from backend
                    HelloApplication.ports.delete(port.getPortPosition());
                }

                TreeItem<String> gsToRemoveFromTI = gameToRemoveTI.getParent();//removing from system games list
                String gsToRemoveFromName = gsToRemoveFromTI.getValue().substring(12);//getting rid of "| SYSTEM |  "
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

                gsToRemoveFrom.getGames().remove(game);
                HelloApplication.games.delete(game.getPosition());
                gameToRemoveTI.getParent().getChildren().remove(gameToRemoveTI);


            }

            case 3 -> {
                TreeItem<String> portToDeleteTI = system.getSelectionModel().getSelectedItem();
                if (portToDeleteTI.getValue().endsWith(" |" )) {//| SYSTEM | -> | GAME | -> | PORT |
                    String tempGSName = portToDeleteTI.getParent().getParent().getValue();
                    int endToRemove = tempGSName.substring(12).length()+6;//6="  | " + " |"
                    String portName = portToDeleteTI.getValue().substring(10,portToDeleteTI.getValue().length()-endToRemove);//getting rid of "| PORT |  ";
                    int key = HelloApplication.ports.hashFunction(portName);
                    GamePort port = HelloApplication.ports.getElementFromPosition(key);
                    boolean portEmpty = (port == null);
                    if (portEmpty)
                        for (int i = 0; i < HelloApplication.ports.size(); i++)
                            if (HelloApplication.ports.getElementFromPosition(i) != null) {
                                port = HelloApplication.ports.getElementFromPosition(i);//assigning dummy game to avoid null pointer exception if gsToAddTo is initially null
                                break;
                            }

                    if (!port.getTitle().equals(portName)) {
                        int home = key;
                        do {
                            key = (key + 1) % (HelloApplication.ports.size());
                            if (HelloApplication.ports.getElementFromPosition(key) != null) {
                                if (HelloApplication.ports.getElementFromPosition(key).getTitle().equalsIgnoreCase(portName)) {//getting actual port if it has been linearly probed
                                    port = HelloApplication.ports.getElementFromPosition(key);
                                    break;
                                }
                            }
                        } while (home != key);
                    }

                    for (TreeItem<String> system : root.getChildren()) {
                        String gsName = system.getValue().substring(12);//getting rid of "| SYSTEM |  ";
                        int keyForGS = HelloApplication.gameSystems.hashFunction(gsName);
                        GameSystem gs = HelloApplication.gameSystems.getElementFromPosition(keyForGS);
                        boolean gsEmpty = (gs == null);
                        if (gsEmpty)
                            for (int i = 0; i < HelloApplication.gameSystems.size(); i++)
                                if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                                    gs = HelloApplication.gameSystems.getElementFromPosition(i);//assigning dummy system to avoid null pointer exception if gsToAddTo is initially null
                                    break;
                                }

                        if (!gs.getName().equals(gsName)) {
                            int home = keyForGS;
                            do {
                                keyForGS = (keyForGS + 1) % (HelloApplication.gameSystems.size());
                                if (HelloApplication.gameSystems.getElementFromPosition(keyForGS) != null) {
                                    if (HelloApplication.gameSystems.getElementFromPosition(keyForGS).getName().equalsIgnoreCase(portName)) {//getting actual port if it has been linearly probed
                                        gs = HelloApplication.gameSystems.getElementFromPosition(keyForGS);
                                        break;
                                    }
                                }
                            } while (home != keyForGS);
                        }

                        for (TreeItem<String> child : system.getChildren()) {
                            boolean portRemoved = false;
                            if (child.getValue().equals("| PORT |  " + port.getTitle())) {
                                gs.getGames().remove(port);//removing port from systems game list
                                child.getParent().getChildren().remove(child);
                                break;

                            } else if (child.getValue().equals("| GAME |  " + port.getTitle())) {
                                for (TreeItem<String> game : child.getChildren()) {
                                    String gameName = game.getValue().substring(10,portToDeleteTI.getValue().length()-endToRemove);//getting rid of "| GAME |  ";
                                    int keyForGame = HelloApplication.games.hashFunction(gameName);
                                    Game gameToRemovePortFrom = HelloApplication.games.getElementFromPosition(keyForGame);

                                    boolean gameEmpty = (gameToRemovePortFrom == null);
                                    if (gameEmpty)
                                        for (int i = 0; i < HelloApplication.games.size(); i++)
                                            if (HelloApplication.games.getElementFromPosition(i) != null) {
                                                gameToRemovePortFrom = HelloApplication.games.getElementFromPosition(i);//assigning dummy game to avoid null pointer exception if gsToAddTo is initially null
                                                break;
                                            }

                                    if (!gameToRemovePortFrom.getTitle().equals(gameName)) {
                                        int homeOfGame = keyForGame;
                                        do {
                                            keyForGame = (keyForGame + 1) % (HelloApplication.games.size());
                                            if (HelloApplication.games.getElementFromPosition(keyForGame) != null) {
                                                if (HelloApplication.games.getElementFromPosition(keyForGame).getTitle().equalsIgnoreCase(portName)) {//getting actual port if it has been linearly probed
                                                    gameToRemovePortFrom = HelloApplication.games.getElementFromPosition(keyForGame);
                                                    break;
                                                }
                                            }
                                        } while (homeOfGame != keyForGame);
                                    }

                                    for (GamePort port1 : gameToRemovePortFrom.getPorts()) {
                                        if (port1.getTitle().equals(port.getTitle())) {
                                            gameToRemovePortFrom.getPorts().remove(port1);
                                            portRemoved = true;
                                            break;
                                        }
                                    }
                                    if (portRemoved == true) break;//break for | GAME | route
                                }

                            }
                        }
                    }
                    HelloApplication.ports.delete(port.getPortPosition());
                    portToDeleteTI.getParent().getChildren().remove(portToDeleteTI);


                } else { //| SYSTEM | -> | PORT |
                    String portName = portToDeleteTI.getValue().substring(10);
                    int key = HelloApplication.ports.hashFunction(portName);
                    GamePort port = HelloApplication.ports.getElementFromPosition(key);
                    boolean portEmpty = (port == null);
                    if (portEmpty)
                        for (int i = 0; i < HelloApplication.ports.size(); i++)
                            if (HelloApplication.ports.getElementFromPosition(i) != null) {
                                port = HelloApplication.ports.getElementFromPosition(i);//assigning dummy port to avoid null pointer exception if gsToAddTo is initially null
                                break;
                            }

                    if (!port.getTitle().equals(portName)) {
                        int home = key;
                        do {
                            key = (key + 1) % (HelloApplication.ports.size());
                            if (HelloApplication.ports.getElementFromPosition(key) != null) {
                                if (HelloApplication.ports.getElementFromPosition(key).getTitle().equalsIgnoreCase(portName)) {//getting actual port if it has been linearly probed
                                    port = HelloApplication.ports.getElementFromPosition(key);
                                    break;
                                }
                            }
                        } while (home != key);
                    }


                    for (TreeItem<String> system : root.getChildren()) {
                        String gsName = system.getValue().substring(12);//getting rid of "| SYSTEM |  ";
                        int keyForGS = HelloApplication.gameSystems.hashFunction(gsName);
                        GameSystem gs = HelloApplication.gameSystems.getElementFromPosition(keyForGS);
                        boolean gsEmpty = (gs == null);
                        if (gsEmpty)
                            for (int i = 0; i < HelloApplication.gameSystems.size(); i++)
                                if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                                    gs = HelloApplication.gameSystems.getElementFromPosition(i);//assigning dummy system to avoid null pointer exception if gsToAddTo is initially null
                                    break;
                                }

                        if (!gs.getName().equals(gsName)) {
                            int home = keyForGS;
                            do {
                                keyForGS = (keyForGS + 1) % (HelloApplication.gameSystems.size());
                                if (HelloApplication.gameSystems.getElementFromPosition(keyForGS) != null) {
                                    if (HelloApplication.gameSystems.getElementFromPosition(keyForGS).getName().equalsIgnoreCase(portName)) {//getting actual port if it has been linearly probed
                                        gs = HelloApplication.gameSystems.getElementFromPosition(keyForGS);
                                        break;
                                    }
                                }
                            } while (home != keyForGS);
                        }

                        for (TreeItem<String> child : system.getChildren()) {
                            boolean portRemoved = false;
                            if (child.getValue().equals("| PORT |  " + port.getTitle())) {
                                gs.getGames().remove(port);//removing port from systems game list
                                child.getParent().getChildren().remove(child);
                                break;

                            } else if (child.getValue().equals("| GAME |  " + port.getTitle())) {
                                for (TreeItem<String> game : child.getChildren()) {//game should be called port, changed to game for convenience
                                    String gameName = child.getValue().substring(10);//getting rid of "| GAME |  ";
                                    int keyForGame = HelloApplication.games.hashFunction(gameName);
                                    Game gameToRemovePortFrom = HelloApplication.games.getElementFromPosition(keyForGame);

                                    boolean gameEmpty = (gameToRemovePortFrom == null);
                                    if (gameEmpty)
                                        for (int i = 0; i < HelloApplication.games.size(); i++)
                                            if (HelloApplication.games.getElementFromPosition(i) != null) {
                                                gameToRemovePortFrom = HelloApplication.games.getElementFromPosition(i);//assigning dummy game to avoid null pointer exception if gsToAddTo is initially null
                                                break;
                                            }

                                    if (!gameToRemovePortFrom.getTitle().equals(gameName)) {
                                        int homeOfGame = keyForGame;
                                        do {
                                            keyForGame = (keyForGame + 1) % (HelloApplication.games.size());
                                            if (HelloApplication.games.getElementFromPosition(keyForGame) != null) {
                                                if (HelloApplication.games.getElementFromPosition(keyForGame).getTitle().equalsIgnoreCase(portName)) {//getting actual port if it has been linearly probed
                                                    gameToRemovePortFrom = HelloApplication.games.getElementFromPosition(keyForGame);
                                                    break;
                                                }
                                            }
                                        } while (homeOfGame != keyForGame);
                                    }

                                    for (GamePort port1 : gameToRemovePortFrom.getPorts()) {
                                        if (port1.getTitle().contains(port.getTitle())) {
                                            game.getParent().getChildren().remove(game);//port getting removed from treeview
                                            gameToRemovePortFrom.getPorts().remove(port1);
                                            portRemoved = true;
                                            break;
                                        }
                                    }
                                    if (portRemoved == true) break;//break for | GAME | route
                                }

                            }
                        }

                    }

                    HelloApplication.ports.delete(port.getPortPosition());
                }

            }

            case 0 -> {
                Utilities.showWarningAlert("WARNING!", "Select what you would like to delete");
            }

        }
    }

    @FXML
    public void save(){
        try {
            HelloApplication.save();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void load(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        system.setRoot(root);
        mainController = this;
    }
}
