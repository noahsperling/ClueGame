package edu.up.cs301.game.actionMsg;

import edu.up.cs301.game.Card;
import edu.up.cs301.game.GamePlayer;

/**
 * Created by Paige on 11/8/16.
 */
public class ClueSuggestionAction extends ClueMoveAction
{
    public ClueSuggestionAction(GamePlayer player)
    {
        super(player);
    }
    public Card room;
    public Card person;
    public Card weapon;
}
