package edu.up.cs301.game;

import android.util.Log;

import java.util.ArrayList;
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
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * Created by Noah on 11/8/2016.
 */

/*
* An AI player that tries to collect as much information as possible as fast as possible to win.
* It makes moves towards the closest room that it doesn't know isn't the location of the murder,
* and suggests weapons and suspects it has yet to know about.
*/
public class ClueComputerPlayerSmart extends ClueComputerPlayer {
    //checkboxes for the AI to keep track of known cards
    private boolean[] checkBoxVals = new boolean[21];
    //an array of all the cards in the order of the checkboxes
    private Card[] allCards = {Card.COL_MUSTARD, Card.PROF_PLUM, Card.MR_GREEN, Card.MRS_PEACOCK, Card.MISS_SCARLET,
            Card.MRS_WHITE, Card.KNIFE, Card.CANDLESTICK, Card.REVOLVER, Card.ROPE, Card.LEAD_PIPE,
            Card.WRENCH, Card.HALL, Card.LOUNGE, Card.DINING_ROOM, Card.KITCHEN, Card.BALLROOM,
            Card.CONSERVATORY, Card.BILLIARD_ROOM, Card.LIBRARY, Card.STUDY};
    //the most recently received copy of the state
    private ClueState myState;
    //the previous suggestion made
    private ClueSuggestionAction prevSuggestion = null;
    //the coordinates of all the doors on the board
    private int[][] doorCoordinates = {{5,10}, {7,12}, {7,13}, {6,18}, {10,18}, {13,17}, {19,20},
            {21,16}, {18,15}, {18,10}, {21,9}, {20,5}, {16,6}, {13,2}, {11,4}, {9,7}, {4,7}};
    //the rooms that correspond to the doors in the previous array
    private Card[] doorRooms = {Card.HALL, Card.HALL, Card.HALL, Card.LOUNGE, Card.DINING_ROOM,
            Card.DINING_ROOM,Card.KITCHEN, Card.BALLROOM, Card.BALLROOM, Card.BALLROOM, Card.BALLROOM,
            Card.CONSERVATORY, Card.BILLIARD_ROOM, Card.BILLIARD_ROOM, Card.LIBRARY, Card.LIBRARY, Card.STUDY};
    //whether or not the hand has been checked off in the checkbox array
    private boolean handChecked;
    //the previous two moves - used to help prevent getting stuck
    private ClueMoveAction prevMov1;
    private ClueMoveAction prevMov2;
    //the number of moves made in the current turn
    private int numMoves;
    private static final int LEFT = 1, UP = 2, RIGHT = 3, DOWN = 4;

    public ClueComputerPlayerSmart(String name) {
        super(name);
        handChecked = false;
        prevMov1 = null;
        prevMov2 = null;
        numMoves = 0;
    }

    public int getPlayerID() {
        return playerNum;
    }

