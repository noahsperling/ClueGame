package edu.up.cs301.game;

import org.junit.Test;

import edu.up.cs301.game.actionMsg.ClueAccuseAction;
import edu.up.cs301.game.actionMsg.ClueCheckAction;
import edu.up.cs301.game.actionMsg.ClueEndTurnAction;
import edu.up.cs301.game.actionMsg.ClueMoveDownAction;
import edu.up.cs301.game.actionMsg.ClueMoveLeftAction;
import edu.up.cs301.game.actionMsg.ClueMoveRightAction;
import edu.up.cs301.game.actionMsg.ClueRollAction;
import edu.up.cs301.game.actionMsg.ClueShowCardAction;
import edu.up.cs301.game.actionMsg.ClueSuggestionAction;

import edu.up.cs301.game.actionMsg.ClueMoveUpAction;
import edu.up.cs301.game.actionMsg.ClueUsePassagewayAction;
import edu.up.cs301.game.actionMsg.ClueWrittenNoteAction;

import static org.junit.Assert.*;

/**
 * Created by sperling19 on 11/9/2016.
 */
public class ClueLocalGameTest {

    @Test
    public void testCanMove() throws Exception {
        ClueLocalGame cLG = new ClueLocalGame();
        assertFalse(cLG.canMove(0));
    }

    @Test
    public void testMakeMove() throws Exception {
        ClueLocalGame cLG = new ClueLocalGame();
        if(cLG.m != null){
            if(cLG.state.getTurnId() == cLG.m.playerID) {
                if (cLG.m instanceof ClueSuggestionAction) {
                    ClueSuggestionAction csa = (ClueSuggestionAction) cLG.m;
                    if (csa.person != null && csa.room != null && csa.weapon != null) {
                        assertTrue(cLG.makeMove(cLG.m));
                    } else {
                        assertFalse(cLG.makeMove(cLG.m));
                    }
                }

                if (cLG.m instanceof ClueAccuseAction) {
                    ClueAccuseAction caa = (ClueAccuseAction) cLG.m;
                    if (caa.person != null && caa.room != null && caa.weapon != null) {
                        assertTrue(cLG.makeMove(cLG.m));
                    } else {
                        assertFalse(cLG.makeMove(cLG.m));
                    }
                }

                if (cLG.m instanceof ClueRollAction) {
                    ClueRollAction cra = (ClueRollAction) cLG.m;
                    assertEquals(cLG.state.getCanRoll(cra.playerID), cLG.makeMove(cLG.m));
                }

                if (cLG.m instanceof ClueMoveUpAction || cLG.m instanceof ClueMoveDownAction || cLG.m instanceof ClueMoveLeftAction || cLG.m instanceof ClueMoveRightAction) {
                    assertEquals((cLG.state.getDieValue() - cLG.state.getSpacesMoved() >= 1), cLG.makeMove(cLG.m));
                }

                if (cLG.m instanceof ClueUsePassagewayAction) {
                    assertEquals((cLG.state.getDieValue() - cLG.state.getSpacesMoved() == 6), cLG.makeMove(cLG.m));
                }

                if (cLG.m instanceof ClueEndTurnAction) {
                    assertTrue(cLG.makeMove(cLG.m));
                }
            }else{
                assertFalse(cLG.makeMove(cLG.m));
            }
        }else{
            assertFalse(cLG.makeMove(cLG.m));
        }
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