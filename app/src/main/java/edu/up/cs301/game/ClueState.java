package edu.up.cs301.game;

import edu.up.cs301.game.infoMsg.GameState;

/**
 * Created by Noah on 10/25/2016.
 */

public class ClueState extends GameState {

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
    private boolean gameOver;
    //private Card cards[][] = new Card[numPlayers][7];

    //getters
    public int getTurnId() {
        return turnID;
    }

    public int getDieValue() {
        return dieValue;
    }

    public int getSpacesMoved() {
        return spacesMoved;
    }

    public int getRoom(int playerID) {
        //flesh out more
        return 1;
    }

    public String getNotes(int playerID) {
        return notes[playerID];
    }

    public String getPlayerName(int playerID) {
        return playerNames[playerID];
    }

    public boolean getCanSuggest(int playerID) {
        return canSuggest[playerID];
    }

    public boolean getCanRoll(int playerID) {
        return canRoll[playerID];
    }
    //public Card[] getCards(int playerID) { return cards[playerID]; }

    public boolean getGameOver() {
        return gameOver;
    }

    //setters
    public void setTurnID(int newTurnID) {
        turnID = newTurnID;
    }

    public void setNumPlayers(int newNumPlayers) {
        numPlayers = newNumPlayers;
    }

    public void setPlayerID(int newID, int index) {
        playerIDs[index] = newID;
    }

    public void setDieValue(int newDieValue) {
        dieValue = newDieValue;
    }

    public void setSpacesMoved(int newSpacesMoved) {
        spacesMoved = newSpacesMoved;
    }

    public void setNotes(int index, String newNotes) {
        notes[index] = newNotes;
    }

    public void setPlayerName(int index, String newName) {
        playerNames[index] = newName;
    }

    public void setCanSuggest(int index, boolean newCanSuggest) {
        canSuggest[index] = newCanSuggest;
    }

    public void setCanRoll(int index, boolean newCanRoll) {
        canRoll[index] = newCanRoll;
    }

    public void setGameOver(boolean newGameOver) {
        gameOver = newGameOver;
    }
}