    public void setPlayerID(int newPlayerID) {
        playerNum = newPlayerID;
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        if (info instanceof ClueState) {
            myState = (ClueState) info; //cast it
            //checks cards in the hand if not already done so
            if(!handChecked) {
                for(int j = 0; j < 21; j++) {
                    for(int i = 0; i < myState.getCards(playerNum).getArrayListLength(); i++) {
                        if(myState.getCards(playerNum).getCards()[i].equals(allCards[j])) {
                            checkBoxVals[j] = true;
                        }
                    }
                }
            }
            //check card shown in boolean array if card is shown
            for(int i = 0; i < 21;i++) {
                if(allCards[i].getName().equals(myState.getCardToShow(playerNum))) {
                    checkBoxVals[i] = true;
                }
            }
            //if it's the AIs turn, set numMoves to zero
            if(myState.getTurnId() != playerNum) {
                numMoves = 0;
            }
            //if the AI needs to show a card, shows a card
            if(myState.getCheckCardToSend()[playerNum]) {
                Log.i("Computer Player "+playerNum,"Showing Card");
                String[] temp = myState.getSuggestCards();
                Hand tempHand = myState.getCards(playerNum);
                Card[] tempCards = tempHand.getCards();
                String[] cardNames = new String[tempHand.getArrayListLength()];
                ArrayList<String> cards = new ArrayList<String>();
                for(int i = 0; i < tempHand.getArrayListLength(); i++) {
                    cardNames[i] = tempCards[i].getName();
                }

                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < cardNames.length; j++) {
                        if(cardNames[j].equals(temp[i])) {
                            cards.add(temp[i]);
                        }
                    }
                }

                if(cards.size() == 0) {
                    game.sendAction(new ClueShowCardAction(this));
                    return;
                }else {
                    Random rand1 = new Random();
                    int c = rand1.nextInt(cards.size());
                    ClueShowCardAction s = new ClueShowCardAction(this);
                    s.setCardToShow(cards.get(c));
                    game.sendAction(s);
                    return;
                }
            //if the AI is still in the game and it is the AIs turn
            }else if (myState.getTurnId() == playerNum && myState.getPlayerStillInGame(playerNum)) {
                if (myState.getCanRoll(this.playerNum)) {
                    Log.i("Computer Player"+playerNum, "Rolling");
                    game.sendAction(new ClueRollAction(this));
                    return;
                //if the previous suggestion was not contested
                }else if(myState.getCardToShow(this.playerNum).equals("No card shown.")) {
                    ClueAccuseAction caa = new ClueAccuseAction(this);
                    caa.room = prevSuggestion.room;
                    caa.suspect = prevSuggestion.suspect;
                    caa.weapon = prevSuggestion.weapon;
                    game.sendAction(caa);
                    return;
                //if the AI can suggest, it will
                } else if (myState.getCanSuggest(this.playerNum) && !myState.getOnDoorTile()[playerNum] && myState.getInRoom()[playerNum]) {
                    //sets up arrays of the suspects and weapons
                    Card[] suspects = {Card.COL_MUSTARD, Card.PROF_PLUM, Card.MR_GREEN,
                            Card.MRS_PEACOCK, Card.MISS_SCARLET, Card.MRS_WHITE};
                    Card[] weapons = {Card.KNIFE, Card.CANDLESTICK, Card.REVOLVER, Card.ROPE,
                            Card.LEAD_PIPE, Card.WRENCH};
                    Random rand = new Random();
                    //store the cards that aren't already known to not be in the solution
                    ArrayList<Card> availableSuspects = new ArrayList<Card>();
                    ArrayList<Card> availableWeapons = new ArrayList<Card>();

                    //add suspects not already known to not be the murderer
                    for(int i = 0; i < 6; i++) {
                        if(!checkBoxVals[i]) {
                            availableSuspects.add(suspects[i]);
                        }
                    }

                    //add weapons not already known to not be the murder weapon
                    for(int i = 6; i < 12; i++) {
                        if(!checkBoxVals[i]) {
                            availableWeapons.add(weapons[i-6]);
                        }
                    }

                    //the suggestion to send to the LocalGame
                    ClueSuggestionAction csa = new ClueSuggestionAction(this);
                    //sets the room for the suggestion
                    outerLoop:
                    for (int i = 0; i < 26; i++) {
                        for (int j = 0; j < 26; j++) {
                            if (myState.getBoard().getPlayerBoard()[j][i] == playerNum) {
                                csa.room = myState.getBoard().getBoard()[j][i].getRoom().getName();
                                break outerLoop;
                            }
                        }
                    }

                    //sets the suspect and weapon in the suggestion
                    csa.suspect = availableSuspects.get(rand.nextInt(availableSuspects.size())).getName();
                    csa.weapon = availableWeapons.get(rand.nextInt(availableWeapons.size())).getName();

                    //sets the previous suggestion, sends the action to the state, sends a log message
                    //and returns
                    Log.i("Computer Player "+playerNum,"Suggesting");
                    prevSuggestion = csa;
                    game.sendAction(csa);
                    return;

                //if the player can move
                } else if ((myState.getDieValue() != myState.getSpacesMoved() && numMoves < 8 )||
                    myState.getOnDoorTile()[playerNum]) {
                    Log.i("Computer Player" + playerNum+ " Moving", " ");
                    //waits for a bit
                    sleep(300);
                    //an array of the rooms
                    Card[] rooms = {Card.HALL, Card.LOUNGE, Card.DINING_ROOM, Card.KITCHEN, Card.BALLROOM,
                            Card.CONSERVATORY, Card.BILLIARD_ROOM, Card.LIBRARY, Card.STUDY};
                    //the space the AI is at and the one to move to
                    int curX = -1;
                    int curY = -1;
                    int closestX = 100;
                    int closestY = 100;

                    //the room the player is currently in
                    Card currentRoom = null; //null if not in a room, set to room if in room

                    //sets the current location
                    loop:
                    for (int i = 0; i < 26; i++) {
                        for (int j = 0; j < 26; j++) {
                            if (myState.getBoard().getPlayerBoard()[j][i] == playerNum) {
                                curY = j;
                                curX = i;
                                currentRoom = myState.getBoard().getBoard()[j][i].getRoom();
                                break loop;
                            }
                        }
                    }

                    //if in a corner room and haven't checked off the room at the other end of
                    //the passageway
                    if(currentRoom == Card.STUDY){
                        if(!checkBoxVals[15] && !myState.getUsedPassageway()[playerNum]){
                           game.sendAction(new ClueUsePassagewayAction(this));
                            return;
                        }
                    }else if(currentRoom == Card.KITCHEN){
                        if(!checkBoxVals[20] && !myState.getUsedPassageway()[playerNum]){
                            game.sendAction(new ClueUsePassagewayAction(this));
                            return;
                        }
                    }else if(currentRoom == Card.LOUNGE){
                        if(!checkBoxVals[17] && !myState.getUsedPassageway()[playerNum]){
                            game.sendAction(new ClueUsePassagewayAction(this));
                            return;
                        }
                    }else if(currentRoom == Card.CONSERVATORY){
                        if(!checkBoxVals[13] && !myState.getUsedPassageway()[playerNum]){
                            game.sendAction(new ClueUsePassagewayAction(this));
                            return;
                        }
                    }

                    //calculates where to move to
                    for (int i = 0; i < doorCoordinates.length; i++) {
                        if (!doorRooms[i].equals(currentRoom)) {
                            boolean roomChecked = true;
                            for (int k = 0; k < 9; k++) {
                                if (rooms[k].equals(doorRooms[i]) && !checkBoxVals[12 + k]) {
                                    roomChecked = false;
                                }
                            }

                            //update to dX dY
                            if ((((closestX - curX) * (closestX - curX)) + ((closestY - curY) * (closestY - curY))) >
                                    (((doorCoordinates[i][0] - curY) * (doorCoordinates[i][0] - curY)) + ((doorCoordinates[i][1] - curX)
                                            * (doorCoordinates[i][1] - curX))) && !roomChecked) {
                                closestY = doorCoordinates[i][0];
                                closestX = doorCoordinates[i][1];
                            }
                        }
                    }
                    //if the AI is in the room and
                    if(curX >= 0 && curY >= 0 && myState.getBoard().getBoard()[curY][curX].getTileType() == 1 && !myState.getCanSuggest(playerNum)){
                        int destX = closestX;
                        int destY = closestY;
                        for(int j = 0; j < doorCoordinates.length; j++) {
                            if(doorRooms[j].equals(currentRoom)) {
                                if(closestX == destX && closestY == destY) {
                                    closestX = doorCoordinates[j][1];
                                    closestY = doorCoordinates[j][0];
                                }else if((((destX - closestX) * (destX -closestX)) + ((destY - closestY) * (destY - closestY))) >
                                (((destX - doorCoordinates[j][1]) * (destX - doorCoordinates[j][1])) + ((destY - doorCoordinates[j][0])
                                * (destY - doorCoordinates[j][0])))) {
                                    closestX = doorCoordinates[j][1];
                                    closestY = doorCoordinates[j][0];
                                }
                            }
                        }
                    }

                    //calculates how far the AI wants to move in the x and y directions
                    int dX = closestX - curX;
                    int dY = closestY - curY;

                    //if dX and dY are negative
                    boolean dXNegative = false;
                    boolean dYNegative = false;

                    //make dX and dY positive for easier comparison
                    if (dX < 0) {
                        dX *= -1;
                        dXNegative = true;
                    }
                    if (dY < 0) {
                        dY *= -1;
                        dYNegative = true;
                    }

                    //calculates the most logical move to make
                    if (myState.getBoard().getBoard()[curY][curX].getTileType() == 0 ||
                            (myState.getBoard().getBoard()[curY][curX].getTileType() == 1
                                    && !myState.getBoard().getBoard()[curY][curX].getIsDoor())) {
                        if (dX == 0 && isThereAWall(curX, curY) && !isThereADoor(curX,curY)) {
                            int dir = 0;
                            if (prevMov1 instanceof ClueMoveLeftAction) {
                                dir = LEFT;
                            } else if (prevMov1 instanceof ClueMoveUpAction) {
                                dir = UP;
                            } else if (prevMov1 instanceof ClueMoveRightAction) {
                                dir = RIGHT;
                            } else if (prevMov1 instanceof ClueMoveDownAction) {
                                dir = DOWN;
                            }

                            if (checkIfAvailableTile(curX, curY, dir)) {
                                prevMov2 = prevMov1;
                                numMoves++;
                                game.sendAction(prevMov1);
                                Log.i("Computer Player" + playerNum + " Moved", "Previous Move");
                                return;
                            } else {
                                    if (checkIfAvailableTile(curX, curY, LEFT)) {
                                        sendMoveAction(new ClueMoveLeftAction(this));
                                        Log.i("Computer Player" + playerNum + " Moved", "Left");
                                        return;
                                    } else if (checkIfAvailableTile(curX, curY, RIGHT)) {
                                        sendMoveAction(new ClueMoveRightAction(this));
                                        Log.i("Computer Player" + playerNum + " Moved", "Right");
                                        return;
                                    } else if(checkIfAvailableTile(curX, curY, UP)){
                                        sendMoveAction(new ClueMoveUpAction(this));
                                        Log.i("Computer Player" + playerNum + " Moved", "Up");
                                        return;
                                    } else if(checkIfAvailableTile(curX, curY, DOWN)){
                                        sendMoveAction(new ClueMoveDownAction(this));
                                        Log.i("Computer Player" + playerNum + " Moved", "Down");
                                        return;
                                    } else {
                                        game.sendAction(new ClueEndTurnAction(this));
                                    }
                            }
                        } else if (dY == 0 && isThereAWall(curX, curY) && !isThereADoor(curX,curY)) {
                            int dir = 0;
                            if (prevMov1 instanceof ClueMoveLeftAction) {
                                dir = 1;
                            } else if (prevMov1 instanceof ClueMoveUpAction) {
                                dir = 2;
                            } else if (prevMov1 instanceof ClueMoveRightAction) {
                                dir = 3;
                            } else if (prevMov1 instanceof ClueMoveDownAction) {
                                dir = 4;
                            }

                            if (checkIfAvailableTile(curX, curY, dir)) {
                                prevMov2 = prevMov1;
                                numMoves++;
                                game.sendAction(prevMov1);
                                Log.i("Computer Player" + playerNum + " Moved", "Previous Move");
                                return;
                            } else {
                                if (checkIfAvailableTile(curX, curY, UP)) {
                                    sendMoveAction(new ClueMoveUpAction(this));
                                    Log.i("Computer Player" + playerNum + " Moved", "Up");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, DOWN)) {
                                    sendMoveAction(new ClueMoveDownAction(this));
                                    Log.i("Computer Player" + playerNum + " Moved", "Down");
                                    return;
                                }else if (checkIfAvailableTile(curX, curY, LEFT)) {
                                    sendMoveAction(new ClueMoveLeftAction(this));
                                    Log.i("Computer Player" + playerNum + " Moved", "Left");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, RIGHT)) {
                                    sendMoveAction(new ClueMoveRightAction(this));
                                    Log.i("Computer Player" + playerNum + " Moved", "Right");
                                    return;
                                } else {
                                    game.sendAction(new ClueEndTurnAction(this));
                                }
                            }
                        } else if (dX > dY) {
                            Log.i("Computer Player" + playerNum, "dX > dY");
                            if (!dXNegative && !dYNegative) {
                                if (checkIfAvailableTile(curX, curY, RIGHT)) {
                                    sendMoveAction(new ClueMoveRightAction(this));
                                    Log.i("Computer Player" + playerNum + " Moved", "Right");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, DOWN)) {
                                    sendMoveAction(new ClueMoveDownAction(this));
                                    Log.i("Computer Player" + playerNum + " Moved", "Down");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, UP)) {
                                    sendMoveAction(new ClueMoveUpAction(this));
                                    Log.i("Computer Player" + playerNum + " Moved", "Up");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, LEFT)) {
                                    sendMoveAction(new ClueMoveLeftAction(this));
                                    Log.i("Computer Player" + playerNum + " Moved", "Left");
                                    return;
                                } else {
                                    game.sendAction(new ClueEndTurnAction(this));
                                }
                            } else if (dXNegative && !dYNegative) {
                                if (checkIfAvailableTile(curX, curY, LEFT)) {
                                    sendMoveAction(new ClueMoveLeftAction(this));
                                    Log.i("Computer Player" + playerNum + " Moved", "Left");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, DOWN)) {
                                    sendMoveAction(new ClueMoveDownAction(this));
                                    Log.i("Computer Player" + playerNum + " Moved", "Down");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, UP)) {
                                    sendMoveAction(new ClueMoveUpAction(this));
                                    Log.i("Computer Player" + playerNum + " Moved", "Up");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, RIGHT)) {
                                    sendMoveAction(new ClueMoveRightAction(this));
                                    Log.i("Computer Player" + playerNum + " Moved", "Right");
                                    return;
                                } else {
                                    game.sendAction(new ClueEndTurnAction(this));
                                }
                            } else if (!dXNegative && dYNegative) {
                                if (checkIfAvailableTile(curX, curY, RIGHT)) {
                                    sendMoveAction(new ClueMoveRightAction(this));
                                    Log.i("Computer Player" + playerNum + " Moved", "Right");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, UP)) {
                                    sendMoveAction(new ClueMoveUpAction(this));
                                    Log.i("Computer Player" + playerNum + " Moved", "Up");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, DOWN)) {
                                    sendMoveAction(new ClueMoveDownAction(this));
                                    Log.i("Computer Player" + playerNum + " Moved", "Down");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, LEFT)) {
                                    sendMoveAction(new ClueMoveLeftAction(this));
                                    Log.i("Computer Player" + playerNum + " Moved", "Left");
                                    return;
                                } else {
                                    game.sendAction(new ClueEndTurnAction(this));
                                }
                            } else if (dXNegative && dYNegative) {
                                if (checkIfAvailableTile(curX, curY, LEFT)) {
                                    sendMoveAction(new ClueMoveLeftAction(this));
                                    Log.i("Computer Player" + playerNum + " Moved", "Left");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, UP)) {
                                    sendMoveAction(new ClueMoveUpAction(this));
                                    Log.i("Computer Player" + playerNum + " Moved", "Up");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, DOWN)) {
                                    sendMoveAction(new ClueMoveDownAction(this));
                                    Log.i("Computer Player" + playerNum + " Moved", "Down");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, RIGHT)) {
                                    sendMoveAction(new ClueMoveRightAction(this));
                                    Log.i("Computer Player" + playerNum + " Moved", "Right");
                                    return;
                                } else {
                                    game.sendAction(new ClueEndTurnAction(this));
                                }
                            }
                        } else {
                            Log.i("Computer Player" + playerNum, "dY >= dX");

                                if (!dXNegative && !dYNegative) {
                                    if (checkIfAvailableTile(curX, curY, DOWN)) {
                                        sendMoveAction(new ClueMoveDownAction(this));
                                        Log.i("Computer Player" + playerNum + " Moved", "Down");
                                        return;
                                    } else if (checkIfAvailableTile(curX, curY, RIGHT)) {
                                        sendMoveAction(new ClueMoveRightAction(this));
                                        Log.i("Computer Player" + playerNum + " Moved", "Right");
                                        return;
                                    } else if (checkIfAvailableTile(curX, curY, LEFT)) {
                                        sendMoveAction(new ClueMoveLeftAction(this));
                                        Log.i("Computer Player" + playerNum + " Moved", "Left");
                                        return;
                                    } else if (checkIfAvailableTile(curX, curY, UP)) {
                                        sendMoveAction(new ClueMoveUpAction(this));
                                        Log.i("Computer Player" + playerNum + " Moved", "Up");
                                        return;
                                    }else {
                                        game.sendAction(new ClueEndTurnAction(this));
                                    }
                                } else if (!dXNegative && dYNegative) {
                                    if (checkIfAvailableTile(curX, curY, UP)) {
                                        sendMoveAction(new ClueMoveUpAction(this));
                                        Log.i("Computer Player" + playerNum + " Moved", "Up");
                                        return;
                                    } else if (checkIfAvailableTile(curX, curY, RIGHT)) {
                                        sendMoveAction(new ClueMoveRightAction(this));
                                        Log.i("Computer Player" + playerNum + " Moved", "Right");
                                        return;
                                    } else if (checkIfAvailableTile(curX, curY, LEFT)) {
                                        sendMoveAction(new ClueMoveLeftAction(this));
                                        Log.i("Computer Player" + playerNum + " Moved", "Left");
                                        return;
                                    } else if (checkIfAvailableTile(curX, curY, DOWN)) {
                                        sendMoveAction(new ClueMoveDownAction(this));
                                        Log.i("Computer Player" + playerNum + " Moved", "Down");
                                        return;
                                    }else {
                                        game.sendAction(new ClueEndTurnAction(this));
                                    }
                                } else if (dXNegative && !dYNegative) {
                                    if (checkIfAvailableTile(curX, curY, DOWN)) {
                                        sendMoveAction(new ClueMoveDownAction(this));
                                        Log.i("Computer Player" + playerNum + " Moved", "Down");
                                        return;
                                    } else if (checkIfAvailableTile(curX, curY, LEFT)) {
                                        sendMoveAction(new ClueMoveLeftAction(this));
                                        Log.i("Computer Player" + playerNum + " Moved", "Left");
                                        return;
                                    } else if (checkIfAvailableTile(curX, curY, RIGHT)) {
                                        sendMoveAction(new ClueMoveRightAction(this));
                                        Log.i("Computer Player" + playerNum + " Moved", "Right");
                                        return;
                                    } else if (checkIfAvailableTile(curX, curY, UP)) {
                                        sendMoveAction(new ClueMoveUpAction(this));
                                        Log.i("Computer Player" + playerNum + " Moved", "Up");
                                        return;
                                    }else {
                                        game.sendAction(new ClueEndTurnAction(this));
                                    }
                                } else if (dXNegative && dYNegative) {
                                    if (checkIfAvailableTile(curX, curY, UP)) {
                                        sendMoveAction(new ClueMoveUpAction(this));
                                        Log.i("Computer Player" + playerNum + " Moved", "Up");
                                        return;
                                    } else if (checkIfAvailableTile(curX, curY, LEFT)) {
                                        sendMoveAction(new ClueMoveLeftAction(this));
                                        Log.i("Computer Player" + playerNum + " Moved", "Left");
                                        return;
                                    } else if (checkIfAvailableTile(curX, curY, RIGHT)) {
                                        sendMoveAction(new ClueMoveRightAction(this));
                                        Log.i("Computer Player" + playerNum + " Moved", "Right");
                                        return;
                                    } else if (checkIfAvailableTile(curX, curY, DOWN)) {
                                        sendMoveAction(new ClueMoveDownAction(this));
                                        Log.i("Computer Player" + playerNum + " Moved", "Down");
                                        return;
                                    }else {
                                        game.sendAction(new ClueEndTurnAction(this));
                                    }
                                }
                            }
                        } else if(myState.getBoard().getBoard()[curY][curX].getIsDoor() &&
                            myState.getCanSuggest(playerNum)){
                        Log.i("Computer Player" + playerNum, "Room");
                        if (MoveOffDoor(curX, curY, UP)) { //Move Up
                            sendMoveAction(new ClueMoveUpAction(this));
                            Log.i("Computer Player" + playerNum + " Moved", "Up");
                            return;
                        }
                        if (MoveOffDoor(curX, curY, DOWN)) { //Move Down
                            sendMoveAction(new ClueMoveDownAction(this));
                            Log.i("Computer Player" + playerNum + " Moved", "Down");
                            return;
                        }
                        if (MoveOffDoor(curX, curY, LEFT)) { //Move Left
                            sendMoveAction(new ClueMoveLeftAction(this));
                            Log.i("Computer Player" + playerNum + " Moved", "Left");
                            return;
                        }
                        if (MoveOffDoor(curX, curY, RIGHT)) { //Move Right
                            sendMoveAction(new ClueMoveRightAction(this));
                            Log.i("Computer Player" + playerNum + " Moved", "Right");
                            return;
                        }else {
                            game.sendAction(new ClueEndTurnAction(this));
                        }
                    }else if(myState.getBoard().getBoard()[curY][curX].getIsDoor() &&
                            !myState.getCanSuggest(playerNum)) {
                        Log.i("Computer Player" + playerNum, "Room");

                        if (myState.getBoard().getBoard()[curY + 1][curX].getTileType() != 1 && checkIfAvailableTile(curX, curY, DOWN)) {
                            sendMoveAction(new ClueMoveDownAction(this));
                            Log.i("Computer Player" + playerNum + " Moved", "Down");
                            return;
                        } else if (myState.getBoard().getBoard()[curY - 1][curX].getTileType() != 1 && checkIfAvailableTile(curX, curY, UP)) {
                            sendMoveAction(new ClueMoveUpAction(this));
                            Log.i("Computer Player" + playerNum + " Moved", "Up");
                            return;
                        } else if (myState.getBoard().getBoard()[curY][curX - 1].getTileType() != 1 && checkIfAvailableTile(curX, curY, LEFT)) {
                            sendMoveAction(new ClueMoveLeftAction(this));
                            Log.i("Computer Player" + playerNum + " Moved", "Left");
                            return;
                        }else if(checkIfAvailableTile(curX,curY, RIGHT)) {
                            sendMoveAction(new ClueMoveRightAction(this));
                            Log.i("Computer Player" + playerNum + " Moved", "Right");
                            return;
                        }else{
                            if (checkIfAvailableTile(curX, curY, DOWN)) {
                                sendMoveAction(new ClueMoveDownAction(this));
                                Log.i("Computer Player" + playerNum + " Moved", "Down");
                                return;
                            } else if (checkIfAvailableTile(curX, curY, UP)) {
                                sendMoveAction(new ClueMoveUpAction(this));
                                Log.i("Computer Player" + playerNum + " Moved", "Up");
                                return;
                            } else if (checkIfAvailableTile(curX, curY, LEFT)) {
                                sendMoveAction(new ClueMoveLeftAction(this));
                                Log.i("Computer Player" + playerNum + " Moved", "Left");
                                return;
                            }else {
                                game.sendAction(new ClueEndTurnAction(this));
                            }
                        }
                    }

                    //check room coordinates to figure out closest room not checked in checkbox
                    //array to set roomToMoveTo to most closest room not current room
                    //also consider passageways
                } else {
                    Log.i("Computer Player "+playerNum, "End Turn");
                    game.sendAction(new ClueEndTurnAction(this));
                }
            }
        } else {
            return;
        }

        //if it enters a room, suggest random
        //if it uses all its moves and does not enter a room, end turn
    }

    //checks to see if there is a door near the AI
    private boolean isThereADoor(int curX, int curY) {
            if (myState.getBoard().getBoard()[curY+1][curX] != null &&  myState.getBoard().getBoard()[curY + 1][curX].getIsDoor()) {
                return true;
            } else if (myState.getBoard().getBoard()[curY-1][curX] != null && myState.getBoard().getBoard()[curY - 1][curX].getIsDoor()) {
                return true;
            } else if (myState.getBoard().getBoard()[curY][curX+1] != null && myState.getBoard().getBoard()[curY][curX + 1].getIsDoor()) {
                return true;
            } else if (myState.getBoard().getBoard()[curY][curX-1] != null && myState.getBoard().getBoard()[curY][curX - 1].getIsDoor()) {
                return true;
            }

            return false;
    }

    //checks to see if there is a wall near the AI including the current tile
    private boolean isThereAWall(int curX, int curY) {
            if (myState.getBoard().getBoard()[curY][curX].getTopWall()) {
                return true;
            } else if (myState.getBoard().getBoard()[curY][curX].getBottomWall()) {
                return true;
            } else if (myState.getBoard().getBoard()[curY][curX].getRightWall()) {
                return true;
            } else if (myState.getBoard().getBoard()[curY][curX].getLeftWall()) {
                return true;
            } else if (myState.getBoard().getBoard()[curY+1][curX] != null && myState.getBoard().getBoard()[curY + 1][curX].getTopWall()) {
                return true;
            } else if (myState.getBoard().getBoard()[curY-1][curX] != null && myState.getBoard().getBoard()[curY - 1][curX].getBottomWall()) {
                return true;
            } else if (myState.getBoard().getBoard()[curY][curX+1] != null && myState.getBoard().getBoard()[curY][curX + 1].getLeftWall()) {
                return true;
            } else if (myState.getBoard().getBoard()[curY][curX-1] != null && myState.getBoard().getBoard()[curY][curX - 1].getRightWall()) {
                return true;
            }

            return false;
    }

    //moves the AI off of a door tile
    //direction 1 = left, 2 = up, 3 = right, 4 = down
    private boolean MoveOffDoor(int x, int y, int direction) {
        Log.i("Computer Player" + playerNum, "Move off Door X: "+x+" Y: "+y+" Dir: "+direction);
        if(myState.getBoard().getBoard()[y][x] != null) {
            if(direction == LEFT) { //Move Left
                if(!myState.getBoard().getBoard()[y][x].getLeftWall()) {
                    if(myState.getBoard().getPlayerBoard()[y][x - 1] == -1) {
                        if(myState.getBoard().getBoard()[y][x - 1] != null && !myState.getBoard().getBoard()[y][x - 1].getRightWall() && myState.getBoard().getBoard()[y][x - 1].getTileType() == 1) {
                            return true;
                        }
                    }
                }
            } else if(direction == UP) { //Move up
                if(!myState.getBoard().getBoard()[y][x].getTopWall()) {
                    if(myState.getBoard().getPlayerBoard()[y - 1][x] == -1) {
                        if(myState.getBoard().getBoard()[y - 1][x] != null && !myState.getBoard().getBoard()[y - 1][x].getBottomWall() && myState.getBoard().getBoard()[y - 1][x].getTileType() == 1) {
                            return true;
                        }
                    }
                }
            }else if(direction == RIGHT) { //move right
                if(!myState.getBoard().getBoard()[y][x].getRightWall()) {
                    if(myState.getBoard().getPlayerBoard()[y][x + 1] == -1) {
                        if(myState.getBoard().getBoard()[y][x + 1] != null && !myState.getBoard().getBoard()[y][x + 1].getLeftWall() && myState.getBoard().getBoard()[y][x + 1].getTileType() == 1) {
                            return true;
                        }
                    }
                }
            }else if(direction == DOWN) { //move down
                if(!myState.getBoard().getBoard()[y][x].getBottomWall() && myState.getBoard().getBoard()[y][x - 1].getTileType() == 1) {
                    if(myState.getBoard().getPlayerBoard()[y + 1][x] == -1) {
                        if(myState.getBoard().getBoard()[y + 1][x] != null && !myState.getBoard().getBoard()[y + 1][x].getTopWall() && myState.getBoard().getBoard()[y + 1][x].getTileType() == 1) {
                            return true;
                        }
                    }
                }
            }
        }else {
            return false;
        }

        return false;
    }

    //sends a ClueMoveAction to the game and sets local variables
    private void sendMoveAction(ClueMoveAction c) {
        prevMov2 = prevMov1;
        prevMov1 = c;
        numMoves++;
        game.sendAction(c);
    }

    //checks to see if moving to the tile to the passed in direction of the current tile is valid
    //direction 1 = left, 2 = up, 3 = right, 4 = down
    private boolean checkIfAvailableTile(int x, int y, int direction) {
        Log.i("Computer Player" + playerNum, "X: "+x+" Y: "+y+" Dir: "+direction);

        //check to make sure that the AI doesn't get stuck moving back and forth between two squares
        if(direction == LEFT) {
            if(prevMov2 != null) {
                if(prevMov2 instanceof ClueMoveLeftAction && prevMov1 instanceof ClueMoveRightAction) {
                    return false;
                }
            }
        }else if (direction == UP) {
            if(prevMov2 != null) {
                if(prevMov2 instanceof ClueMoveUpAction && prevMov1 instanceof ClueMoveDownAction) {
                    return false;
                }
            }
        }else if(direction == RIGHT) {
            if(prevMov2 != null) {
                if(prevMov2 instanceof ClueMoveRightAction && prevMov1 instanceof ClueMoveLeftAction) {
                    return false;
                }
            }
        }else if(direction == DOWN) {
            if(prevMov2 != null) {
                if(prevMov2 instanceof ClueMoveDownAction && prevMov1 instanceof ClueMoveUpAction) {
                    return false;
                }
            }
        }

        if(myState.getBoard().getBoard()[y][x] != null) {
            if(direction == LEFT) { //Move Left
                if(!myState.getBoard().getBoard()[y][x].getLeftWall()) {
                    if(myState.getBoard().getBoard()[y ][x-1] != null) {
                        Log.i("Computer Smart" + playerNum, "Left Wall: " + myState.getBoard().getBoard()[y][x].getLeftWall() + " Right Wall" + myState.getBoard().getBoard()[y][x-1].getRightWall() +
                                " Player: " + myState.getBoard().getPlayerBoard()[y][x - 1] + " Null: " + myState.getBoard().getBoard()[y][x - 1]);
                    }
                    if(myState.getBoard().getPlayerBoard()[y][x - 1] == -1) {
                        if(myState.getBoard().getBoard()[y][x - 1] != null && !myState.getBoard().getBoard()[y][x - 1].getRightWall()) {
                            return true;
                        }
                    }
                }
            } else if(direction == UP) { //Move up
                if(!myState.getBoard().getBoard()[y][x].getTopWall()) {
                    if(myState.getBoard().getBoard()[y-1][x] != null) {
                        Log.i("Computer Smart" + playerNum, "Top Wall: " + myState.getBoard().getBoard()[y][x].getTopWall() + " Bottom Wall" + myState.getBoard().getBoard()[y - 1][x].getBottomWall() +
                                " Player: " + myState.getBoard().getPlayerBoard()[y - 1][x] + " Null: " + myState.getBoard().getBoard()[y - 1][x]);
                    }
                    if(myState.getBoard().getPlayerBoard()[y - 1][x] == -1) {
                        if(myState.getBoard().getBoard()[y - 1][x] != null && !myState.getBoard().getBoard()[y - 1][x].getBottomWall()) {
                            return true;
                        }
                    }
                }
            }else if(direction == RIGHT) { //move right
                if(!myState.getBoard().getBoard()[y][x].getRightWall()) {
                    if(myState.getBoard().getBoard()[y ][x+1] != null) {
                        Log.i("Computer Smart" + playerNum, "Left Wall: " + myState.getBoard().getBoard()[y][x+1].getLeftWall() + " Right Wall" + myState.getBoard().getBoard()[y ][x].getRightWall() +
                                " Player: " + myState.getBoard().getPlayerBoard()[y][x + 1] + " Null: " + myState.getBoard().getBoard()[y][x + 1]);
                    }
                    if(myState.getBoard().getPlayerBoard()[y][x + 1] == -1) {
                        if(myState.getBoard().getBoard()[y][x + 1] != null && !myState.getBoard().getBoard()[y][x + 1].getLeftWall()) {
                            return true;
                        }
                    }
                }
            }else if(direction == DOWN) { //move down
                if(!myState.getBoard().getBoard()[y][x].getBottomWall()) {
                    if(myState.getBoard().getBoard()[y+1][x] != null) {
                        Log.i("Computer Smart" + playerNum, "Top Wall: " + myState.getBoard().getBoard()[y+1][x].getTopWall() + " Bottom Wall" + myState.getBoard().getBoard()[y][x].getBottomWall() +
                                " Player: " + myState.getBoard().getPlayerBoard()[y + 1][x] + " Null: " + myState.getBoard().getBoard()[y + 1][x]);
                    }
                    if(myState.getBoard().getPlayerBoard()[y + 1][x] == -1) {
                        if(myState.getBoard().getBoard()[y + 1][x] != null && !myState.getBoard().getBoard()[y + 1][x].getTopWall()) {
                            return true;
                        }
                    }
                }
            }
        }else {
            return false;
        }

        return false;
    }

}
