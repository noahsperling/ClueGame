package edu.up.cs301.game;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

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
import edu.up.cs301.game.infoMsg.GameOverInfo;

import static edu.up.cs301.game.R.id.editText;
import static edu.up.cs301.game.R.id.noteLayout;

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

    //playersHand
    //playerTurn
    //noteView

    private ClueBoardView boardView;
    private ClueCardView cardView;

    private Activity myActivity;

    private int layoutID;

    public ClueHumanPlayer(String initName, int initID)
    {
        //surfaceView = (ClueSurfaceView)findViewById(R.id); moved to setAsGui method

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

        upButton = (Button)myActivity.findViewById(R.id.upButton);
        upButton.setOnClickListener(this);
        //upButton.setEnabled(false);
        upButton.setEnabled(true);

        downButton = (Button)myActivity.findViewById(R.id.downButton);
        downButton.setOnClickListener(this);
        //downButton.setEnabled(false);
        downButton.setEnabled(true);

        leftButton = (Button)myActivity.findViewById(R.id.leftButton);
        leftButton.setOnClickListener(this);
        //leftButton.setEnabled(false);
        leftButton.setEnabled(true);

        rightButton = (Button)myActivity.findViewById(R.id.rightButton);
        rightButton.setOnClickListener(this);
        //rightButton.setEnabled(false);
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

        //Spinners
        //will have to make sure room is locked when making a suggestion
//        String[] roomItems = new String[]{"Ballroom","Billiard Room ", "Conservatory", "Dining Room", "Hall", "Kitchen", "Library", "Lounge", "Study"};
//        String[] weaponItems = new String[]{"Candlestick", "Knife", "Lead Pipe", "Revolver", "Rope", "Wrench", };
//        String[] suspectItem = new String []{"Mr. Green", "Col. Mustard", "Mrs. Peacock", "Prof. Plum", "Miss Scarlet", "Mrs.White"};
//        ArrayAdapter<String> roomAdapter = new ArrayAdapter<String>(myActivity, android.R.layout.simple_spinner_dropdown_item, roomItems);
//        ArrayAdapter<String> weaponAdapter = new ArrayAdapter<String>(myActivity, android.R.layout.simple_spinner_dropdown_item, weaponItems);
//        ArrayAdapter<String> suspectAdapter = new ArrayAdapter<String>(myActivity,android.R.layout.simple_spinner_dropdown_item, suspectItem );

        roomSpinner = (Spinner)myActivity.findViewById(R.id.roomSpinner);
//        roomSpinner.setAdapter(roomAdapter);

        weaponSpinner = (Spinner)myActivity.findViewById(R.id.weaponSpinner);
//        weaponSpinner.setAdapter(weaponAdapter);

        suspectSpinner = (Spinner)myActivity.findViewById(R.id.suspectSpinner);
//        suspectSpinner.setAdapter(suspectAdapter);

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

        numberOfMovesLeft = (TextView)myActivity.findViewById(R.id.numberOfMovesTextView);
        numberOfMovesLeft.setText(0+"");

        messageTextView = (TextView)myActivity.findViewById(R.id.messageTextView);
        messageTextView.setText("");

        playerTextView = (TextView)myActivity.findViewById(R.id.playerTextView);
        playerTextView.setText("You are a human\n player with a \nundetermined name.");

        notesGUI = (EditText)myActivity.findViewById(R.id.editText);
        notesGUI.setOnClickListener(this);

    }

    /* unsure why this exists
    public ClueState getRecentState()
    {
        return recentState;
    }
    */

    @Override
    public void receiveInfo(GameInfo info)
    {
        if(info instanceof ClueState) {
            recentState = new ClueState((ClueState)info);
            if(!nameSet) {
                switch (playerNum) {
                    case 0: playerTextView.setText("You are Miss\n Scarlet.\n");
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
            recentState.setCardToShow("You Lost!", playerNum);
        }

        boolean corner[] = recentState.getInCornerRoom();
        boolean usedPassage[] = recentState.getUsedPassageway();
        boolean room[] = recentState.getInRoom();

        if(recentState.getTurnId() == playerNum && recentState.getPlayerStillInGame(playerNum)) {
            accuseR.setEnabled(true);
            accuseR.setChecked(false);
            endTurnButton.setEnabled(true);
            if(recentState.getCanRoll(playerNum)) {
                rollButton.setEnabled(true);
            }else if(!recentState.getCanRoll(playerNum)) {
                rollButton.setEnabled(false);
            }

            if (corner[playerNum] && !usedPassage[playerNum])
            {
                secretPassagewayButton.setEnabled(true);
            }

        }else if(recentState.getTurnId() == playerNum && !recentState.getPlayerStillInGame(playerNum)){
            endTurnButton.setEnabled(false);
            game.sendAction(new ClueEndTurnAction(this));
        }

        //enable suggest button
//        if (room[playerID])
//        {
//            suggestR.setEnabled(true);
//        }
//        else
//        {
//            suggestR.setEnabled(false);
//        }

        //if another player made a suggestion
        if(recentState.getCheckCardToSend()[playerNum]) {
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

        //suggest and accuse radio buttons handled
        Log.i("New to room = " +recentState.getNewToRoom(playerNum), " ");
        Log.i("Room = " + room[playerNum], " ");
        if (room[playerNum] && recentState.getNewToRoom(playerNum)) {
            Log.i("Got to suggest if", " ");
            suggestR.setEnabled(true);
            suggestR.setChecked(false);
        }
        else{
            Log.i("Got to suggest else", " ");
            suggestR.setEnabled(false);
            suggestR.setChecked(false);
        }

        messageTextView.setText(recentState.getCardToShow(playerNum));

        boardView.updateBoard(recentState.getBoard());
        boardView.invalidate();
        cardView.updateCards(recentState.getCards(playerNum));
        cardView.invalidate();
        numberOfMovesLeft.setText(recentState.getDieValue()-recentState.getSpacesMoved()+"");
    }

    public void onClick(View view)
    {
        //Move player actions
        if (game == null)
        {
            return;
        }

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


        }
        else if (view.getId() == R.id.radioShowCardButton) {
                setSpinners();
            /*suggestR.setChecked(false);
            accuseR.setChecked(false);
            suggestR.setEnabled(false);
            accuseR.setEnabled(false);

            showCardR.setChecked(true);*/
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
                    Log.i("suggest action sent", " ");
                    game.sendAction(suggest);



                    //need to send in show card action because now the player to their left
                    //has to choose from the spinners and stuff?
                    //set the showcard radio button to enabled/is checked true for the next player.
                    //then if that player has no cards, then go to the next player.
                    //if the player has a card, then they submit it and it the info shows up on the player who
                    //suggested GUI in a textview somewhere obvious

                    //does this need to sent in as info?
                    ClueShowCardAction showCard = new ClueShowCardAction(this);
                    showCardR.setEnabled(true);
                    showCardR.setChecked(false);
                    suggestR.setEnabled(false);
                    suggestR.setChecked(false);
                    accuseR.setEnabled(false);

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
                    Log.i("accuse action sent", " ");
                    game.sendAction(accuse);
                } else if (showCardR.isChecked() == true) {
                    //disable the other radio buttons then re-enable?
                    showCardR.setEnabled(false);
                    showCardR.setChecked(false);
                    ClueShowCardAction showCard = new ClueShowCardAction(this);

                //use only one spinner for all the cards that can be shown
                String showCardString = roomSpinner.getSelectedItem().toString();

                showCard.setCardToShow(showCardString);

                game.sendAction(showCard);


                }
                //might have to add show card radio button?? have a text view saying it you need to pick a card to display
            } else if (view == secretPassagewayButton) {
                ClueUsePassagewayAction passageway = new ClueUsePassagewayAction(this);
                game.sendAction(passageway);
                secretPassagewayButton.setEnabled(false);
            }

            //end turn button
            else if (view == endTurnButton) {
                ClueEndTurnAction endTurn = new ClueEndTurnAction(this);
                Log.i("You clicked End Turn", "YAY");
                endTurnButton.setEnabled(false);
                rollButton.setEnabled(false);
                game.sendAction(endTurn);
            }
            //note edit text
            else if (view == notesGUI) {
                ClueWrittenNoteAction writtenNote = new ClueWrittenNoteAction(this);
                writtenNote.note = notesGUI.getText().toString();
                game.sendAction(writtenNote);
            }

        //CheckBoxes
        else if (view == colonelMustardCheck)
        {
            if(!checkBoxBool[0]) {
                checkBoxBool[0] = true;
                colonelMustardCheck.setChecked(true);
            } else {
                checkBoxBool[0] = false;
                colonelMustardCheck.setChecked(false);
            }
            ClueCheckAction checkAct = new ClueCheckAction(this);
            game.sendAction(checkAct);
        }
        else if (view == professorPlumCheck)
        {
            if(!checkBoxBool[1]) {
                checkBoxBool[1] = true;
                professorPlumCheck.setChecked(true);
            } else {
                checkBoxBool[1] = false;
                professorPlumCheck.setChecked(false);
            }
            ClueCheckAction checkAct = new ClueCheckAction(this);
            game.sendAction(checkAct);
        }
        else if (view == mrGreenCheck)
        {
            if(!checkBoxBool[2]) {
                checkBoxBool[2] = true;
                mrGreenCheck.setChecked(true);
            } else {
                checkBoxBool[2] = false;
                mrGreenCheck.setChecked(false);
            }
            ClueCheckAction checkAct = new ClueCheckAction(this);
            game.sendAction(checkAct);
        }
        else if (view == mrsPeacockCheck)
        {
            if(!checkBoxBool[3]) {
                checkBoxBool[3] = true;
                mrsPeacockCheck.setChecked(true);
            } else {
                checkBoxBool[3] = false;
                mrsPeacockCheck.setChecked(false);
            }
            ClueCheckAction checkAct = new ClueCheckAction(this);
            game.sendAction(checkAct);
        }
        else if (view == missScarletCheck)
        {
            if(!checkBoxBool[4]) {
                checkBoxBool[4] = true;
                missScarletCheck.setChecked(true);
            } else {
                checkBoxBool[4] = false;
                missScarletCheck.setChecked(false);
            }
            ClueCheckAction checkAct = new ClueCheckAction(this);
            game.sendAction(checkAct);
        }
        else if (view == mrsWhiteCheck)
        {
            if(!checkBoxBool[5]) {
                checkBoxBool[5] = true;
                mrsWhiteCheck.setChecked(true);
            } else {
                checkBoxBool[5] = false;
                mrsWhiteCheck.setChecked(false);
            }

                ClueCheckAction checkAct = new ClueCheckAction(this);
                game.sendAction(checkAct);
            } else if (view == knifeCheck) {
                if (!checkBoxBool[6]) {
                    checkBoxBool[6] = true;
                    knifeCheck.setChecked(true);
                } else {
                    checkBoxBool[6] = false;
                    knifeCheck.setChecked(false);
                }
                ClueCheckAction checkAct = new ClueCheckAction(this);
                game.sendAction(checkAct);
            } else if (view == candlestickCheck) {
                if (!checkBoxBool[7]) {
                    checkBoxBool[7] = true;
                    candlestickCheck.setChecked(true);
                } else {
                    checkBoxBool[7] = false;
                    candlestickCheck.setChecked(false);
                }
                ClueCheckAction checkAct = new ClueCheckAction(this);
                game.sendAction(checkAct);
            } else if (view == revolverCheck) {
                if (!checkBoxBool[8]) {
                    checkBoxBool[8] = true;
                    revolverCheck.setChecked(true);
                } else {
                    checkBoxBool[8] = false;
                    revolverCheck.setChecked(false);
                }
                ClueCheckAction checkAct = new ClueCheckAction(this);
                game.sendAction(checkAct);
            } else if (view == ropeCheck) {
                if (!checkBoxBool[9]) {
                    checkBoxBool[9] = true;
                    ropeCheck.setChecked(true);
                } else {
                    checkBoxBool[9] = false;
                    ropeCheck.setChecked(false);
                }

                ClueCheckAction checkAct = new ClueCheckAction(this);
                game.sendAction(checkAct);
            } else if (view == leadPipeCheck) {
                if (!checkBoxBool[10]) {
                    checkBoxBool[10] = true;
                    leadPipeCheck.setChecked(true);
                } else {
                    checkBoxBool[10] = false;
                    leadPipeCheck.setChecked(false);
                }
                ClueCheckAction checkAct = new ClueCheckAction(this);
                game.sendAction(checkAct);
            } else if (view == wrenchCheck) {
                if (!checkBoxBool[11]) {
                    checkBoxBool[11] = true;
                    wrenchCheck.setChecked(true);
                } else {
                    checkBoxBool[11] = false;
                    wrenchCheck.setChecked(false);
                }
                ClueCheckAction checkAct = new ClueCheckAction(this);
                game.sendAction(checkAct);
            } else if (view == hallCheck) {
                if (!checkBoxBool[12]) {
                    checkBoxBool[12] = true;
                    hallCheck.setChecked(true);
                } else {
                    checkBoxBool[12] = false;
                    hallCheck.setChecked(false);
                }
                ClueCheckAction checkAct = new ClueCheckAction(this);
                game.sendAction(checkAct);
            } else if (view == loungeCheck) {
                if (!checkBoxBool[13]) {
                    checkBoxBool[13] = true;
                    loungeCheck.setChecked(true);
                } else {
                    checkBoxBool[13] = false;
                    loungeCheck.setChecked(false);
                }
                ClueCheckAction checkAct = new ClueCheckAction(this);
                game.sendAction(checkAct);
            } else if (view == diningRoomCheck) {
                if (!checkBoxBool[14]) {
                    checkBoxBool[14] = true;
                    diningRoomCheck.setChecked(true);
                } else {
                    checkBoxBool[14] = false;
                    diningRoomCheck.setChecked(false);
                }
                ClueCheckAction checkAct = new ClueCheckAction(this);
                game.sendAction(checkAct);
            } else if (view == kitchenCheck) {
                if (!checkBoxBool[15]) {
                    checkBoxBool[15] = true;
                    kitchenCheck.setChecked(true);
                } else {
                    checkBoxBool[15] = false;
                    kitchenCheck.setChecked(false);
                }
                ClueCheckAction checkAct = new ClueCheckAction(this);
                game.sendAction(checkAct);
            } else if (view == ballroomCheck) {
                if (!checkBoxBool[16]) {
                    checkBoxBool[16] = true;
                    ballroomCheck.setChecked(true);
                } else {
                    checkBoxBool[16] = false;
                    ballroomCheck.setChecked(false);
                }
                ClueCheckAction checkAct = new ClueCheckAction(this);
                game.sendAction(checkAct);
            } else if (view == conservatoryCheck) {
                if (!checkBoxBool[17]) {
                    checkBoxBool[17] = true;
                    conservatoryCheck.setChecked(true);
                } else {
                    checkBoxBool[17] = false;
                    conservatoryCheck.setChecked(false);
                }
                ClueCheckAction checkAct = new ClueCheckAction(this);
                game.sendAction(checkAct);
            } else if (view == billiardRoomCheck) {
                if (!checkBoxBool[18]) {
                    checkBoxBool[18] = true;
                    billiardRoomCheck.setChecked(true);
                } else {
                    checkBoxBool[18] = false;
                    billiardRoomCheck.setChecked(false);
                }
                ClueCheckAction checkAct = new ClueCheckAction(this);
                game.sendAction(checkAct);
            } else if (view == libraryCheck) {
                if (!checkBoxBool[19]) {
                    checkBoxBool[19] = true;
                    libraryCheck.setChecked(true);
                } else {
                    checkBoxBool[19] = false;
                    libraryCheck.setChecked(false);
                }
                ClueCheckAction checkAct = new ClueCheckAction(this);
                game.sendAction(checkAct);
            } else if (view == studyCheck) {
                if (!checkBoxBool[20]) {
                    checkBoxBool[20] = true;
                    studyCheck.setChecked(true);
                } else {
                    checkBoxBool[20] = false;
                    studyCheck.setChecked(false);
                }
                ClueCheckAction checkAct = new ClueCheckAction(this);
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
    public void setSpinners(){
        //where the spinner for show cards will have to created and sent in appropriately

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

    }

    public void setSuggestSpinners(){
        String[] roomItems = new String[]{"Current Room"};
        String[] weaponItems = new String[]{Card.CANDLESTICK.getName(), Card.KNIFE.getName(), Card.LEAD_PIPE.getName(), Card.REVOLVER.getName(), Card.ROPE.getName(),Card.WRENCH.getName()};
        String[] suspectItem = new String []{Card.MR_GREEN.getName(), Card.COL_MUSTARD.getName(),Card.MRS_PEACOCK.getName(), Card.PROF_PLUM.getName(), Card.MISS_SCARLET.getName(), Card.MRS_WHITE.getName()};
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<String>(myActivity, android.R.layout.simple_spinner_dropdown_item, roomItems);
        ArrayAdapter<String> weaponAdapter = new ArrayAdapter<String>(myActivity, android.R.layout.simple_spinner_dropdown_item, weaponItems);
        ArrayAdapter<String> suspectAdapter = new ArrayAdapter<String>(myActivity,android.R.layout.simple_spinner_dropdown_item, suspectItem );

        roomSpinner.setAdapter(roomAdapter);
        weaponSpinner.setAdapter(weaponAdapter);
        suspectSpinner.setAdapter(suspectAdapter);
    }

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
            game.sendAction(new ClueShowCardAction(null));
        }else {
            ArrayAdapter<String> roomAdapter = new ArrayAdapter<String>(myActivity, android.R.layout.simple_spinner_dropdown_item, validCards);
            roomSpinner.setAdapter(roomAdapter);
            weaponSpinner.setEnabled(false);
            suspectSpinner.setEnabled(false);
        }
    }
}
