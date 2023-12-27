package com.example.retro.models;

import com.example.retro.MyNeatList;

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


}
