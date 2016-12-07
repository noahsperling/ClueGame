package edu.up.cs301.game.actionMsg;

import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;

/**
 * Created by Paige on 11/8/16.
 */
public class ClueMoveLeftAction extends ClueMoveAction implements Serializable
{
    private static final long serialVersionUID = 3067808087452020L;

    public ClueMoveLeftAction(GamePlayer player)
    {
        super(player); 
    }
}
