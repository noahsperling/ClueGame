//package edu.up.cs301.game;
//
//import org.junit.Test;
//
//import edu.up.cs301.game.actionMsg.ClueAccuseAction;
//import edu.up.cs301.game.actionMsg.ClueCheckAction;
//import edu.up.cs301.game.actionMsg.ClueEndTurnAction;
//import edu.up.cs301.game.actionMsg.ClueMoveDownAction;
//import edu.up.cs301.game.actionMsg.ClueMoveLeftAction;
//import edu.up.cs301.game.actionMsg.ClueMoveRightAction;
//import edu.up.cs301.game.actionMsg.ClueRollAction;
//import edu.up.cs301.game.actionMsg.ClueShowCardAction;
//import edu.up.cs301.game.actionMsg.ClueSuggestionAction;
//
//import edu.up.cs301.game.actionMsg.ClueMoveUpAction;
//import edu.up.cs301.game.actionMsg.ClueUsePassagewayAction;
//import edu.up.cs301.game.actionMsg.ClueWrittenNoteAction;
//
//import static org.junit.Assert.*;
//
///**
// * Created by sperling19 on 11/9/2016.
// */
//public class ClueLocalGameTest {
//
//    @Test
//    public void testCanMove() throws Exception {
//        ClueLocalGame cLG = new ClueLocalGame();
//        assertTrue(cLG.canMove(0));
//    }
//
//    @Test
//    public void testMakeMove() throws Exception {
//        ClueLocalGame cLG = new ClueLocalGame();
//        if(cLG.moveAction != null){
//            if(cLG.state.getTurnId() == cLG.moveAction.playerID) {
//                if (cLG.moveAction instanceof ClueSuggestionAction) {
//                    ClueSuggestionAction csa = (ClueSuggestionAction) cLG.moveAction;
//                    if (csa.person != null && csa.room != null && csa.weapon != null) {
//                        assertTrue(cLG.makeMove(cLG.moveAction));
//                    } else {
//                        assertFalse(cLG.makeMove(cLG.moveAction));
//                    }
//                }
//
//                if (cLG.moveAction instanceof ClueAccuseAction) {
//                    ClueAccuseAction caa = (ClueAccuseAction) cLG.moveAction;
//                    if (caa.person != null && caa.room != null && caa.weapon != null) {
//                        assertTrue(cLG.makeMove(cLG.moveAction));
//                    } else {
//                        assertFalse(cLG.makeMove(cLG.moveAction));
//                    }
//                }
//
//                if (cLG.moveAction instanceof ClueRollAction) {
//                    ClueRollAction cra = (ClueRollAction) cLG.moveAction;
//                    assertEquals(cLG.state.getCanRoll(cra.playerID), cLG.makeMove(cLG.moveAction));
//                }
//
//                if (cLG.moveAction instanceof ClueMoveUpAction || cLG.moveAction instanceof ClueMoveDownAction || cLG.moveAction instanceof ClueMoveLeftAction || cLG.moveAction instanceof ClueMoveRightAction) {
//                    assertEquals((cLG.state.getDieValue() - cLG.state.getSpacesMoved() >= 1), cLG.makeMove(cLG.moveAction));
//                }
//
//                if (cLG.moveAction instanceof ClueUsePassagewayAction) {
//                    assertEquals((cLG.state.getDieValue() - cLG.state.getSpacesMoved() == 6), cLG.makeMove(cLG.moveAction));
//                }
//
//                if (cLG.moveAction instanceof ClueEndTurnAction) {
//                    assertTrue(cLG.makeMove(cLG.moveAction));
//                }
//            }else{
//                assertFalse(cLG.makeMove(cLG.moveAction));
//            }
//        }else{
//            assertFalse(cLG.makeMove(cLG.moveAction));
//        }
//    }
//
//    @Test
//    public void testMakeNonTurnAction() throws Exception {
//        ClueLocalGame cLG = new ClueLocalGame();
//        boolean isTurn = cLG.nonTurnAction.playerID == cLG.state.getTurnId();
//        assertTrue(isTurn);
//        if(cLG.nonTurnAction instanceof ClueShowCardAction){
//            Card card = ((ClueShowCardAction) cLG.nonTurnAction).card;
//            assertTrue(card != null);
//        }
//
//        if(cLG.nonTurnAction instanceof ClueCheckAction){
//            ClueCheckAction cca = ((ClueCheckAction) cLG.nonTurnAction);
//            assertTrue(cca.checkbox != null);
//            assertTrue(cca.checkbox.length == 21);
//            for(boolean b: cca.checkbox){
//                assertTrue((b == true || b == false));
//            }
//        }
//
//        if(cLG.nonTurnAction instanceof ClueWrittenNoteAction){
//            ClueWrittenNoteAction cwna = ((ClueWrittenNoteAction) cLG.nonTurnAction);
//            assertTrue(cwna.note != null);
//        }
//    }
//
//    @Test
//    public void testSendUpdatedStateTo() throws Exception
//    {
//        ClueLocalGame cLG = new ClueLocalGame();
//        CluePlayer p1 = new CluePlayer();
//        cLG.sendUpdatedStateTo(p1);
//        assertTrue("State has not been updated",p1.getRecentState().equals(cLG.state));
//
//
//    }
//
//    @Test
//    public void testCheckIfGameOver() throws Exception {
//        ClueLocalGame cLG = new ClueLocalGame();
//        assertNull(cLG.checkIfGameOver());
//    }
//}