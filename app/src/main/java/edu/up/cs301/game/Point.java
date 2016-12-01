package edu.up.cs301.game;

import java.io.Serializable;

/**
 * Created by Eric Imperio on 11/30/2016.
 */

public class Point implements Serializable {

    public int x,y;
    private static final long serialVersionUID = 93257835098509335L;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
