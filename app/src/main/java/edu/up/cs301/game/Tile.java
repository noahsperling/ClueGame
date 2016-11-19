package edu.up.cs301.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

/**
 * Created by Eric Imperio on 11/8/2016.
 */

public class Tile {
    private int tileType; //0 = hallway, 1 = room
    private boolean isDoor;
    private boolean topWall = false;
    private boolean bottomWall = false;
    private boolean rightWall = false;
    private boolean leftWall = false;
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
    public void setTopWall(boolean wall){ this.topWall = wall; }
    public void setBottomWall(boolean wall){ bottomWall = wall; }
    public void setRightWall(boolean wall){ rightWall = wall; }
    public void setLeftWall(boolean wall){ leftWall = wall; }

    public void onDraw(Canvas c)
    {
        Paint p = new Paint();
        p.setColor(Color.argb(0,0,0,0));
        int adjustedX = location.x+((c.getWidth()-(27*Board.TILE_SIZE))/2);
        int adjustedY = location.y-(Board.TILE_SIZE/2);
        if(tileType == 0){
            p.setColor(Color.rgb(245,203,167));
            c.drawRect(adjustedX,adjustedY,adjustedX+Board.TILE_SIZE,adjustedY+Board.TILE_SIZE,p);
            p.setColor(Color.BLACK);
            c.drawLine(adjustedX,adjustedY,adjustedX,adjustedY+Board.TILE_SIZE,p);
            c.drawLine(adjustedX,adjustedY,adjustedX+Board.TILE_SIZE,adjustedY,p);
            c.drawLine(adjustedX,adjustedY+Board.TILE_SIZE,adjustedX+Board.TILE_SIZE,adjustedY+Board.TILE_SIZE,p);
            c.drawLine(adjustedX+Board.TILE_SIZE,adjustedY+Board.TILE_SIZE,adjustedX+Board.TILE_SIZE,adjustedY,p);

        }else if (tileType == 1){
            p.setColor(room.getColor());
            c.drawRect(adjustedX,adjustedY,adjustedX+Board.TILE_SIZE,adjustedY+Board.TILE_SIZE,p);
            p.setColor(Color.BLACK);
                if(topWall){//top
                    c.drawRect(adjustedX,adjustedY,adjustedX+Board.TILE_SIZE,adjustedY+(Board.TILE_SIZE/8),p);
                }

                if(rightWall){//right
                    c.drawRect(adjustedX+Board.TILE_SIZE-(Board.TILE_SIZE/8),adjustedY+Board.TILE_SIZE,adjustedX+Board.TILE_SIZE,adjustedY,p);
                }

                if(bottomWall){//bottom
                    c.drawRect(adjustedX,adjustedY+Board.TILE_SIZE-(Board.TILE_SIZE/8),adjustedX+Board.TILE_SIZE,adjustedY+Board.TILE_SIZE,p);
                }

                if(leftWall) { //left
                    c.drawRect(adjustedX, adjustedY, adjustedX + (Board.TILE_SIZE / 8), adjustedY + Board.TILE_SIZE, p);
                }
        }


    }
}
