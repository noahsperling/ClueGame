package edu.up.cs301.game;

import org.junit.Test;

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

    }

    @Test
    public void testSendUpdatedStateTo() throws Exception {

    }

    @Test
    public void testCheckIfGameOver() throws Exception {

    }
}