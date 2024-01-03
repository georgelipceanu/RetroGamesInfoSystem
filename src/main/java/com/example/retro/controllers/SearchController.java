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
        if (gsFilter.getSelectionModel().getSelectedItem()!=null && ascOrDesc.getSelectionModel().getSelectedItem()!=null){
            boolean emptySearch = searchBar.getText().isEmpty();
            String gsNameToSearchFor = "";
            if (!emptySearch) gsNameToSearchFor=searchBar.getText();
            int filterOption = (gsFilter.getSelectionModel().getSelectedItem().equals("Name")) ? 1 : (gsFilter.getSelectionModel().getSelectedItem().equals("Price")) ? 2 : 3;
            int ascOrDescOption=(ascOrDesc.getSelectionModel().getSelectedItem().equals("Ascending")) ? 1 : 2;

            switch (filterOption){
                case 1 -> { //sorting by name
                    if (ascOrDescOption==2){//ascending
                        MyNeatList<GameSystem> sortedGS = SortUtils.sortByGameSystemNameAscending();
                        for (GameSystem gs : sortedGS) {
                            if (gs.getName().contains(gsNameToSearchFor)) {

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
                                        gsTI.getChildren().add(new TreeItem<String>("| PORT |  " + game.getTitle()));
                                    } else {
                                        TreeItem<String> gameTI = new TreeItem<>("| GAME |  " + game.getTitle());
                                        TreeItem<String> detailsOfGame = new TreeItem<>("DETAILS: ");
                                        gsTI.getChildren().add(gameTI);
                                        gameTI.getChildren().add(detailsOfGame);
                                        detailsOfGame.getChildren().add(new TreeItem<>("DESCRIPTION: " + game.getDescription()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("YEAR: " + game.getYearOfRelease()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("PUBLISHER: " + game.getPublisher()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("DEVELOPER: " + game.getOgDeveloper()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("MANUFACTURER: " + game.getCoverArtURL()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("COVER ART URL: " + game.getCoverArtURL()));
                                        for (GamePort port : game.getPorts()) {
                                            gameTI.getChildren().add(new TreeItem<>("| PORT |  " + port.getTitle() + "  | " + port.getGsPortedTo() + " |"));
                                        }
                                    }
                                }

                            }

                        }

                    } else {//descending
                        MyNeatList<GameSystem> sortedGS = SortUtils.sortByGameSystemNameDescending();
                        for (GameSystem gs : sortedGS) {
                            if (gs.getName().contains(gsNameToSearchFor)) {
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
                                        TreeItem<String> detailsOfGame = new TreeItem<>("DETAILS: ");
                                        gsTI.getChildren().add(gameTI);
                                        gameTI.getChildren().add(detailsOfGame);
                                        detailsOfGame.getChildren().add(new TreeItem<>("DESCRIPTION: " + game.getDescription()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("YEAR: " + game.getYearOfRelease()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("PUBLISHER: " + game.getPublisher()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("DEVELOPER: " + game.getOgDeveloper()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("MANUFACTURER: " + game.getCoverArtURL()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("COVER ART URL: " + game.getCoverArtURL()));
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
                    if (ascOrDescOption==2){//ascending
                        MyNeatList<GameSystem> sortedGS = SortUtils.sortByGameSystemPriceAscending();
                        for (GameSystem gs : sortedGS) {
                            if (gs.getName().contains(gsNameToSearchFor)) {

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
                                        gsTI.getChildren().add(new TreeItem<String>("| PORT |  " + game.getTitle()));
                                    } else {
                                        TreeItem<String> gameTI = new TreeItem<>("| GAME |  " + game.getTitle());
                                        TreeItem<String> detailsOfGame = new TreeItem<>("DETAILS: ");
                                        gsTI.getChildren().add(gameTI);
                                        gameTI.getChildren().add(detailsOfGame);
                                        detailsOfGame.getChildren().add(new TreeItem<>("DESCRIPTION: " + game.getDescription()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("YEAR: " + game.getYearOfRelease()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("PUBLISHER: " + game.getPublisher()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("DEVELOPER: " + game.getOgDeveloper()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("MANUFACTURER: " + game.getCoverArtURL()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("COVER ART URL: " + game.getCoverArtURL()));
                                        for (GamePort port : game.getPorts()) {
                                            gameTI.getChildren().add(new TreeItem<>("| PORT |  " + port.getTitle() + "  | " + port.getGsPortedTo() + " |"));
                                        }
                                    }
                                }

                            }

                        }

                    } else {//descending
                        MyNeatList<GameSystem> sortedGS = SortUtils.sortByGameSystemPriceDescending();
                        for (GameSystem gs : sortedGS) {
                            if (gs.getName().contains(gsNameToSearchFor)) {
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
                                        TreeItem<String> detailsOfGame = new TreeItem<>("DETAILS: ");
                                        gsTI.getChildren().add(gameTI);
                                        gameTI.getChildren().add(detailsOfGame);
                                        detailsOfGame.getChildren().add(new TreeItem<>("DESCRIPTION: " + game.getDescription()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("YEAR: " + game.getYearOfRelease()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("PUBLISHER: " + game.getPublisher()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("DEVELOPER: " + game.getOgDeveloper()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("MANUFACTURER: " + game.getCoverArtURL()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("COVER ART URL: " + game.getCoverArtURL()));
                                        for (GamePort port : game.getPorts()) {
                                            gameTI.getChildren().add(new TreeItem<>("| PORT |  " + port.getTitle() + "  | " + port.getGsPortedTo() + " |"));
                                        }
                                    }
                                }

                            }

                        }
                    }

                }

                case 3 -> {//year
                    if (ascOrDescOption==2){//ascending
                        MyNeatList<GameSystem> sortedGS = SortUtils.sortByGameSystemLaunchYearAscending();
                        for (GameSystem gs : sortedGS) {
                            if (gs.getName().contains(gsNameToSearchFor)) {

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
                                        gsTI.getChildren().add(new TreeItem<String>("| PORT |  " + game.getTitle()));
                                    } else {
                                        TreeItem<String> gameTI = new TreeItem<>("| GAME |  " + game.getTitle());
                                        TreeItem<String> detailsOfGame = new TreeItem<>("DETAILS: ");
                                        gsTI.getChildren().add(gameTI);
                                        gameTI.getChildren().add(detailsOfGame);
                                        detailsOfGame.getChildren().add(new TreeItem<>("DESCRIPTION: " + game.getDescription()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("YEAR: " + game.getYearOfRelease()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("PUBLISHER: " + game.getPublisher()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("DEVELOPER: " + game.getOgDeveloper()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("MANUFACTURER: " + game.getCoverArtURL()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("COVER ART URL: " + game.getCoverArtURL()));
                                        for (GamePort port : game.getPorts()) {
                                            gameTI.getChildren().add(new TreeItem<>("| PORT |  " + port.getTitle() + "  | " + port.getGsPortedTo() + " |"));
                                        }
                                    }
                                }

                            }

                        }

                    } else {//descending
                        MyNeatList<GameSystem> sortedGS = SortUtils.sortByGameSystemLaunchYearDescending();
                        for (GameSystem gs : sortedGS) {
                            if (gs.getName().contains(gsNameToSearchFor)) {
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
                                        TreeItem<String> detailsOfGame = new TreeItem<>("DETAILS: ");
                                        gsTI.getChildren().add(gameTI);
                                        gameTI.getChildren().add(detailsOfGame);
                                        detailsOfGame.getChildren().add(new TreeItem<>("DESCRIPTION: " + game.getDescription()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("YEAR: " + game.getYearOfRelease()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("PUBLISHER: " + game.getPublisher()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("DEVELOPER: " + game.getOgDeveloper()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("MANUFACTURER: " + game.getCoverArtURL()));
                                        detailsOfGame.getChildren().add(new TreeItem<>("COVER ART URL: " + game.getCoverArtURL()));
                                        for (GamePort port : game.getPorts()) {
                                            gameTI.getChildren().add(new TreeItem<>("| PORT |  " + port.getTitle() + "  | " + port.getGsPortedTo() + " |"));
                                        }
                                    }
                                }

                            }

                        }
                    }

                }
            }
        }else Utilities.showWarningAlert("WARNING", "Fill in all details");
    }

    @FXML
    public void searchGame(){
        searchResults.getRoot().getChildren().clear();
        if (gameFilter.getSelectionModel().getSelectedItem()!=null && ascOrDesc.getSelectionModel().getSelectedItem()!=null) {
            boolean emptySearch = searchBar.getText().isEmpty();
            String gameNameToSearchFor = "";
            if (!emptySearch) gameNameToSearchFor=searchBar.getText();
            int filterOption = (gameFilter.getSelectionModel().getSelectedItem().equals("Name")) ? 1 : (gameFilter.getSelectionModel().getSelectedItem().equals("Description")) ? 2 : 3;
            int ascOrDescOption = (ascOrDesc.getSelectionModel().getSelectedItem().equals("Ascending")) ? 1 : 2;

            switch (filterOption) {
                case 1 -> {//name
                    if (ascOrDescOption == 2) {//ascending
                        MyNeatList<Game> sortedGS = SortUtils.sortByGameNameAscending();
                        for (Game game : sortedGS) {
                            if (game.getTitle().contains(gameNameToSearchFor)) {

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
                        MyNeatList<Game> sortedGS = SortUtils.sortByGameNameDescending();
                        for (Game game : sortedGS) {
                            if (game.getTitle().contains(gameNameToSearchFor)) {

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
                    if (ascOrDescOption == 2) {//ascending
                        MyNeatList<Game> sortedGS = SortUtils.sortByGameDescriptionAscending();
                        for (Game game : sortedGS) {
                            if (game.getTitle().contains(gameNameToSearchFor)) {

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
                        MyNeatList<Game> sortedGS = SortUtils.sortByGameDescriptionDescending();
                        for (Game game : sortedGS) {
                            if (game.getTitle().contains(gameNameToSearchFor)) {

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
                case 3 -> {//year
                    if (ascOrDescOption == 2) {//ascending
                        MyNeatList<Game> sortedGS = SortUtils.sortByGameReleaseYearAscending();
                        for (Game game : sortedGS) {
                            if (game.getTitle().contains(gameNameToSearchFor)) {

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
                        MyNeatList<Game> sortedGS = SortUtils.sortByGameReleaseYearDescending();
                        for (Game game : sortedGS) {
                            if (game.getTitle().contains(gameNameToSearchFor)) {

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
            }
        }else Utilities.showWarningAlert("WARNING", "Fill in all details");
    }

    @FXML
    public void searchPort() {
        searchResults.getRoot().getChildren().clear();
        if (!searchBar.getText().isEmpty() && portFilter.getSelectionModel().getSelectedItem() != null && ascOrDesc.getSelectionModel().getSelectedItem() != null) {
            boolean emptySearch = searchBar.getText().isEmpty();
            String gameNameToSearchFor = "";
            if (!emptySearch) gameNameToSearchFor=searchBar.getText();
            int filterOption = (portFilter.getSelectionModel().getSelectedItem().equals("Name")) ? 1 : 2;
            int ascOrDescOption = (ascOrDesc.getSelectionModel().getSelectedItem().equals("Ascending")) ? 1 : 2;

            switch (filterOption) {
                case 1 -> {//name
                    if (ascOrDescOption == 2) {//ascending
                        MyNeatList<GamePort> sortedGS = SortUtils.sortByGamePortNameAscending();
                        for (Game game : sortedGS) {
                            if (game.getTitle().contains(gameNameToSearchFor)) {

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
                        MyNeatList<GamePort> sortedGS = SortUtils.sortByGamePortNameDescending();
                        for (Game game : sortedGS) {
                            if (game.getTitle().contains(gameNameToSearchFor)) {

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


                case 2 -> {//year
                    if (ascOrDescOption == 2) {//ascending
                        MyNeatList<GamePort> sortedGS = SortUtils.sortByGamePortYearAscending();
                        for (GamePort port : sortedGS) {
                            if (port.getTitle().contains(gameNameToSearchFor)) {

                                TreeItem<String> portTI = new TreeItem<>("| PORT |  " + port.getTitle() + "  | " + port.getGsPortedTo() + " |");
                                TreeItem<String> details = new TreeItem<>("DETAILS: ");
                                root.getChildren().add(portTI);
                                portTI.getChildren().add(details);
                                details.getChildren().add(new TreeItem<>("DESCRIPTION: " + port.getDescription()));
                                details.getChildren().add(new TreeItem<>("YEAR: " + port.getYearOfRelease()));
                                details.getChildren().add(new TreeItem<>("PUBLISHER: " + port.getPublisher()));
                                details.getChildren().add(new TreeItem<>("DEVELOPER: " + port.getOgDeveloper()));
                                details.getChildren().add(new TreeItem<>("MANUFACTURER: " + port.getCoverArtURL()));
                                details.getChildren().add(new TreeItem<>("COVER ART URL: " + port.getCoverArtURL()));
                                details.getChildren().add(new TreeItem<>("PORT DEV: " + port.getPortDev()));
                                details.getChildren().add(new TreeItem<>("YEAR OF PORT RELEASE: " + port.getNewYear()));
                                details.getChildren().add(new TreeItem<>("PORT COVER ART URL: " + port.getNewCoverArt()));

                            }
                        }

                    } else {//descending
                        MyNeatList<GamePort> sortedGS = SortUtils.sortByGamePortYearDescending();
                        for (GamePort port : sortedGS) {
                            if (port.getTitle().contains(gameNameToSearchFor)) {

                                TreeItem<String> portTI = new TreeItem<>("| PORT |  " + port.getTitle() + "  | " + port.getGsPortedTo() + " |");
                                TreeItem<String> details = new TreeItem<>("DETAILS: ");
                                root.getChildren().add(portTI);
                                portTI.getChildren().add(details);
                                details.getChildren().add(new TreeItem<>("DESCRIPTION: " + port.getDescription()));
                                details.getChildren().add(new TreeItem<>("YEAR: " + port.getYearOfRelease()));
                                details.getChildren().add(new TreeItem<>("PUBLISHER: " + port.getPublisher()));
                                details.getChildren().add(new TreeItem<>("DEVELOPER: " + port.getOgDeveloper()));
                                details.getChildren().add(new TreeItem<>("MANUFACTURER: " + port.getCoverArtURL()));
                                details.getChildren().add(new TreeItem<>("COVER ART URL: " + port.getCoverArtURL()));
                                details.getChildren().add(new TreeItem<>("PORT DEV: " + port.getPortDev()));
                                details.getChildren().add(new TreeItem<>("YEAR OF PORT RELEASE: " + port.getNewYear()));
                                details.getChildren().add(new TreeItem<>("PORT COVER ART URL: " + port.getNewCoverArt()));

                            }

                        }
                    }
                }
            }
        }else Utilities.showWarningAlert("WARNING", "Fill in all details");
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


    }



