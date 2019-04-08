package edu.up.cs301.game;

import java.io.Serializable;

/**
 * Created by Eric Imperio on 11/30/2016.
 */
/*Point class made for network play
 *Stores x,y
 *Used in Tile class to store their draw Location with respect to the first Tile on the board
 *Implements Serializable for network play*/
public class Point implements Serializable
{
    //Tag for logging
    private static final String TAG = "Point";

    public int x,y;
    //Long for network play
    private static final long serialVersionUID = 93257835098509335L;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
