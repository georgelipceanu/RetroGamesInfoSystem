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

    public static MainController getMainController() {
        return mainController;
    }

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
                    HelloApplication.gameSystems.replace(gsToAddTo,gsToAddTo.getPosition());//update
                    gsToAddToTI.getChildren().add(new TreeItem<>("| GAME |  "+gameToAdd.getTitle()));
                } else Utilities.showWarningAlert("WARNING", "Enter valid details");
            } else Utilities.showWarningAlert("WARNING", "Select a Game System to add to");
        }else Utilities.showWarningAlert("WARNING", "Fill all boxes");
    }

    @FXML
    public void addPort() {
        if (!portCover.getText().isEmpty() && !portRelease.getText().isEmpty() && !portDev.getText().isEmpty()&&gameSystems.getSelectionModel().getSelectedItem()!=null) { //checking if each text box is filled
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
                            if (HelloApplication.games.getElementFromPosition(key).getTitle().equals(gameName)) {
                                gameToPort = HelloApplication.games.getElementFromPosition(key);
                                break;
                            }
                        }

                    } while (home != key);
                }

                GameSystem gsToAddTo = gameSystems.getValue();//getting system from choicebox
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

                    if (validYear && Utilities.isValidURL(url)&&uniquePort && year>=gameToPort.getYearOfRelease()) {
                        portToAdd.setGsPortedTo(realGS.getName());
                        portToAdd.setPortPosition(HelloApplication.ports.add(portToAdd));
                        gameToPort.getPorts().add(portToAdd);
                        realGS.getGames().add(portToAdd);
                        HelloApplication.gameSystems.replace(realGS,realGS.getPosition());//update
                        HelloApplication.games.replace(gameToPort,gameToPort.getPosition());
                        gameToAddToTI.getChildren().add(new TreeItem<>("| PORT |  "+portToAdd.getTitle() + "  | " + realGS.getName() + " |" ));

                        for (TreeItem<String> child : root.getChildren()) {//looking for system in treeview from root
                            if (child.getValue().equals("| SYSTEM |  "+ realGS.getName())) {
                                child.getChildren().add(new TreeItem<>("| PORT |  "+portToAdd.getTitle()));
                                break;
                            }
                        }

                        System.out.println(portToAdd.getGsPortedTo());


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
                SystemController.getSystemController().setGames(games);
                for (Game game : gs.getGames()) {

                    if (game instanceof GamePort) {
                        boolean foundOGGS = false;
                        String ogSystem="";
                        for (int i=0;i<HelloApplication.gameSystems.size();i++){
                            if (HelloApplication.gameSystems.getElementFromPosition(i)!=null) {
                                for (Game game1 : HelloApplication.gameSystems.getElementFromPosition(i).getGames()) {//finding original system of ports orignal game
                                    if (!(game1 instanceof GamePort) && game1.getTitle().equals(game.getTitle())) {
                                        ogSystem = HelloApplication.gameSystems.getElementFromPosition(i).getName();
                                        foundOGGS = true;
                                        break;
                                    }

                                }
                            }
                            if (foundOGGS) break;
                        }

                        games.getChildren().add(new TreeItem<>(game.getTitle() + " (Port from " + ogSystem + ")"));
                    }
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

                SystemController.getSystemController().setGs(gs);
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

                String gsName = gameTI.getParent().getValue().substring(12);//getting rid of "| SYSTEM |  "
                int keyForGSName = HelloApplication.gameSystems.hashFunction(gsName);
                GameSystem gs=HelloApplication.gameSystems.getElementFromPosition(keyForGSName);
                boolean gsEmpty=(gs==null);
                if (gsEmpty)
                    for (int i=0;i<HelloApplication.gameSystems.size();i++)
                        if (HelloApplication.gameSystems.getElementFromPosition(i)!=null){
                            gs=HelloApplication.gameSystems.getElementFromPosition(i);//assigning dummy system to avoid null pointer exception if gsToAddTo is initially null
                            break;
                        }

                if (!gs.getName().equals(gsName)) {
                    int home=keyForGSName;
                    do {
                        keyForGSName=(keyForGSName+1)%(HelloApplication.gameSystems.size());

                        if (HelloApplication.gameSystems.getElementFromPosition(keyForGSName) != null) {
                            if (HelloApplication.gameSystems.getElementFromPosition(keyForGSName).getName().equalsIgnoreCase(gsName)) {
                                gs = HelloApplication.gameSystems.getElementFromPosition(keyForGSName);
                                break;
                            }
                        }

                    } while (home!=keyForGSName);
                }


                GameController.getGameController().getGameDetails().setRoot(new TreeItem<>(game.getTitle() + "  | " + gsName + " |") );
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



                GameController.getGameController().setGs(gs);
                GameController.getGameController().setGame(game);

                HelloApplication.mainStage.setScene(HelloApplication.gameS);
            }
            case 3 -> {
                TreeItem<String> portTI=system.getSelectionModel().getSelectedItem();
                if (portTI.getValue().endsWith(" |")){// | SYSTEM | -> | GAME | -> | PORT |
                    String gsPortedTo = portTI.getValue().split("\\|")[3].trim();// Extract system name using "|"

                    int endToRemove = gsPortedTo.length()+6;// "  | " + " |"
                    String portName = portTI.getValue().substring(10,portTI.getValue().length()-endToRemove);//getting rid of "| PORT |  " and system;
                    int key = HelloApplication.ports.hashFunction(portName);
                    GamePort port = HelloApplication.ports.getElementFromPosition(key);

                    boolean portEmpty = (port == null);
                    if (portEmpty)
                        for (int i = 0; i < HelloApplication.ports.size(); i++)
                            if (HelloApplication.ports.getElementFromPosition(i) != null) {
                                port = HelloApplication.ports.getElementFromPosition(i);//assigning dummy game to avoid null pointer exception if gsToAddTo is initially null
                                break;
                            }

                    if (!(port.getTitle().equals(portName) && port.getGsPortedTo().equals(gsPortedTo))) {
                        int home = key;
                        do {
                            key = (key + 1) % (HelloApplication.ports.size());
                            if (HelloApplication.ports.getElementFromPosition(key) != null) {
                                if (HelloApplication.ports.getElementFromPosition(key).getTitle().equalsIgnoreCase(portName) && HelloApplication.ports.getElementFromPosition(key).getGsPortedTo().equals(gsPortedTo)) {//getting actual port if it has been linearly probed
                                    port = HelloApplication.ports.getElementFromPosition(key);
                                    break;
                                }
                            }
                        } while (home != key);
                    }

                    String ogGS=portTI.getParent().getParent().getValue().substring(12);
                    PortController.getPortController().setGamePort(port);

                    PortController.getPortController().getPortDetails().setRoot(new TreeItem<>(port.getTitle() + " (Port from " + ogGS + " to "+ gsPortedTo+")"));
                    PortController.getPortController().getPortDetails().getRoot().getChildren().add(new TreeItem<>("Description: "+port.getDescription()));
                    PortController.getPortController().getPortDetails().getRoot().getChildren().add(new TreeItem<>("Publisher: "+port.getPublisher()));
                    PortController.getPortController().getPortDetails().getRoot().getChildren().add(new TreeItem<>("Original Developer: "+port.getOgDeveloper()));
                    PortController.getPortController().getPortDetails().getRoot().getChildren().add(new TreeItem<>("Initial Year of release: "+port.getYearOfRelease()));
                    PortController.getPortController().getPortDetails().getRoot().getChildren().add(new TreeItem<>("Original Cover Art (URL): "+port.getCoverArtURL()));
                    PortController.getPortController().getPortDetails().getRoot().getChildren().add(new TreeItem<>("Port Developer: "+port.getPortDev()));
                    PortController.getPortController().getPortDetails().getRoot().getChildren().add(new TreeItem<>("Year of Port release: "+port.getNewYear()));
                    PortController.getPortController().getPortDetails().getRoot().getChildren().add(new TreeItem<>("Port Cover Art (URL): "+port.getNewCoverArt()));
                    TreeItem<String> portsSystems = new TreeItem<>("Systems this game is on: ");
                    PortController.getPortController().getPortDetails().getRoot().getChildren().add(portsSystems);

                    String ogGameName = portTI.getParent().getValue().substring(10);
                    int keyForOGGame = HelloApplication.games.hashFunction(ogGameName);
                    Game ogGame = HelloApplication.games.getElementFromPosition(keyForOGGame);
                    boolean gameEmpty= (ogGame==null);

                    if (gameEmpty)
                        for (int i=0;i<HelloApplication.games.size();i++)
                            if (HelloApplication.games.getElementFromPosition(i)!=null){
                                ogGame=HelloApplication.games.getElementFromPosition(i);//assigning dummy system to avoid null pointer exception if port is initially null
                                break;
                            }

                    if (!ogGame.getTitle().equals(ogGameName)) {
                        int home=keyForOGGame;
                        do {
                            keyForOGGame=(keyForOGGame+1)%(HelloApplication.games.size());

                            if (HelloApplication.games.getElementFromPosition(keyForOGGame) != null) {
                                if (HelloApplication.games.getElementFromPosition(keyForOGGame).getTitle().equals(ogGameName)) {
                                    ogGame = HelloApplication.games.getElementFromPosition(keyForOGGame);
                                    break;
                                }
                            }

                        } while (home!=keyForOGGame);
                    }

                    for (GamePort portOfOG : ogGame.getPorts()){
                        portsSystems.getChildren().add(new TreeItem<>(portOfOG.getGsPortedTo()+ " (" + portOfOG.getNewYear() +")"));
                    }

                    PortController.getPortController().portDev.setText(port.getPortDev());
                    PortController.getPortController().portCover.setText(port.getNewCoverArt());
                    PortController.getPortController().portRelease.setText(String.valueOf(port.getNewYear()));

                    int keyForGSOfPort = HelloApplication.gameSystems.hashFunction(port.getGsPortedTo());
                    GameSystem gsOfPort=HelloApplication.gameSystems.getElementFromPosition(keyForGSOfPort);
                    boolean gsEmpty2 = (gsOfPort == null);
                    if (gsEmpty2)
                        for (int i = 0; i < HelloApplication.gameSystems.size(); i++)
                            if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                                gsOfPort = HelloApplication.gameSystems.getElementFromPosition(i);//assigning dummy system to avoid null pointer exception if gsToAddTo is initially null
                                break;
                            }

                    if (!gsOfPort.getName().equals(port.getGsPortedTo())) {//finding game system in backend hash map stored at diff location
                        int home = keyForGSOfPort;
                        do {
                            keyForGSOfPort = (keyForGSOfPort + 1) % (HelloApplication.gameSystems.size());
                            if (HelloApplication.gameSystems.getElementFromPosition(keyForGSOfPort) != null) {
                                if (HelloApplication.gameSystems.getElementFromPosition(keyForGSOfPort).getName().equals(port.getTitle())) {
                                    gsOfPort = HelloApplication.gameSystems.getElementFromPosition(keyForGSOfPort);
                                    break;
                                }
                            }
                        } while (home != keyForGSOfPort);
                    }

                    PortController.getPortController().setGs(gsOfPort);

                } else {// | SYSTEM | -> | PORT |
                    String gsPortedTo = portTI.getParent().getValue().substring(12);

                    String portName = portTI.getValue().substring(10);//getting rid of "| PORT |  " and system;
                    int key = HelloApplication.ports.hashFunction(portName);
                    GamePort port = HelloApplication.ports.getElementFromPosition(key);

                    boolean portEmpty = (port == null);
                    if (portEmpty)
                        for (int i = 0; i < HelloApplication.ports.size(); i++)
                            if (HelloApplication.ports.getElementFromPosition(i) != null) {
                                port = HelloApplication.ports.getElementFromPosition(i);//assigning dummy game to avoid null pointer exception if port is initially null
                                break;
                            }

                    if (!(port.getTitle().equals(portName) && port.getGsPortedTo().equals(gsPortedTo))) {
                        int home = key;
                        do {
                            key = (key + 1) % (HelloApplication.ports.size());
                            if (HelloApplication.ports.getElementFromPosition(key) != null) {
                                if (HelloApplication.ports.getElementFromPosition(key).getTitle().equalsIgnoreCase(portName) && HelloApplication.ports.getElementFromPosition(key).getGsPortedTo().equals(gsPortedTo)) {//getting actual port if it has been linearly probed
                                    port = HelloApplication.ports.getElementFromPosition(key);
                                    break;
                                }
                            }
                        } while (home != key);
                    }

                    for (TreeItem<String> system:root.getChildren()){//searching through systems to find original game
                        boolean ogGameFound=false;
                        String gsName=system.getValue().substring(12);
                        int keyForGS=HelloApplication.gameSystems.hashFunction(gsName);
                        GameSystem gs = HelloApplication.gameSystems.getElementFromPosition(keyForGS);
                        boolean gsEmpty=(gs==null);
                        if (gsEmpty)
                            for (int i=0;i<HelloApplication.gameSystems.size();i++)
                                if (HelloApplication.gameSystems.getElementFromPosition(i)!=null){
                                    gs=HelloApplication.gameSystems.getElementFromPosition(i);//assigning dummy system to avoid null pointer exception if gs is initially null
                                    break;
                                }

                        if (!gs.getName().equals(gsName)) {//finding game system in backend hash map stored at diff location
                            int home = keyForGS;
                            do {
                                keyForGS = (keyForGS + 1) % (HelloApplication.gameSystems.size());
                                if (HelloApplication.gameSystems.getElementFromPosition(key) != null) {
                                    if (HelloApplication.gameSystems.getElementFromPosition(key).getName().equalsIgnoreCase(gsName)) {
                                        gs = HelloApplication.gameSystems.getElementFromPosition(key);
                                        break;
                                    }
                                }
                            } while (home != keyForGS);
                        }

                        Game ogGame=null;

                        for (Game game : gs.getGames())//searching through systems games
                            if (!(game instanceof GamePort) && game.getTitle().equals(port.getTitle())){
                                ogGame=game;
                                ogGameFound=true;
                                break;
                            }

                        if (ogGameFound) {
                            PortController.getPortController().portDev.setText(port.getPortDev());
                            PortController.getPortController().portCover.setText(port.getNewCoverArt());
                            PortController.getPortController().portRelease.setText(String.valueOf(port.getNewYear()));
                            PortController.getPortController().setGamePort(port);

                            PortController.getPortController().getPortDetails().setRoot(new TreeItem<>(port.getTitle() + " (Port from " + system.getValue().substring(12) + " to "+ port.getGsPortedTo()+")"));
                            PortController.getPortController().getPortDetails().getRoot().getChildren().add(new TreeItem<>("Description: "+port.getDescription()));
                            PortController.getPortController().getPortDetails().getRoot().getChildren().add(new TreeItem<>("Publisher: "+port.getPublisher()));
                            PortController.getPortController().getPortDetails().getRoot().getChildren().add(new TreeItem<>("Original Developer: "+port.getOgDeveloper()));
                            PortController.getPortController().getPortDetails().getRoot().getChildren().add(new TreeItem<>("Initial Year of release: "+port.getYearOfRelease()));
                            PortController.getPortController().getPortDetails().getRoot().getChildren().add(new TreeItem<>("Original Cover Art (URL): "+port.getCoverArtURL()));
                            PortController.getPortController().getPortDetails().getRoot().getChildren().add(new TreeItem<>("Port Developer: "+port.getPortDev()));
                            PortController.getPortController().getPortDetails().getRoot().getChildren().add(new TreeItem<>("Year of Port release: "+port.getNewYear()));
                            PortController.getPortController().getPortDetails().getRoot().getChildren().add(new TreeItem<>("Port Cover Art (URL): "+port.getNewCoverArt()));
                            TreeItem<String> portsSystems = new TreeItem<>("Systems this game is on: ");
                            PortController.getPortController().getPortDetails().getRoot().getChildren().add(portsSystems);

                            int keyForGSOfPort = HelloApplication.gameSystems.hashFunction(port.getGsPortedTo());
                            GameSystem gsOfPort=HelloApplication.gameSystems.getElementFromPosition(keyForGSOfPort);
                            boolean gsEmpty2 = (gsOfPort == null);
                            if (gsEmpty2)
                                for (int i = 0; i < HelloApplication.gameSystems.size(); i++)
                                    if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                                        gsOfPort = HelloApplication.gameSystems.getElementFromPosition(i);//assigning dummy system to avoid null pointer exception if gsToAddTo is initially null
                                        break;
                                    }

                            if (!gsOfPort.getName().equals(port.getGsPortedTo())) {//finding game system in backend hash map stored at diff location
                                int home = keyForGSOfPort;
                                do {
                                    keyForGSOfPort = (keyForGSOfPort + 1) % (HelloApplication.gameSystems.size());
                                    if (HelloApplication.gameSystems.getElementFromPosition(keyForGSOfPort) != null) {
                                        if (HelloApplication.gameSystems.getElementFromPosition(keyForGSOfPort).getName().equals(port.getTitle())) {
                                            gsOfPort = HelloApplication.gameSystems.getElementFromPosition(keyForGSOfPort);
                                            break;
                                        }
                                    }
                                } while (home != keyForGSOfPort);
                            }

                            PortController.getPortController().setGs(gsOfPort);


                            PortController.getPortController().setGs(gs);
                            for (GamePort portOfOG:ogGame.getPorts()){//adding all ports to treeview
                                portsSystems.getChildren().add(new TreeItem<>(portOfOG.getGsPortedTo()+ " (" + portOfOG.getNewYear() +")"));
                            }
                            break;
                        }
                    }
                }
                HelloApplication.mainStage.setScene(HelloApplication.portS);
            }
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


                    String gsPortedTo = portToDeleteTI.getValue().split("\\|")[3].trim();// Extract system name using "|"

                    int endToRemove = gsPortedTo.length()+6;// "  | " + " |"
                    String portName = portToDeleteTI.getValue().substring(10,portToDeleteTI.getValue().length()-endToRemove);//getting rid of "| PORT |  " and system;
                    int key = HelloApplication.ports.hashFunction(portName);
                    GamePort port = HelloApplication.ports.getElementFromPosition(key);

                    boolean portEmpty = (port == null);
                    if (portEmpty)
                        for (int i = 0; i < HelloApplication.ports.size(); i++)
                            if (HelloApplication.ports.getElementFromPosition(i) != null) {
                                port = HelloApplication.ports.getElementFromPosition(i);//assigning dummy game to avoid null pointer exception if gsToAddTo is initially null
                                break;
                            }

                    if (!(port.getTitle().equals(portName) && port.getGsPortedTo().equals(gsPortedTo))) {
                        int home = key;
                        do {
                            key = (key + 1) % (HelloApplication.ports.size());
                            if (HelloApplication.ports.getElementFromPosition(key) != null) {
                                if (HelloApplication.ports.getElementFromPosition(key).getTitle().equalsIgnoreCase(portName) && HelloApplication.ports.getElementFromPosition(key).getGsPortedTo().equals(gsPortedTo)) {//getting actual port if it has been linearly probed
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
                            boolean inThisGS = (gsPortedTo.equals(gs.getName()));

                            if (inThisGS) {
                                if (child.getValue().equals("| PORT |  " + port.getTitle())) {
                                    gs.getGames().remove(port);//removing port from systems game list
                                    child.getParent().getChildren().remove(child);
                                    break;

                                } else if (child.getValue().equals("| GAME |  " + port.getTitle())) {
                                    for (TreeItem<String> game : child.getChildren()) {
                                        String gameName = game.getValue().substring(10, portToDeleteTI.getValue().length() - endToRemove);//getting rid of "| GAME |  ";
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
                                        if (portRemoved) break;//break for | GAME | route
                                    }

                                }
                            }
                        }
                    }

                    HelloApplication.ports.delete(port.getPortPosition());

                    portToDeleteTI.getParent().getChildren().remove(portToDeleteTI);


                } else { //| SYSTEM | -> | PORT |

                    String gsPortedTo = portToDeleteTI.getParent().getValue().substring(12);
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

                    if (!(port.getTitle().equals(portName) && port.getGsPortedTo().equals(gsPortedTo))) {
                        int home = key;
                        do {
                            key = (key + 1) % (HelloApplication.ports.size());
                            if (HelloApplication.ports.getElementFromPosition(key) != null) {
                                if (HelloApplication.ports.getElementFromPosition(key).getTitle().equalsIgnoreCase(portName) && HelloApplication.ports.getElementFromPosition(key).getGsPortedTo().equals(gsPortedTo)) {//getting actual port if it has been linearly probed
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

                        for (TreeItem<String> game : system.getChildren()) {
                            boolean portRemoved = false;
                            boolean inThisGS = false;

                            for (Game game1 : gs.getGames())
                                if (game1.getTitle().equals(port.getTitle())&&!(game1 instanceof GamePort)) {
                                    inThisGS = true;//check if original game is in this system
                                    break;
                                }


                            if (inThisGS) {
                                if (game.getValue().equals("| GAME |  " + port.getTitle())) {
                                    for (TreeItem<String> port2 : game.getChildren()) {//going through game treeitems
                                        String rightGS = port2.getValue().split("\\|")[3].trim();// Extract system name using "|"
                                        if (rightGS.equals(port.getGsPortedTo())) {
                                            String gameName = game.getValue().substring(10);//getting rid of "| GAME |  ";
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
                                                if (port1.getTitle().equals(port.getTitle()) && port1.getGsPortedTo().equals(port.getGsPortedTo())) {
                                                    port2.getParent().getChildren().remove(port2);//port getting removed from treeview
                                                    gameToRemovePortFrom.getPorts().remove(port1);
                                                    portRemoved = true;
                                                    break;
                                                }
                                            }
                                            if (portRemoved) break;//break for | GAME | route
                                        }
                                    }
                                }
                            }
                        }
                    }

                    String gsName = port.getGsPortedTo();//for system chosen
                    int keyForGS=HelloApplication.gameSystems.hashFunction(gsName);
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

                    for (Game game : gs.getGames())
                        if (game instanceof GamePort)
                            if (game.getTitle().equals(port.getTitle())) {
                                gs.getGames().remove(game);
                                break;
                            }

                    HelloApplication.ports.delete(port.getPortPosition());


                    portToDeleteTI.getParent().getChildren().remove(portToDeleteTI);
                }

            }

            case 0 -> {
                Utilities.showWarningAlert("WARNING!", "Select what you would like to delete");
            }

        }
    }

    public void clear(){//for load and back buttons in drilldown
        system.getRoot().getChildren().clear();
        gameSystems.getItems().clear();
    }

    public void refresh(){
        clear();
        for (int i = 0 ; i<HelloApplication.gameSystems.size();i++){//adding everything back into fxml elements
            if (HelloApplication.gameSystems.getElementFromPosition(i)!=null) {
                TreeItem<String> gs = new TreeItem<>("| SYSTEM |  " + HelloApplication.gameSystems.getElementFromPosition(i).getName());
                MainController.getRoot().getChildren().add(gs);
                MainController.getMainController().getGameSystems().getItems().add(HelloApplication.gameSystems.getElementFromPosition(i));
                for (Game game : HelloApplication.gameSystems.getElementFromPosition(i).getGames()) {
                    if (!(game instanceof GamePort)){
                        TreeItem<String> gameTI = new TreeItem<>("| GAME |  " + game.getTitle());
                        gs.getChildren().add(gameTI);
                        HelloApplication.games.replace(game,game.getPosition());
                        for (GamePort port : game.getPorts()) {
                            gameTI.getChildren().add(new TreeItem<>("| PORT |  " + port.getTitle() + "  | " + port.getGsPortedTo() + " |"));
                            HelloApplication.ports.replace(port,port.getPortPosition());
                        }
                    } else {
                        gs.getChildren().add(new TreeItem<>("| PORT |  " + game.getTitle()));
                        HelloApplication.ports.replace((GamePort) game,((GamePort) game).getPortPosition());
                    }
                }
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

    @FXML
    public void load() throws Exception {
        clear();
        HelloApplication.load();
        refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        system.setRoot(root);
        mainController = this;
    }
}
