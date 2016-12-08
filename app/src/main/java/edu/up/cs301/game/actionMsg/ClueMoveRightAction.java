package edu.up.cs301.game.actionMsg;

import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;

/**
 * Created by Paige on 11/8/16.
 */
/*Sent by player to the ClueLocalGame to move right on the board
 *Extends ClueMoveAction to be passed into makeMove() in the ClueLocalGame
 *Implements Serializable for network play
 */
public class ClueMoveRightAction extends ClueMoveAction implements Serializable
{
    //Long for network play
    private static final long serialVersionUID = 306432453772021L;

    public ClueMoveRightAction(GamePlayer player)
    {
        super(player);
    }
}
