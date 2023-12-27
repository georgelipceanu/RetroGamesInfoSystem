package com.example.retro.models;

public class GamePort extends Game{
    private String portDev;
    private String newCoverArt;
    private int newYear=-1;

    public GamePort(String title, String publisher, String desc, String ogDev, String coverArtURL, int year, String portDev, String newCoverArt, int newYear){
        super(title, publisher, desc, ogDev, coverArtURL, year);
        setPortDev(portDev);
        setNewCoverArt(newCoverArt);
        setNewYear(newYear);
    }

    public String getNewCoverArt() {
        return newCoverArt;
    }

    public String getPortDev() {
        return portDev;
    }

    public int getNewYear() {
        return newYear;
    }

    public void setNewCoverArt(String newCoverArt) {
        this.newCoverArt = newCoverArt;
    }

    public void setPortDev(String portDev) {
        this.portDev = portDev;
    }

    public void setNewYear(int newYear) {
        this.newYear = newYear;
    }


}
