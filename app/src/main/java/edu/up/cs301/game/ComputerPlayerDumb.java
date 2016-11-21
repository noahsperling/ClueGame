package edu.up.cs301.game;

import android.util.Log;

import java.util.Random;

import edu.up.cs301.game.actionMsg.ClueEndTurnAction;
import edu.up.cs301.game.actionMsg.ClueMoveDownAction;
import edu.up.cs301.game.actionMsg.ClueMoveLeftAction;
import edu.up.cs301.game.actionMsg.ClueMoveRightAction;
import edu.up.cs301.game.actionMsg.ClueMoveUpAction;
import edu.up.cs301.game.actionMsg.ClueRollAction;
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
        return playerID;
    }

    public void setPlayerID(int newPlayerID) {
        playerID = newPlayerID;
    }

    @Override
    protected void receiveInfo(GameInfo info) {

        if(info instanceof ClueState) {
            if(((ClueState)info).getTurnId() == playerID) {
                game.sendAction(new ClueEndTurnAction(this));
            }

            /* I just commented it out to try a couple things.
                ClueState myState = (ClueState)info; //cast it
            if(myState.getTurnId() == playerID) {
                Log.i("My Turn",""+playerID);
                if (myState.getCanRoll(this.playerID)) {
                    Log.i("Roll","Rolling");
                    game.sendAction(new ClueRollAction(this));
                    return;
                }

                if(myState.getNewToRoom(playerID)){
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

                    while(myState.getAllCards().get(intGuess2).getType() != Type.PERSON){
                        int temp2 = ranCards.nextInt(21);
                        intGuess2 = temp2;
                    }

                    guess1 = myState.getAllCards().get(intGuess1);
                    guess2 = myState.getAllCards().get(intGuess2);
                    ClueSuggestionAction csa = new ClueSuggestionAction(this);
                    for(int i=0;i<26;i++){
                        for(int j=0;j<26;j++){
                            if(myState.getBoard().getPlayerBoard()[j][i]==playerID){
                                csa.room = myState.getBoard().getBoardArr()[j][i].getRoom().getName();
                                break;
                            }
                        }
                    }
                    csa.weapon = guess1.getName();
                    csa.person = guess2.getName();
                    game.sendAction(csa);
                    return;

                }else if(myState.getDieValue() != myState.getSpacesMoved()) {
                    Random rand = new Random();
                    int move = rand.nextInt(5) + 1;
                    Log.i("Moving",""+move+":"+" "+playerID);

                    if (move == 1) {
                        game.sendAction(new ClueMoveLeftAction((this)));
                        return;
                    }
                    if (move == 2) {
                        game.sendAction(new ClueMoveUpAction((this)));
                        return;
                    }
                    if (move == 3) {
                        game.sendAction(new ClueMoveRightAction((this)));
                        return;
                    }
                    if (move == 4) {
                        game.sendAction(new ClueMoveDownAction(this));
                        return;
                    }
                    if(move == 5){
                        game.sendAction(new ClueUsePassagewayAction(this));
                    }
                }else{
                    Log.i("End"," Turn");
                    game.sendAction(new ClueEndTurnAction(this));
                    return;
                }
            }*/




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

        }else {return;}

        //if it enters a room, suggest random
        //if it uses all its moves and does not enter a room, end turn
    }

}
