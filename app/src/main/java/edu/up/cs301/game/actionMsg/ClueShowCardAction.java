package edu.up.cs301.game.actionMsg;

import edu.up.cs301.game.Card;
import edu.up.cs301.game.GamePlayer;

/**
 * Created by Eric Imperio on 11/8/2016.
 */

public class ClueShowCardAction extends ClueNonTurnAction{
    private static final long serialVersionUID = 30672024L;

    public String room;
    public String suspect;
    public String weapon;

    public ClueShowCardAction(GamePlayer player){
        super(player);
    }
}
