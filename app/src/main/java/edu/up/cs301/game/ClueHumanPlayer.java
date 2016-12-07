package edu.up.cs301.game;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import edu.up.cs301.game.actionMsg.ClueAccuseAction;
import edu.up.cs301.game.actionMsg.ClueCheckAction;
import edu.up.cs301.game.actionMsg.ClueEndTurnAction;
import edu.up.cs301.game.actionMsg.ClueMoveDownAction;
import edu.up.cs301.game.actionMsg.ClueMoveLeftAction;
import edu.up.cs301.game.actionMsg.ClueMoveRightAction;
import edu.up.cs301.game.actionMsg.ClueMoveUpAction;
import edu.up.cs301.game.actionMsg.ClueRollAction;
import edu.up.cs301.game.actionMsg.ClueShowCardAction;
import edu.up.cs301.game.actionMsg.ClueSuggestionAction;
import edu.up.cs301.game.actionMsg.ClueUsePassagewayAction;
import edu.up.cs301.game.actionMsg.ClueWrittenNoteAction;
import edu.up.cs301.game.infoMsg.GameInfo;

import static android.os.SystemClock.sleep;


/**
 * Created by Noah on 11/8/2016.
 */

public class ClueHumanPlayer extends GameHumanPlayer implements GamePlayer, View.OnClickListener{

    private boolean nameSet;

    private ClueState recentState;
    private boolean checkBoxBool[];
    private Button upButton;
    private Button downButton;
    private Button leftButton;
    private Button rightButton;
    private Button endTurnButton;
    private RadioButton suggestR;
    private RadioButton showCardR;
    private RadioButton accuseR;
    private Spinner roomSpinner;
    private Spinner weaponSpinner;
    private Spinner suspectSpinner;
    private Button rollButton;
    private TextView numberOfMovesLeft;
    private TextView messageTextView;
    private TextView message2TextView;
    private TextView playerTextView;
    private Button cancelButton;
    private Button submitButton;
    private Button secretPassagewayButton;
    private EditText notesGUI;

    //Check Boxes!!
    private CheckBox colonelMustardCheck;
    private CheckBox professorPlumCheck;
    private CheckBox mrGreenCheck;
    private CheckBox mrsPeacockCheck;
    private CheckBox missScarletCheck;
    private CheckBox mrsWhiteCheck;
    private CheckBox knifeCheck;
    private CheckBox candlestickCheck;
    private CheckBox revolverCheck;
    private CheckBox ropeCheck;
    private CheckBox leadPipeCheck;
    private CheckBox wrenchCheck;
    private CheckBox hallCheck;
    private CheckBox loungeCheck;
    private CheckBox diningRoomCheck;
    private CheckBox kitchenCheck;
    private CheckBox ballroomCheck;
    private CheckBox conservatoryCheck;
    private CheckBox billiardRoomCheck;
    private CheckBox libraryCheck;
    private CheckBox studyCheck;

    private ClueBoardView boardView;
    private ClueCardView cardView;

    private Activity myActivity;

    private int layoutID;
    private boolean notSent = false;

    public ClueHumanPlayer(String initName, int initID)
    {
        super(initName);

        //Set boolean array to false initially!  When they are checked they will be set to true.
        checkBoxBool = new boolean[21];
        layoutID = initID;
        nameSet = false;
    }

    @Override
    public View getTopView() {
        return null;
    }

    public int getID()
    {
        return playerNum;
    }

    public void setPlayerID(int newPlayerID) {
        playerNum = newPlayerID;
    }

