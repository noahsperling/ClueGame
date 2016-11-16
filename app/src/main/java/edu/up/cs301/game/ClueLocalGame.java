package edu.up.cs301.game;

import edu.up.cs301.game.actionMsg.ClueMoveAction;
import edu.up.cs301.game.actionMsg.ClueMoveUpAction;
import edu.up.cs301.game.actionMsg.ClueNonTurnAction;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Created by Noah on 10/25/2016.
 */

public class ClueLocalGame extends LocalGame {

    ClueNonTurnAction nonTurnAction;
    ClueMoveAction moveAction;
    ClueState state;
    int numPlayers;

    public ClueLocalGame() {
        super();
    }

    @Override
    public void start(GamePlayer[] players){
        super.start(players);
        numPlayers = players.length;
        state = new ClueState(numPlayers, playerNames, 0); // needs arguments from startup
    }

    public boolean canMove(int playerID) {
        return true;
    } //always returns true

    @Override
    public boolean makeMove(GameAction a) {
        int[][] playBoard;

        if(a instanceof ClueMoveAction) {
            moveAction = (ClueMoveAction) a;
            if (moveAction instanceof ClueMoveUpAction)
            {
                int x = 0; //Create current position variables
                int y = 0;
                int curPlayerID = ((ClueMoveAction) a).playerID;
                playBoard = state.getPlayerBoard();
                for (int i = 0; i < 27; i++) //Find the current position of the player
                {
                    for (int j = 0; j< 27; j++)
                    {
                        if (playBoard[i][j] == curPlayerID)
                        {
                            //Set the current position of the player.
                            x = i;
                            y = j;
                        }
                    }
                }
                state.setPlayerBoard(x, y, x, y-1, curPlayerID); //Set the new position of the player
                //and set the old position to zero.
            }
            return true;
        }
        return false;

    }

    public boolean makeNonTurnAction(ClueNonTurnAction a) { //arguments and maybe just delete
        nonTurnAction = a;
        return true;
    }

    @Override
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

    @Override
    public String checkIfGameOver() {
        if(!state.getGameOver()) {
            return null;
        }else {
            return "Game over."; //this will be updated in the future with player names
        }
    }

}
