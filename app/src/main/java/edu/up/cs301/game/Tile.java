package edu.up.cs301.game;

import java.io.Serializable;
import edu.up.cs301.game.util.Logger;

/**
 * Created by Eric Imperio on 11/8/2016.
 */
/*Stores all the information refering to a tile on the board
*Implements Serializable for network play*/
public class Tile implements Serializable
{
    //Tag for logging
    private static final String TAG = "Tile";
    //Long used for network play
    private static final long serialVersionUID = 354323253259789895L;
    private int tileType; //0 = hallway, 1 = room
    private boolean isDoor = false;
    //If there is set the wall variable for that side to true
    private boolean topWall = false;
    private boolean bottomWall = false;
    private boolean rightWall = false;
    private boolean leftWall = false;
    private Card room; //A Room from the Card enum in which the tile belongs to.
    private Point location; //Location to draw the Tile (takes in account the i and j value of the tile as well as the TILE_SIZE of the tiles, stored with respect to the first Tile on the board

    public Tile(int tileType, boolean isDoor, Card room,Point location)
    {
        this.tileType = tileType;
        this.isDoor = isDoor;
        this.room = room;
        this.location = location;
    }

    public int getTileType()
    {
        return tileType;
    }

    public boolean getIsDoor()
    {
        return isDoor;
    }

    public Card getRoom()
    {
        return room;
    }

    public boolean getLeftWall()
    {
        return leftWall;
    }

    public boolean getRightWall()
    {
        return rightWall;
    }

    public boolean getTopWall()
    {
        return topWall;
    }

    public boolean getBottomWall()
    {
        return bottomWall;
    }

    public void setIsDoor(boolean isDoor)
    {
        this.isDoor = isDoor;
    }

    public void setTopWall(boolean wall)
    {
        this.topWall = wall;
    }

    public void setBottomWall(boolean wall)
    {
        bottomWall = wall;
    }

    public void setRightWall(boolean wall)
    {
        rightWall = wall;
    }

    public void setLeftWall(boolean wall)
    {
        leftWall = wall;
    }

    public Point getLocation()
    {
        return location;
    }
}
