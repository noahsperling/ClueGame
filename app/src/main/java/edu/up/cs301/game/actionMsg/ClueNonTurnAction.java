package edu.up.cs301.game.actionMsg;

import edu.up.cs301.game.CluePlayer;
import edu.up.cs301.game.GamePlayer;

/**
 * Created by Eric Imperio on 11/8/2016.
 */

public class ClueNonTurnAction extends GameAction
{
    public int playerID;

    public ClueNonTurnAction(GamePlayer player)
    {
        super(player);
        playerID = ((CluePlayer) player).getPlayerId();
    }

}
