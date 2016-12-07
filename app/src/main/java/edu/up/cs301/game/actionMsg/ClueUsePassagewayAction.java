package edu.up.cs301.game.actionMsg;

import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;

/**
 * Created by Paige on 11/8/16.
 */
public class ClueUsePassagewayAction extends ClueMoveAction implements Serializable
{
    private static final long serialVersionUID = 3067245345636026L;

    public ClueUsePassagewayAction(GamePlayer player)
    {
        super(player);
    }
}
