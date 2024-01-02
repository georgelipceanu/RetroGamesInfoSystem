package com.example.retro.controllers;

import com.example.retro.HelloApplication;

import com.example.retro.MyNeatList;
import com.example.retro.models.Game;
import com.example.retro.models.GamePort;
import com.example.retro.models.GameSystem;
import com.example.retro.utils.SortUtils;
import com.example.retro.utils.Utilities;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    private static SearchController searchController;

    public static SearchController getSearchController() {
        return searchController;
    }

    @FXML
    public TreeView<String> searchResults;

    @FXML
    public final TreeItem<String> root= new TreeItem<>("Results");

    @FXML
    public TextField searchBar;

    @FXML
    public ChoiceBox<String> ascOrDesc, gsFilter, gameFilter, portFilter;

    public String[] gsFilters= {"Name","Price","Year"};
    public String[] gameFilters={"Name","Description","Year"};
    public String[] portFilters={"Name", "Year"};
    public String[] ascOrDescOptions={"Ascending","Descending"};

    @FXML
    public void searchSystem(){
        searchResults.getRoot().getChildren().clear();
        if (!searchBar.getText().isEmpty()&&gsFilter.getSelectionModel().getSelectedItem()!=null && ascOrDesc.getSelectionModel().getSelectedItem()!=null){
            String gsNameToSearchFor = searchBar.getText();
            int filterOption = (gsFilter.getSelectionModel().getSelectedItem().equals("Name")) ? 1 : (gsFilter.getSelectionModel().getSelectedItem().equals("Price")) ? 2 : 3;
            int ascOrDescOption=(ascOrDesc.getSelectionModel().getSelectedItem().equals("Ascending")) ? 1 : 2;

            switch (filterOption){
                case 1 -> { //sorting by name
                    if (ascOrDescOption==1){//ascending
                        MyNeatList<String> sortedNames = SortUtils.sortByGameSystemNameAscendingReturn();
                        for (String gsName : sortedNames) {
                            if (gsName.contains(gsNameToSearchFor)) {
                                int key = HelloApplication.gameSystems.hashFunction(gsName);
                                GameSystem gs = HelloApplication.gameSystems.getElementFromPosition(key);
                                boolean gsEmpty = (gs == null);
                                if (gsEmpty)
                                    for (int i = 0; i < HelloApplication.gameSystems.size(); i++)
                                        if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                                            gs = HelloApplication.gameSystems.getElementFromPosition(i);//assigning dummy system to avoid null pointer exception if gsToAddTo is initially null
                                            break;
                                        }

                                if (!gs.getName().equals(gsName)) {
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
                                TreeItem<String> gsTI = new TreeItem<>("| SYSTEM |  " + gs.getName());

                                root.getChildren().add(gsTI);

                                for (Game game : gs.getGames()) {
                                    if (game instanceof GamePort) {
                                        gsTI.getChildren().add(new TreeItem<String>("| PORT |  " + game.getTitle()));
                                    } else {
                                        TreeItem<String> gameTI = new TreeItem<>("| GAME |  " + game.getTitle());
                                        gsTI.getChildren().add(gameTI);
                                        for (GamePort port : game.getPorts()) {
                                            gameTI.getChildren().add(new TreeItem<>("| PORT |  " + port.getTitle() + "  | " + port.getGsPortedTo() + " |"));
                                        }
                                    }
                                }

                            }

                        }

                    } else {//descending
                        MyNeatList<String> sortedNames = SortUtils.sortByGameSystemNameDescendingReturn();
                        for (String gsName : sortedNames) {
                            if (gsName.contains(gsNameToSearchFor)) {
                                int key = HelloApplication.gameSystems.hashFunction(gsName);
                                GameSystem gs = HelloApplication.gameSystems.getElementFromPosition(key);
                                boolean gsEmpty = (gs == null);
                                if (gsEmpty)
                                    for (int i = 0; i < HelloApplication.gameSystems.size(); i++)
                                        if (HelloApplication.gameSystems.getElementFromPosition(i) != null) {
                                            gs = HelloApplication.gameSystems.getElementFromPosition(i);//assigning dummy system to avoid null pointer exception if gsToAddTo is initially null
                                            break;
                                        }

                                if (!gs.getName().equals(gsName)) {
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
                                TreeItem<String> gsTI = new TreeItem<>("| SYSTEM |  " + gs.getName());
                                TreeItem<String> details = new TreeItem<>("DETAILS: ");
                                root.getChildren().add(gsTI);
                                gsTI.getChildren().add(details);
                                details.getChildren().add(new TreeItem<>("DESCRIPTION: " + gs.getDescription()));
                                details.getChildren().add(new TreeItem<>("YEAR: " + gs.getLaunchYear()));
                                details.getChildren().add(new TreeItem<>("PRICE: " + gs.getPrice()));
                                details.getChildren().add(new TreeItem<>("TYPE: " + gs.getType()));
                                details.getChildren().add(new TreeItem<>("MEDIA: " + gs.getMedia()));
                                details.getChildren().add(new TreeItem<>("MANUFACTURER: " + gs.getManufacturer()));
                                details.getChildren().add(new TreeItem<>("IMAGE URL: " + gs.getImageURL()));

                                for (Game game : gs.getGames()) {
                                    if (game instanceof GamePort) {
                                        gsTI.getChildren().add(new TreeItem<>("| PORT |  " + game.getTitle()));
                                    } else {
                                        TreeItem<String> gameTI = new TreeItem<>("| GAME |  " + game.getTitle());
                                        gsTI.getChildren().add(gameTI);
                                        for (GamePort port : game.getPorts()) {
                                            gameTI.getChildren().add(new TreeItem<>("| PORT |  " + port.getTitle() + "  | " + port.getGsPortedTo() + " |"));
                                        }
                                    }
                                }

                            }

                        }
                    }

                }

                case 2 -> {//price
                    if (ascOrDescOption==1){//ascending

                    } else {//descending

                    }

                }

                case 3 -> {//year
                    if (ascOrDescOption==1){//ascending

                    } else {//descending

                    }
                }
            }
        }else Utilities.showWarningAlert("WARNING", "Fill in all details");
    }

    @FXML
    public void searchGame(){
        searchResults.getRoot().getChildren().clear();
        if (!searchBar.getText().isEmpty()&&gameFilter.getSelectionModel().getSelectedItem()!=null && ascOrDesc.getSelectionModel().getSelectedItem()!=null) {
            String gameNameToSearchFor = searchBar.getText();
            int filterOption = (gameFilter.getSelectionModel().getSelectedItem().equals("Name")) ? 1 : (gameFilter.getSelectionModel().getSelectedItem().equals("Description")) ? 2 : 3;
            int ascOrDescOption = (ascOrDesc.getSelectionModel().getSelectedItem().equals("Ascending")) ? 1 : 2;

            switch (filterOption){
                case 1 -> {//name
                    if (ascOrDescOption==1){//ascending
                        MyNeatList<String> sortedGames=SortUtils.sortByGameNameAscendingReturn();
                        for (String gameName : sortedGames){
                            if (gameName.contains(gameNameToSearchFor)){
                                int key = HelloApplication.games.hashFunction(gameName);
                                Game game = HelloApplication.games.getElementFromPosition(key);
                                boolean gameEmpty=(game==null);

                                if (gameEmpty)
                                    for (int i=0;i<HelloApplication.games.size();i++)
                                        if (HelloApplication.games.getElementFromPosition(i)!=null){
                                            game=HelloApplication.games.getElementFromPosition(i);//assigning dummy game to avoid null pointer exception if gsToAddTo is initially null
                                            break;
                                        }

                                if (!game.getTitle().equals(gameName)) {
                                    int home = key;
                                    do {
                                        key = (key + 1) % (HelloApplication.games.size());

                                        if (HelloApplication.games.getElementFromPosition(key) != null) {
                                            if (HelloApplication.games.getElementFromPosition(key).getTitle().equals(gameName)) {
                                                game = HelloApplication.games.getElementFromPosition(key);
                                                break;
                                            }
                                        }

                                    } while (home != key);
                                }

                                TreeItem<String> gameTI = new TreeItem<>("| GAME |  " + game.getTitle());
                                TreeItem<String> details = new TreeItem<>("DETAILS: ");
                                root.getChildren().add(gameTI);
                                gameTI.getChildren().add(details);
                                details.getChildren().add(new TreeItem<>("DESCRIPTION: " + game.getDescription()));
                                details.getChildren().add(new TreeItem<>("YEAR: " + game.getYearOfRelease()));
                                details.getChildren().add(new TreeItem<>("PUBLISHER: " + game.getPublisher()));
                                details.getChildren().add(new TreeItem<>("DEVELOPER: " + game.getOgDeveloper()));
                                details.getChildren().add(new TreeItem<>("MANUFACTURER: " + game.getCoverArtURL()));
                                details.getChildren().add(new TreeItem<>("COVER ART URL: " + game.getCoverArtURL()));

                                for (GamePort port : game.getPorts()) {
                                    if (game instanceof GamePort) {
                                        gameTI.getChildren().add(new TreeItem<>("| PORT |  " + port.getTitle() + "  | " + port.getGsPortedTo() + " |"));
                                    }
                                }

                            }
                        }
                    } else {//descending
                        MyNeatList<String> sortedGames=SortUtils.sortByGameNameDescendingReturn();
                        for (String gameName : sortedGames) {
                            if (gameName.contains(gameNameToSearchFor)) {
                                int key = HelloApplication.games.hashFunction(gameName);
                                Game game = HelloApplication.games.getElementFromPosition(key);
                                boolean gameEmpty = (game == null);

                                if (gameEmpty)
                                    for (int i = 0; i < HelloApplication.games.size(); i++)
                                        if (HelloApplication.games.getElementFromPosition(i) != null) {
                                            game = HelloApplication.games.getElementFromPosition(i);//assigning dummy game to avoid null pointer exception if gsToAddTo is initially null
                                            break;
                                        }

                                if (!game.getTitle().equals(gameName)) {
                                    int home = key;
                                    do {
                                        key = (key + 1) % (HelloApplication.games.size());

                                        if (HelloApplication.games.getElementFromPosition(key) != null) {
                                            if (HelloApplication.games.getElementFromPosition(key).getTitle().equals(gameName)) {
                                                game = HelloApplication.games.getElementFromPosition(key);
                                                break;
                                            }
                                        }

                                    } while (home != key);
                                }

                                TreeItem<String> gameTI = new TreeItem<>("| GAME |  " + game.getTitle());
                                TreeItem<String> details = new TreeItem<>("DETAILS: ");
                                root.getChildren().add(gameTI);
                                gameTI.getChildren().add(details);
                                details.getChildren().add(new TreeItem<>("DESCRIPTION: " + game.getDescription()));
                                details.getChildren().add(new TreeItem<>("YEAR: " + game.getYearOfRelease()));
                                details.getChildren().add(new TreeItem<>("PUBLISHER: " + game.getPublisher()));
                                details.getChildren().add(new TreeItem<>("DEVELOPER: " + game.getOgDeveloper()));
                                details.getChildren().add(new TreeItem<>("MANUFACTURER: " + game.getCoverArtURL()));
                                details.getChildren().add(new TreeItem<>("COVER ART URL: " + game.getCoverArtURL()));

                                for (GamePort port : game.getPorts()) {
                                    if (game instanceof GamePort) {
                                        gameTI.getChildren().add(new TreeItem<>("| PORT |  " + port.getTitle() + "  | " + port.getGsPortedTo() + " |"));
                                    }
                                }

                            }
                        }
                    }

                }
                case 2 -> {//description
                    if (ascOrDescOption==1){//ascending

                    } else {//descending

                    }

                }
                case 3 -> {//price
                    if (ascOrDescOption==1){//ascending

                    } else {//descending

                    }

                }
            }
        }
    }

    @FXML
    public void searchPort(){
        searchResults.getRoot().getChildren().clear();
        if (!searchBar.getText().isEmpty()&&gameFilter.getSelectionModel().getSelectedItem()!=null && ascOrDesc.getSelectionModel().getSelectedItem()!=null) {
            String gameNameToSearchFor = searchBar.getText();
            int filterOption = (gameFilter.getSelectionModel().getSelectedItem().equals("Name")) ? 1 : (gameFilter.getSelectionModel().getSelectedItem().equals("Description")) ? 2 : 3;
            int ascOrDescOption = (ascOrDesc.getSelectionModel().getSelectedItem().equals("Ascending")) ? 1 : 2;

            switch (filterOption){
                case 1 -> {//name
                    if (ascOrDescOption==1){//ascending

                    } else {//descending

                    }

                }
                case 2 -> {//description
                    if (ascOrDescOption==1){//ascending

                    } else {//descending

                    }

                }
                case 3 -> {//price
                    if (ascOrDescOption==1){//ascending

                    } else {//descending

                    }

                }
            }
        }
    }

    @FXML
    public void goBack(){
        searchResults.getRoot().getChildren().clear();
        HelloApplication.mainStage.setScene(HelloApplication.mainS);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchResults.setRoot(root);
        gsFilter.getItems().addAll(gsFilters);
        gameFilter.getItems().addAll(gameFilters);
        portFilter.getItems().addAll(portFilters);
        ascOrDesc.getItems().addAll(ascOrDescOptions);
        searchController=this;
    }



//    public void searchGameSystemsByName(){
//        if (!searchBar.getText().isEmpty()) {
//            searchedListOfGameSystems.getItems().clear();
//            String goods = searchBar.getText();
//            MyNeatList<String> strSearchedGameSystems = new MyNeatList<>();
//
//            for (int i = 0; i < HelloApplication.gameSystems.size(); i++) {
//                    if (HelloApplication.gameSystems.getElementFromPosition(i).getName().toLowerCase().contains(goods.toLowerCase())) {//searches through all containers and pallets to find goods
//                        strSearchedGameSystems.add(HelloApplication.gameSystems.getElementFromPosition(i).getName());
//                    }
//                }
//            SortUtils.insertionSortAscending(strSearchedGameSystems);
//            }
//
//            if (!strSearchedGameSystems.isEmpty()) {//checks if any goods were found
//                searchedListOfGameSystems.getItems().addAll(strSearchedGameSystems);//adds them to listview
//            }else Utilities.showWarningAlert("WARNING!","No goods of this description found");
//        }else Utilities.showWarningAlert("WARNING!","Search bar is empty");

    }



