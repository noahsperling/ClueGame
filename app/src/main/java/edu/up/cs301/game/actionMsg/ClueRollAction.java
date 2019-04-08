package edu.up.cs301.game.actionMsg;

import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;

/**
 * Created by Paige on 11/8/16.
 */
/*Sent by player to the ClueLocalGame to roll the die
 *Extends ClueMoveAction to be passed into makeMove() in the ClueLocalGame
 *Implements Serializable for network play
 */
public class ClueRollAction extends ClueMoveAction implements Serializable
{
    //Tag for logging
    private static final String TAG = "ClueRollAction";
    //Long for network play
    private static final long serialVersionUID = 3067643643762023L;

    public ClueRollAction(GamePlayer player)
    {
        super(player);
    }
}
