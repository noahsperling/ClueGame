package edu.up.cs301.game.actionMsg;

import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;

/**
 * Created by Paige on 11/8/16.
 */
/*Sent by player to the ClueLocalGame to move left on the board
 *Extends ClueMoveAction to be passed into makeMove() in the ClueLocalGame
 *Implements Serializable for network play
 */
public class ClueMoveLeftAction extends ClueMoveAction implements Serializable
{
    //Tag for logging
    private static final String TAG = "ClueMoveLeftAction";
    //Long for network play
    private static final long serialVersionUID = 3067808087452020L;

    public ClueMoveLeftAction(GamePlayer player)
    {
        super(player); 
    }
}
