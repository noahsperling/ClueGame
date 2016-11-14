package edu.up.cs301.game;

import edu.up.cs301.game.actionMsg.ClueMoveAction;
import edu.up.cs301.game.actionMsg.ClueMoveUpAction;
import edu.up.cs301.game.actionMsg.ClueNonTurnAction;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Created by Noah on 10/25/2016.
 */

public class ClueLocalGame extends LocalGame {

    public ClueLocalGame() {
        super();
    }
    int numPlayers = players.length;
    ClueState state = new ClueState(numPlayers, playerNames, 0); // needs arguments from startup

    ClueNonTurnAction nonTurnAction;
    ClueMoveAction moveAction;

    public boolean canMove(int playerID) {
        return true;
    } //always returns true

    public boolean makeMove(GameAction a) {

        if(a instanceof ClueMoveAction) {
            moveAction = (ClueMoveAction) a;
            return true;
        }
        return false;

    }

    public boolean makeNonTurnAction(ClueNonTurnAction a) { //arguments and maybe just delete
        nonTurnAction = a;
        return true;
    }

    public void sendUpdatedStateTo(GamePlayer p) {
        ClueState sendState = new ClueState(state);
        if(p instanceof ClueHumanPlayer) {
            ClueHumanPlayer player = (ClueHumanPlayer)p;
            int playerCount = sendState.getNumPlayers();
            if(player.getID() == 0) {
                for(int i = 0; i < playerCount; i++) {
                    if(i != player.getID()) {
                        sendState.setNotes(i, null);
                    }
                }
            }
        }
        p.sendInfo(new ClueState(state));
    }

    public String checkIfGameOver() {
        if(!state.getGameOver()) {
            return null;
        }else {
            return "Game over."; //this will be updated in the future with player names
        }
    }

}
