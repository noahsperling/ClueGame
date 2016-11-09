package edu.up.cs301.game.actionMsg;

import edu.up.cs301.game.Card;

/**
 * Created by Eric Imperio on 11/8/2016.
 */

public class ClueShowCardAction extends ClueNonTurnAction{
    public Card card;

    public ClueShowCardAction(int playerID){
        super(playerID);
    }
}
