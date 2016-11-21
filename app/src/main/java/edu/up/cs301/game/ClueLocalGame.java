package edu.up.cs301.game;

import android.util.Log;

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

import static edu.up.cs301.game.Card.CONSERVATORY;
import static edu.up.cs301.game.Card.KITCHEN;
import static edu.up.cs301.game.Card.LOUNGE;
import static edu.up.cs301.game.Card.STUDY;
import static edu.up.cs301.game.Type.ROOM;
import static edu.up.cs301.game.Type.WEAPON;

/**
 * Created by Noah on 10/25/2016.
 */

public class ClueLocalGame extends LocalGame {

    ClueNonTurnAction nonTurnAction;
    ClueMoveAction moveAction;
    ClueState state;
    private Random rand;

    public ClueLocalGame(ArrayList<GamePlayerType> gamePlayerTypes) {
        super();
        String[] str = new String[gamePlayerTypes.size()];
        for(int i = 0; i<str.length;i++){
            str[i] = gamePlayerTypes.get(i).getTypeName();
        }
        state = new ClueState(gamePlayerTypes.size(),str, 0);
        rand = new Random();
    }

    //@Override
    //public void start(GamePlayer[] players){
        //super.start(players);
        //numPlayers = players.length;
        //state = new ClueState(numPlayers, playerNames, 0); // needs arguments from startup*/
    //}

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
            return false;
        }
    }

    @Override
    public boolean makeMove(GameAction a) {
        int[][] playBoard;
        Tile[][] curBoard;

        if(a instanceof ClueMoveAction) {
            moveAction = (ClueMoveAction)a;


            //Instance variables that will be used for some of the actions
            int x = 0; //Create current position variables
            int y = 0;
            int curPlayerID = ((ClueMoveAction) a).playerID; //The ID of the player who made the action
            //player = (CluePlayer)a.getPlayer();
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
            if (state.getTurnId() == curPlayerID)
            {
                if (moveAction instanceof ClueRollAction)
                {
                    //What to do if a certain action is made
                    if (state.getCanRoll(curPlayerID))
                    {
                        int numRolled = rand.nextInt(6) + 1; //Will produce a random number between 1 and 6.
                        state.setDieValue(numRolled);
                        state.setCanRoll(curPlayerID, false);
                        return true;

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

                        if(curBoard[x-1][y] != null) {
                            if (playBoard[x-1][y] == -1 && (curBoard[x-1][y].getTileType() == 0 || curBoard[x-1][y].getIsDoor() || curBoard[x-1][y].getTileType() == 1))
                            {
                                if (curBoard[x-1][y].getIsDoor() && curBoard[x][y].getTileType() == 0) //If the player is moving into a room
                                {
                                    state.getBoard().setPlayerBoard(x - 1, y, x , y, curPlayerID); //Set the new position of the player and set the old position to zero.
                                    state.setSpacesMoved(state.getSpacesMoved() + 1);
                                    state.setNewToRoom(curPlayerID, true); //Set the new to room in array to true.
                                    return true;
                                }
                                else if (curBoard[x][y].getTileType() == 1 && curBoard[x-1][y].getTileType() == 1) //If the player is in and will move within a room
                                {
                                    //If the player is moving around within a room, it will set their new position but will
                                    //not increment the spaces moved.
                                    state.getBoard().setPlayerBoard(x - 1, y, x, y, curPlayerID);
                                    return true;
                                }
                                else //Otherwise they're moving to a hallway.
                                {
                                    state.getBoard().setPlayerBoard(x - 1, y, x, y, curPlayerID); //Set the new position of the player and set the old position to zero.
                                    state.setSpacesMoved(state.getSpacesMoved() + 1);
                                    return true;
                                }
                            }
                        }else {
                            return false;
                        }
                    }
                    else if (moveAction instanceof ClueMoveDownAction)
                    {
                        if(curBoard[x+1][y] != null) {
                            if (playBoard[x+1][y] == -1 && (curBoard[x+1][y].getTileType() == 0 || curBoard[x+1][y].getIsDoor() || curBoard[x-1][y].getTileType() == 1))
                            {
                                if (curBoard[x+1][y].getIsDoor() && curBoard[x+1][y].getTileType() == 0)
                                {
                                    state.getBoard().setPlayerBoard(x + 1, y, x, y, curPlayerID);
                                    state.setSpacesMoved(state.getSpacesMoved() + 1);
                                    state.setNewToRoom(curPlayerID, true);
                                    return true;
                                }
                                else if (curBoard[x][y].getTileType() == 1 && curBoard[x+1][y].getTileType() == 1)
                                {
                                    state.getBoard().setPlayerBoard(x + 1, y, x, y, curPlayerID);
                                    return true;
                                }
                                else
                                {
                                    state.getBoard().setPlayerBoard(x + 1, y, x, y, curPlayerID);
                                    state.setSpacesMoved(state.getSpacesMoved() + 1);
                                    return true;
                                }
                            }
                        }else {
                            return false;
                        }
                    }
                    else if (moveAction instanceof ClueMoveRightAction)
                    {
                        if(curBoard[x][y+1] != null) {
                            if(playBoard[x][y+1] == -1 && (curBoard[x][y+1].getTileType() == 0 || curBoard[x][y+1].getIsDoor() || curBoard[x-1][y].getTileType() == 1))
                            {
                                if (curBoard[x][y+1].getIsDoor() && curBoard[x][y].getTileType() == 0)
                                {
                                    state.getBoard().setPlayerBoard(x, y + 1, x, y, curPlayerID);
                                    state.setSpacesMoved(state.getSpacesMoved() + 1);
                                    state.setNewToRoom(curPlayerID, true);
                                    return true;
                                }
                                else if (curBoard[x][y].getTileType() == 1 && curBoard[x][y+1].getTileType() == 1)
                                {
                                    state.getBoard().setPlayerBoard(x, y + 1, x, y, curPlayerID);
                                    return true;
                                }
                                else
                                {
                                    state.getBoard().setPlayerBoard(x, y + 1, x, y, curPlayerID);
                                    state.setSpacesMoved(state.getSpacesMoved() + 1);
                                    return true;
                                }
                            }
                        }else {
                            return false;
                        }
                    }
                    else if (moveAction instanceof ClueMoveLeftAction)
                    {
                        if(curBoard[x][y-1] != null) {
                            if (playBoard[x][y-1] == -1 && (curBoard[x][y-1].getTileType() == 0 || curBoard[x][y-1].getIsDoor() || curBoard[x-1][y].getTileType() == 1))
                            {
                                if (curBoard[x][y-1].getIsDoor() && curBoard[x][y].getTileType() == 0)
                                {
                                    state.getBoard().setPlayerBoard(x, y - 1, x, y, curPlayerID);
                                    state.setSpacesMoved(state.getSpacesMoved() + 1);
                                    return true;
                                }
                                else if (curBoard[x][y].getTileType() == 1 && curBoard[x][y-1].getTileType() == 1)
                                {
                                    state.getBoard().setPlayerBoard(x, y - 1, x, y, curPlayerID);
                                    return true;
                                }
                                else
                                {
                                    state.getBoard().setPlayerBoard(x, y - 1, x, y, curPlayerID);
                                    state.setSpacesMoved(state.getSpacesMoved() + 1);
                                    state.setNewToRoom(curPlayerID, true);
                                    return true;
                                }
                            }
                        }else {
                            return false;
                        }
                    }
                }
                else if (moveAction instanceof ClueAccuseAction)
                {
                    boolean solved = true;
                    Card solution[] = state.getSolution();
                    ClueAccuseAction moveActionAcc = (ClueAccuseAction)moveAction;

                    //Check to see if the players guess matches the solution
                    for (int i = 0; i < 3; i++)
                    {
                        if (solution[i].getType() == ROOM)
                        {
                            if (solution[i].getName() != moveActionAcc.room)
                            {
                                solved = false;
                            }
                        }
                        else if (solution[i].getType() == WEAPON)
                        {
                            if (solution[i].getName() != moveActionAcc.weapon)
                            {
                                solved = false;
                            }
                        }
                        else
                        {
                            if (solution[i].getName() != moveActionAcc.suspect)
                            {
                                solved = false;
                            }
                        }
                    }

                    if (!solved)
                    {
                        //End the game for that player.

                    }
                    
                }
                else if (moveAction instanceof ClueSuggestionAction)
                {

                }
                else if (moveAction instanceof ClueUsePassagewayAction)
                {
                    //These if statements will see where the player is at and will move them to the corner room
                    //diagonal to them.
                    if (curBoard[x][y].getRoom() == LOUNGE)
                    {
                        state.getBoard().setPlayerBoard(x, y, 22, 2, curPlayerID); //Move Player to conservatory
                        return true;
                    }
                    else if (curBoard[x][y].getRoom() == CONSERVATORY)
                    {
                        state.getBoard().setPlayerBoard(x, y, 2, 20, curPlayerID); //Move Player to lounge
                        return true;
                    }
                    else if (curBoard[x][y].getRoom() == STUDY)
                    {
                        state.getBoard().setPlayerBoard(x, y, 22, 22, curPlayerID); //Move Player to kitchen
                        return true;
                    }
                    else if (curBoard[x][y].getRoom() == KITCHEN)
                    {
                        state.getBoard().setPlayerBoard(x, y, 3, 4, curPlayerID); //Move Player to the study
                        return true;
                    }
                }
                else if (moveAction instanceof ClueEndTurnAction)
                {
                    //Change the turnID to the next player and lets the next player roll
                    state.setNewToRoom(curPlayerID, false); //Once they've ended their turn, they are no longer new to a room.
                    state.setCanRoll(curPlayerID, false);
                    if (state.getTurnId() == (state.getNumPlayers() - 1))
                    {
                        state.setCanRoll(0, true);
                        state.setTurnID(0);
                        state.setSpacesMoved(0);
                        state.setDieValue(0);
                        return true;
                    }
                    else
                    {
                        state.setCanRoll(state.getTurnId() + 1, true);
                        state.setTurnID(state.getTurnId() + 1);
                        state.setSpacesMoved(0);
                        state.setDieValue(0);
                        return true;

                    }
                }
            }
        }
        else //If it's not a move action
        {
            return makeNonTurnAction((ClueNonTurnAction)a);
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
            int index = ((ClueCheckAction)a).playerID;
            for(int i = 0; i < ((ClueCheckAction)a).getCheckbox().length; i++) {
                state.setCheckBox(index, i, ((ClueCheckAction)a).getCheckbox()[i]);
            }
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
                        sendState.setCards(i, null);
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

    /* not necessary but still here in case
    private boolean checkTileValid(int x, int y, Board b) {
        if(b.getBoardArr()[x][y] == null) {
            return false;
        }if(b.getBoardArr()[x][y].getTileType() == 0) {
            return true;
        }else if(b.getBoardArr()[x][y].getTileType() == 1 && b.getBoardArr()[x][y].getIsDoor()) {
            return true;
        }
        return false;
    }

    private boolean checkIntValid(int x, int y, int[][] b) {
        if(b[x][y] == -1) {
            return true;
        }else {
            return false;
        }
    }
    */

}
