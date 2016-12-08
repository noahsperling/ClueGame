package edu.up.cs301.game;

import android.util.Log;

import java.util.Random;

import edu.up.cs301.game.actionMsg.ClueAccuseAction;
import edu.up.cs301.game.actionMsg.ClueEndTurnAction;
import edu.up.cs301.game.actionMsg.ClueMoveAction;
import edu.up.cs301.game.actionMsg.ClueMoveDownAction;
import edu.up.cs301.game.actionMsg.ClueMoveLeftAction;
import edu.up.cs301.game.actionMsg.ClueMoveRightAction;
import edu.up.cs301.game.actionMsg.ClueMoveUpAction;
import edu.up.cs301.game.actionMsg.ClueRollAction;
import edu.up.cs301.game.actionMsg.ClueShowCardAction;
import edu.up.cs301.game.actionMsg.ClueSuggestionAction;
import edu.up.cs301.game.actionMsg.ClueUsePassagewayAction;
import edu.up.cs301.game.actionMsg.GameAction;

import static edu.up.cs301.game.Card.COL_MUSTARD;
import static edu.up.cs301.game.Card.CONSERVATORY;
import static edu.up.cs301.game.Card.KITCHEN;
import static edu.up.cs301.game.Card.LOUNGE;
import static edu.up.cs301.game.Card.MISS_SCARLET;
import static edu.up.cs301.game.Card.MRS_PEACOCK;
import static edu.up.cs301.game.Card.MRS_WHITE;
import static edu.up.cs301.game.Card.MR_GREEN;
import static edu.up.cs301.game.Card.PROF_PLUM;
import static edu.up.cs301.game.Card.STUDY;
import static edu.up.cs301.game.Type.ROOM;
import static edu.up.cs301.game.Type.WEAPON;

/**
 * Created by Noah on 10/25/2016.
 */

public class ClueLocalGame extends LocalGame {

    ClueMoveAction moveAction;
    ClueState state;
    private Random rand;
    public static String sync = "V";

