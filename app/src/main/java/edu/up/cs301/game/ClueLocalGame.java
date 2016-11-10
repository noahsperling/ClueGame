package edu.up.cs301.game;

import edu.up.cs301.game.actionMsg.ClueMoveAction;
import edu.up.cs301.game.actionMsg.ClueNonTurnAction;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Created by Noah on 10/25/2016.
 */

public class ClueLocalGame extends LocalGame {

    ClueState state = new ClueState();

    ClueNonTurnAction c;

    public boolean canMove(int playerID) {
        return true;
    } //always returns true

    public boolean makeMove(GameAction a) {
        if(!(a instanceof ClueMoveAction)) {
            return false;
        }
        return true;
    }

    public boolean makeNonTurnAction(ClueNonTurnAction a) { //arguments and maybe just delete
        return true;
    }

    public void sendUpdatedStateTo(GamePlayer p) {

    }

    public String checkIfGameOver() {
        if(!state.getGameOver()) {
            return null;
        }else {
            return "Game over."; //this will be updated in the future with player names
        }
    }

}
