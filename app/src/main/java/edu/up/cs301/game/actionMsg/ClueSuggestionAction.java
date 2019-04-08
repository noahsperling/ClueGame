package edu.up.cs301.game.actionMsg;

import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;

/**
 * Created by Paige on 11/8/16.
 */
/*Sent by player to the ClueLocalGame to make an Suggestion about:
 *  who committed the murder (suspect)
 *  Where the murder was committed (room)
 *  And With what the murder was committed (weapon)
 *Extends ClueMoveAction to be passed into makeMove() in the ClueLocalGame
 *Implements Serializable for network play
 */
public class ClueSuggestionAction extends ClueMoveAction implements Serializable
{
    //Tag for logging
    private static final String TAG = "ClueSuggestionAction";
    //Long for network play
    private static final long serialVersionUID = 306720547876925L;

    //Note: These are made strings because the Spinners store strings
    public String room; //Name of Room the player is asking if the murder was committed in
    public String suspect; //Name of Suspect player is asking if committed the murder
    public String weapon; //Name of Weapon the player is asking if the murder was committed with

    public ClueSuggestionAction(GamePlayer player)
    {
        super(player);
    }
}
