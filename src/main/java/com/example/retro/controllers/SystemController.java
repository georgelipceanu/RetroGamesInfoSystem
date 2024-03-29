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

                    String gsPortedFrom = portTI.getValue().substring(game.getTitle().length()+12,portTI.getValue().length()-1);
                    int keyGSOfPortToRemoveFrom = HelloApplication.gameSystems.hashFunction(gsPortedFrom);
                    GameSystem gameSystemToRemovePortFrom = HelloApplication.gameSystems.getElementFromPosition(keyGSOfPortToRemoveFrom);
                    boolean gsOfPortToRemoveFrom = (gameSystemToRemovePortFrom) == null;
                    if (gsOfPortToRemoveFrom)
                        for (int i = 0; i < HelloApplication.gameSystems.size(); i++)
                            if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                                gameSystemToRemovePortFrom = HelloApplication.gameSystems.getElementFromPosition(i);//assigning dummy system to avoid null pointer exception if gsToAddTo is initially null
                                break;
                            }

                    if (!gameSystemToRemovePortFrom.getName().equals(gsPortedFrom)) {//finding game system in backend hash map stored at diff location
                        int home = keyGSOfPortToRemoveFrom;
                        do {
                            keyGSOfPortToRemoveFrom = (keyGSOfPortToRemoveFrom + 1) % (HelloApplication.gameSystems.size());
                            if (HelloApplication.gameSystems.getElementFromPosition(keyGSOfPortToRemoveFrom) != null) {
                                if (HelloApplication.gameSystems.getElementFromPosition(keyGSOfPortToRemoveFrom).getName().equalsIgnoreCase(gsPortedFrom)) {
                                    gameSystemToRemovePortFrom = HelloApplication.gameSystems.getElementFromPosition(keyGSOfPortToRemoveFrom);
                                    break;
                                }
                            }
                        } while (home != keyGSOfPortToRemoveFrom);
                    }

                    for (Game game1 : gameSystemToRemovePortFrom.getGames()) { //removing port from ported to system
                        if (!(game1 instanceof GamePort)) {
                            if (game1.getTitle().equals(game.getTitle())) {
                                for (GamePort gamePort : game1.getPorts()) {
                                    if (gamePort.getGsPortedTo().equals(((GamePort) game).getGsPortedTo())) {
                                        game1.getPorts().remove(gamePort);
                                        portRemoved = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    if (portRemoved) break;
                }

                HelloApplication.ports.delete(((GamePort) game).getPortPosition());
            } else {
                for (GamePort port : game.getPorts()){
                    String gsToRemoveFrom = port.getGsPortedTo();
                    int keyGSOfPortToRemoveFrom = HelloApplication.gameSystems.hashFunction(gsToRemoveFrom);
                    GameSystem gameSystemToRemovePortFrom = HelloApplication.gameSystems.getElementFromPosition(keyGSOfPortToRemoveFrom);
                    boolean gsOfPortToRemoveFrom = (gameSystemToRemovePortFrom) == null;
                    if (gsOfPortToRemoveFrom)
                        for (int i = 0; i < HelloApplication.gameSystems.size(); i++)
                            if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                                gameSystemToRemovePortFrom = HelloApplication.gameSystems.getElementFromPosition(i);//assigning dummy system to avoid null pointer exception if gsToAddTo is initially null
                                break;
                            }

                    if (!gameSystemToRemovePortFrom.getName().equals(gsToRemoveFrom)) {//finding game system in backend hash map stored at diff location
                        int home = keyGSOfPortToRemoveFrom;
                        do {
                            keyGSOfPortToRemoveFrom = (keyGSOfPortToRemoveFrom + 1) % (HelloApplication.gameSystems.size());
                            if (HelloApplication.gameSystems.getElementFromPosition(keyGSOfPortToRemoveFrom) != null) {
                                if (HelloApplication.gameSystems.getElementFromPosition(keyGSOfPortToRemoveFrom).getName().equalsIgnoreCase(gsToRemoveFrom)) {
                                    gameSystemToRemovePortFrom = HelloApplication.gameSystems.getElementFromPosition(keyGSOfPortToRemoveFrom);
                                    break;
                                }
                            }
                        } while (home != keyGSOfPortToRemoveFrom);
                    }

                    for (Game game1 : gameSystemToRemovePortFrom.getGames()){
                        if (game1 instanceof GamePort && game1.getTitle().equals(port.getTitle())){
                            gameSystemToRemovePortFrom.getGames().remove(game1);
                            break;
                        }
                    }

                    HelloApplication.ports.delete(port.getPortPosition());
                }
                HelloApplication.games.delete(game.getPosition());
            }
        }
        HelloApplication.gameSystems.delete(gs.getPosition());
        MainController.getMainController().refresh();
        HelloApplication.mainStage.setScene(HelloApplication.mainS);//brought back to main page after delete
    }

    @FXML
    public void view(){
        int option=0;
        if (systemDetails.getSelectionModel().getSelectedItem() != null) {
            if (systemDetails.getSelectionModel().getSelectedItem().getParent() != systemDetails.getRoot()) {
                String gameName= systemDetails.getSelectionModel().getSelectedItem().getValue();
                option = (gameName.endsWith(" (Original game)")) ? 1 : gameName.contains(" (Port from ") ? 2 : 0;

                switch (option) {
                    case 1 -> {//original game
                        System.out.println("og game");
                        gameName=gameName.substring(0,gameName.length()-16);//16 for " (Original game)"
                        for (Game game : gs.getGames()){
                            if (game.getTitle().equals(gameName)){
                                GameController.getGameController().getGameDetails().setRoot(new TreeItem<>(game.getTitle() + "  | " + gs.getName() + " |"));
                                GameController.getGameController().getGameDetails().getRoot().getChildren().add(new TreeItem<>("Description: "+game.getDescription()));
                                GameController.getGameController().getGameDetails().getRoot().getChildren().add(new TreeItem<>("Publisher: "+game.getPublisher()));
                                GameController.getGameController().getGameDetails().getRoot().getChildren().add(new TreeItem<>("Developer: "+game.getOgDeveloper()));
                                GameController.getGameController().getGameDetails().getRoot().getChildren().add(new TreeItem<>("Year of release: "+game.getYearOfRelease()));
                                GameController.getGameController().getGameDetails().getRoot().getChildren().add(new TreeItem<>("Cover Art (URL): "+game.getCoverArtURL()));
                                TreeItem<String> ports =new TreeItem<>("PORTS: ");
                                GameController.getGameController().getGameDetails().getRoot().getChildren().add(ports);
                                for (GamePort port : game.getPorts()){
                                    ports.getChildren().add(new TreeItem<>(port.getGsPortedTo()));//system of port
                                }
                                GameController.getGameController().gameName.setText(game.getTitle());
                                GameController.getGameController().gameDesc.setText(game.getDescription());
                                GameController.getGameController().gamePublisher.setText(game.getPublisher());
                                GameController.getGameController().gameCover.setText(game.getCoverArtURL());
                                GameController.getGameController().gameRelease.setText(String.valueOf(game.getYearOfRelease()));
                                GameController.getGameController().gameDev.setText(game.getOgDeveloper());
                                GameController.getGameController().setGs(gs);
                                GameController.getGameController().setGame(game);
                                HelloApplication.mainStage.setScene(HelloApplication.gameS);
                            }
                        }
                    }

                    case 2 -> {
                        System.out.println("port");
                        GamePort port = null;
                        String ogSys = gameName.split("\\(")[1].trim();//extracting the system name using "("
                        ogSys=ogSys.substring(10,ogSys.length()-1);

                        String portName = gameName.substring(0, gameName.length() - (13+ogSys.length()));


                        int keyForGS = HelloApplication.gameSystems.hashFunction(ogSys);//finding game system in backend hash map
                        GameSystem gs = HelloApplication.gameSystems.getElementFromPosition(keyForGS);

                        boolean gsEmpty = (gs == null);
                        if (gsEmpty)
                            for (int i = 0; i < HelloApplication.gameSystems.size(); i++)
                                if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                                    gs = HelloApplication.gameSystems.getElementFromPosition(i);//assigning dummy system to avoid null pointer exception if gsToAddTo is initially null
                                    break;
                                }

                        if (!gs.getName().equals(ogSys)) {//finding game system in backend hash map stored at diff location
                            int home = keyForGS;
                            do {
                                keyForGS = (keyForGS + 1) % (HelloApplication.gameSystems.size());
                                if (HelloApplication.gameSystems.getElementFromPosition(keyForGS) != null) {
                                    if (HelloApplication.gameSystems.getElementFromPosition(keyForGS).getName().equalsIgnoreCase(ogSys)) {
                                        gs = HelloApplication.gameSystems.getElementFromPosition(keyForGS);
                                        break;
                                    }
                                }
                            } while (home != keyForGS);
                        }

                        for (Game game : this.gs.getGames()){
                            if (game instanceof GamePort){
                                if (game.getTitle().equals(portName)){//finding port object in system
                                    port = (GamePort) game;
                                    break;
                                }
                            }
                        }

                        GameSystem ogSystem=gs;//for clarity
                        Game ogGame=null;

                        for (int i =0;i<HelloApplication.gameSystems.size();i++){//finding system of original game
                            boolean gameFound=false;
                            if(HelloApplication.gameSystems.getElementFromPosition(i)!=null) {
                                for (Game game : HelloApplication.gameSystems.getElementFromPosition(i).getGames()) {
                                    if (!(game instanceof GamePort) && game.getTitle().equals(port.getTitle())) {
                                        ogGame = game;
                                        ogSystem = HelloApplication.gameSystems.getElementFromPosition(i);
                                        gameFound = true;
                                        break;
                                    }
                                }
                            }
                            if (gameFound) break;
                        }


                        PortController.getPortController().portDev.setText(port.getPortDev());
                        PortController.getPortController().portCover.setText(port.getNewCoverArt());
                        PortController.getPortController().portRelease.setText(String.valueOf(port.getNewYear()));
                        PortController.getPortController().setGamePort(port);

                        PortController.getPortController().getPortDetails().setRoot(new TreeItem<>(port.getTitle() + " (Port from " + ogSystem.getName() + " to "+ port.getGsPortedTo()+")"));
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

                        for (GamePort portOfOG:ogGame.getPorts()){//adding all ports to treeview
                            portsSystems.getChildren().add(new TreeItem<>(portOfOG.getGsPortedTo()+ " (" + portOfOG.getNewYear() +")"));
                        }

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
                            int home = keyForGS;
                            do {
                                keyForGS = (keyForGS + 1) % (HelloApplication.gameSystems.size());
                                if (HelloApplication.gameSystems.getElementFromPosition(keyForGS) != null) {
                                    if (HelloApplication.gameSystems.getElementFromPosition(keyForGS).getName().equals(ogSys)) {
                                        gsOfPort = HelloApplication.gameSystems.getElementFromPosition(keyForGS);
                                        break;
                                    }
                                }
                            } while (home != keyForGS);
                        }

                        PortController.getPortController().setGs(gsOfPort);
                        HelloApplication.mainStage.setScene(HelloApplication.portS);
                    }

                    case 0 -> Utilities.showWarningAlert("WARNING", "Please select a game");
                }
            } else Utilities.showWarningAlert("WARNING", "Please select a game");
        } else Utilities.showWarningAlert("WARNING", "Please select a game");
    }

    @FXML
    public void edit(){
        int key = gs.getPosition();
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
            boolean uniqueName = true;
            if (!gs.getName().equals(name)) {//only checks name if it has been changed


                for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
                    if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                        if (HelloApplication.gameSystems.getElementFromPosition(i).getName().equalsIgnoreCase(gsToAdd.getName())) {//checking if name is unique
                            uniqueName = false;
                            break;
                        }
                    }
                }
            }



            if (validPrice&&validYear&&Utilities.isValidURL(url)&&uniqueName && !gsToAdd.getName().contains("| SYSTEM |")){//all valid details
                for (Game game : gs.getGames()){
                    gsToAdd.getGames().add(game);//adding all games back to new gs
                }

                gsToAdd.setPosition(key);
                HelloApplication.gameSystems.replace(gsToAdd,key);
                gs=gsToAdd;


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

            } else Utilities.showWarningAlert("WARNING", "Enter valid details");
        }else Utilities.showWarningAlert("WARNING", "Fill all boxes");
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
