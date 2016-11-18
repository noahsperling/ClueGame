package edu.up.cs301.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by Eric Imperio on 11/8/2016.
 */

public class Tile {
    private int tileType; //0 = hallway, 1 = room
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
    public void setIsDoor(boolean isDoor){ this.isDoor = isDoor; }

    public void onDraw(Canvas c)
    {
        Paint p = new Paint();
        p.setColor(Color.argb(0,0,0,0));
        if(tileType == 0){
            p.setColor(Color.rgb(245,203,167));
        }else if (tileType == 1){
            p.setColor(room.getColor());
        }
        int adjustedX = location.x+((c.getWidth()-(27*Board.TILE_SIZE))/2);
        int adjustedY = location.y-(Board.TILE_SIZE/2);
        c.drawRect(adjustedX,adjustedY,adjustedX+Board.TILE_SIZE,adjustedY+Board.TILE_SIZE,p);
    }
}
