package edu.up.cs301.game;

import java.io.Serializable;

/**
 * Created by Eric Imperio on 11/8/2016.
 */
/*Used to know whether the card is a ROOM, WEAPON or PERSON
*Implements Serializable for network play*/
public enum Type implements Serializable {
    ROOM,
    WEAPON,
    PERSON;

    //Long used for network play
    private static final long serialVersionUID = 43586960432035435L;
    //Tag for logging
    private static final String TAG = "Type";
}
