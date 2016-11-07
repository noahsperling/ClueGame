package edu.up.cs301.game;

import edu.up.cs301.game.infoMsg.GameState;

/**
 * Created by Noah on 10/25/2016.
 */

public class ClueGameState extends GameState {

    private int turnID;
    private int numPlayers;
    private int playerIDs[] = new int[numPlayers]; //0 - 5 max
    private int dieValue;
    private int spacesMoved;
    private String notes[];
    private String playerNames[];
    private int boardTiles[][] = new int[22][19];
    private boolean canSuggest[] = new boolean[numPlayers];
    private boolean canRoll[] = new boolean[numPlayers];
    //private Card cards[][] = new Card[numPlayers][7];

    private int getTurnId() {
        return turnID;
    }
    private int getDieValue() {
        return dieValue;
    }
    int getSpacesMoved() {
        return spacesMoved;
    }
    int getRoom(int playerID) {
        //flesh out more
        return 1;
    }
    String getNotes(int playerID) {
        return notes[playerID];
    }






}
