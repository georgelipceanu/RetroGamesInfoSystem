package com.example.retro.models;

import com.example.retro.MyNeatList;

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

    }


}
