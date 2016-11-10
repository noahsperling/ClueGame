package edu.up.cs301.game.actionMsg;

import edu.up.cs301.game.CluePlayer;
import edu.up.cs301.game.GamePlayer;

/**
 * Created by Paige on 11/8/16.
 */
public class ClueMoveAction extends GameAction
{
    public int playerID;

    public ClueMoveAction(GamePlayer player)
    {
        super(player);
        playerID = (CluePlayer) player.getId();
    }
}