    public ClueLocalGame(int numPlayerFromTableRows) {
        super();
        String[] str = new String[numPlayerFromTableRows];
        state = new ClueState(numPlayerFromTableRows,str, 0);
        rand = new Random();
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
            return false;
        }
    }

    public void inCornerRoom(int x, int y, Tile[][] board, int playerID)
    {
        //Separate if statements that will check to see if they are in a corner room!
        if (board[x][y].getRoom() == LOUNGE)
        {
            state.setInCornerRoom(playerID, true);
        }
        else if (board[x][y].getRoom() == CONSERVATORY)
        {
            state.setInCornerRoom(playerID, true);
        }
        else if (board[x][y].getRoom() == STUDY)
        {
            state.setInCornerRoom(playerID, true);
        }
        else if (board[x][y].getRoom() == KITCHEN)
        {
            state.setInCornerRoom(playerID, true);
        }
        else
        {
            state.setInCornerRoom(playerID, false);
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
            curBoard = (state.getBoard()).getBoard(); //Get the current board w/tiles

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
                            if ((playBoard[x-1][y] == -1 && (curBoard[x-1][y].getTileType() == 0 || curBoard[x-1][y].getIsDoor() || curBoard[x-1][y].getTileType() == 1)) && !curBoard[x-1][y].getBottomWall()
                                    && !curBoard[x][y].getTopWall())
                            {
                                if (curBoard[x-1][y].getIsDoor() && curBoard[x][y].getTileType() == 0) //If the player is moving into a room
                                {
                                    state.getBoard().setPlayerOnBoard(x - 1, y, x , y, curPlayerID); //Set the new position of the player and set the old position to -1.
                                    state.setSpacesMoved(state.getSpacesMoved() + 1);
                                    state.setUsedPassageway(curPlayerID, false);
                                    state.setOnDoorTile(curPlayerID, true);
                                    state.setNewToRoom(curPlayerID, true); //Set the new to room in array to true.
                                    state.setCanSuggest(curPlayerID, true);
                                    x = x - 1;
                                    inCornerRoom(x, y, curBoard, curPlayerID);
                                    //Makes it so that the player is able to move around in a room even if they technically have no moves left.
                                    if (state.getSpacesMoved() == state.getDieValue())
                                    {
                                        state.setSpacesMoved(state.getSpacesMoved() - 2);
                                    }
                                    state.setInRoom(curPlayerID, true);
                                    return true;
                                }
                                else if (curBoard[x][y].getTileType() == 1 && curBoard[x-1][y].getTileType() == 1) //If the player is in and will move within a room
                                {
                                    //If the player is moving around within a room, it will set their new position but will
                                    //not increment the spaces moved.
                                    state.getBoard().setPlayerOnBoard(x - 1, y, x, y, curPlayerID);
                                    //In order to prevent the dumb AI from moving around in a room indefinitely,
                                    //increment their spaces moved.


                                    if (a.getPlayer() instanceof ComputerPlayerDumb)
                                    {
                                        state.setSpacesMoved(state.getSpacesMoved() + 1);
                                    }

                                    x = x - 1;
                                    if (curBoard[x][y].getIsDoor())
                                    {
                                        state.setOnDoorTile(curPlayerID, true);
                                    }
                                    else
                                    {
                                        state.setOnDoorTile(curPlayerID, false);
                                    }
                                    Log.i("New to room="+state.getNewToRoom(curPlayerID), " ");
                                    return true;
                                }
                                else //Otherwise they're moving to a hallway.
                                {
                                    if (state.getNewToRoom(curPlayerID) && curBoard[x][y].getTileType() == 1 && !state.getPulledIn(curPlayerID))
                                    {
                                        return true;
                                    }

                                    state.setPulledIn(curPlayerID, false);
                                    state.getBoard().setPlayerOnBoard(x - 1, y, x, y, curPlayerID); //Set the new position of the player and set the old position to zero.
                                    state.setSpacesMoved(state.getSpacesMoved() + 1);
                                    x = x - 1;
                                    inCornerRoom(x, y, curBoard, curPlayerID);
                                    state.setUsedPassageway(curPlayerID, false);
                                    state.setInRoom(curPlayerID, false);
                                    state.setNewToRoom(curPlayerID, false);
                                    state.setCanSuggest(curPlayerID, false);
                                    state.setOnDoorTile(curPlayerID, false);
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
                            if ((playBoard[x+1][y] == -1 && (curBoard[x+1][y].getTileType() == 0 || curBoard[x+1][y].getIsDoor() || curBoard[x+1][y].getTileType() == 1)) && !curBoard[x+1][y].getTopWall()
                                    && !curBoard[x][y].getBottomWall())
                            {
                                if (curBoard[x+1][y].getIsDoor() && curBoard[x][y].getTileType() == 0)
                                {
                                    state.getBoard().setPlayerOnBoard(x + 1, y, x, y, curPlayerID);
                                    state.setSpacesMoved(state.getSpacesMoved() + 1);
                                    state.setUsedPassageway(curPlayerID, false);
                                    state.setNewToRoom(curPlayerID, true);
                                    state.setCanSuggest(curPlayerID, true);
                                    state.setOnDoorTile(curPlayerID, true);
                                    x = x + 1;
                                    inCornerRoom(x, y, curBoard, curPlayerID);
                                    if (state.getSpacesMoved() == state.getDieValue())
                                    {
                                        state.setSpacesMoved(state.getSpacesMoved() -2);
                                    }
                                    state.setInRoom(curPlayerID, true);
                                    return true;
                                }
                                else if (curBoard[x][y].getTileType() == 1 && curBoard[x+1][y].getTileType() == 1)
                                {
                                    state.getBoard().setPlayerOnBoard(x + 1, y, x, y, curPlayerID);


                                    if (a.getPlayer() instanceof ComputerPlayerDumb)
                                    {
                                        state.setSpacesMoved(state.getSpacesMoved() + 1);
                                    }
                                    x = x + 1;
                                    if (curBoard[x][y].getIsDoor())
                                    {
                                        state.setOnDoorTile(curPlayerID, true);
                                    }
                                    else
                                    {
                                        state.setOnDoorTile(curPlayerID, false);
                                    }
                                    return true;
                                }
                                else
                                {
                                    if (state.getNewToRoom(curPlayerID) && curBoard[x][y].getTileType() == 1 && !state.getPulledIn(curPlayerID))
                                    {
                                        return true;
                                    }

                                    state.setPulledIn(curPlayerID, false);
                                    state.getBoard().setPlayerOnBoard(x + 1, y, x, y, curPlayerID);
                                    state.setSpacesMoved(state.getSpacesMoved() + 1);
                                    x = x + 1;
                                    inCornerRoom(x, y, curBoard, curPlayerID);
                                    state.setUsedPassageway(curPlayerID, false);
                                    state.setInRoom(curPlayerID, false);
                                    state.setNewToRoom(curPlayerID, false);
                                    state.setCanSuggest(curPlayerID, false);
                                    state.setOnDoorTile(curPlayerID, false);
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
                            if((playBoard[x][y+1] == -1 && (curBoard[x][y+1].getTileType() == 0 || curBoard[x][y+1].getIsDoor() || curBoard[x][y+1].getTileType() == 1)) && !curBoard[x][y+1].getLeftWall()
                                    && !curBoard[x][y].getRightWall())
                            {
                                if (curBoard[x][y+1].getIsDoor() && curBoard[x][y].getTileType() == 0)
                                {
                                    state.getBoard().setPlayerOnBoard(x, y + 1, x, y, curPlayerID);
                                    state.setSpacesMoved(state.getSpacesMoved() + 1);
                                    state.setUsedPassageway(curPlayerID, false);
                                    state.setNewToRoom(curPlayerID, true);
                                    state.setCanSuggest(curPlayerID, true);
                                    state.setOnDoorTile(curPlayerID, true);
                                    y = y + 1;
                                    inCornerRoom(x, y, curBoard, curPlayerID);
                                    if (state.getSpacesMoved() == state.getDieValue())
                                    {
                                        state.setSpacesMoved(state.getSpacesMoved() -2);
                                    }
                                    state.setInRoom(curPlayerID, true);
                                    return true;
                                }
                                else if (curBoard[x][y].getTileType() == 1 && curBoard[x][y+1].getTileType() == 1)
                                {
                                    state.getBoard().setPlayerOnBoard(x, y + 1, x, y, curPlayerID);


                                    if (a.getPlayer() instanceof ComputerPlayerDumb)
                                    {
                                        state.setSpacesMoved(state.getSpacesMoved() + 1);
                                    }
                                    y = y + 1;
                                    if (curBoard[x][y].getIsDoor())
                                    {
                                        state.setOnDoorTile(curPlayerID, true);
                                    }
                                    else
                                    {
                                        state.setOnDoorTile(curPlayerID, false);
                                    }
                                    return true;
                                }
                                else
                                {
                                    if (state.getNewToRoom(curPlayerID) && curBoard[x][y].getTileType() == 1 && !state.getPulledIn(curPlayerID))
                                    {
                                        return true;
                                    }

                                    state.setPulledIn(curPlayerID, false);
                                    state.getBoard().setPlayerOnBoard(x, y + 1, x, y, curPlayerID);
                                    state.setSpacesMoved(state.getSpacesMoved() + 1);
                                    y = y + 1;
                                    inCornerRoom(x, y, curBoard, curPlayerID);
                                    state.setUsedPassageway(curPlayerID, false);
                                    state.setInRoom(curPlayerID, false);
                                    state.setNewToRoom(curPlayerID, false);
                                    state.setCanSuggest(curPlayerID, false);
                                    state.setOnDoorTile(curPlayerID, false);
                                    return true;
                                }
                            }
                        }else {
                            return false;
                        }
                    }
                    else if (moveAction instanceof ClueMoveLeftAction)
                    {
                        if(x >= 0 && y >= 1 && curBoard[x][y-1] != null) {
                            if ((playBoard[x][y-1] == -1 && (curBoard[x][y-1].getTileType() == 0 || curBoard[x][y-1].getIsDoor() || curBoard[x][y-1].getTileType() == 1)) && !curBoard[x][y-1].getRightWall()
                                    && !curBoard[x][y].getLeftWall())
                            {
                                if (curBoard[x][y-1].getIsDoor() && curBoard[x][y].getTileType() == 0)
                                {
                                    state.getBoard().setPlayerOnBoard(x, y - 1, x, y, curPlayerID);
                                    state.setSpacesMoved(state.getSpacesMoved() + 1);
                                    state.setUsedPassageway(curPlayerID, false);
                                    state.setNewToRoom(curPlayerID, true);
                                    state.setCanSuggest(curPlayerID, true);
                                    state.setOnDoorTile(curPlayerID, true);
                                    y = y - 1;
                                    inCornerRoom(x, y, curBoard, curPlayerID);
                                    if (state.getSpacesMoved() == state.getDieValue())
                                    {
                                        state.setSpacesMoved(state.getSpacesMoved() -2);
                                    }
                                    state.setInRoom(curPlayerID, true);
                                    return true;
                                }
                                else if (curBoard[x][y].getTileType() == 1 && curBoard[x][y-1].getTileType() == 1)
                                {
                                    state.getBoard().setPlayerOnBoard(x, y - 1, x, y, curPlayerID);


                                    if (a.getPlayer() instanceof ComputerPlayerDumb)
                                    {
                                        state.setSpacesMoved(state.getSpacesMoved() + 1);
                                    }
                                    y = y - 1;
                                    if (curBoard[x][y].getIsDoor())
                                    {
                                        state.setOnDoorTile(curPlayerID, true);
                                    }
                                    else
                                    {
                                        state.setOnDoorTile(curPlayerID, false);
                                    }
                                    //
                                    return true;
                                }
                                else
                                {
                                    if (state.getNewToRoom(curPlayerID) && curBoard[x][y].getTileType() == 1 && !state.getPulledIn(curPlayerID))
                                    {
                                        return true;
                                    }

                                    state.setPulledIn(curPlayerID,false);
                                    state.getBoard().setPlayerOnBoard(x, y - 1, x, y, curPlayerID);
                                    state.setSpacesMoved(state.getSpacesMoved() + 1);
                                    state.setNewToRoom(curPlayerID, false);
                                    state.setCanSuggest(curPlayerID, false);
                                    y = y - 1;
                                    inCornerRoom(x, y, curBoard, curPlayerID);
                                    state.setUsedPassageway(curPlayerID, false);
                                    state.setInRoom(curPlayerID, false);
                                    state.setOnDoorTile(curPlayerID, false);
                                    return true;
                                }

                            }
                        }else {
                            return false;
                        }
                    }
                }

                if (moveAction instanceof ClueAccuseAction)
                {
                    boolean solved = true;
                    Card solution[] = state.getSolution();
                    ClueAccuseAction moveActionAcc = (ClueAccuseAction)moveAction;

                    //Check to see if the players guess matches the solution
                    for (int i = 0; i < 3; i++)
                    {
                        if (solution[i].getType() == ROOM)
                        {
                            if (!solution[i].getName().equals(moveActionAcc.room))
                            {
                                solved = false;
                            }
                        }
                        else if (solution[i].getType() == WEAPON)
                        {
                            if (!solution[i].getName().equals(moveActionAcc.weapon))
                            {
                                solved = false;
                            }
                        }
                        else
                        {
                            if (!solution[i].getName().equals(moveActionAcc.suspect))
                            {
                                solved = false;
                            }
                        }
                    }

                    if (!solved)
                    {
                        //End the game for that player.
                        state.setPlayerStillInGame(curPlayerID, false);
                        if (!state.getPlayerStillInGame(curPlayerID)) {
                            if (curPlayerID == 0) {
                                state.getBoard().setPlayerOnBoard(1, 17, x, y, curPlayerID);
                            } else if (curPlayerID == 1) {
                                state.getBoard().setPlayerOnBoard(8, 24, x, y, curPlayerID);
                            } else if (curPlayerID == 2) {
                                state.getBoard().setPlayerOnBoard(25, 15, x, y, curPlayerID);
                            } else if (curPlayerID == 3) {
                                state.getBoard().setPlayerOnBoard(25, 10, x, y, curPlayerID);
                            } else if (curPlayerID == 4) {
                                state.getBoard().setPlayerOnBoard(19, 1, x, y, curPlayerID);
                            } else {
                                state.getBoard().setPlayerOnBoard(6, 1, x, y, curPlayerID);
                            }
                        }
                        return true;

                    }else {
                        state.setGameOver(true);
                        state.setWinnerIndex(curPlayerID);
                        return true;
                    }
                }
                else if (moveAction instanceof ClueSuggestionAction)
                {
                    //make sure they are not on a door tile when they suggest to prevent the player
                    //from ending their turn on a door tile
                    if (!curBoard[x][y].getIsDoor()) {
                        //check if they are on a room tile and they are new to room
                        if (curBoard[x][y].getTileType() == 1 && state.getNewToRoom(curPlayerID)) {
                            ClueSuggestionAction b = (ClueSuggestionAction) a;
                            String[] suggestionCards = new String[3];
                            suggestionCards[0] = b.room;
                            suggestionCards[1] = b.suspect;
                            suggestionCards[2] = b.weapon;
                            state.setSuggestCards(suggestionCards);
                            state.setPlayerIDWhoSuggested(b.playerID);
                            state.setNewToRoom(b.playerID, false);
                            state.setCanSuggest(b.playerID, false);
                            state.setCanRoll(b.playerID, false);
                            state.setHasSuggested(b.playerID, true);

                            //move the player in the suggestion into the room as well next to the player
                            //only if they are still in the game
                            if (state.getPlayerStillInGame(b.playerID)) {
                                String[] tempCards = state.getSuggestCards();

                                //check if the player to move is still in the game
                                if (tempCards[1].equals(MISS_SCARLET.getName())) {
                                    state.setPlayerInSuggestion(0);
                                }
                                else if (tempCards[1].equals(COL_MUSTARD.getName())) {
                                    state.setPlayerInSuggestion(1);
                                }
                                else if (tempCards[1].equals(MRS_WHITE.getName())) {
                                    state.setPlayerInSuggestion(2);
                                }
                                else if (tempCards[1].equals(MR_GREEN.getName())) {
                                    state.setPlayerInSuggestion(3);
                                }
                                else if (tempCards[1].equals(MRS_PEACOCK.getName())){
                                    state.setPlayerInSuggestion(4);
                                }
                                else if (tempCards[1].equals(PROF_PLUM.getName())) {
                                    state.setPlayerInSuggestion(5);
                                }
                                else {
                                    state.setPlayerInSuggestion(-1);
                                }

                                //if a player was in the suggestion, pull them into the room
                                if (state.getPlayerInSuggestion() != -1) {
                                    String moveToRoom = " ";
                                    int currentX = 1;
                                    int currentY = 1;
                                    for (int i = 0; i < 26; i++) {
                                        for (int j = 0; j < 26; j++) {

                                            if (state.getBoard().getPlayerBoard()[j][i] == b.playerID) {

                                                moveToRoom = state.getBoard().getBoard()[j][i].getRoom().getName();
                                            }
                                            if (state.getBoard().getPlayerBoard()[j][i] == state.getPlayerInSuggestion()) {
                                                currentX = i;
                                                currentY = j;
                                            }
                                        }
                                     }

                                    loop1:
                                    for (int i = 0; i < 26; i++) {
                                        for (int j = 0; j < 26; j++) {
                                            //find first available tile in the room to place the player
                                            if (state.getBoard().getBoard()[j][i] != null && state.getBoard().getBoard()[j][i].getRoom() != null && state.getBoard().getBoard()[j][i].getRoom().getName().equals(moveToRoom) && state.getBoard().getPlayerBoard()[j][i] == - 1 && !state.getBoard().getBoard()[j][i].getIsDoor()) {
                                                state.getBoard().setPlayerOnBoard(j, i,currentY, currentX, state.getPlayerInSuggestion());
                                                if(state.getPlayerInSuggestion() < state.getNumPlayers()) {
                                                    state.setCanSuggest(state.getPlayerInSuggestion(), true);
                                                    state.setNewToRoom(state.getPlayerInSuggestion(), true);
                                                    state.setUsedPassageway(state.getPlayerInSuggestion(), false);
                                                    inCornerRoom(j, i, curBoard, state.getPlayerInSuggestion());
                                                    state.setOnDoorTile(state.getPlayerInSuggestion(), false);
                                                    state.setPulledIn(state.getPlayerInSuggestion(), true);
                                                }
                                                break loop1;
                                            }
                                        }
                                    }
//                                    state.getBoard().setPlayerOnBoard(moveToY, moveToX,currentY, currentX, state.getPlayerInSuggestion());
                                }
                             }





                            if (b.playerID == state.getNumPlayers() - 1) {
                                state.setCheckCardToSend(0, true);
                            } else {
                                state.setCheckCardToSend(b.playerID + 1, true);
                            }

                            if (state.getTurnId() == (state.getNumPlayers() - 1))
                            {
                                state.setCanRoll(0, false);
                                state.setCanSuggest(0, false);
                                state.setTurnID(0);
                                state.setSpacesMoved(0);
                                state.setDieValue(0);
                                return true;
                            }
                            else
                            {
                                state.setCanRoll(state.getTurnId() + 1, false);
                                state.setCanSuggest(state.getTurnId()+1,false);
                                state.setTurnID(state.getTurnId() + 1);
                                state.setSpacesMoved(0);
                                state.setDieValue(0);
                                return true;

                            }
                        }

                        return false;
                    }

                    return false;
                }
                else if (moveAction instanceof ClueUsePassagewayAction)
                {
                    //These if statements will see where the player is at and will move them to the corner room
                    //diagonal to them.
                    boolean inCorner[] = state.getInCornerRoom();
                    boolean usedPass[] = state.getUsedPassageway();

                    if (inCorner[curPlayerID] && !usedPass[curPlayerID])
                    {
                        if (curBoard[x][y].getRoom() == LOUNGE)
                        {
                            Log.i("Got to lounge if", " ");
                            (state.getBoard()).setPlayerOnBoard(22, 1+curPlayerID, x, y, curPlayerID); //Move Player to conservatory
                            state.setUsedPassageway(curPlayerID, true);
                            state.setCanRoll(curPlayerID, false);
                            state.setNewToRoom(curPlayerID, true);
                            state.setOnDoorTile(curPlayerID, false);
                            state.setCanSuggest(curPlayerID, true);
                            return true;
                        }
                        else if (curBoard[x][y].getRoom() == CONSERVATORY)
                        {
                            int sum = 18+curPlayerID;
                            if(sum > 24){
                                sum = 24;
                            }
                            state.getBoard().setPlayerOnBoard(2, sum, x, y, curPlayerID); //Move Player to lounge
                            state.setUsedPassageway(curPlayerID, true);
                            state.setCanRoll(curPlayerID, false);
                            state.setNewToRoom(curPlayerID, true);
                            state.setCanSuggest(curPlayerID, true);
                            state.setOnDoorTile(curPlayerID, false);
                            return true;
                        }
                        else if (curBoard[x][y].getRoom() == STUDY)
                        {
                            int sum = 19+curPlayerID;
                            if(sum > 24){
                                sum = 24;
                            }
                            state.getBoard().setPlayerOnBoard(22, sum, x, y, curPlayerID); //Move Player to kitchen
                            state.setUsedPassageway(curPlayerID, true);
                            state.setCanRoll(curPlayerID, false);
                            state.setNewToRoom(curPlayerID, true);
                            state.setOnDoorTile(curPlayerID, false);
                            state.setCanSuggest(curPlayerID, true);
                            return true;
                        }
                        else if (curBoard[x][y].getRoom() == KITCHEN)
                        {
                            state.getBoard().setPlayerOnBoard(3, 1+curPlayerID, x, y, curPlayerID); //Move Player to the study
                            state.setUsedPassageway(curPlayerID, true);
                            state.setCanRoll(curPlayerID, false);
                            state.setNewToRoom(curPlayerID, true);
                            state.setCanSuggest(curPlayerID, true);
                            state.setOnDoorTile(curPlayerID, false);
                            return true;

                        }
                    }
                }
                else if (moveAction instanceof ClueEndTurnAction)
                {
                    if(state.getTurnId() != moveAction.playerID)
                    {
                        return false;
                    }

                    //Check to make sure they are not on a door tile(so they can't block the door)
                    Log.i("Player Still in Game" + state.getPlayerStillInGame(curPlayerID), " ");
                    if (!state.getPlayerStillInGame(curPlayerID))
                    {
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
                    else if (!curBoard[x][y].getIsDoor() && state.getPlayerStillInGame(curPlayerID))
                    {
                        //Change the turnID to the next player and lets the next player roll
                        state.setNewToRoom(curPlayerID, false); //Once they've ended their turn, they are no longer new to a room.
                        state.setCanRoll(curPlayerID, false);
                        state.setCanSuggest(curPlayerID, false);
                        state.setHasSuggested(curPlayerID, false);
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
                    else if(players[curPlayerID] instanceof ComputerPlayerDumb) {
                        state.setSpacesMoved(state.getSpacesMoved()-1);
                        return false;
                    }

                }else if (moveAction instanceof ClueShowCardAction) { //Might not work
                    ClueShowCardAction b = (ClueShowCardAction)a;
                    state.setPlayerWhoShowedCard(b.playerID); //sent the id of the player who suggested
                    if(b.getCardToShow() == null) {
                        if(b.playerID == state.getNumPlayers() - 1 && state.getPlayerIDWhoSuggested() != 0) {
                            state.setCheckCardToSend(b.playerID, false);
                            state.setCheckCardToSend(0, true);
                            state.setCanRoll(0, false);
                            state.setCanSuggest(0,false);
                            state.setSpacesMoved(0);
                            state.setDieValue(0);
                            state.setTurnID(0);
                            return true;
                        }else if(b.playerID == state.getNumPlayers() - 1 && state.getPlayerIDWhoSuggested() == 0) {
                            for (int i = 0; i < state.getNumPlayers(); i++)
                            {
                                state.setCardToShow("No card shown.", i);
                            }

                            state.setCheckCardToSend(b.playerID, false);
                            state.setCardToShow("No card shown.", state.getPlayerIDWhoSuggested());
                            state.setPlayerWhoShowedCard(-1); //print empty string

                            state.setNewToRoom(state.getPlayerIDWhoSuggested(), false); //Once they've ended their turn, they are no longer new to a room.
                            state.setCanRoll(state.getPlayerIDWhoSuggested(), false);
                            state.setCanSuggest(state.getPlayerIDWhoSuggested(), false);
                            state.setSpacesMoved(0);
                            state.setDieValue(0);
                            state.setTurnID(state.getPlayerIDWhoSuggested());
//                            if (state.getPlayerIDWhoSuggested() == state.getNumPlayers()-1)
//                            {
//                                state.setTurnID(0);
//                            }
//                            else
//                            {
//                                state.setTurnID(state.getPlayerIDWhoSuggested() + 1);
//                            }

                            return true;

                        }else if(state.getPlayerIDWhoSuggested() != b.playerID + 1) {
                            state.setCheckCardToSend(b.playerID, false);
                            state.setCheckCardToSend(b.playerID + 1, true);
                            state.setCanRoll(b.playerID + 1, false);
                            state.setCanSuggest(b.playerID + 1,false);
                            state.setSpacesMoved(0);
                            state.setDieValue(0);
                            state.setTurnID(b.playerID+1);
                            return true;
                        }else if(b.playerID == state.getPlayerIDWhoSuggested()-1){
                            for (int i = 0; i < state.getNumPlayers(); i++)
                            {
                                state.setCardToShow("No card shown.", i);
                            }

                            state.setCheckCardToSend(b.playerID, false);
                            state.setCardToShow("No card shown.", state.getPlayerIDWhoSuggested());
                            state.setPlayerWhoShowedCard(-1);

                            state.setNewToRoom(state.getPlayerIDWhoSuggested(), false); //Once they've ended their turn, they are no longer new to a room.
                            state.setCanRoll(state.getPlayerIDWhoSuggested(), false);
                            state.setCanSuggest(state.getPlayerIDWhoSuggested(), false);
                            state.setSpacesMoved(0);
                            state.setDieValue(0);
//                            if (state.getPlayerIDWhoSuggested() == state.getNumPlayers()-1)
//                            {
//                                state.setTurnID(0);
//                            }
//                            else
//                            {
//                                state.setTurnID(state.getPlayerIDWhoSuggested() + 1);
//                            }
                            state.setTurnID(b.playerID+1);
                            return true;
                        }
                    }else {
                        for (int i = 0; i < state.getNumPlayers(); i++)
                        {
                            state.setCardToShow("Card was shown.", i);
                        }

                        state.setCheckCardToSend(b.playerID, false);
                        state.setCardToShow(b.getCardToShow(), state.getPlayerIDWhoSuggested());
                        state.setNewToRoom(state.getPlayerIDWhoSuggested(), false); //Once they've ended their turn, they are no longer new to a room.
                        state.setCanRoll(state.getPlayerIDWhoSuggested(), false);
                        state.setCanSuggest(state.getPlayerIDWhoSuggested(), false);
                        state.setSpacesMoved(0);
                        state.setDieValue(0);
                        state.setTurnID(state.getPlayerIDWhoSuggested());
//                        if (state.getPlayerIDWhoSuggested() == state.getNumPlayers()-1)
//                        {
//                            state.setTurnID(0);
//                        }
//                        else
//                        {
//                            state.setTurnID(state.getPlayerIDWhoSuggested() + 1);
//                        }

                        return true;
                    }

                    return false;
                }
            }
            return false;
        }

        return false;
    }

    @Override
    public void sendUpdatedStateTo(GamePlayer p) {
        ClueState sendState = new ClueState(state);
        if(p instanceof ClueHumanPlayer) {
            ClueHumanPlayer player = (ClueHumanPlayer)p;
            int playerCount = sendState.getNumPlayers();
            for(int i = 0; i < playerCount; i++) {
                if(i != player.getPlayerID()) {
                    sendState.setNotes(i, null);
                    sendState.setCards(i, null);
                }
            }
        }else if(p instanceof ComputerPlayerDumb){
            ComputerPlayerDumb player = (ComputerPlayerDumb)p;
            int playerCount = sendState.getNumPlayers();
            for(int i = 0; i < playerCount; i++) {
                if(i != player.getPlayerID()) {
                    sendState.setNotes(i, null);
                    sendState.setCards(i, null);
                }
            }
        }else if(p instanceof ComputerPlayerSmart){
            ComputerPlayerSmart player = (ComputerPlayerSmart)p;
            int playerCount = sendState.getNumPlayers();
            for(int i = 0; i < playerCount; i++) {
                if(i != player.getPlayerID()) {
                    sendState.setNotes(i, null);
                    sendState.setCards(i, null);
                }
            }
        }

        p.sendInfo(sendState);
    }

    @Override
    public String checkIfGameOver() {
        int numPlayersLeft = 0;
        for(int i = 0; i < state.getNumPlayers(); i++) {
            if(state.getPlayerStillInGame(i)) {
                numPlayersLeft++;
            }
        }
        if(numPlayersLeft != 1 && !state.getGameOver()) {
            return null;
        }else if(numPlayersLeft == 1){
            for(int i = 0; i < state.getNumPlayers(); i++) {
                if(state.getPlayerStillInGame(i)) {
                    state.setWinnerIndex(i);
                }
            }
            state.setGameOver(true);
            String gameOverMessage = "Game Over";
            switch (state.getWinnerIndex()) {
                case 0: gameOverMessage = "Miss Scarlet won.";
                    break;
                case 1: gameOverMessage = "Colonel Mustard won.";
                    break;
                case 2: gameOverMessage = "Mrs. White won.";
                    break;
                case 3: gameOverMessage = "Mr. Green won";
                    break;
                case 4: gameOverMessage = "Mrs. Peacock won.";
                    break;
                case 5: gameOverMessage = "Professor Plum won.";
                    break;
            }
            return gameOverMessage;
        }else {
            Log.i("Game", "Over");
            String gameOverMessage = "Game Over";
            switch (state.getWinnerIndex()) {
                case 0: gameOverMessage = "Miss Scarlet won.";
                    break;
                case 1: gameOverMessage = "Colonel Mustard won.";
                    break;
                case 2: gameOverMessage = "Mrs. White won.";
                    break;
                case 3: gameOverMessage = "Mr. Green won";
                    break;
                case 4: gameOverMessage = "Mrs. Peacock won.";
                    break;
                case 5: gameOverMessage = "Professor Plum won.";
                    break;
            }
            return gameOverMessage;
        }
    }

//    /* not necessary but still here in case
//    private boolean checkTileValid(int x, int y, Board b) {
//        if(b.getBoard()[x][y] == null) {
//            return false;
//        }if(b.getBoard()[x][y].getTileType() == 0) {
//            return true;
//        }else if(b.getBoard()[x][y].getTileType() == 1 && b.getBoard()[x][y].getIsDoor()) {
//            return true;
//        }
//        return false;
//    }
//
//    private boolean checkIntValid(int x, int y, int[][] b) {
//        if(b[x][y] == -1) {
//            return true;
//        }else {
//            return false;
//        }
//    }
//    */

//    public boolean endTurn (ClueState state, int curPlayerID)
//    {
//        state.setNewToRoom(curPlayerID, false); //Once they've ended their turn, they are no longer new to a room.
//        state.setCanRoll(curPlayerID, false);
//        state.setCanSuggest(curPlayerID, false);
//        state.setHasSuggested(curPlayerID, false);
//        if (state.getTurnId() == (state.getNumPlayers() - 1))
//        {
//            state.setCanRoll(0, true);
//            state.setTurnID(0);
//            state.setSpacesMoved(0);
//            state.setDieValue(0);
//            return true;
//        }
//        else
//        {
//            state.setCanRoll(state.getTurnId() + 1, true);
//            state.setTurnID(state.getTurnId() + 1);
//            state.setSpacesMoved(0);
//            state.setDieValue(0);
//            return true;
//        }
//    }

}
