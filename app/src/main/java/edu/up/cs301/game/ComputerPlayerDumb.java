package edu.up.cs301.game;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import edu.up.cs301.game.actionMsg.ClueEndTurnAction;
import edu.up.cs301.game.actionMsg.ClueMoveDownAction;
import edu.up.cs301.game.actionMsg.ClueMoveLeftAction;
import edu.up.cs301.game.actionMsg.ClueMoveRightAction;
import edu.up.cs301.game.actionMsg.ClueMoveUpAction;
import edu.up.cs301.game.actionMsg.ClueRollAction;
import edu.up.cs301.game.actionMsg.ClueShowCardAction;
import edu.up.cs301.game.actionMsg.ClueSuggestionAction;
import edu.up.cs301.game.actionMsg.ClueUsePassagewayAction;
import edu.up.cs301.game.infoMsg.BindGameInfo;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.IllegalMoveInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;

/**
 * Created by Langley on 11/17/2016.
 */

public class ComputerPlayerDumb extends GameComputerPlayer {

    public ComputerPlayerDumb(String name){
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
            /*if(((ClueState)info).getTurnId() == playerID) {
                game.sendAction(new ClueEndTurnAction(this));
            }*/

                // I just commented it out to try a couple things.
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
                        /*ClueShowCardAction s = new ClueShowCardAction(this);
                        s.setCardToShow(null);
                        game.sendAction(s);
                        //game.sendAction(null);*/
                        game.sendAction(new ClueShowCardAction(this));
                    }else {
                        Random rand1 = new Random();
                        int c = rand1.nextInt(validCards.length);
                        ClueShowCardAction s = new ClueShowCardAction(this);
                        s.setCardToShow(validCards[c]);
                        game.sendAction(s);
                    }
                }

                if (myState.getTurnId() == playerNum && myState.getPlayerStillInGame(playerNum)) {
                    if (myState.getCanRoll(this.playerNum)) {
                        Log.i("Computer Player"+playerNum, "Rolling");
                        game.sendAction(new ClueRollAction(this));
                        return;
                    } else if (myState.getCanSuggest(this.playerNum) && !myState.getOnDoorTile()[playerNum]) {
                        //make suggestion

                        Card[] suspects = {Card.MISS_SCARLET, Card.COL_MUSTARD, Card.MR_GREEN, Card.MRS_PEACOCK, Card.MRS_WHITE, Card.PROF_PLUM};
                        Card[] weapons = {Card.WRENCH, Card.KNIFE, Card.CANDLESTICK, Card.REVOLVER, Card.ROPE, Card.LEAD_PIPE};
                        Random rand = new Random();


                        ClueSuggestionAction csa = new ClueSuggestionAction(this);
                        for (int i = 0; i < 26; i++) {
                            for (int j = 0; j < 26; j++) {
                                if (myState.getBoard().getPlayerBoard()[j][i] == playerNum) {
                                    csa.room = myState.getBoard().getBoardArr()[j][i].getRoom().getName();
                                    break;
                                }
                            }
                        }

                        csa.suspect = suspects[rand.nextInt(6)].getName();
                        csa.weapon = weapons[rand.nextInt(6)].getName();
                        Log.i("Computer Player "+playerNum,"Suggesting");
                        game.sendAction(csa);

                    } else if (myState.getDieValue() != myState.getSpacesMoved() || myState.getOnDoorTile()[playerNum]) {
                        Random rand = new Random();
                        int move = rand.nextInt(5) + 1;
                        Log.i("Computer Player "+playerNum, "Moving"+ move);
                        sleep(300);

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
