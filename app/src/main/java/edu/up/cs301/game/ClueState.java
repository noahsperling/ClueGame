package edu.up.cs301.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;

import edu.up.cs301.game.infoMsg.GameState;

/**
 * Created by Noah on 10/25/2016.
 */

public class ClueState extends GameState {

    private int turnID;
    private int numPlayers;
    private int playerIDs[]; //starts at 0, then 1, 2, 3, 4, 5 if there are enough players
    private int dieValue;
    private int spacesMoved;
    private String notes[];
    private String playerNames[];
    private int boardTiles[][];
    private boolean canSuggest[];
    private boolean canRoll[];
    private boolean checkboxes[][];
    private int cardsPerHand;
    private Hand cards[];
    private Card solution[];
    private ArrayList<Card> allCards;
    private boolean gameOver;

    public ClueState(int initNumPlayers, String initPlayerNames[], int initTurnID) {
        turnID = initTurnID;
        numPlayers = initNumPlayers;
        playerIDs = new int[numPlayers];
        playerNames = new String[numPlayers];
        canSuggest = new boolean[numPlayers];
        canRoll = new boolean[numPlayers];
        checkboxes = new boolean[numPlayers][21];
        if(numPlayers == 3) {
            cardsPerHand = 6;
        }else if(numPlayers == 4) {
            cardsPerHand = 5;
        }else if(numPlayers == 5) {
            cardsPerHand = 4;
        }else if(numPlayers == 6) {
            cardsPerHand = 3;
        }

        for(Card c: Card.values()) {
            allCards.add(c);
        }
        Collections.shuffle(allCards);
        Collections.shuffle(allCards); //a second time just to be thorough
        boolean suspect = false;
        boolean weapon = false;
        boolean room = false;
        for(int i = 0; i < 21; i++) {
            Card temp = allCards.get(i);
            if(!suspect && !weapon && !room) {
                solution[0] = temp;
                if(solution[0].getType() == Type.PERSON) {
                    suspect = true;
                }else if(solution[0].getType() == Type.WEAPON) {
                    weapon = true;
                }else if(solution[0].getType() == Type.ROOM) {
                    room = true;
                }
                allCards.remove(i);
            }else if(!suspect && !room && (temp.getType().equals(Type.PERSON) || temp.getType().equals(Type.ROOM))) {
                solution[1] = temp;
                if(solution[1].getType() == Type.PERSON) {
                    suspect = true;
                }else if(solution[1].getType() == Type.ROOM) {
                    room = true;
                }
                allCards.remove(i);
            }else if(!suspect && !weapon && (temp.getType().equals(Type.PERSON) || temp.getType().equals(Type.WEAPON))) {
                solution[1] = temp;
                if(solution[1].getType() == Type.PERSON) {
                    suspect = true;
                }else if(solution[1].getType() == Type.WEAPON) {
                    room = true;
                }
                allCards.remove(i);
            }else if(!room && !weapon && (temp.getType().equals(Type.ROOM) || temp.getType().equals(Type.WEAPON))) {
                solution[1] = temp;
                if(solution[1].getType() == Type.ROOM) {
                    suspect = true;
                }else if(solution[1].getType() == Type.WEAPON) {
                    room = true;
                }
                allCards.remove(i);
            }else if(!suspect && temp.getType().equals(Type.PERSON))  {
                solution[2] = temp;
                allCards.remove(i);
                break;
            }else if(!weapon && temp.getType().equals(Type.WEAPON)) {
                solution[2] = temp;
                allCards.remove(i);
                break;
            }else if(!room && temp.getType().equals(Type.ROOM)) {
                solution[2] = temp;
                allCards.remove(i);
                break;
            }
        }
        //put cards in players hands



        gameOver = false;

        for(int i = 0; i < numPlayers; i++) {
            playerIDs[i] = i;
            canSuggest[i] = false;
            canRoll[i] = false;
            notes[i] = "";
            playerNames[i] = new String(initPlayerNames[i]);
            for(int j = 0; j < 21; j++) {
                checkboxes[i][j] = false;
            }
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
        checkboxes = new boolean[numPlayers][21];
        for(int i = 0; i < numPlayers; i++) {
            for(int j = 0; j < 21; j++) {
                checkboxes[i][j] = s.getCheckBox(i, j);
            }
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

    public boolean getCheckBox(int playerID, int index) {
        return checkboxes[playerID][index];
    }

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

    public void setCheckBox(int playerID, int index, boolean newVal) {
        checkboxes[playerID][index] = newVal;
    }

    public void setGameOver(boolean newGameOver) {
        gameOver = newGameOver;
    }

    //public void setCards(int index, int index1, Card c) {
        //cards[index][index1] = c;
    //}
}
