package edu.up.cs301.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import edu.up.cs301.game.actionMsg.ClueAccuseAction;
import edu.up.cs301.game.actionMsg.ClueCheckAction;
import edu.up.cs301.game.actionMsg.ClueEndTurnAction;
import edu.up.cs301.game.actionMsg.ClueMoveDownAction;
import edu.up.cs301.game.actionMsg.ClueMoveLeftAction;
import edu.up.cs301.game.actionMsg.ClueMoveRightAction;
import edu.up.cs301.game.actionMsg.ClueMoveUpAction;
import edu.up.cs301.game.actionMsg.ClueSuggestionAction;
import edu.up.cs301.game.actionMsg.ClueWrittenNoteAction;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * Created by Noah on 11/8/2016.
 */

public class ClueHumanPlayer extends GameHumanPlayer implements GamePlayer, View.OnClickListener{

    int playerID;
    String name; //I don't know if this is important or not, or even needs to be here
    private ClueState recentState;
    private boolean checkBoxBool[];
    private Button upButton;
    private Button downButton;
    private Button leftButton;
    private Button rightButton;
    private Button endTurnButton;
    private Button suggestButton;
    private Button accuseButton;

    private Button noteButton;

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

    private Activity myActivity;

    private int layoutID;

    public ClueHumanPlayer(String initName, int initID)
    {
        //surfaceView = (ClueSurfaceView)findViewById(R.id); moved to setAsGui method

        super(initName, initID);

        //Set boolean array to false initially!  When they are checked they will be set to true.
        checkBoxBool = new boolean[21];
        layoutID = initID;
    }



    @Override
    public View getTopView() {
        return null;
    }

    public int getID()
    {
        return playerID;
    }

    public void setPlayerID(int newPlayerID) {
        playerID = newPlayerID;
    }

    public void setAsGui(GameMainActivity g) {
        myActivity = g;
        g.setContentView(layoutID);
        boardView = (ClueBoardView) myActivity.findViewById(R.id.boardView);
        if(recentState != null) {
            boardView.updateBoard(recentState.getBoard());
        }

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
        endTurnButton.setEnabled(false);

        suggestButton = (Button)myActivity.findViewById(R.id.suggestButton);
        suggestButton.setOnClickListener(this);
        suggestButton.setEnabled(false);

        accuseButton = (Button)myActivity.findViewById(R.id.accuseButton);
        accuseButton.setOnClickListener(this);
        accuseButton.setEnabled(false);

        noteButton = (Button)myActivity.findViewById(R.id.notesPopUpButton);
        noteButton.setOnClickListener(this);
        noteButton.setEnabled(true);

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
    }

    public ClueState getRecentState()
    {
        return recentState;
    }


    public void start()
    {

    }

    public void sendInfo(GameInfo g)
    {

    }

    @Override
    public void receiveInfo(GameInfo info)
    {
        if(info instanceof ClueState) {
            recentState = new ClueState((ClueState)info);
        }
    }

