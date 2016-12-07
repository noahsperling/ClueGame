package edu.up.cs301.game.actionMsg;

import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;

/**
 * Created by Paige on 11/8/16.
 */
public class ClueMoveDownAction extends ClueMoveAction implements Serializable
{
    private static final long serialVersionUID = 3063213768972018L;
    public ClueMoveDownAction(GamePlayer player)
    {
        super(player);
    }
}
