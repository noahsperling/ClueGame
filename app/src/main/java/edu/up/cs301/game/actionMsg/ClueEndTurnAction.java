package edu.up.cs301.game.actionMsg;

import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;

/**
 * Created by Paige on 11/8/16.
 */
public class ClueEndTurnAction extends ClueMoveAction implements Serializable
{
    private static final long serialVersionUID = 122324325347645443L;

    public ClueEndTurnAction(GamePlayer player) {
        super(player);
    }
}
