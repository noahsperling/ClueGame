package edu.up.cs301.game;

import android.graphics.Canvas;
import android.graphics.Point;

/**
 * Created by Eric Imperio on 11/8/2016.
 */

public class Tile {
    private int tileType; //0 = hallway, 1 = room, 2 = door
    private boolean isDoor;
    private Card room; //A Room from the Card enum in which the tile belongs to.
    private Point location;
    public Tile(int tileType, boolean isDoor, Card room,Point location){
        this.tileType = tileType;
        this.isDoor = isDoor;
        this.room = room;
        this.location = location;
    }

    public int getTileType(){
        return tileType;
    }
    public boolean getIsDoor(){ return isDoor; }
    public Card getRoom()
    {
        return room;
    }

    public void onDraw(Canvas c){}
}
