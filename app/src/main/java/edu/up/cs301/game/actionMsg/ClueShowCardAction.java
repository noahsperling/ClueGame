package edu.up.cs301.game.actionMsg;

import edu.up.cs301.game.Card;
import edu.up.cs301.game.GamePlayer;

/**
 * Created by Eric Imperio on 11/8/2016.
 */

public class ClueShowCardAction extends ClueNonTurnAction{
    private static final long serialVersionUID = 30672024L;

    private String cardToShow;

    public String getCardToShow() {
        return cardToShow;
    }

    public void setCardToShow(String newCard) {
        cardToShow = newCard;
    }


    public ClueShowCardAction(GamePlayer player){
        super(player);
        cardToShow = null;
    }
}
