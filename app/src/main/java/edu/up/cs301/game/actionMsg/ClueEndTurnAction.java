package edu.up.cs301.game.actionMsg;

import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;

/**
 * Created by Paige on 11/8/16.
 */
/*Sent by player to the ClueLocalGame to end their turn
 *Extends ClueMoveAction to be passed into makeMove() in the ClueLocalGame
 *Implements Serializable for network play
 */
public class ClueEndTurnAction extends ClueMoveAction implements Serializable
{
    //Tag for logging
    private static final String TAG = "ClueEndTurnAction";
    //Long for network play
    private static final long serialVersionUID = 122324325347645443L;

    public ClueEndTurnAction(GamePlayer player) {
        super(player);
    }
}
