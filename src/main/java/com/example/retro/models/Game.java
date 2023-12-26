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


    public Game(String title, String publisher, String desc, String ogDev, String coverArtURL, int year, GameSystem gs){
    }
}
