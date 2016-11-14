package edu.up.cs301.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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