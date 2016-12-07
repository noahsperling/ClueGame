package edu.up.cs301.game.actionMsg;

import java.io.Serializable;

import edu.up.cs301.game.ClueHumanPlayer;
import edu.up.cs301.game.ComputerPlayerDumb;
import edu.up.cs301.game.ComputerPlayerSmart;
import edu.up.cs301.game.GamePlayer;

/**
 * Created by Eric Imperio on 11/8/2016.
 */

public class ClueNonTurnAction extends GameAction implements Serializable
{
    public int playerID;

    public ClueNonTurnAction(GamePlayer player)
    {
        super(player);
        if(player instanceof ClueHumanPlayer) {
            playerID = ((ClueHumanPlayer)player).getPlayerID();
        }else if (player instanceof ComputerPlayerDumb) {
            playerID = ((ComputerPlayerDumb)player).getPlayerID();
        }else if(player instanceof ComputerPlayerSmart) {
            playerID = ((ComputerPlayerSmart)player).getPlayerID();
        }
    }

}
