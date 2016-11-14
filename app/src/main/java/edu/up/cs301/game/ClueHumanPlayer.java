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
    private int[] moveButton = new int[4];
    private Button noteButton;
    //turnButtons
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

        moveButton[0] = R.id.rightButton;
        moveButton[1] = R.id.leftButton;
        moveButton[2] = R.id.downButton;
        moveButton[3] = R.id.upButton;

        for (int i = 0; i < 4; i++)
        {
            //Set the move buttons as on click listeners
            //Button moveBut = (Button)findViewById(moveButton[i]);
            //moveBut.setOnClickListener(this);
        }


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

        for (int i = 0; i < 4; i++)
        {
            //Set the move buttons as on click listeners
            //Button moveBut = (Button)findViewById(moveButton[i]);
            //moveBut.setOnClickListener(this);
        }


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