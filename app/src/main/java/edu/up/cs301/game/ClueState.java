package edu.up.cs301.game;

import edu.up.cs301.game.infoMsg.GameState;

/**
 * Created by Noah on 10/25/2016.
 */

public class ClueState extends GameState {

    private int turnID;
    private int numPlayers;
    private int playerIDs[];// = new int[numPlayers]; //0 - 5 max
    private int dieValue;
    private int spacesMoved;
    private String notes[];// = new String[numPlayers];
    private String playerNames[];
    private int boardTiles[][];// = new int[22][19];
    private boolean canSuggest[];// = new boolean[numPlayers];
    private boolean canRoll[];// = new boolean[numPlayers];
    //private Card cards[][];// = new Card[numPlayers][7];
    private boolean gameOver; // = false;

    public ClueState(int initNumPlayers, String initPlayerNames[], int initTurnID) {
        turnID = initTurnID;
        numPlayers = initNumPlayers;
        playerIDs = new int[numPlayers];
        playerNames = new String[numPlayers];
        canSuggest = new boolean[numPlayers];
        canRoll = new boolean[numPlayers];
        //cards[][] = new Card[numPlayers][7];
        gameOver = false;

        for(int i = 0; i < numPlayers; i++) {
            playerIDs[i] = i;
            canSuggest[i] = false;
            canRoll[i] = false;
            notes[i] = "";
            playerNames[i] = new String(initPlayerNames[i]);
        }
    }

    public ClueState(ClueState s) {
        turnID = s.turnID;
        numPlayers = s.getNumPlayers();
        for(int i = 0; i < numPlayers; i++) {
            playerIDs[i] = s.getPlayerID(i);
            playerNames[i] = new String(s.getPlayerName(i));
            canSuggest[i] = s.getCanSuggest(i);
            canRoll[i] = s.getCanRoll(i);
            notes[i] = new String(s.getNotes(i));
            /*for(int j = 0; j < cards[i].length; j++) {
                cards[i][j] = s.getCards(i, j);
            }*/
        }
        for(int i = 0; i < boardTiles.length; i++) {
            for(int j = 0; j < boardTiles[i].length; j++) {
                boardTiles[i][j] = s.getBoardTile(i, j);
            }
        }
        dieValue = s.getDieValue();
        spacesMoved = s.getSpacesMoved();
        gameOver = s.getGameOver();
    }

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

    public int getNumPlayers() {
        return numPlayers;
    }

    public int getRoom(int playerID) {
        //flesh out more
        return 1;
    }

    public int getPlayerID(int index) {
        return playerIDs[index];
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

    public int getBoardTile(int x, int y) {
        return boardTiles[x][y];
    }

    //public Card[] getCards(int playerID, int index) { return cards[playerID][index]; }

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

    //public void setCards(int index, int index1, Card c) {
        //cards[index][index1] = c;
    //}
}
