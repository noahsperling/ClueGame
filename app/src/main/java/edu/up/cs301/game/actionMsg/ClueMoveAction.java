package edu.up.cs301.game.actionMsg;

import edu.up.cs301.game.ClueComputerPlayer;
import edu.up.cs301.game.ClueHumanPlayer;
import edu.up.cs301.game.CluePlayer;
import edu.up.cs301.game.GamePlayer;

/**
 * Created by Paige on 11/8/16.
 */
public class ClueMoveAction extends GameAction
{
    private static final long serialVersionUID = 30672017L;

    public int playerID;

    public ClueMoveAction(GamePlayer player)
    {
        super(player);
        if(player instanceof ClueHumanPlayer) {
            playerID = ((ClueHumanPlayer)player).getPlayerID();
        }else if(player instanceof ClueComputerPlayer) {
            playerID = ((ClueComputerPlayer)player).getPlayerID();
        }
    }
}
