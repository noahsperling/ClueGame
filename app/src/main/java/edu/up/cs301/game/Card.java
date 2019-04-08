package edu.up.cs301.game;

import java.io.Serializable;

/**
 * Created by Eric Imperio on 11/8/2016.
 */
//Enum that stores information about the cards
//Implements Serializable for network play
public enum Card implements Serializable
{
    //enums
    //NOTE: Name of Card, Type of Card: Room,Weapon,Person, Color of Card (for drawing)
    HALL("Hall", Type.ROOM, Color.rgb(247,220,111), 0), //yellow
    BILLIARD_ROOM("Billiard Room", Type.ROOM, Color.rgb(39,174,96), 1), //green
    BALLROOM("Ballroom", Type.ROOM, Color.rgb(220,118,51), 2), //brown
    DINING_ROOM("Dining Room", Type.ROOM, Color.rgb(44,55,30), 3),//brown
    STUDY("Study", Type.ROOM, Color.rgb(231,76,60), 4), //red
    CONSERVATORY("Conservatory", Type.ROOM, Color.rgb(60,100,140), 5), //purple
    LIBRARY("Library", Type.ROOM, Color.rgb(133,146,158), 6), //grey
    LOUNGE("Lounge", Type.ROOM, Color.rgb(133,153,233), 7), //blue
    KITCHEN("Kitchen", Type.ROOM, Color.rgb(133,193,133), 8), //green
    ROPE("Rope",Type.WEAPON, Color.WHITE, 9), //white
    KNIFE("Knife",Type.WEAPON, Color.GRAY, 10), //gray
    WRENCH("Wrench",Type.WEAPON, Color.DKGRAY, 11), //dark gray
    LEAD_PIPE("Lead Pipe",Type.WEAPON, Color.LTGRAY, 12), //light gray
    CANDLESTICK("Candlestick",Type.WEAPON, Color.rgb(247,220,111), 13), //golden
    REVOLVER("Revolver",Type.WEAPON, Color.rgb(160,64,0), 14), //dark brown
    MR_GREEN("Mr. Green",Type.PERSON, Color.GREEN, 15), //green
    MRS_PEACOCK("Mrs. Peacock",Type.PERSON, Color.BLUE, 16), //blue
    MRS_WHITE("Mrs. White",Type.PERSON, Color.WHITE, 17), //white
    COL_MUSTARD("Col. Mustard",Type.PERSON, Color.YELLOW, 18), //yellow
    MISS_SCARLET("Miss Scarlet",Type.PERSON, Color.RED, 19), //red
    PROF_PLUM("Prof. Plum",Type.PERSON, Color.rgb(142,68,173), 20); //purple

    private String name; //Name of Card
    private Type cardType; //Type of Card: Room,Weapon,Person
    private int color; //Color to draw the Card when visible in ClueBoardView
    private int playerID;
    private static final long serialVersionUID = 45093509329032850L; //Long for network play
    //Tag for logging
    private static final String TAG = "Card";

    Card(String name, Type cardType, int color, int playerID)
    {
        this.name = name;
        this.cardType = cardType;
        this.color = color;
        this.playerID = playerID;
    }

    //Gets name of Card
    public String getName()
    {
        return name;
    }

    //Gets type of Card
    public Type getType()
    {
        return cardType;
    }

    //Gets color of Card
    public int getColor()
    {
        return color;
    }

    public int getPlayerID() {return this.playerID;}
}
