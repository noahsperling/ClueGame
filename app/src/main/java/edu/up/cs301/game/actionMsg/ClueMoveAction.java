package edu.up.cs301.game.actionMsg;

import java.io.Serializable;

import edu.up.cs301.game.ClueHumanPlayer;
import edu.up.cs301.game.CluePlayer;
import edu.up.cs301.game.ComputerPlayerDumb;
import edu.up.cs301.game.ComputerPlayerSmart;
import edu.up.cs301.game.GamePlayer;

/**
 * Created by Paige on 11/8/16.
 */
/*Sent by player to the ClueLocalGame to move down up the board
 * Contains information about the playerID of the player who sent this action
 *Extends GameAction to be passed into makeMove() in the LocalGame
 *Implements Serializable for network play
 */
public class ClueMoveAction extends GameAction implements Serializable
{
    //Long for network play
    private static final long serialVersionUID = 3067264564645017L;

    //Player who is sending the ClueMoveAction
    public int playerID;

    public ClueMoveAction(GamePlayer player)
    {
        super(player);

        //Sets the playerID to the ID of the player who sent the action
        if(player instanceof CluePlayer)
        {
            playerID = ((CluePlayer) player).getPlayerID();
        }
    }
}
