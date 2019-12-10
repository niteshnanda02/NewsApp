package com.example.citispotter.Model;

public class HistoryClass {
    private String title;
    private String date;
    private String lasttime;
    private int nooftime;
    private String image;


    public HistoryClass() {
    }

    public HistoryClass(String title, String date, String lasttime, int nooftime, String image) {
        this.title = title;
        this.date = date;
        this.lasttime = lasttime;
        this.nooftime = nooftime;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    public int getNooftime() {
        return nooftime;
    }

    public void setNooftime(int nooftime) {
        this.nooftime = nooftime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

