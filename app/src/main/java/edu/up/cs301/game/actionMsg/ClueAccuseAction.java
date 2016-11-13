package edu.up.cs301.game.actionMsg;

import edu.up.cs301.game.Card;
import edu.up.cs301.game.GamePlayer;

/**
 * Created by Paige on 11/8/16.
 */
public class ClueAccuseAction extends ClueMoveAction
{
    public Card room;
    public Card person;
    public Card weapon;

    public ClueAccuseAction(GamePlayer player)
    {
        super(player);
    }
}