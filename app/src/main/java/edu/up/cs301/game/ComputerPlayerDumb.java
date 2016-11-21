package edu.up.cs301.game;

import android.util.Log;

import java.util.Random;

import edu.up.cs301.game.actionMsg.ClueEndTurnAction;
import edu.up.cs301.game.actionMsg.ClueMoveDownAction;
import edu.up.cs301.game.actionMsg.ClueMoveLeftAction;
import edu.up.cs301.game.actionMsg.ClueMoveRightAction;
import edu.up.cs301.game.actionMsg.ClueMoveUpAction;
import edu.up.cs301.game.actionMsg.ClueRollAction;
import edu.up.cs301.game.infoMsg.BindGameInfo;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.IllegalMoveInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;

/**
 * Created by Langley on 11/17/2016.
 */

public class ComputerPlayerDumb extends ClueComputerPlayer {

    public ComputerPlayerDumb(String name){
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        if(info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            return;
        }else if(info instanceof ClueState) {
                ClueState myState = (ClueState)info; //cast it
                if(myState.getTurnId() == playerID) {
                    //game.sendAction(new ClueRollAction(this));
                    game.sendAction(new ClueEndTurnAction(this));
                }


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

            }else{
            return;
        }

        //if it enters a room, suggest random
        //if it uses all its moves and does not enter a room, end turn
    }

}
