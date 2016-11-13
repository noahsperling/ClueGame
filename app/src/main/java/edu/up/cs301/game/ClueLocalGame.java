package edu.up.cs301.game;

import edu.up.cs301.game.actionMsg.ClueMoveUpAction;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Created by Noah on 10/25/2016.
 */

public class ClueLocalGame extends LocalGame {

    ClueState state = new ClueState();

    public boolean canMove(int playerID) {
        return true;
    }
    public boolean makeMove(GameAction g) {
        if(g instanceof ClueMoveUpAction) {

        }
        return true;
    }
    public boolean makeNonTurnAction() { //arguments and maybe just delete
        return true;
    }
    public void sendUpdatedStateTo(GamePlayer p) {

    }
    public String checkIfGameOver() {
        String winner = "";
        if(state.getGameOver() == false) {
            return null;
        }else {
            return "Game Over. " + winner +"has won the round.";
        }
    }
}
