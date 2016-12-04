package edu.up.cs301.game;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import edu.up.cs301.game.actionMsg.ClueAccuseAction;
import edu.up.cs301.game.actionMsg.ClueEndTurnAction;
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

public class ComputerPlayerSmart extends GameComputerPlayer {
    //does smart AI stuff
    private boolean[] checkBoxVals = new boolean[21];
    private Card[] allCards;
    private ClueSuggestionAction prevSuggestion = null;
    private int[][] roomCoordinates = {{12, 5}, {21, 4}, {21, 13}, {21,22}, {12, 22}, {4, 22},
            {4, 15}, {4, 9}, {4, 3}};


    public ComputerPlayerSmart(String name) {
        super(name);
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
            ClueState myState = (ClueState) info; //cast it
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
                }else {
                    Random rand1 = new Random();
                    int c = rand1.nextInt(validCards.length);
                    ClueShowCardAction s = new ClueShowCardAction(this);
                    s.setCardToShow(validCards[c]);
                    game.sendAction(s);
                }
            }
            boolean oneChecked = false;
            for(int i = 0; i < 21; i++) {
                boolean tempChecked = myState.getCheckBox(playerNum, i);
                if(tempChecked) {
                    oneChecked = true;
                }
            }
            if(!oneChecked) {
                allCards = new Card[21];
                int j = 0;
                for(Card card: Card.values()) {
                    allCards[j] = card;
                    j++;
                }
                j = 0;
                for(Card c: Card.values()) {
                    for(int i = 0; i < myState.getCards(playerNum).getArrayListLength(); i++) {
                        if(c.equals(myState.getCards(playerNum).getCards()[i])) {
                            checkBoxVals[j] = true;
                        }
                    }
                    j++;
                }
            }
            if (myState.getTurnId() == playerNum && myState.getPlayerStillInGame(playerNum)) {
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

                }else if (myState.getNewToRoom(this.playerNum)) {
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
                    for(int i = -5; i < 12; i++) {
                        if(!checkBoxVals[i]) {
                            availableWeapons.add(suspects[i]);
                        }
                    }


                    ClueSuggestionAction csa = new ClueSuggestionAction(this);
                    for (int i = 0; i < 26; i++) {
                        for (int j = 0; j < 26; j++) {
                            if (myState.getBoard().getPlayerBoard()[j][i] == playerNum) {
                                csa.room = myState.getBoard().getBoardArr()[j][i].getRoom().getName();
                                break;
                            }
                        }
                    }

                    csa.suspect = availableSuspects.get(rand.nextInt(availableSuspects.size())).getName();
                    csa.weapon = availableWeapons.get(rand.nextInt(availableWeapons.size())).getName();

                    Log.i("Computer Player "+playerNum,"Suggesting");
                    prevSuggestion = csa;
                    game.sendAction(csa);

                } else if (myState.getDieValue() != myState.getSpacesMoved()) {
                    int move = 0;

                    Card[] rooms = {Card.HALL, Card.LOUNGE, Card.DINING_ROOM, Card.KITCHEN, Card.BALLROOM,
                            Card.CONSERVATORY, Card.BILLIARD_ROOM, Card.LIBRARY, Card.STUDY};
                    Card roomToMoveTo = null;
                    Card currentRoom = null; //null if not in a room, set to room if in room

                    for(int i = 0; i < 9; i++) {
                        //check room coordinates to figure out closest room not checked in checkbox
                        //array to set roomToMoveTo to most closest room not current room
                        //also consider passageways
                    }

                    //still useful to send actual moves once generated
                    if (move == 1) {
                        game.sendAction(new ClueMoveLeftAction((this)));
                    } else if (move == 2) {
                        game.sendAction(new ClueMoveUpAction((this)));
                    } else if (move == 3) {
                        game.sendAction(new ClueMoveRightAction((this)));
                    } else if (move == 4) {
                        game.sendAction(new ClueMoveDownAction(this));
                    } else if (move == 5) {
                        game.sendAction(new ClueUsePassagewayAction(this));
                    }
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
}
