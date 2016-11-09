package edu.up.cs301.game;

/**
 * Created by Eric Imperio on 11/8/2016.
 */

public enum Card {
    HALL("Hall", Type.ROOM),
    BILLIARD_ROOM("Billiard Room", Type.ROOM),
    BALLROOM("Ballroom", Type.ROOM),
    DINING_ROOM("Dining Room", Type.ROOM),
    STUDY("Study", Type.ROOM),
    CONSERVATORY("Conversatory", Type.ROOM),
    LIBRARY("Library", Type.ROOM),
    LOUNGE("Lounge", Type.ROOM),
    ROPE("Rope",Type.WEAPON),
    KNIFE("Knife",Type.WEAPON),
    WRENCH("Wrench",Type.WEAPON),
    LEAD_PIPE("Lead Pipe",Type.WEAPON),
    CANDLESTICK("Candlestick",Type.WEAPON),
    REVOLVER("Revolver",Type.WEAPON),
    MR_GREEN("Mr. Green",Type.PERSON),
    MRS_PEACOCK("Mrs. Peacock",Type.PERSON),
    MRS_WHTE("Mrs. White",Type.PERSON),
    COL_MUSTARD("COl. Mustard",Type.PERSON),
    MISS_SCARLET("Miss Scarlet",Type.PERSON),
    PROF_PLUM("Professor Plum",Type.PERSON);

    private String name;
    private Type cardType;

    Card(String name, Type cardType){
        this.name = name;
        this.cardType = cardType;
    }

    public String getName(){
        return name;
    }

    public Type getType(){
        return cardType;
    }
}