    public void setAsGui(GameMainActivity g) {
        myActivity = g;
        g.setContentView(layoutID);
        boardView = (ClueBoardView) myActivity.findViewById(R.id.boardView);
        if(recentState != null) {
            boardView.updateBoard(recentState.getBoard());
        }
        cardView = (ClueCardView) myActivity.findViewById(R.id.playerHandView);

        //GUI button/spinner/radio button declarations, listeners and default configurations

        upButton = (Button)myActivity.findViewById(R.id.upButton);
        upButton.setOnClickListener(this);
        //upButton.setEnabled(false);
        upButton.setEnabled(true);

        downButton = (Button)myActivity.findViewById(R.id.downButton);
        downButton.setOnClickListener(this);
        downButton.setEnabled(true);

        leftButton = (Button)myActivity.findViewById(R.id.leftButton);
        leftButton.setOnClickListener(this);
        leftButton.setEnabled(true);

        rightButton = (Button)myActivity.findViewById(R.id.rightButton);
        rightButton.setOnClickListener(this);
        rightButton.setEnabled(true);

        endTurnButton = (Button)myActivity.findViewById(R.id.endTurnButton);
        endTurnButton.setOnClickListener(this);
        endTurnButton.setEnabled(true);

        suggestR = (RadioButton)myActivity.findViewById(R.id.radioSuggestButton);
        suggestR.setOnClickListener(this);
        suggestR.setEnabled(false);
        suggestR.setChecked(false);

        accuseR = (RadioButton)myActivity.findViewById(R.id.radioAccuseButton);
        accuseR.setOnClickListener(this);
        accuseR.setEnabled(false);
        accuseR.setChecked(false);

        showCardR = (RadioButton)myActivity.findViewById(R.id.radioShowCardButton);
        showCardR.setOnClickListener(this);
        showCardR.setChecked(false);
        showCardR.setEnabled(false);

        rollButton = (Button)myActivity.findViewById(R.id.rollButton);
        rollButton.setOnClickListener(this);
        rollButton.setEnabled(true);

        cancelButton = (Button)myActivity.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(this);

        submitButton = (Button)myActivity.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);

        secretPassagewayButton = (Button)myActivity.findViewById(R.id.secretPassagewayButton);
        secretPassagewayButton.setOnClickListener(this);
        secretPassagewayButton.setEnabled(false);

        //spinners
        roomSpinner = (Spinner)myActivity.findViewById(R.id.roomSpinner);

        weaponSpinner = (Spinner)myActivity.findViewById(R.id.weaponSpinner);

        suspectSpinner = (Spinner)myActivity.findViewById(R.id.suspectSpinner);
//        suspectSpinner.setAdapter(suspectAdapter);

        //Set up the spinners with the default configuration with all of the rooms, weapons and suspects
        setSpinners();

        //CheckBoxes!!
        colonelMustardCheck = (CheckBox)myActivity.findViewById(R.id.colMustardCheckBox);
        colonelMustardCheck.setOnClickListener(this);

        professorPlumCheck = (CheckBox)myActivity.findViewById(R.id.profPlumCheckBox);
        professorPlumCheck.setOnClickListener(this);

        mrGreenCheck = (CheckBox)myActivity.findViewById(R.id.mrGreenCheckBox);
        mrGreenCheck.setOnClickListener(this);

        mrsPeacockCheck = (CheckBox)myActivity.findViewById(R.id.mrsPeacockCheckBox);
        mrsPeacockCheck.setOnClickListener(this);

        missScarletCheck = (CheckBox)myActivity.findViewById(R.id.missScarletCheckBox);
        missScarletCheck.setOnClickListener(this);

        mrsWhiteCheck = (CheckBox)myActivity.findViewById(R.id.mrsWhiteCheckBox);
        mrsWhiteCheck.setOnClickListener(this);

        knifeCheck = (CheckBox)myActivity.findViewById(R.id.knifeCheckBox);
        knifeCheck.setOnClickListener(this);

        candlestickCheck = (CheckBox)myActivity.findViewById(R.id.candlestickCheckBox);
        candlestickCheck.setOnClickListener(this);

        revolverCheck = (CheckBox)myActivity.findViewById(R.id.revolverCheckbox);
        revolverCheck.setOnClickListener(this);

        ropeCheck = (CheckBox)myActivity.findViewById(R.id.ropeCheckBox);
        ropeCheck.setOnClickListener(this);

        leadPipeCheck = (CheckBox)myActivity.findViewById(R.id.leadPipeCheckBox);
        leadPipeCheck.setOnClickListener(this);

        wrenchCheck = (CheckBox)myActivity.findViewById(R.id.wrenchCheckBox);
        wrenchCheck.setOnClickListener(this);

        hallCheck = (CheckBox)myActivity.findViewById(R.id.hallCheckBox);
        hallCheck.setOnClickListener(this);

        loungeCheck = (CheckBox)myActivity.findViewById(R.id.loungeCheckBox);
        loungeCheck.setOnClickListener(this);

        diningRoomCheck = (CheckBox)myActivity.findViewById(R.id.diningRoomCheckBox);
        diningRoomCheck.setOnClickListener(this);

        kitchenCheck = (CheckBox)myActivity.findViewById(R.id.kitchenCheckBox);
        kitchenCheck.setOnClickListener(this);

