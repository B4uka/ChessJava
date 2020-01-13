package com.example.demo.DataBase;

import java.text.DateFormat;


public class Games {

    private String whitePlayerAccountName;
    private String blackPlayerAccountName;
    private int whitePlayerRating;
    private int blackPlayerRating;
    private Result result;
    private String ECO;
    private DateFormat whenGameWasPlayed;

    private boolean isFilled;
    private int iD;
    public static int count =0;

    public Games (String whitePlayerAccountName, String blackPlayerAccountName, int whitePlayerRating, int blackPlayerRating, Result result, String ECO, DateFormat whenGameWasPlayed) {

        this.whitePlayerAccountName = whitePlayerAccountName;
        this.blackPlayerAccountName = blackPlayerAccountName;
        this.whitePlayerRating = whitePlayerRating;
        this.blackPlayerRating = blackPlayerRating;
        this.result = result;
        this.ECO = ECO;
        this.whenGameWasPlayed = whenGameWasPlayed;

        isFilled = false;
        this.iD = ++count;
    }


    @Override
    public String toString () {
        return "Games{" +
                "iD=" + this.iD +
                ", whitePlayerAccountName='" + whitePlayerAccountName + '\'' +
                ", blackPlayerAccountName='" + blackPlayerAccountName + '\'' +
                ", whitePlayerRating=" + whitePlayerRating +
                ", blackPlayerRating=" + blackPlayerRating +
                ", result=" + result +
                ", ECO='" + ECO + '\'' +
                ", whenGameWasPlayed=" + whenGameWasPlayed +
                '}' + "\n";
    }

    public String getWhitePlayerAccountName () {
        return whitePlayerAccountName;
    }

    public String getBlackPlayerAccountName () {
        return blackPlayerAccountName;
    }

    public int getBlackPlayerRating () {
        return blackPlayerRating;
    }

    public int getiD () {
        return iD;
    }

    public int getWhitePlayerRating () {
        return whitePlayerRating;
    }

    public Result getResult () {
        return result;
    }

    public String getECO () {
        return ECO;
    }

    public DateFormat getWhenGameWasPlayed () {
        return whenGameWasPlayed;
    }

    public void setBlackPlayerAccountName (String blackPlayerAccountName) {
        this.blackPlayerAccountName = blackPlayerAccountName;
    }

    public void setBlackPlayerRating (int blackPlayerRating) {
        this.blackPlayerRating = blackPlayerRating;
    }

    public void setECO (String ECO) {
        this.ECO = ECO;
    }

    public void setResult (Result result) {
        this.result = result;
    }

    public void setWhenGameWasPlayed (DateFormat whenGameWasPlayed) {
        this.whenGameWasPlayed = whenGameWasPlayed;
    }

    public void setWhitePlayerAccountName (String whitePlayerAccountName) {
        this.whitePlayerAccountName = whitePlayerAccountName;
    }

    public void setWhitePlayerRating (int whitePlayerRating) {
        this.whitePlayerRating = whitePlayerRating;
    }
}
