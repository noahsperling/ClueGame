package edu.up.cs301.game.actionMsg;

import edu.up.cs301.game.GamePlayer;

/**
 * Created by Eric Imperio on 11/8/2016.
 */

public class ClueWrittenNoteAction extends ClueNonTurnAction {
    private static final long serialVersionUID = 30672027L;

    public String note;

    public ClueWrittenNoteAction(GamePlayer player){
        super(player);
    }
}
