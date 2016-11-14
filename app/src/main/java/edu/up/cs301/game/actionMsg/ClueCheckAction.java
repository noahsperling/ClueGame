package edu.up.cs301.game.actionMsg;

import edu.up.cs301.game.GamePlayer;

/**
 * Created by Eric Imperio on 11/8/2016.
 */

public class ClueCheckAction extends ClueNonTurnAction {
    public boolean[] checkbox;

    public ClueCheckAction(GamePlayer player){
        super(player);
    }
}
