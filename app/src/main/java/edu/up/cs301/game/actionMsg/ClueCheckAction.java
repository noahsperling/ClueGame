package edu.up.cs301.game.actionMsg;

import edu.up.cs301.game.ClueComputerPlayer;
import edu.up.cs301.game.ClueHumanPlayer;
import edu.up.cs301.game.GamePlayer;

/**
 * Created by Eric Imperio on 11/8/2016.
 */

public class ClueCheckAction extends ClueNonTurnAction {
    public boolean[] checkbox;

    public ClueCheckAction(GamePlayer player){
        super(player);
        if(player instanceof ClueHumanPlayer) {
            checkbox =((ClueHumanPlayer)player).getCheckBoxArray();
        }else if(player instanceof ClueComputerPlayer) {
            checkbox =((ClueComputerPlayer)player).getCheckBoxArray();
        }
    }
    public boolean[] getCheckbox() {
        return checkbox;
    }
}
