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
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * Created by Noah on 11/8/2016.
 */

public class ComputerPlayerSmart extends GameComputerPlayer {
    //does smart AI stuff
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
            /*if(((ClueState)info).getTurnId() == playerID) {
                game.sendAction(new ClueEndTurnAction(this));
            }*/

            // I just commented it out to try a couple things.
            ClueState myState = (ClueState) info; //cast it
            if (myState.getCheckCardToSend()[playerID]) {
                Log.i("Computer Player " + playerID, "Showing Card");
                String[] temp = myState.getSuggestCards();
                Hand tempHand = myState.getCards(playerID);
                Card[] tempCards = tempHand.getCards();
                String[] cardNames = new String[tempHand.getArrayListLength()];
                ArrayList<String> cards = new ArrayList<String>();
                for (int i = 0; i < tempHand.getArrayListLength(); i++) {
                    cardNames[i] = tempCards[i].getName();
                }

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < cardNames.length; j++) {
                        if (cardNames[j].equals(temp[i])) {
                            cards.add(temp[i]);
                        }
                    }
                }
                String[] validCards = new String[cards.size()];
                cards.toArray(validCards);
                if (validCards.length == 0) {
                        /*ClueShowCardAction s = new ClueShowCardAction(this);
                        s.setCardToShow(null);
                        game.sendAction(s);
                        //game.sendAction(null);*/
                    game.sendAction(new ClueShowCardAction(this));
                } else {
                    Random rand1 = new Random();
                    int c = rand1.nextInt(validCards.length);
                    ClueShowCardAction s = new ClueShowCardAction(this);
                    s.setCardToShow(validCards[c]);
                    game.sendAction(s);
                }
            }
            if (myState.getTurnId() == playerID && myState.getPlayerStillInGame(playerID)) {
                if (myState.getCanRoll(this.playerID)) {
                    Log.i("Computer Player" + playerID, "Rolling");
                    game.sendAction(new ClueRollAction(this));
                    return;
                } else if (myState.getNewToRoom(this.playerID)) {
                    //make suggestion
                    Card guess1;
                    Card guess2;
                    Random ranCards = new Random();
                    int intGuess1 = ranCards.nextInt(21);
                    int intGuess2 = ranCards.nextInt(21);

                    while (myState.getAllCards().get(intGuess1).getType() != Type.WEAPON) {
                        int temp1 = ranCards.nextInt(21);
                        intGuess1 = temp1;
                    }

                    while (myState.getAllCards().get(intGuess2).getType() != Type.PERSON) {
                        int temp2 = ranCards.nextInt(21);
                        intGuess2 = temp2;
                    }

                    guess1 = myState.getAllCards().get(intGuess1);
                    guess2 = myState.getAllCards().get(intGuess2);
                    ClueSuggestionAction csa = new ClueSuggestionAction(this);
                    for (int i = 0; i < 26; i++) {
                        for (int j = 0; j < 26; j++) {
                            if (myState.getBoard().getPlayerBoard()[j][i] == playerID) {
                                csa.room = myState.getBoard().getBoardArr()[j][i].getRoom().getName();
                                break;
                            }
                        }
                    }
                    csa.weapon = guess1.getName();
                    csa.suspect = guess2.getName();
                    Log.i("Computer Player " + playerID, "Suggesting");
                    game.sendAction(csa);

                } else if (myState.getDieValue() != myState.getSpacesMoved()) {
                    Random rand = new Random();
                    int move = rand.nextInt(5) + 1;
                    Log.i("Computer Player " + playerID, "Moving" + move);
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
                    Log.i("Computer Player " + playerID, "End Turn");
                    game.sendAction(new ClueEndTurnAction(this));
                }


            }//




            /*Card guess1;
            Card guess2;

            //return because it is not the AI's turn
            if (myState.getTurnId() != this.playerID) {
                return;
            }

            // delay for a second to make opponent think we're thinking
            sleep(1000);

            //get its hand in case we need to do something with this
            myState.getCards(this.playerID);


            //roll the die
            //check to see if the player can roll
            //These actions take in a GamePlayer
            if (myState.getCanRoll(this.playerID) == true) {
                game.sendAction(new ClueRollAction(this));
            }

            //random generator for move actions
            Random rand = new Random();
            int move = rand.nextInt(4);

            if (move == 1) {
                game.sendAction(new ClueMoveLeftAction((this)));
            }
            if (move == 2) {
                game.sendAction(new ClueMoveUpAction((this)));
            }
            if (move == 3) {
                game.sendAction(new ClueMoveRightAction((this)));
            }
            if (move == 4) {
                game.sendAction(new ClueMoveDownAction(this));
            }

            //if it enters a room, suggest random
            if (myState.getCanSuggest(this.playerID)) {
                Random ranCards = new Random();

                int intGuess1 = ranCards.nextInt(21);
                int intGuess2 = ranCards.nextInt(21);

                while (intGuess1 == intGuess2) {
                    int temp1 = ranCards.nextInt(21);
                    int temp2 = ranCards.nextInt(21);

                    intGuess1 = temp1;
                    intGuess2 = temp2;
                }

                guess1 = myState.getAllCards().get(intGuess1);
                guess2 = myState.getAllCards().get(intGuess2);
            }*/

        } else {
            return;
        }

        //if it enters a room, suggest random
        //if it uses all its moves and does not enter a room, end turn
    }
}
