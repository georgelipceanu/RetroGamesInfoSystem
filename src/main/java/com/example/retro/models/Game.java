package com.example.retro.models;

import com.example.retro.MyNeatList;

import java.util.Objects;

public class Game {
    private String title;
    private String publisher;
    private String description;
    private String ogDeveloper;
    private String coverArtURL;
    private int yearOfRelease=-1;

    private MyNeatList<GamePort> ports=new MyNeatList<>();


    public Game(String title, String publisher, String desc, String ogDev, String coverArtURL, int year){
        setTitle(title);
        setPublisher(publisher);
        setDescription(desc);
        setOgDeveloper(ogDev);
        setCoverArtURL(coverArtURL);
        setYearOfRelease(year);
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public String getOgDeveloper() {
        return ogDeveloper;
    }

    public String getCoverArtURL() {
        return coverArtURL;
    }

    public String getPublisher() {
        return publisher;
    }

    public MyNeatList<GamePort> getPorts() {
        return ports;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOgDeveloper(String ogDeveloper) {
        this.ogDeveloper = ogDeveloper;
    }

    public void setCoverArtURL(String coverArtURL) {
        this.coverArtURL = coverArtURL;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    private int position=-1;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return title; //simple toString() making games readable in fxml items (choiceboxes, listviews, etc)
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return yearOfRelease == game.yearOfRelease && Objects.equals(title, game.title) && Objects.equals(publisher, game.publisher) && Objects.equals(description, game.description) && Objects.equals(ogDeveloper, game.ogDeveloper) && Objects.equals(coverArtURL, game.coverArtURL) && Objects.equals(ports, game.ports);
    }

}