    public void onClick(View view)
    {
        //Move player actions
        if (game == null)
        {
            return;
        }

        if (view == upButton)
        {
            ClueMoveUpAction up = new ClueMoveUpAction(this);
            game.sendAction(up);
        }
        else if (view == downButton)
        {
            ClueMoveDownAction down = new ClueMoveDownAction(this);
            game.sendAction(down);
        }
        else if (view == leftButton)
        {
            ClueMoveLeftAction left = new ClueMoveLeftAction(this);
            game.sendAction(left);
        }
        else if (view == rightButton)
        {
            ClueMoveRightAction right = new ClueMoveRightAction(this);
            game.sendAction(right);
        }
        //Non move buttons
        else if (view == accuseButton)
        {
            ClueAccuseAction accuse = new ClueAccuseAction(this);
            game.sendAction(accuse);
        }
        else if (view == suggestButton)
        {
            ClueSuggestionAction suggest = new ClueSuggestionAction(this);
            game.sendAction(suggest);
        }
        else if (view == endTurnButton)
        {
            ClueEndTurnAction endTurn = new ClueEndTurnAction(this);
            game.sendAction(endTurn);
        }
        else if (view == noteButton)
        {
            ClueWrittenNoteAction writtenNote = new ClueWrittenNoteAction(this);
            game.sendAction(writtenNote);
        }
        //CheckBoxes
        else if (view == colonelMustardCheck)
        {
            checkBoxBool[0] = true;
            colonelMustardCheck.setChecked(true);
            ClueCheckAction checkAct = new ClueCheckAction(this);
            game.sendAction(checkAct);
        }
        else if (view == professorPlumCheck)
        {
            checkBoxBool[1] = true;
            professorPlumCheck.setChecked(true);
            ClueCheckAction checkAct = new ClueCheckAction(this);
            game.sendAction(checkAct);
        }
        else if (view == mrGreenCheck)
        {
            checkBoxBool[2] = true;
            mrGreenCheck.setChecked(true);
            ClueCheckAction checkAct = new ClueCheckAction(this);
            game.sendAction(checkAct);
        }
        else if (view == mrsPeacockCheck)
        {
            checkBoxBool[3] = true;
            mrsPeacockCheck.setChecked(true);
            ClueCheckAction checkAct = new ClueCheckAction(this);
            game.sendAction(checkAct);
        }
        else if (view == missScarletCheck)
        {
            checkBoxBool[4] = true;
            missScarletCheck.setChecked(true);
            ClueCheckAction checkAct = new ClueCheckAction(this);
            game.sendAction(checkAct);
        }
        else if (view == mrsWhiteCheck)
        {
            checkBoxBool[5] = true;
            mrsWhiteCheck.setChecked(true);
            ClueCheckAction checkAct = new ClueCheckAction(this);
            game.sendAction(checkAct);
        }
        else if (view == knifeCheck)
        {
            checkBoxBool[6] = true;
            knifeCheck.setChecked(true);
            ClueCheckAction checkAct = new ClueCheckAction(this);
            game.sendAction(checkAct);
        }
        else if (view == candlestickCheck)
        {
            checkBoxBool[7] = true;
            candlestickCheck.setChecked(true);
            ClueCheckAction checkAct = new ClueCheckAction(this);
            game.sendAction(checkAct);
        }
        else if (view == revolverCheck)
        {
            checkBoxBool[8] = true;
            revolverCheck.setChecked(true);
            ClueCheckAction checkAct = new ClueCheckAction(this);
            game.sendAction(checkAct);
        }
        else if (view == ropeCheck)
        {
            checkBoxBool[9] = true;
            ropeCheck.setChecked(true);
            ClueCheckAction checkAct = new ClueCheckAction(this);
            game.sendAction(checkAct);
        }
        else if (view == leadPipeCheck)
        {
            checkBoxBool[10] = true;
            leadPipeCheck.setChecked(true);
            ClueCheckAction checkAct = new ClueCheckAction(this);
            game.sendAction(checkAct);
        }
        else if (view == wrenchCheck)
        {
            checkBoxBool[11] = true;
            wrenchCheck.setChecked(true);
            ClueCheckAction checkAct = new ClueCheckAction(this);
            game.sendAction(checkAct);
        }
        else if (view == hallCheck)
        {
            checkBoxBool[12] = true;
            hallCheck.setChecked(true);
            ClueCheckAction checkAct = new ClueCheckAction(this);
            game.sendAction(checkAct);
        }
        else if (view == loungeCheck)
        {
            checkBoxBool[13] = true;
            loungeCheck.setChecked(true);
            ClueCheckAction checkAct = new ClueCheckAction(this);
            game.sendAction(checkAct);
        }
        else if (view == diningRoomCheck)
        {
            checkBoxBool[14] = true;
            diningRoomCheck.setChecked(true);
            ClueCheckAction checkAct = new ClueCheckAction(this);
            game.sendAction(checkAct);
        }
        else if (view == kitchenCheck)
        {
            checkBoxBool[15] = true;
            kitchenCheck.setChecked(true);
            ClueCheckAction checkAct = new ClueCheckAction(this);
            game.sendAction(checkAct);
        }
        else if (view == ballroomCheck)
        {
            checkBoxBool[16] = true;
            ballroomCheck.setChecked(true);
            ClueCheckAction checkAct = new ClueCheckAction(this);
            game.sendAction(checkAct);
        }
        else if (view == conservatoryCheck)
        {
            checkBoxBool[17] = true;
            conservatoryCheck.setChecked(true);
            ClueCheckAction checkAct = new ClueCheckAction(this);
            game.sendAction(checkAct);
        }
        else if (view == billiardRoomCheck)
        {
            checkBoxBool[18] = true;
            billiardRoomCheck.setChecked(true);
            ClueCheckAction checkAct = new ClueCheckAction(this);
            game.sendAction(checkAct);
        }
        else if (view == libraryCheck)
        {
            checkBoxBool[19] = true;
            libraryCheck.setChecked(true);
            ClueCheckAction checkAct = new ClueCheckAction(this);
            game.sendAction(checkAct);
        }
        else if (view == studyCheck)
        {
            checkBoxBool[20] = true;
            studyCheck.setChecked(true);
            ClueCheckAction checkAct = new ClueCheckAction(this);
            game.sendAction(checkAct);
        }

    }

    public int getPlayerID() {
        return playerID;
    }

    public boolean[] getCheckBoxArray() {
        boolean temp[] = new boolean[21];
        for(int i = 0; i < 21; i++) {
            temp[i] = checkBoxBool[i];
        }
        return temp;
    }
}