package edu.up.cs301.game;

import org.junit.Test;

import edu.up.cs301.game.actionMsg.ClueMoveUpAction;

import static org.junit.Assert.*;

/**
 * Created by sperling19 on 11/9/2016.
 */
public class ClueLocalGameTest {

    @Test
    public void testCanMove() throws Exception {
        ClueLocalGame cLG = new ClueLocalGame();
        assertTrue(cLG.canMove(0));
    }

    @Test
    public void testMakeMove() throws Exception {
        ClueLocalGame cLG = new ClueLocalGame();
        assertTrue(cLG.canMove(0));
    }

    @Test
    public void testMakeNonTurnAction() throws Exception {
        ClueLocalGame cLG = new ClueLocalGame();
        ClueHumanPlayer h = new ClueHumanPlayer("", 0);

        ClueMoveUpAction a = new ClueMoveUpAction(h);

    }

    @Test
    public void testSendUpdatedStateTo() throws Exception
    {
        ClueLocalGame cLG = new ClueLocalGame();
        CluePlayer p1 = new CluePlayer();
        cLG.sendUpdatedStateTo(p1);
        assertTrue(p1.getRecentState() == cLG.state);


    }

    @Test
    public void testCheckIfGameOver() throws Exception {
        ClueLocalGame cLG = new ClueLocalGame();
        assertTrue("Game somehow over." , cLG.checkIfGameOver() != null);
    }
}