package com.example.retro.models;

public class GamePort extends Game{
    private String portDev;
    private String newCoverArt;
    private int newYear=-1;
    public GamePort(String title, String publisher, String desc, String ogDev, String coverArtURL, int year, GameSystem gs, GameSystem newSys, String portDev, String newCoverArt, int newYear){
        super(title, publisher, desc, ogDev, coverArtURL, year, gs);
    }
}
