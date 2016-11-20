package edu.up.cs301.game;

import android.graphics.Color;

/**
 * Created by Eric Imperio on 11/8/2016.
 */

public enum Card {
    HALL("Hall", Type.ROOM, Color.rgb(247,220,111)), //yellow
    BILLIARD_ROOM("Billiard Room", Type.ROOM, Color.rgb(39,174,96)), //green
    BALLROOM("Ballroom", Type.ROOM, Color.rgb(220,118,51)), //brown
    DINING_ROOM("Dining Room", Type.ROOM, Color.rgb(44,55,30)),//brown
    STUDY("Study", Type.ROOM, Color.rgb(231,76,60)), //red
    CONSERVATORY("Conservatory", Type.ROOM, Color.rgb(60,100,140)), //purple
    LIBRARY("Library", Type.ROOM, Color.rgb(133,146,158)), //grey
    LOUNGE("Lounge", Type.ROOM, Color.rgb(133,153,233)), //blue
    KITCHEN("Kitchen", Type.ROOM, Color.rgb(133,193,133)), //green
    ROPE("Rope",Type.WEAPON, Color.WHITE),
    KNIFE("Knife",Type.WEAPON, Color.GRAY),
    WRENCH("Wrench",Type.WEAPON, Color.DKGRAY),
    LEAD_PIPE("Lead Pipe",Type.WEAPON, Color.LTGRAY),
    CANDLESTICK("Candlestick",Type.WEAPON, Color.rgb(247,220,111)), //golden
    REVOLVER("Revolver",Type.WEAPON, Color.rgb(160,64,0)), //dark brown
    MR_GREEN("Mr. Green",Type.PERSON, Color.GREEN),
    MRS_PEACOCK("Mrs. Peacock",Type.PERSON, Color.BLUE),
    MRS_WHTE("Mrs. White",Type.PERSON, Color.WHITE),
    COL_MUSTARD("Col. Mustard",Type.PERSON, Color.YELLOW),
    MISS_SCARLET("Miss Scarlet",Type.PERSON, Color.RED),
    PROF_PLUM("Professor Plum",Type.PERSON, Color.rgb(142,68,173)); //purple

    private String name;
    private Type cardType;
    private int color;

    Card(String name, Type cardType, int color){
        this.name = name;
        this.cardType = cardType;
        this.color = color;
    }

    public String getName(){
        return name;
    }

    public Type getType(){
        return cardType;
    }

    public int getColor(){ return color; }
}
