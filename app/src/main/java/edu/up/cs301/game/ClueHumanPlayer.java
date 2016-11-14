package edu.up.cs301.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

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

    private ClueSurfaceView surfaceView;

    private Activity myActivity;

    private int layoutID;

    public ClueHumanPlayer(String initName, int initID)
    {
        //surfaceView = (ClueSurfaceView)findViewById(R.id); moved to setAsGui method

        super(initName, initID);

        upButton = (Button)myActivity.findViewById(R.id.upButton);
        upButton.setOnClickListener(this);
        upButton.setEnabled(false);

        downButton = (Button)myActivity.findViewById(R.id.downButton);
        downButton.setOnClickListener(this);
        downButton.setEnabled(false);

        leftButton = (Button)myActivity.findViewById(R.id.leftButton);
        leftButton.setOnClickListener(this);
        leftButton.setEnabled(false);

        rightButton = (Button)myActivity.findViewById(R.id.rightButton);
        rightButton.setOnClickListener(this);
        rightButton.setEnabled(false);

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
//        noteButton = R.id.notesPopUpButton;
//        moveButton[0] = R.id.rightButton;
//        moveButton[1] = R.id.leftButton;
//        moveButton[2] = R.id.downButton;
//        moveButton[3] = R.id.upButton;

    }

    //why is this here if everything is in the constructor? -Noah

    protected void onCreate(Bundle savedInstanceState)
    {
        //super.onCreate(savedInstanceState);
        //Store all the move buttons in an array
        //moveButton[0] = R.id.rightButton;
        //moveButton[1] = R.id.leftButton;
        //moveButton[2] = R.id.downButton;
        //moveButton[3] = R.id.upButton;
//        for (int i = 0; i < 4; i++)
//        {
            //Set the move buttons as on click listeners
            //Button moveBut = (Button)findViewById(moveButton[i]);
            //moveBut.setOnClickListener(this);
//        }

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

    public void recieveInfo(GameInfo i) {
        if(i instanceof ClueState) {
            recentState = new ClueState((ClueState)i);
        }
    }

    public void setAsGui(GameMainActivity g) {
        myActivity = g;
        g.setContentView(layoutID);
        //surfaceView = (ClueSurfaceView)myActivity.findViewById(R.id.surfaceView);
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

    }

    public void onClick(View view) {

    }
}