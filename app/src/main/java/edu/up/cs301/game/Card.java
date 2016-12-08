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
    HALL("Hall", Type.ROOM, Color.rgb(247,220,111)), //yellow
    BILLIARD_ROOM("Billiard Room", Type.ROOM, Color.rgb(39,174,96)), //green
    BALLROOM("Ballroom", Type.ROOM, Color.rgb(220,118,51)), //brown
    DINING_ROOM("Dining Room", Type.ROOM, Color.rgb(44,55,30)),//brown
    STUDY("Study", Type.ROOM, Color.rgb(231,76,60)), //red
    CONSERVATORY("Conservatory", Type.ROOM, Color.rgb(60,100,140)), //purple
    LIBRARY("Library", Type.ROOM, Color.rgb(133,146,158)), //grey
    LOUNGE("Lounge", Type.ROOM, Color.rgb(133,153,233)), //blue
    KITCHEN("Kitchen", Type.ROOM, Color.rgb(133,193,133)), //green
    ROPE("Rope",Type.WEAPON, Color.WHITE), //white
    KNIFE("Knife",Type.WEAPON, Color.GRAY), //gray
    WRENCH("Wrench",Type.WEAPON, Color.DKGRAY), //dark gray
    LEAD_PIPE("Lead Pipe",Type.WEAPON, Color.LTGRAY), //light gray
    CANDLESTICK("Candlestick",Type.WEAPON, Color.rgb(247,220,111)), //golden
    REVOLVER("Revolver",Type.WEAPON, Color.rgb(160,64,0)), //dark brown
    MR_GREEN("Mr. Green",Type.PERSON, Color.GREEN), //green
    MRS_PEACOCK("Mrs. Peacock",Type.PERSON, Color.BLUE), //blue
    MRS_WHITE("Mrs. White",Type.PERSON, Color.WHITE), //white
    COL_MUSTARD("Col. Mustard",Type.PERSON, Color.YELLOW), //yellow
    MISS_SCARLET("Miss Scarlet",Type.PERSON, Color.RED), //red
    PROF_PLUM("Prof. Plum",Type.PERSON, Color.rgb(142,68,173)); //purple

    private String name; //Name of Card
    private Type cardType; //Type of Card: Room,Weapon,Person
    private int color; //Color to draw the Card when visible in ClueBoardView
    private static final long serialVersionUID = 45093509329032850L; //Long for network play

    Card(String name, Type cardType, int color)
    {
        this.name = name;
        this.cardType = cardType;
        this.color = color;
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
}