        ballroomCheck = (CheckBox)myActivity.findViewById(R.id.ballRoomCheckBox);
        ballroomCheck.setOnClickListener(this);

        conservatoryCheck = (CheckBox)myActivity.findViewById(R.id.conservatoryCheckBox);
        conservatoryCheck.setOnClickListener(this);

        billiardRoomCheck = (CheckBox)myActivity.findViewById(R.id.billiardCheckBox);
        billiardRoomCheck.setOnClickListener(this);

        libraryCheck = (CheckBox)myActivity.findViewById(R.id.libraryCheckBox);
        libraryCheck.setOnClickListener(this);

        studyCheck = (CheckBox)myActivity.findViewById(R.id.studyCheckBox);
        studyCheck.setOnClickListener(this);

        //Text view for moves left, displayed above move arrows
        numberOfMovesLeft = (TextView)myActivity.findViewById(R.id.numberOfMovesTextView);
        numberOfMovesLeft.setText(0+"");

        //Text view that displays what cards are shown to the player above the secret passageway button
        messageTextView = (TextView)myActivity.findViewById(R.id.messageTextView);
        messageTextView.setText("");

        message2TextView = (TextView)myActivity.findViewById(R.id.message2TextView);
        message2TextView.setText("");

        //Text view that says who you are, displayed above the checkboxes
        playerTextView = (TextView)myActivity.findViewById(R.id.playerTextView);
        playerTextView.setText("You are a human\n player with a \nundetermined name.");

