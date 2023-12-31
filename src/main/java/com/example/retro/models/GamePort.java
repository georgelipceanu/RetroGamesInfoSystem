package com.example.retro.models;

import java.util.Objects;

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

    private int portPosition=-1;

    public int getPortPosition() {
        return portPosition;
    }

    public void setPortPosition(int portPosition) {
        this.portPosition = portPosition;
    }

    @Override
    public String toString() {
        return super.getTitle(); //simple toString() making game systems readable in fxml items (choiceboxes, listviews, etc), and for hash function
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GamePort gamePort = (GamePort) o;
        return newYear == gamePort.newYear && Objects.equals(portDev, gamePort.portDev) && Objects.equals(newCoverArt, gamePort.newCoverArt);
    }

}
