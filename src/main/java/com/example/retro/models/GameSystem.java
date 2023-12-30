package com.example.retro.models;

import com.example.retro.MyNeatList;

import java.util.Objects;

public class GameSystem {
    private String name;
    private String manufacturer;
    private String description;
    private String type;
    private String media;
    private String imageURL;
    private int launchYear=-1;
    private double price=-1;
    private MyNeatList<Game> games = new MyNeatList<>();



    public GameSystem(String name, String manufacturer, String description, String type, String media, String imageURL, int launchYear, double price){
        setName(name);
        setManufacturer(manufacturer);
        setDescription(description);
        setType(type);
        setMedia(media);
        setImageURL(imageURL);
        setLaunchYear(launchYear);
        setPrice(price);
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public int getLaunchYear() {
        return launchYear;
    }

    public String getMedia() {
        return media;
    }

    public String getImageURL() {
        return imageURL;
    }

    public MyNeatList<Game> getGames() {
        return games;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setLaunchYear(int launchYear) {
        this.launchYear = launchYear;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return name; //simple toString() making game systems readable in fxml items (choiceboxes, listviews, etc)
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameSystem that = (GameSystem) o;
        return launchYear == that.launchYear && Double.compare(that.price, price) == 0 && Objects.equals(name, that.name) && Objects.equals(manufacturer, that.manufacturer) && Objects.equals(description, that.description) && Objects.equals(type, that.type) && Objects.equals(media, that.media) && Objects.equals(imageURL, that.imageURL) && Objects.equals(games, that.games);
    }

}