        //Editable text box below the end turn button
        notesGUI = (EditText)myActivity.findViewById(R.id.editText);
        notesGUI.setOnClickListener(this);
    }

    @Override
    public void receiveInfo(GameInfo info)
    {
        //Display the correct character for the human player
        if(info instanceof ClueState) {
            recentState = new ClueState((ClueState)info);
            if(!nameSet) {
                switch (playerNum) {
                    case 0:
                        playerTextView.setText("You are Miss\n Scarlet.\n");
                        break;
                    case 1:
                        playerTextView.setText("You are Colonel\n Mustard.\n");
                        break;
                    case 2:
                        playerTextView.setText("You are Mrs.\n White.\n");
                        break;
                    case 3:
                        playerTextView.setText("You are Mr.\n Green.\n");
                        break;
                    case 4:
                        playerTextView.setText("You are Mrs.\n Peacock.\n");
                        break;
                    case 5:
                        playerTextView.setText("You are \nProfessor Plum.\n");
                        break;
                }
                nameSet = true;
            }

            if(!recentState.getPlayerStillInGame(playerNum)) {
                //the player is out of the game, so disable all non-essential GUI things
                endTurnButton.setEnabled(false);
                rollButton.setEnabled(false);
                upButton.setEnabled(false);
                downButton.setEnabled(false);
                leftButton.setEnabled(false);
                rightButton.setEnabled(false);
                secretPassagewayButton.setEnabled(false);
                //disable and uncheck
                suggestR.setEnabled(false);
                accuseR.setEnabled(false);
                suggestR.setChecked(false);
                accuseR.setChecked(false);
                //continue disabling
                colonelMustardCheck.setEnabled(false);
                professorPlumCheck.setEnabled(false);
                mrGreenCheck.setEnabled(false);
                mrsPeacockCheck.setEnabled(false);
                missScarletCheck.setEnabled(false);
                mrsWhiteCheck.setEnabled(false);
                knifeCheck.setEnabled(false);
                candlestickCheck.setEnabled(false);
                revolverCheck.setEnabled(false);
                ropeCheck.setEnabled(false);
                leadPipeCheck.setEnabled(false);
                wrenchCheck.setEnabled(false);
                hallCheck.setEnabled(false);
                loungeCheck.setEnabled(false);
                diningRoomCheck.setEnabled(false);
                kitchenCheck.setEnabled(false);
                ballroomCheck.setEnabled(false);
                conservatoryCheck.setEnabled(false);
                billiardRoomCheck.setEnabled(false);
                libraryCheck.setEnabled(false);
                studyCheck.setEnabled(false);
                recentState.setCardToShow("\n You Lost!", playerNum);
                setSolutionSpinners(recentState.getSolution());
            }

        //if another player made a suggestion
        if(recentState.getCheckCardToSend()[playerNum]) {
            notSent = true;
            suggestR.setChecked(false);
            accuseR.setChecked(false);
            suggestR.setEnabled(false);
            accuseR.setEnabled(false);
            showCardR.setEnabled(true);
            showCardR.setChecked(true);

            setSendCardSpinners();
        }else if(!recentState.getCheckCardToSend()[playerNum]) {
            suggestR.setEnabled(true);
            accuseR.setEnabled(true);
            showCardR.setChecked(false);
            showCardR.setEnabled(false);
            setSpinners();
        }


            boolean corner[] = recentState.getInCornerRoom();
            boolean usedPassage[] = recentState.getUsedPassageway();
            boolean room[] = recentState.getInRoom();

        // initial set up for human player's turn
        if(recentState.getTurnId() == playerNum && recentState.getPlayerStillInGame(playerNum)) {

            accuseR.setEnabled(true);
            accuseR.setChecked(false);
            submitButton.setEnabled(true);
            cancelButton.setEnabled(true);
            endTurnButton.setEnabled(true);
            upButton.setEnabled(true);
            downButton.setEnabled(true);
            leftButton.setEnabled(true);
            rightButton.setEnabled(true);
            if(recentState.getCanRoll(playerNum)) {
                rollButton.setEnabled(true);
            }else if(!recentState.getCanRoll(playerNum)) {
                rollButton.setEnabled(false);
            }

            //if the player is in a corner room and has not used the secret passageway, enable the button
            //If the player has already used the secret passageway, disable the button
            if (corner[playerNum] && !usedPassage[playerNum])
            {
                secretPassagewayButton.setEnabled(true);
            }
            else {
                secretPassagewayButton.setEnabled(false);
            }
        }else if(recentState.getTurnId() == playerNum && !recentState.getPlayerStillInGame(playerNum) && !notSent){
            endTurnButton.setEnabled(false);
            //sleep(300);
            game.sendAction(new ClueEndTurnAction(this));
        }
        else if(recentState.getTurnId() != playerNum && recentState.getPlayerStillInGame(playerNum))
        {

            accuseR.setEnabled(false);
            accuseR.setChecked(false);
            submitButton.setEnabled(true);
            cancelButton.setEnabled(true);
            endTurnButton.setEnabled(false);
            upButton.setEnabled(false);
            downButton.setEnabled(false);
            leftButton.setEnabled(false);
            rightButton.setEnabled(false);
            rollButton.setEnabled(false);
            secretPassagewayButton.setEnabled(false);

        }

            //suggest and accuse radio buttons handled
            //Log.i("New to room = " + recentState.getNewToRoom(playerNum), " ");
            //Log.i("Room = " + room[playerNum], " ");
            if (room[playerNum] && recentState.getNewToRoom(playerNum)) {
                //Log.i("Got to suggest if", " ");
                suggestR.setEnabled(true);
                suggestR.setChecked(false);
            } else {
                //Log.i("Got to suggest else", " " + this.getPlayerID());
                suggestR.setEnabled(false);
                suggestR.setChecked(false);
            }

            messageTextView.setText("Card: " + recentState.getCardToShow(playerNum));
            message2TextView.setText("Shown By: " + setPlayerWhoShowedCardName(recentState.getPlayerWhoShowedCard()));

            boardView.updateBoard(recentState.getBoard());
            boardView.invalidate();
            cardView.updateCards(recentState.getCards(playerNum));
            cardView.invalidate();
            numberOfMovesLeft.setText(recentState.getDieValue() - recentState.getSpacesMoved() + "");
        }
    }

    public void onClick(View view)
    {
        //Move player actions
        if (game == null) {return;}

            //Send the appropriate actions for whatever is touched on the GUI

            //Roll and move buttons
            if (view == upButton) {
                ClueMoveUpAction up = new ClueMoveUpAction(this);
                game.sendAction(up);
            } else if (view == downButton) {
                ClueMoveDownAction down = new ClueMoveDownAction(this);
                game.sendAction(down);
            } else if (view == leftButton) {
                ClueMoveLeftAction left = new ClueMoveLeftAction(this);
                game.sendAction(left);
            } else if (view == rightButton) {
                ClueMoveRightAction right = new ClueMoveRightAction(this);
                game.sendAction(right);
            } else if (view == rollButton) {
                ClueRollAction roll = new ClueRollAction(this);
                rollButton.setEnabled(false);
                game.sendAction(roll);
            }

            //for radio buttons
            else if (view.getId() == R.id.radioAccuseButton) {
                suggestR.setChecked(false);
                accuseR.setChecked(true);
                setSpinners();
            } else if (view.getId() == R.id.radioSuggestButton) {
                suggestR.setChecked(true);
                accuseR.setChecked(false);
                showCardR.setChecked(false);
                setSuggestSpinners();
                roomSpinner.setEnabled(true);
                weaponSpinner.setEnabled(true);
                suspectSpinner.setEnabled(true);

                //Log.i("On door: " + recentState.getOnDoorTile()[playerNum], " ");
                if(recentState.getOnDoorTile()[playerNum])
                {
                    //Log.i("On door: " + recentState.getOnDoorTile()[playerNum], " ");
                    submitButton.setEnabled(false);
                }
                else
                {
                    submitButton.setEnabled(true);
                }


        }
        else if (view.getId() == R.id.radioShowCardButton) {
                setSpinners();
        }
        //no showCardR onclick listener because it is just to show the user that they need to choose
        //cards in the spinners to show a card to a player who has made a suggestion

            else if (view.getId() == R.id.cancelButton) {
                suggestR.setChecked(false);
                accuseR.setChecked(false);
            } else if (view.getId() == R.id.submitButton) {
                if (suggestR.isChecked() == true) {


                    int[][] board = recentState.getBoard().getPlayerBoard();
                    int x = 0;
                    int y = 0;
                    for(int i=0;i<27;i++){
                        for(int j=0;j<27;j++){
                            if(board[j][i] == playerNum){
                                x = i;
                                y = j;
                            }
                        }
                    }
                    if(recentState.getBoard().getBoardArr()[y][x].getRoom() == null){
                        return;
                    }

                    String roomSelect = recentState.getBoard().getBoardArr()[y][x].getRoom().getName();
                    String weaponSelect = weaponSpinner.getSelectedItem().toString();
                    String suspectSelect = suspectSpinner.getSelectedItem().toString();

                    ClueSuggestionAction suggest = new ClueSuggestionAction(this);
                    suggest.room = roomSelect;
                    suggest.weapon = weaponSelect;
                    suggest.suspect = suspectSelect;
                    //Log.i("suggest action sent", " ");
                    game.sendAction(suggest);

                    showCardR.setEnabled(true);
                    showCardR.setChecked(false);
                    suggestR.setEnabled(false);
                    suggestR.setChecked(false);
                    accuseR.setEnabled(false);
                    accuseR.setChecked(false);

                    //ClueEndTurnAction end = new ClueEndTurnAction(this);
                    endTurnButton.setEnabled(false);
                    //game.sendAction(end);

                //arraylist to array

                } else if (accuseR.isChecked() == true) {
                    String roomSelect = roomSpinner.getSelectedItem().toString();
                    String weaponSelect = weaponSpinner.getSelectedItem().toString();
                    String suspectSelect = suspectSpinner.getSelectedItem().toString();

                    ClueAccuseAction accuse = new ClueAccuseAction(this);
                    accuse.room = roomSelect;
                    accuse.weapon = weaponSelect;
                    accuse.suspect = suspectSelect;
                    accuseR.setEnabled(false);
                    //Log.i("accuse action sent", " ");
                    game.sendAction(accuse);
                } else if (showCardR.isChecked() == true) {
                    showCardR.setEnabled(false);
                    showCardR.setChecked(false);
                    ClueShowCardAction showCard = new ClueShowCardAction(this);

                    //use only one spinner for all the cards that can be shown
                    String showCardString = roomSpinner.getSelectedItem().toString();
                    showCard.setCardToShow(showCardString);
                    game.sendAction(showCard);
                    notSent = false;
                }

            }
            //secret passageway button
            else if (view == secretPassagewayButton) {
                ClueUsePassagewayAction passageway = new ClueUsePassagewayAction(this);
                game.sendAction(passageway);
                secretPassagewayButton.setEnabled(false);
            }

            //end turn button
            else if (view == endTurnButton) {
                ClueEndTurnAction endTurn = new ClueEndTurnAction(this);
                //Log.i("You clicked End Turn", "YAY");
                endTurnButton.setEnabled(false);
                rollButton.setEnabled(false);
                secretPassagewayButton.setEnabled(false);
                suggestR.setEnabled(false);
                accuseR.setEnabled(false);
                submitButton.setEnabled(false);
                cancelButton.setEnabled(false);
                upButton.setEnabled(false);
                downButton.setEnabled(false);
                leftButton.setEnabled(false);
                rightButton.setEnabled(false);
                game.sendAction(endTurn);
            }
            //note edit text
            else if (view == notesGUI) {
                ClueWrittenNoteAction writtenNote = new ClueWrittenNoteAction(this);
                writtenNote.note = notesGUI.getText().toString();
                game.sendAction(writtenNote);
            }

        //CheckBoxes
            //Once a checkbox is checked, set the corresponding boolean array elements to true or false
        else if (view == colonelMustardCheck)
        {
            ClueCheckAction checkAct = new ClueCheckAction(this);
            checkBoxBool[0] = !checkBoxBool[0];
            colonelMustardCheck.setChecked(checkBoxBool[0]);
            game.sendAction(checkAct);
        }
        else if (view == professorPlumCheck)
        {
            ClueCheckAction checkAct = new ClueCheckAction(this);
            checkBoxBool[1] = !checkBoxBool[1];
            professorPlumCheck.setChecked(checkBoxBool[1]);
            game.sendAction(checkAct);
        }
        else if (view == mrGreenCheck)
        {
            ClueCheckAction checkAct = new ClueCheckAction(this);
            checkBoxBool[2] = !checkBoxBool[2];
            mrGreenCheck.setChecked(checkBoxBool[2]);
            game.sendAction(checkAct);
        }
        else if (view == mrsPeacockCheck)
        {
            ClueCheckAction checkAct = new ClueCheckAction(this);
            checkBoxBool[3] = !checkBoxBool[3];
            mrsPeacockCheck.setChecked(checkBoxBool[3]);
            game.sendAction(checkAct);
        }
        else if (view == missScarletCheck)
        {
            ClueCheckAction checkAct = new ClueCheckAction(this);
            checkBoxBool[4] = !checkBoxBool[4];
            missScarletCheck.setChecked(checkBoxBool[4]);
            game.sendAction(checkAct);
        } else if (view == mrsWhiteCheck)
        {
            ClueCheckAction checkAct = new ClueCheckAction(this);
            checkBoxBool[5] = !checkBoxBool[5];
            mrsWhiteCheck.setChecked(checkBoxBool[5]);
            game.sendAction(checkAct);
        } else if (view == knifeCheck) {
            ClueCheckAction checkAct = new ClueCheckAction(this);
            checkBoxBool[6] = !checkBoxBool[6];
            knifeCheck.setChecked(checkBoxBool[6]);
            game.sendAction(checkAct);
        } else if (view == candlestickCheck) {
            ClueCheckAction checkAct = new ClueCheckAction(this);
            checkBoxBool[7] = !checkBoxBool[7];
            candlestickCheck.setChecked(checkBoxBool[7]);
            game.sendAction(checkAct);
            } else if (view == revolverCheck) {
                ClueCheckAction checkAct = new ClueCheckAction(this);
                checkBoxBool[8] = !checkBoxBool[8];
                revolverCheck.setChecked(checkBoxBool[8]);
                game.sendAction(checkAct);
            } else if (view == ropeCheck) {
                ClueCheckAction checkAct = new ClueCheckAction(this);
                checkBoxBool[9] = !checkBoxBool[9];
                ropeCheck.setChecked(checkBoxBool[9]);
                game.sendAction(checkAct);
            } else if (view == leadPipeCheck) {
                ClueCheckAction checkAct = new ClueCheckAction(this);
                checkBoxBool[10] = !checkBoxBool[10];
                leadPipeCheck.setChecked(checkBoxBool[10]);
                game.sendAction(checkAct);
            } else if (view == wrenchCheck) {
                ClueCheckAction checkAct = new ClueCheckAction(this);
                checkBoxBool[11] = !checkBoxBool[11];
                wrenchCheck.setChecked(checkBoxBool[11]);
                game.sendAction(checkAct);
            } else if (view == hallCheck) {
                ClueCheckAction checkAct = new ClueCheckAction(this);
                checkBoxBool[12] = !checkBoxBool[12];
                hallCheck.setChecked(checkBoxBool[12]);
                game.sendAction(checkAct);
            } else if (view == loungeCheck) {
                ClueCheckAction checkAct = new ClueCheckAction(this);
                checkBoxBool[13] = !checkBoxBool[13];
                loungeCheck.setChecked(checkBoxBool[13]);
                game.sendAction(checkAct);
            } else if (view == diningRoomCheck) {
                ClueCheckAction checkAct = new ClueCheckAction(this);
                checkBoxBool[14] = !checkBoxBool[14];
                diningRoomCheck.setChecked(checkBoxBool[14]);
                game.sendAction(checkAct);
            } else if (view == kitchenCheck) {
                ClueCheckAction checkAct = new ClueCheckAction(this);
                checkBoxBool[15] = !checkBoxBool[15];
                kitchenCheck.setChecked(checkBoxBool[15]);
                game.sendAction(checkAct);
            } else if (view == ballroomCheck) {
                ClueCheckAction checkAct = new ClueCheckAction(this);
                checkBoxBool[16] = !checkBoxBool[16];
                ballroomCheck.setChecked(checkBoxBool[16]);
                game.sendAction(checkAct);
            } else if (view == conservatoryCheck) {
                ClueCheckAction checkAct = new ClueCheckAction(this);
                checkBoxBool[17] = !checkBoxBool[17];
                conservatoryCheck.setChecked(checkBoxBool[17]);
                game.sendAction(checkAct);
            } else if (view == billiardRoomCheck) {
                ClueCheckAction checkAct = new ClueCheckAction(this);
                checkBoxBool[18] = !checkBoxBool[18];
                billiardRoomCheck.setChecked(checkBoxBool[18]);
                game.sendAction(checkAct);
            } else if (view == libraryCheck) {
                ClueCheckAction checkAct = new ClueCheckAction(this);
                checkBoxBool[19] = !checkBoxBool[19];
                libraryCheck.setChecked(checkBoxBool[19]);
                game.sendAction(checkAct);
            } else if (view == studyCheck) {
                ClueCheckAction checkAct = new ClueCheckAction(this);
                checkBoxBool[20] = !checkBoxBool[20];
                studyCheck.setChecked(checkBoxBool[20]);
                game.sendAction(checkAct);
            } else {
                return;
            }
    }

    public int getPlayerID() {
        return playerNum;
    }

    public boolean[] getCheckBoxArray() {
        boolean temp[] = new boolean[21];
        for(int i = 0; i < 21; i++) {
            temp[i] = checkBoxBool[i];
        }
        return temp;
    }

    public String setPlayerWhoShowedCardName (int playerID) {
        if (playerID == 0) {
            return "Miss Scarlet";
        } else if (playerID == 1) {
            return "Col. Mustard";
        } else if (playerID == 2) {
            return "Mrs. White";
        } else if (playerID == 3) {
            return "Mr. Green";
        } else if (playerID == 4) {
            return "Mrs. Peacock";
        } else if (playerID == 5) {
            return "Prof. Plum";
        } else {
            return "";
        }
    }

    /*
    Method that sets the initial spinners with all of the rooms, weapons, and suspects
     */
    public void setSpinners(){
        String[] roomItems = new String[]{Card.BALLROOM.getName(),Card.BILLIARD_ROOM.getName(), Card.CONSERVATORY.getName(),
                Card.DINING_ROOM.getName(), Card.HALL.getName(), Card.KITCHEN.getName(), Card.LIBRARY.getName(), Card.LOUNGE.getName(), Card.STUDY.getName()};
        String[] weaponItems = new String[]{Card.CANDLESTICK.getName(), Card.KNIFE.getName(), Card.LEAD_PIPE.getName(), Card.REVOLVER.getName(), Card.ROPE.getName(),Card.WRENCH.getName()};
        String[] suspectItem = new String []{Card.MR_GREEN.getName(), Card.COL_MUSTARD.getName(),Card.MRS_PEACOCK.getName(), Card.PROF_PLUM.getName(), Card.MISS_SCARLET.getName(), Card.MRS_WHITE.getName()};
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<String>(myActivity, android.R.layout.simple_spinner_dropdown_item, roomItems);
        ArrayAdapter<String> weaponAdapter = new ArrayAdapter<String>(myActivity, android.R.layout.simple_spinner_dropdown_item, weaponItems);
        ArrayAdapter<String> suspectAdapter = new ArrayAdapter<String>(myActivity,android.R.layout.simple_spinner_dropdown_item, suspectItem );

        roomSpinner.setAdapter(roomAdapter);
        weaponSpinner.setAdapter(weaponAdapter);
        suspectSpinner.setAdapter(suspectAdapter);

        submitButton.setEnabled(true);

    }
    /*
    Method that sets the spinners with all of the weapons and suspects and the room spinner is set to the current room
     */
    public void setSuggestSpinners() {
        String[] roomItems = new String[]{"Current Room"};
        String[] weaponItems = new String[]{Card.CANDLESTICK.getName(), Card.KNIFE.getName(), Card.LEAD_PIPE.getName(), Card.REVOLVER.getName(), Card.ROPE.getName(), Card.WRENCH.getName()};
        String[] suspectItem = new String[]{Card.MR_GREEN.getName(), Card.COL_MUSTARD.getName(), Card.MRS_PEACOCK.getName(), Card.PROF_PLUM.getName(), Card.MISS_SCARLET.getName(), Card.MRS_WHITE.getName()};
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<String>(myActivity, android.R.layout.simple_spinner_dropdown_item, roomItems);
        ArrayAdapter<String> weaponAdapter = new ArrayAdapter<String>(myActivity, android.R.layout.simple_spinner_dropdown_item, weaponItems);
        ArrayAdapter<String> suspectAdapter = new ArrayAdapter<String>(myActivity, android.R.layout.simple_spinner_dropdown_item, suspectItem);

        roomSpinner.setAdapter(roomAdapter);
        weaponSpinner.setAdapter(weaponAdapter);
        suspectSpinner.setAdapter(suspectAdapter);
    }

    /*
    Method that sets the spinners to display the solution when a player accuses
     */
    private void setSolutionSpinners(Card[] solution) {
        String[] roomItems = new String[]{Card.BALLROOM.getName(),Card.BILLIARD_ROOM.getName(), Card.CONSERVATORY.getName(),
                Card.DINING_ROOM.getName(), Card.HALL.getName(), Card.KITCHEN.getName(), Card.LIBRARY.getName(), Card.LOUNGE.getName(), Card.STUDY.getName()};
        String[] weaponItems = new String[]{Card.CANDLESTICK.getName(), Card.KNIFE.getName(), Card.LEAD_PIPE.getName(), Card.REVOLVER.getName(), Card.ROPE.getName(),Card.WRENCH.getName()};
        String[] suspectItem = new String []{Card.MR_GREEN.getName(), Card.COL_MUSTARD.getName(),Card.MRS_PEACOCK.getName(), Card.PROF_PLUM.getName(), Card.MISS_SCARLET.getName(), Card.MRS_WHITE.getName()};
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<String>(myActivity, android.R.layout.simple_spinner_dropdown_item, roomItems);
        ArrayAdapter<String> weaponAdapter = new ArrayAdapter<String>(myActivity, android.R.layout.simple_spinner_dropdown_item, weaponItems);
        ArrayAdapter<String> suspectAdapter = new ArrayAdapter<String>(myActivity,android.R.layout.simple_spinner_dropdown_item, suspectItem );

        int suspect = 0;
        int weapon = 0;
        int room = 0;
        for(int i = 0; i<suspectItem.length;i++) {
            if (solution[0].getName().equals(suspectItem[i])) { //suspect
                suspect = i;
            }

            if (solution[1].getName().equals(weaponItems[i])) { //weapon
                weapon = i;
            }

            if (solution[2].getName().equals(roomItems[i])) { //room
                room = i;
            }
        }

        roomSpinner.setAdapter(roomAdapter);
        weaponSpinner.setAdapter(weaponAdapter);
        suspectSpinner.setAdapter(suspectAdapter);

        roomSpinner.setSelection(room);
        weaponSpinner.setSelection(weapon);
        suspectSpinner.setSelection(suspect);
    }

    /*
    Method that gets the cards from the player's hand and compares them to the suggestion made by another
    player. If the cards match, they will be put into one spinner (the room spinner), for them to choose
    whichever card they want to show to the other player.
     */
    public void setSendCardSpinners() {
        String[] temp = recentState.getSuggestCards();
        Hand tempHand = recentState.getCards(playerNum);
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
            notSent = false;
        }else {
            ArrayAdapter<String> roomAdapter = new ArrayAdapter<String>(myActivity, android.R.layout.simple_spinner_dropdown_item, validCards);
            roomSpinner.setAdapter(roomAdapter);
            weaponSpinner.setEnabled(false);
            suspectSpinner.setEnabled(false);
        }
    }
}
