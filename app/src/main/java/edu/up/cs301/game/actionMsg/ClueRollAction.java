package edu.up.cs301.game.actionMsg;

import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;

/**
 * Created by Paige on 11/8/16.
 */
public class ClueRollAction extends ClueMoveAction implements Serializable
{
    private static final long serialVersionUID = 3067643643762023L;

    public ClueRollAction(GamePlayer player)
    {
        super(player);
    }
}
