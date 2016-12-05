package edu.up.cs301.game.actionMsg;

import edu.up.cs301.game.ClueHumanPlayer;
import edu.up.cs301.game.ComputerPlayerDumb;
import edu.up.cs301.game.ComputerPlayerSmart;
import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.GamePlayer;

/**
 * Created by Eric Imperio on 11/8/2016.
 */

public class ClueCheckAction extends ClueNonTurnAction {
    private static final long serialVersionUID = 30672015L;

    public boolean[] checkbox;

    public ClueCheckAction(GamePlayer player){
        super(player);
        if(player instanceof ClueHumanPlayer) {
            checkbox =((ClueHumanPlayer)player).getCheckBoxArray();
        }else if(player instanceof ComputerPlayerDumb) {
            checkbox =((ComputerPlayerDumb)player).getCheckBoxArray();
        }else if(player instanceof ComputerPlayerSmart){

        }
    }
    public boolean[] getCheckbox() {
        return checkbox;
    }
}
