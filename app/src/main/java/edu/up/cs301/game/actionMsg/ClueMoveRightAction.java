package edu.up.cs301.game.actionMsg;

import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;

/**
 * Created by Paige on 11/8/16.
 */
public class ClueMoveRightAction extends ClueMoveAction implements Serializable
{
    private static final long serialVersionUID = 306432453772021L;

    public ClueMoveRightAction(GamePlayer player)
    {
        super(player);
    }
}
