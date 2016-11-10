package edu.up.cs301.game;

import org.junit.Test;

import edu.up.cs301.game.actionMsg.ClueCheckAction;
import edu.up.cs301.game.actionMsg.ClueShowCardAction;
import edu.up.cs301.game.actionMsg.ClueSuggestionAction;

import edu.up.cs301.game.actionMsg.ClueMoveUpAction;
import edu.up.cs301.game.actionMsg.ClueWrittenNoteAction;

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
        boolean isTurn = cLG.c.playerID == cLG.state.getTurnId();
        assertTrue(isTurn);
        if(cLG.c instanceof ClueShowCardAction){
            Card card = ((ClueShowCardAction) cLG.c).card;
            assertTrue(card != null);
        }

        if(cLG.c instanceof ClueCheckAction){
            ClueCheckAction cca = ((ClueCheckAction) cLG.c);
            assertTrue(cca.checkbox != null);
            assertTrue(cca.checkbox.length == 21);
            for(boolean b: cca.checkbox){
                assertTrue((b == true || b == false));
            }
        }

        if(cLG.c instanceof ClueWrittenNoteAction){
            ClueWrittenNoteAction cwna = ((ClueWrittenNoteAction) cLG.c);
            assertTrue(cwna.note != null);
        }
    }

    @Test
    public void testSendUpdatedStateTo() throws Exception {

    }

    @Test
    public void testCheckIfGameOver() throws Exception {
        ClueLocalGame cLG = new ClueLocalGame();
        assertTrue("Game somehow over." , cLG.checkIfGameOver() != null);
    }
}