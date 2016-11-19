package edu.up.cs301.game;

import java.util.ArrayList;

import java.util.Random;

import edu.up.cs301.game.actionMsg.ClueAccuseAction;
import edu.up.cs301.game.actionMsg.ClueCheckAction;
import edu.up.cs301.game.actionMsg.ClueEndTurnAction;
import edu.up.cs301.game.actionMsg.ClueMoveAction;
import edu.up.cs301.game.actionMsg.ClueMoveDownAction;
import edu.up.cs301.game.actionMsg.ClueMoveLeftAction;
import edu.up.cs301.game.actionMsg.ClueMoveRightAction;
import edu.up.cs301.game.actionMsg.ClueMoveUpAction;
import edu.up.cs301.game.actionMsg.ClueNonTurnAction;
import edu.up.cs301.game.actionMsg.ClueRollAction;
import edu.up.cs301.game.actionMsg.ClueShowCardAction;
import edu.up.cs301.game.actionMsg.ClueSuggestionAction;
import edu.up.cs301.game.actionMsg.ClueUsePassagewayAction;
import edu.up.cs301.game.actionMsg.ClueWrittenNoteAction;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.config.GamePlayerType;

/**
 * Created by Noah on 10/25/2016.
 */

public class ClueLocalGame extends LocalGame {

    ClueNonTurnAction nonTurnAction;
    ClueMoveAction moveAction;
    ClueState state;
    int numPlayers;
    private Random rand;

    public ClueLocalGame(ArrayList<GamePlayerType> gamePlayerTypes) {
        super();
        String[] str = new String[gamePlayerTypes.size()];
        for(int i = 0; i<str.length;i++){
            str[i] = gamePlayerTypes.get(i).getTypeName();
        }
        state = new ClueState(gamePlayerTypes.size(),str, 0);
    }

    @Override
    public void start(GamePlayer[] players){
        super.start(players);/*
        numPlayers = players.length;
        state = new ClueState(numPlayers, playerNames, 0); // needs arguments from startup*/
    }

    public boolean canMove(int playerID) {
        return true;
    } //always returns true

    public boolean movePlayer(int playerID)
    {
        if (state.getSpacesMoved() < state.getDieValue())
        {
           return true;
        }
        else
        {
            //The players have moved the max number of times and their turn will be ended.
            if (state.getTurnId() == 5)
            {
                state.setTurnID(0);
            }
            else
            {
                state.setTurnID(state.getTurnId() + 1);
            }
            return false;
        }
    }

    @Override
    public boolean makeMove(GameAction a) {
        int[][] playBoard;
        Tile[][] curBoard;

        if(a instanceof ClueMoveAction) {
            moveAction = (ClueMoveAction) a;


            //Instance variables that will be used for some of the actions
            int x = 0; //Create current position variables
            int y = 0;
            int curPlayerID = ((ClueMoveAction) a).playerID; //The ID of the player who made the action
            CluePlayer player = (CluePlayer)a.getPlayer();
            playBoard = state.getBoard().getPlayerBoard(); //Get the current player board so we know where all the players are
            curBoard = (state.getBoard()).getBoardArr(); //Get the current board w/tiles

            //Set x and y:
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

            //Check to make sure it's actually the player's turn
            if (state.getTurnId() == curPlayerID) {

                if (state.getCanRoll(curPlayerID))
                {
                    //What to do if a certain action is made
                    if (moveAction instanceof ClueRollAction)
                    {
                        int numRolled = rand.nextInt(6) + 1; //Will produce a random number between 1 and 6.
                        state.setDieValue(numRolled);
                        state.setCanRoll(curPlayerID, false);

                        //Set roll button to disabled here?  or maybe do that when it is pressed?
                    }
                }
                else if (movePlayer(curPlayerID))
                {
                    if (moveAction instanceof ClueMoveUpAction)
                    {
                        //Makes sure the player's next move would not be to another player's current
                        //position on the board.  Also checks to make sure their next position is in the hallway
                        //or through a door.
                        if (playBoard[x][y-1] == -1 && (curBoard[x][y-1].getTileType() == 0 || curBoard[x][y-1].getIsDoor()))
                        {
                            state.getBoard().setPlayerBoard(x, y, x, y - 1, curPlayerID); //Set the new position of the player and set the old position to zero.
                            state.setSpacesMoved(state.getSpacesMoved() + 1);
                        }
                    }
                    else if (moveAction instanceof ClueMoveDownAction)
                    {
                        if (playBoard[x][y+1] == -1 && (curBoard[x][y+1].getTileType() == 0 || curBoard[x][y+1].getIsDoor()))
                        {
                            state.getBoard().setPlayerBoard(x, y, x, y + 1, curPlayerID);
                            state.setSpacesMoved(state.getSpacesMoved() + 1);
                        }
                    }
                    else if (moveAction instanceof ClueMoveRightAction)
                    {
                        if (playBoard[x+1][y] == -1 && (curBoard[x+1][y].getTileType() == 0 || curBoard[x+1][y].getIsDoor()))
                        {
                            state.getBoard().setPlayerBoard(x, y, x + 1, y, curPlayerID);
                            state.setSpacesMoved(state.getSpacesMoved() + 1);
                        }
                    }
                    else if (moveAction instanceof ClueMoveLeftAction)
                    {
                        if (playBoard[x-1][y] == -1 && (curBoard[x-1][y].getTileType() == 0 || curBoard[x-1][y].getIsDoor()))
                        {
                            state.getBoard().setPlayerBoard(x, y, x - 1, y, curPlayerID);
                            state.setSpacesMoved(state.getSpacesMoved() + 1);
                        }
                    }
                }
                else if (moveAction instanceof ClueAccuseAction)
                {
                    if(state.getTurnId() == moveAction.playerID) {

                    }else {
                        return false;
                    }
                }
                else if (moveAction instanceof ClueSuggestionAction)
                {

                }
                else if (moveAction instanceof ClueUsePassagewayAction)
                {

                }
                else if (moveAction instanceof ClueEndTurnAction)
                {
                    //Change the turnID to the next player and lets the next player roll
                    if (state.getTurnId() == (state.getNumPlayers() - 1))
                    {
                        state.setCanRoll(0, true);
                        state.setTurnID(0);
                    }
                    else
                    {
                        state.setCanRoll(state.getTurnId() + 1, true);
                        state.setTurnID(state.getTurnId() + 1);
                    }
                }
            }
            return true;
        }
        else
        {
            makeNonTurnAction((ClueNonTurnAction)a);
        }
        return false;

    }

    public boolean makeNonTurnAction(ClueNonTurnAction a)
    { //arguments and maybe just delete
        nonTurnAction = a;

        if (nonTurnAction instanceof ClueWrittenNoteAction)
        {

        }
        else if (nonTurnAction instanceof ClueCheckAction)
        {
            return true;
        }
        else if (nonTurnAction instanceof ClueShowCardAction)
        {

        }
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
