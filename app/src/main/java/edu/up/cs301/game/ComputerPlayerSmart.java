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

public class ComputerPlayerSmart extends ClueComputerPlayer {
    //does smart AI stuff
    private boolean[] checkBoxVals = new boolean[21];
    private Card[] allCards = {Card.COL_MUSTARD, Card.PROF_PLUM, Card.MR_GREEN, Card.MRS_PEACOCK, Card.MISS_SCARLET,
            Card.MRS_WHITE, Card.KNIFE, Card.CANDLESTICK, Card.REVOLVER, Card.ROPE, Card.LEAD_PIPE,
            Card.WRENCH, Card.HALL, Card.LOUNGE, Card.DINING_ROOM, Card.KITCHEN, Card.BALLROOM,
            Card.CONSERVATORY, Card.BILLIARD_ROOM, Card.LIBRARY, Card.STUDY};
    private ClueState myState;
    private ClueSuggestionAction prevSuggestion = null;
    private int[][] doorCoordinates = {{5,10}, {7,12}, {7,13}, {6,18}, {10,18}, {13,17}, {19,20},
            {21,16}, {18,15}, {18,10}, {21,9}, {20,5}, {16,6}, {13,2}, {11,4}, {9,7}, {4,7}};
    private Card[] doorRooms = {Card.HALL, Card.HALL, Card.HALL, Card.LOUNGE, Card.DINING_ROOM,
            Card.DINING_ROOM,Card.KITCHEN, Card.BALLROOM, Card.BALLROOM, Card.BALLROOM, Card.BALLROOM,
            Card.CONSERVATORY, Card.BILLIARD_ROOM, Card.BILLIARD_ROOM, Card.LIBRARY, Card.LIBRARY, Card.STUDY};
    private boolean handChecked;
    private ClueMoveAction prevMov1;
    private ClueMoveAction prevMov2;
    private int numMoves;


