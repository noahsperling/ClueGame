package edu.up.cs301.game.actionMsg;

import edu.up.cs301.game.Card;
import edu.up.cs301.game.GamePlayer;

/**
 * Created by Paige on 11/8/16.
 */
public class ClueSuggestionAction extends ClueMoveAction
{
    private static final long serialVersionUID = 30672025L;

    public ClueSuggestionAction(GamePlayer player)
    {
        super(player);
    }
    public String room;
    public String person;
    public String weapon;
}
