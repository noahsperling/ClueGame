package edu.up.cs301.game.actionMsg;

import java.io.Serializable;

import edu.up.cs301.game.ClueHumanPlayer;
import edu.up.cs301.game.ComputerPlayerDumb;
import edu.up.cs301.game.ComputerPlayerSmart;
import edu.up.cs301.game.GamePlayer;

/**
 * Created by Paige on 11/8/16.
 */
public class ClueMoveAction extends GameAction implements Serializable
{
    private static final long serialVersionUID = 3067264564645017L;

    public int playerID;

    public ClueMoveAction(GamePlayer player)
    {
        super(player);
        if(player instanceof ClueHumanPlayer) {
            playerID = ((ClueHumanPlayer)player).getPlayerID();
        }else if(player instanceof ComputerPlayerDumb) {
            playerID = ((ComputerPlayerDumb)player).getPlayerID();
        }else if(player instanceof ComputerPlayerSmart) {
            playerID = ((ComputerPlayerSmart)player).getPlayerID();
        }
    }
}