    public ComputerPlayerSmart(String name) {
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
            if(myState.getTurnId() != playerNum) {
                numMoves = 0;
            }
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
                String[] validCards = new String[cards.size()];
                cards.toArray(validCards);
                if(validCards.length == 0) {
                    game.sendAction(new ClueShowCardAction(this));
                    return;
                }else {
                    Random rand1 = new Random();
                    int c = rand1.nextInt(validCards.length);
                    ClueShowCardAction s = new ClueShowCardAction(this);
                    s.setCardToShow(validCards[c]);
                    game.sendAction(s);
                    return;
                }
            }else if (myState.getTurnId() == playerNum && myState.getPlayerStillInGame(playerNum)) {
                if (myState.getCanRoll(this.playerNum)) {
                    Log.i("Computer Player"+playerNum, "Rolling");
                    game.sendAction(new ClueRollAction(this));
                    return;
                }else if(myState.getCardToShow(this.playerNum).equals("No card shown.")) {
                    ClueAccuseAction caa = new ClueAccuseAction(this);
                    caa.room = prevSuggestion.room;
                    caa.suspect = prevSuggestion.suspect;
                    caa.weapon = prevSuggestion.weapon;
                    game.sendAction(caa);
                    return;

                } else if (myState.getCanSuggest(this.playerNum) && !myState.getOnDoorTile()[playerNum] && myState.getInRoom()[playerNum]) {
                    //make suggestion

                    Card[] suspects = {Card.COL_MUSTARD, Card.PROF_PLUM, Card.MR_GREEN,
                            Card.MRS_PEACOCK, Card.MISS_SCARLET, Card.MRS_WHITE};
                    Card[] weapons = {Card.KNIFE, Card.CANDLESTICK, Card.REVOLVER, Card.ROPE,
                            Card.LEAD_PIPE, Card.WRENCH};
                    Random rand = new Random();
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


                    ClueSuggestionAction csa = new ClueSuggestionAction(this);
                    outerLoop:
                    for (int i = 0; i < 26; i++) {
                        for (int j = 0; j < 26; j++) {
                            if (myState.getBoard().getPlayerBoard()[j][i] == playerNum) {
                                csa.room = myState.getBoard().getBoard()[j][i].getRoom().getName();
                                break outerLoop;
                            }
                        }
                    }

                    csa.suspect = availableSuspects.get(rand.nextInt(availableSuspects.size())).getName();
                    csa.weapon = availableWeapons.get(rand.nextInt(availableWeapons.size())).getName();

                    Log.i("Computer Player "+playerNum,"Suggesting");
                    prevSuggestion = csa;
                    game.sendAction(csa);
                    return;

                } else if ((myState.getDieValue() != myState.getSpacesMoved() && numMoves < 8 )||
                    myState.getOnDoorTile()[playerNum]) {
                    Log.i("Computer Player" + playerNum+ " Moving", " ");
                    sleep(300);
                    Card[] rooms = {Card.HALL, Card.LOUNGE, Card.DINING_ROOM, Card.KITCHEN, Card.BALLROOM,
                            Card.CONSERVATORY, Card.BILLIARD_ROOM, Card.LIBRARY, Card.STUDY};
                    int curX = -1;
                    int curY = -1;
                    int closestX = 100;
                    int closestY = 100;

                    Card currentRoom = null; //null if not in a room, set to room if in room

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

                    if(currentRoom == Card.STUDY){
                        if(!checkBoxVals[15] && !myState.getUsedPassageway()[playerNum]){
                           game.sendAction(new ClueUsePassagewayAction(this));
                        }
                    }else if(currentRoom == Card.KITCHEN){
                        if(!checkBoxVals[20] && !myState.getUsedPassageway()[playerNum]){
                            game.sendAction(new ClueUsePassagewayAction(this));
                        }
                    }else if(currentRoom == Card.LOUNGE){
                        if(!checkBoxVals[17] && !myState.getUsedPassageway()[playerNum]){
                            game.sendAction(new ClueUsePassagewayAction(this));
                        }
                    }else if(currentRoom == Card.CONSERVATORY){
                        if(!checkBoxVals[13] && !myState.getUsedPassageway()[playerNum]){
                            game.sendAction(new ClueUsePassagewayAction(this));
                        }
                    }

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


                    int dX = closestX - curX;
                    int dY = closestY - curY;
                    boolean dXNegative = false;
                    boolean dYNegative = false;

                    if (dX < 0) {
                        dX *= -1;
                        dXNegative = true;
                    }
                    if (dY < 0) {
                        dY *= -1;
                        dYNegative = true;
                    }

                    if (myState.getBoard().getBoard()[curY][curX].getTileType() == 0 ||
                            (myState.getBoard().getBoard()[curY][curX].getTileType() == 1
                                    && !myState.getBoard().getBoard()[curY][curX].getIsDoor())) {
                        if (dX == 0 && isThereAWall(curX, curY) && !isThereADoor(curX,curY)) {
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
                                    if (checkIfAvailableTile(curX, curY, 1)) {
                                        ClueMoveLeftAction c = new ClueMoveLeftAction(this);
                                        prevMov2 = prevMov1;
                                        prevMov1 = c;
                                        numMoves++;
                                        game.sendAction(c);
                                        Log.i("Computer Player" + playerNum + " Moved", "Left");
                                        return;
                                    } else if (checkIfAvailableTile(curX, curY, 3)) {
                                        ClueMoveRightAction c = new ClueMoveRightAction(this);
                                        prevMov2 = prevMov1;
                                        prevMov1 = c;
                                        numMoves++;
                                        game.sendAction(c);
                                        Log.i("Computer Player" + playerNum + " Moved", "Right");
                                        return;
                                    } else if(checkIfAvailableTile(curX, curY, 2)){
                                        ClueMoveUpAction c = new ClueMoveUpAction(this);
                                        prevMov2 = prevMov1;
                                        prevMov1 = c;
                                        numMoves++;
                                        game.sendAction(c);
                                        Log.i("Computer Player" + playerNum + " Moved", "Up");
                                        return;
                                    } else if(checkIfAvailableTile(curX, curY, 4)){
                                        ClueMoveDownAction c = new ClueMoveDownAction(this);
                                        prevMov2 = prevMov1;
                                        prevMov1 = c;
                                        numMoves++;
                                        game.sendAction(c);
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
                                if (checkIfAvailableTile(curX, curY, 2)) {
                                    ClueMoveUpAction c = new ClueMoveUpAction(this);
                                    prevMov2 = prevMov1;
                                    prevMov1 = c;
                                    numMoves++;
                                    game.sendAction(c);
                                    Log.i("Computer Player" + playerNum + " Moved", "Up");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, 4)) {
                                    ClueMoveDownAction c = new ClueMoveDownAction(this);
                                    prevMov2 = prevMov1;
                                    prevMov1 = c;
                                    numMoves++;
                                    game.sendAction(c);
                                    Log.i("Computer Player" + playerNum + " Moved", "Down");
                                    return;
                                }else if (checkIfAvailableTile(curX, curY, 1)) {
                                    ClueMoveLeftAction c = new ClueMoveLeftAction(this);
                                    prevMov2 = prevMov1;
                                    prevMov1 = c;
                                    numMoves++;
                                    game.sendAction(c);
                                    Log.i("Computer Player" + playerNum + " Moved", "Left");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, 3)) {
                                    ClueMoveRightAction c = new ClueMoveRightAction(this);
                                    prevMov2 = prevMov1;
                                    prevMov1 = c;
                                    numMoves++;
                                    game.sendAction(c);
                                    Log.i("Computer Player" + playerNum + " Moved", "Right");
                                    return;
                                } else {
                                    game.sendAction(new ClueEndTurnAction(this));
                                }
                            }
                        } else if (dX > dY) {
                            Log.i("Computer Player" + playerNum, "dX > dY");
                            if (!dXNegative && !dYNegative) {
                                if (checkIfAvailableTile(curX, curY, 3)) {
                                    ClueMoveRightAction c = new ClueMoveRightAction(this);
                                    prevMov2 = prevMov1;
                                    prevMov1 = c;
                                    numMoves++;
                                    game.sendAction(c);
                                    Log.i("Computer Player" + playerNum + " Moved", "Right");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, 4)) {
                                    ClueMoveDownAction c = new ClueMoveDownAction(this);
                                    prevMov2 = prevMov1;
                                    prevMov1 = c;
                                    numMoves++;
                                    game.sendAction(c);
                                    Log.i("Computer Player" + playerNum + " Moved", "Down");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, 2)) {
                                    ClueMoveUpAction c = new ClueMoveUpAction(this);
                                    prevMov2 = prevMov1;
                                    prevMov1 = c;
                                    numMoves++;
                                    game.sendAction(c);
                                    Log.i("Computer Player" + playerNum + " Moved", "Up");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, 1)) {
                                    ClueMoveLeftAction c = new ClueMoveLeftAction(this);
                                    prevMov2 = prevMov1;
                                    prevMov1 = c;
                                    numMoves++;
                                    game.sendAction(c);
                                    Log.i("Computer Player" + playerNum + " Moved", "Left");
                                    return;
                                } else {
                                    game.sendAction(new ClueEndTurnAction(this));
                                }
                            } else if (dXNegative && !dYNegative) {
                                if (checkIfAvailableTile(curX, curY, 1)) {
                                    ClueMoveLeftAction c = new ClueMoveLeftAction(this);
                                    prevMov2 = prevMov1;
                                    prevMov1 = c;
                                    numMoves++;
                                    game.sendAction(c);
                                    Log.i("Computer Player" + playerNum + " Moved", "Left");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, 4)) {
                                    ClueMoveDownAction c = new ClueMoveDownAction(this);
                                    prevMov2 = prevMov1;
                                    prevMov1 = c;
                                    numMoves++;
                                    game.sendAction(c);
                                    Log.i("Computer Player" + playerNum + " Moved", "Down");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, 2)) {
                                    ClueMoveUpAction c = new ClueMoveUpAction(this);
                                    prevMov2 = prevMov1;
                                    prevMov1 = c;
                                    numMoves++;
                                    game.sendAction(c);
                                    Log.i("Computer Player" + playerNum + " Moved", "Up");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, 3)) {
                                    ClueMoveRightAction c = new ClueMoveRightAction(this);
                                    prevMov2 = prevMov1;
                                    prevMov1 = c;
                                    numMoves++;
                                    game.sendAction(c);
                                    Log.i("Computer Player" + playerNum + " Moved", "Right");
                                    return;
                                } else {
                                    game.sendAction(new ClueEndTurnAction(this));
                                }
                            } else if (!dXNegative && dYNegative) {
                                if (checkIfAvailableTile(curX, curY, 3)) {
                                    ClueMoveRightAction c = new ClueMoveRightAction(this);
                                    prevMov2 = prevMov1;
                                    prevMov1 = c;
                                    numMoves++;
                                    game.sendAction(c);
                                    Log.i("Computer Player" + playerNum + " Moved", "Right");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, 2)) {
                                    ClueMoveUpAction c = new ClueMoveUpAction(this);
                                    prevMov2 = prevMov1;
                                    prevMov1 = c;
                                    numMoves++;
                                    game.sendAction(c);
                                    Log.i("Computer Player" + playerNum + " Moved", "Up");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, 4)) {
                                    ClueMoveDownAction c = new ClueMoveDownAction(this);
                                    prevMov2 = prevMov1;
                                    prevMov1 = c;
                                    numMoves++;
                                    game.sendAction(c);
                                    Log.i("Computer Player" + playerNum + " Moved", "Down");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, 1)) {
                                    ClueMoveLeftAction c = new ClueMoveLeftAction(this);
                                    prevMov2 = prevMov1;
                                    prevMov1 = c;
                                    numMoves++;
                                    game.sendAction(c);
                                    Log.i("Computer Player" + playerNum + " Moved", "Left");
                                    return;
                                } else {
                                    game.sendAction(new ClueEndTurnAction(this));
                                }
                            } else if (dXNegative && dYNegative) {
                                if (checkIfAvailableTile(curX, curY, 1)) {
                                    ClueMoveLeftAction c = new ClueMoveLeftAction(this);
                                    prevMov2 = prevMov1;
                                    prevMov1 = c;
                                    numMoves++;
                                    game.sendAction(c);
                                    Log.i("Computer Player" + playerNum + " Moved", "Left");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, 2)) {
                                    ClueMoveUpAction c = new ClueMoveUpAction(this);
                                    prevMov2 = prevMov1;
                                    prevMov1 = c;
                                    numMoves++;
                                    game.sendAction(c);
                                    Log.i("Computer Player" + playerNum + " Moved", "Up");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, 4)) {
                                    ClueMoveDownAction c = new ClueMoveDownAction(this);
                                    prevMov2 = prevMov1;
                                    prevMov1 = c;
                                    numMoves++;
                                    game.sendAction(c);
                                    Log.i("Computer Player" + playerNum + " Moved", "Down");
                                    return;
                                } else if (checkIfAvailableTile(curX, curY, 3)) {
                                    ClueMoveRightAction c = new ClueMoveRightAction(this);
                                    prevMov2 = prevMov1;
                                    prevMov1 = c;
                                    numMoves++;
                                    game.sendAction(c);
                                    Log.i("Computer Player" + playerNum + " Moved", "Right");
                                    return;
                                } else {
                                    game.sendAction(new ClueEndTurnAction(this));
                                }
                            }
                        } else {
                            Log.i("Computer Player" + playerNum, "dY >= dX");

                                if (!dXNegative && !dYNegative) {
                                    if (checkIfAvailableTile(curX, curY, 4)) {
                                        ClueMoveDownAction c = new ClueMoveDownAction(this);
                                        prevMov2 = prevMov1;
                                        prevMov1 = c;
                                        numMoves++;
                                        game.sendAction(c);
                                        Log.i("Computer Player" + playerNum + " Moved", "Down");
                                        return;
                                    } else if (checkIfAvailableTile(curX, curY, 3)) {
                                        ClueMoveRightAction c = new ClueMoveRightAction(this);
                                        prevMov2 = prevMov1;
                                        prevMov1 = c;
                                        numMoves++;
                                        game.sendAction(c);
                                        Log.i("Computer Player" + playerNum + " Moved", "Right");
                                        return;
                                    } else if (checkIfAvailableTile(curX, curY, 1)) {
                                        ClueMoveLeftAction c = new ClueMoveLeftAction(this);
                                        prevMov2 = prevMov1;
                                        prevMov1 = c;
                                        numMoves++;
                                        game.sendAction(c);
                                        Log.i("Computer Player" + playerNum + " Moved", "Left");
                                        return;
                                    } else if (checkIfAvailableTile(curX, curY, 2)) {
                                        ClueMoveUpAction c = new ClueMoveUpAction(this);
                                        prevMov2 = prevMov1;
                                        prevMov1 = c;
                                        numMoves++;
                                        game.sendAction(c);
                                        Log.i("Computer Player" + playerNum + " Moved", "Up");
                                        return;
                                    }else {
                                        game.sendAction(new ClueEndTurnAction(this));
                                    }
                                } else if (!dXNegative && dYNegative) {
                                    if (checkIfAvailableTile(curX, curY, 2)) {
                                        ClueMoveUpAction c = new ClueMoveUpAction(this);
                                        prevMov2 = prevMov1;
                                        prevMov1 = c;
                                        numMoves++;
                                        game.sendAction(c);
                                        Log.i("Computer Player" + playerNum + " Moved", "Up");
                                        return;
                                    } else if (checkIfAvailableTile(curX, curY, 3)) {
                                        ClueMoveRightAction c = new ClueMoveRightAction(this);
                                        prevMov2 = prevMov1;
                                        prevMov1 = c;
                                        numMoves++;
                                        game.sendAction(c);
                                        Log.i("Computer Player" + playerNum + " Moved", "Right");
                                        return;
                                    } else if (checkIfAvailableTile(curX, curY, 1)) {
                                        ClueMoveLeftAction c = new ClueMoveLeftAction(this);
                                        prevMov2 = prevMov1;
                                        prevMov1 = c;
                                        numMoves++;
                                        game.sendAction(c);
                                        Log.i("Computer Player" + playerNum + " Moved", "Left");
                                        return;
                                    } else if (checkIfAvailableTile(curX, curY, 4)) {
                                        ClueMoveDownAction c = new ClueMoveDownAction(this);
                                        prevMov2 = prevMov1;
                                        prevMov1 = c;
                                        numMoves++;
                                        game.sendAction(c);
                                        Log.i("Computer Player" + playerNum + " Moved", "Down");
                                        return;
                                    }else {
                                        game.sendAction(new ClueEndTurnAction(this));
                                    }
                                } else if (dXNegative && !dYNegative) {
                                    if (checkIfAvailableTile(curX, curY, 4)) {
                                        ClueMoveDownAction c = new ClueMoveDownAction(this);
                                        prevMov2 = prevMov1;
                                        prevMov1 = c;
                                        numMoves++;
                                        game.sendAction(c);
                                        Log.i("Computer Player" + playerNum + " Moved", "Down");
                                        return;
                                    } else if (checkIfAvailableTile(curX, curY, 1)) {
                                        ClueMoveLeftAction c = new ClueMoveLeftAction(this);
                                        prevMov2 = prevMov1;
                                        prevMov1 = c;
                                        numMoves++;
                                        game.sendAction(c);
                                        Log.i("Computer Player" + playerNum + " Moved", "Left");
                                        return;
                                    } else if (checkIfAvailableTile(curX, curY, 3)) {
                                        ClueMoveRightAction c = new ClueMoveRightAction(this);
                                        prevMov2 = prevMov1;
                                        prevMov1 = c;
                                        numMoves++;
                                        game.sendAction(c);
                                        Log.i("Computer Player" + playerNum + " Moved", "Right");
                                        return;
                                    } else if (checkIfAvailableTile(curX, curY, 2)) {
                                        ClueMoveUpAction c = new ClueMoveUpAction(this);
                                        prevMov2 = prevMov1;
                                        prevMov1 = c;
                                        numMoves++;
                                        game.sendAction(c);
                                        Log.i("Computer Player" + playerNum + " Moved", "Up");
                                        return;
                                    }else {
                                        game.sendAction(new ClueEndTurnAction(this));
                                    }
                                } else if (dXNegative && dYNegative) {
                                    if (checkIfAvailableTile(curX, curY, 2)) {
                                        ClueMoveUpAction c = new ClueMoveUpAction(this);
                                        prevMov2 = prevMov1;
                                        prevMov1 = c;
                                        numMoves++;
                                        game.sendAction(c);
                                        Log.i("Computer Player" + playerNum + " Moved", "Up");
                                        return;
                                    } else if (checkIfAvailableTile(curX, curY, 1)) {
                                        ClueMoveLeftAction c = new ClueMoveLeftAction(this);
                                        prevMov2 = prevMov1;
                                        prevMov1 = c;
                                        numMoves++;
                                        game.sendAction(c);
                                        Log.i("Computer Player" + playerNum + " Moved", "Left");
                                        return;
                                    } else if (checkIfAvailableTile(curX, curY, 3)) {
                                        ClueMoveRightAction c = new ClueMoveRightAction(this);
                                        prevMov2 = prevMov1;
                                        prevMov1 = c;
                                        numMoves++;
                                        game.sendAction(c);
                                        Log.i("Computer Player" + playerNum + " Moved", "Right");
                                        return;
                                    } else if (checkIfAvailableTile(curX, curY, 4)) {
                                        ClueMoveDownAction c = new ClueMoveDownAction(this);
                                        prevMov2 = prevMov1;
                                        prevMov1 = c;
                                        numMoves++;
                                        game.sendAction(c);
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
                        if (MoveOffDoor(curX, curY, 2)) { //Move Up
                            ClueMoveUpAction c = new ClueMoveUpAction(this);
                            prevMov2 = prevMov1;
                            prevMov1 = c;
                            numMoves++;
                            game.sendAction(c);
                            Log.i("Computer Player" + playerNum + " Moved", "Up");
                            return;
                        }
                        if (MoveOffDoor(curX, curY, 4)) { //Move Down
                            ClueMoveDownAction c = new ClueMoveDownAction(this);
                            prevMov2 = prevMov1;
                            prevMov1 = c;
                            numMoves++;
                            game.sendAction(c);
                            Log.i("Computer Player" + playerNum + " Moved", "Down");
                            return;
                        }
                        if (MoveOffDoor(curX, curY, 1)) { //Move Left
                            ClueMoveLeftAction c = new ClueMoveLeftAction(this);
                            prevMov2 = prevMov1;
                            prevMov1 = c;
                            numMoves++;
                            game.sendAction(c);
                            Log.i("Computer Player" + playerNum + " Moved", "Left");
                            return;
                        }
                        if (MoveOffDoor(curX, curY, 3)) { //Move Right
                            ClueMoveRightAction c = new ClueMoveRightAction(this);
                            prevMov2 = prevMov1;
                            prevMov1 = c;
                            numMoves++;
                            game.sendAction(c);
                            Log.i("Computer Player" + playerNum + " Moved", "Right");
                            return;
                        }else {
                            game.sendAction(new ClueEndTurnAction(this));
                        }
                    }else if(myState.getBoard().getBoard()[curY][curX].getIsDoor() &&
                            !myState.getCanSuggest(playerNum)) {
                        Log.i("Computer Player" + playerNum, "Room");

                        if (myState.getBoard().getBoard()[curY + 1][curX].getTileType() != 1 && checkIfAvailableTile(curX, curY, 4)) {
                            ClueMoveDownAction c = new ClueMoveDownAction(this);
                            prevMov2 = prevMov1;
                            prevMov1 = c;
                            numMoves++;
                            game.sendAction(c);
                            Log.i("Computer Player" + playerNum + " Moved", "Down");
                            return;
                        } else if (myState.getBoard().getBoard()[curY - 1][curX].getTileType() != 1 && checkIfAvailableTile(curX, curY, 2)) {
                            ClueMoveUpAction c = new ClueMoveUpAction(this);
                            prevMov2 = prevMov1;
                            prevMov1 = c;
                            numMoves++;
                            game.sendAction(c);
                            Log.i("Computer Player" + playerNum + " Moved", "Up");
                            return;
                        } else if (myState.getBoard().getBoard()[curY][curX - 1].getTileType() != 1 && checkIfAvailableTile(curX, curY, 1)) {
                            ClueMoveLeftAction c = new ClueMoveLeftAction(this);
                            prevMov2 = prevMov1;
                            prevMov1 = c;
                            numMoves++;
                            game.sendAction(c);
                            Log.i("Computer Player" + playerNum + " Moved", "Left");
                            return;
                        }else if(checkIfAvailableTile(curX,curY, 3)) {
                            ClueMoveRightAction c = new ClueMoveRightAction(this);
                            prevMov2 = prevMov1;
                            prevMov1 = c;
                            numMoves++;
                            game.sendAction(c);
                            Log.i("Computer Player" + playerNum + " Moved", "Right");
                            return;
                        }else{
                            if (checkIfAvailableTile(curX, curY, 4)) {
                                ClueMoveDownAction c = new ClueMoveDownAction(this);
                                prevMov2 = prevMov1;
                                prevMov1 = c;
                                numMoves++;
                                game.sendAction(c);
                                Log.i("Computer Player" + playerNum + " Moved", "Down");
                                return;
                            } else if (checkIfAvailableTile(curX, curY, 2)) {
                                ClueMoveUpAction c = new ClueMoveUpAction(this);
                                prevMov2 = prevMov1;
                                prevMov1 = c;
                                numMoves++;
                                game.sendAction(c);
                                Log.i("Computer Player" + playerNum + " Moved", "Up");
                                return;
                            } else if (checkIfAvailableTile(curX, curY, 1)) {
                                ClueMoveLeftAction c = new ClueMoveLeftAction(this);
                                prevMov2 = prevMov1;
                                prevMov1 = c;
                                numMoves++;
                                game.sendAction(c);
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

    //direction 1 = left, 2 = up, 3 = right, 4 = down
    private boolean MoveOffDoor(int x, int y, int direction) {
        Log.i("Computer Player" + playerNum, "Move off Door X: "+x+" Y: "+y+" Dir: "+direction);
        if(myState.getBoard().getBoard()[y][x] != null) {
            if(direction == 1) { //Move Left
                if(!myState.getBoard().getBoard()[y][x].getLeftWall()) {
                    if(myState.getBoard().getPlayerBoard()[y][x - 1] == -1) {
                        if(myState.getBoard().getBoard()[y][x - 1] != null && !myState.getBoard().getBoard()[y][x - 1].getRightWall() && myState.getBoard().getBoard()[y][x - 1].getTileType() == 1) {
                            return true;
                        }
                    }
                }
            } else if(direction == 2) { //Move up
                if(!myState.getBoard().getBoard()[y][x].getTopWall()) {
                    if(myState.getBoard().getPlayerBoard()[y - 1][x] == -1) {
                        if(myState.getBoard().getBoard()[y - 1][x] != null && !myState.getBoard().getBoard()[y - 1][x].getBottomWall() && myState.getBoard().getBoard()[y - 1][x].getTileType() == 1) {
                            return true;
                        }
                    }
                }
            }else if(direction == 3) { //move right
                if(!myState.getBoard().getBoard()[y][x].getRightWall()) {
                    if(myState.getBoard().getPlayerBoard()[y][x + 1] == -1) {
                        if(myState.getBoard().getBoard()[y][x + 1] != null && !myState.getBoard().getBoard()[y][x + 1].getLeftWall() && myState.getBoard().getBoard()[y][x + 1].getTileType() == 1) {
                            return true;
                        }
                    }
                }
            }else if(direction == 4) { //move down
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

    //direction 1 = left, 2 = up, 3 = right, 4 = down
    private boolean checkIfAvailableTile(int x, int y, int direction) {
        Log.i("Computer Player" + playerNum, "X: "+x+" Y: "+y+" Dir: "+direction);

        //check to make sure that the AI doesn't get stuck moving back and forth between two squares
        if(direction == 1) {
            if(prevMov2 != null) {
                if(prevMov2 instanceof ClueMoveLeftAction && prevMov1 instanceof ClueMoveRightAction) {
                    return false;
                }
            }
        }else if (direction == 2) {
            if(prevMov2 != null) {
                if(prevMov2 instanceof ClueMoveUpAction && prevMov1 instanceof ClueMoveDownAction) {
                    return false;
                }
            }
        }else if(direction == 3) {
            if(prevMov2 != null) {
                if(prevMov2 instanceof ClueMoveRightAction && prevMov1 instanceof ClueMoveLeftAction) {
                    return false;
                }
            }
        }else if(direction == 4) {
            if(prevMov2 != null) {
                if(prevMov2 instanceof ClueMoveDownAction && prevMov1 instanceof ClueMoveUpAction) {
                    return false;
                }
            }
        }

        if(myState.getBoard().getBoard()[y][x] != null) {
            if(direction == 1) { //Move Left
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
            } else if(direction == 2) { //Move up
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
            }else if(direction == 3) { //move right
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
            }else if(direction == 4) { //move down
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
