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
    private boolean canSuggest[];
    private boolean canRoll[];
    private boolean checkboxes[][];
    //private int playerBoard[][];
    private int cardsPerHand;
    private Hand cards[];
    private Card solution[] = new Card[3];
    private ArrayList<Card> allCards = new ArrayList<Card>();
    private boolean gameOver;
    private Board board;

    public ClueState(int initNumPlayers, String initPlayerNames[], int initTurnID) {
        turnID = initTurnID;
        numPlayers = initNumPlayers;
        if(numPlayers == 3) {
            cardsPerHand = 6;
        }else if(numPlayers == 4) {
            cardsPerHand = 5;
        }else if(numPlayers == 5) {
            cardsPerHand = 4;
        }else {
            cardsPerHand = 3;
        }
        playerIDs = new int[numPlayers];
        playerNames = new String[numPlayers];
        canSuggest = new boolean[numPlayers];
        canRoll = new boolean[numPlayers];
        checkboxes = new boolean[numPlayers][21];
        cards = new Hand[numPlayers];
        for(int i =0; i<numPlayers;i++){
            cards[i] = new Hand();
        }
        notes = new String[numPlayers];

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
        //Create integer array that will keep track of where the players are
        //playerBoard = new int[27][27];

        //Set every element of the array to -1 initially.  If there is not a player
        //on a tile, it will be set to -1.
        /*for (int m = 0; m < 27; m++)
        {
            for (int n = 0; n < 27; n++)
            {
                playerBoard[m][n] = -1;

            }
        }
        */

        //creates board, which stores board tiles and player locations
        board = new Board();

        //I had to comment this out weirdly. Not sure why

        /*
        Set each player's initial position on the board and store it in an integer array.
        switch(initNumPlayers)
        {
            case 1: playerBoard[17][1] = 0; //Player 0 starts at mrs.peacocks spot on the board.
                board.setPlayerBoard(17, 1, 0, 0, 0);
                break;
            case 2: playerBoard[17][1] = 0;
                playerBoard[19][1] = 1;
                board.setPlayerBoard(17, 1, 0, 0, 0);
                board.setPlayerBoard(19, 1, 0, 0, 1);
                break;
            case 3: playerBoard[17][1] = 0;
                playerBoard[19][1] = 1;
                playerBoard[24][8] = 2;

                board.setPlayerBoard(17, 1, 0, 0, 0);
                board.setPlayerBoard(19, 1, 0, 0, 1);
                board.setPlayerBoard(24, 8, 0, 0, 2);
                break;*//*
            case 4: playerBoard[17][1] = 0;
                playerBoard[19][1] = 1;
                playerBoard[24][8] = 2;
                playerBoard[15][25] = 3;

                board.setPlayerBoard(17, 1, 0, 0, 0);
                board.setPlayerBoard(19, 1, 0, 0, 1);
                board.setPlayerBoard(24, 8, 0, 0, 2);
                board.setPlayerBoard(15, 25, 0, 0, 3);
                break;
            case 5: playerBoard[17][1] = 0;
                playerBoard[19][1] = 1;
                playerBoard[24][8] = 2;
                playerBoard[15][25] = 3;
                playerBoard[10][25] = 4;

                board.setPlayerBoard(17, 1, 0, 0, 0);
                board.setPlayerBoard(19, 1, 0, 0, 1);
                board.setPlayerBoard(24, 8, 0, 0, 2);
                board.setPlayerBoard(15, 25, 0, 0, 3);
                board.setPlayerBoard(10, 25, 0, 0, 4);
                break;
            case 6: playerBoard[17][1] = 0;
                playerBoard[19][1] = 1;
                playerBoard[24][8] = 2;
                playerBoard[15][25] = 3;
                playerBoard[10][25] = 4;
                playerBoard[1][6] = 5;

                board.setPlayerBoard(17, 1, 0, 0, 0);
                board.setPlayerBoard(19, 1, 0, 0, 1);
                board.setPlayerBoard(24, 8, 0, 0, 2);
                board.setPlayerBoard(15, 25, 0, 0, 3);
                board.setPlayerBoard(10, 25, 0, 0, 4);
                board.setPlayerBoard(1, 6, 0, 0, 5);
                break;
        }*/



        //put cards in players hands
        for(int i = 0; i < numPlayers; i++) {
            for(int j = 0; j < cardsPerHand; j++) {
                if(allCards.size() != 0) {
                    int index = allCards.size() - 1;
                    cards[i].addCard(allCards.get(index));
                    allCards.remove(index);
                }
            }
        }



        gameOver = false;

        for(int i = 0; i < numPlayers; i++) {
            playerIDs[i] = i;
            canSuggest[i] = false;
            canRoll[i] = false;
            notes[i] = "";
            playerNames[i] = initPlayerNames[i]+"";
            for(int j = 0; j < 21; j++) {
                checkboxes[i][j] = false;
            }
        }
        //turnID is the same value as the playerID, which is the same as the index in the array
        canRoll[turnID] = true;
    }

    public ClueState(ClueState s) {
        turnID = s.turnID;
        numPlayers = s.getNumPlayers();
        for(int i = 0; i < numPlayers; i++) {
            playerIDs[i] = s.getPlayerID(i);
            playerNames[i] = s.getPlayerName(i)+"";
            canSuggest[i] = s.getCanSuggest(i);
            canRoll[i] = s.getCanRoll(i);
            notes[i] = s.getNotes(i)+"";
            cardsPerHand = s.getCardsPerHand();
            Card temp[] = s.cards[i].getCards();
            for(int j = 0; j < s.cards[i].getArrayListLength(); j++) {
                cards[j].addCard(temp[j]);
            }
        }
        solution = s.getSolution();
        checkboxes = new boolean[numPlayers][21];
        for(int i = 0; i < numPlayers; i++) {
            for(int j = 0; j < 21; j++) {
                checkboxes[i][j] = s.getCheckBox(i, j);
            }
        }

        dieValue = s.getDieValue();
        spacesMoved = s.getSpacesMoved();
        gameOver = s.getGameOver();
        board = s.getBoard();
    }

    //getters
    /*public int[][] getPlayerBoard()
    {
        return playerBoard;
    }
    */

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

    //public Card[] getCards(int playerID, int index) { return cards[playerID][index]; }

    public boolean getCheckBox(int playerID, int index) {
        return checkboxes[playerID][index];
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public Hand getCards(int index) {
        return new Hand(cards[index]);
    }

    public int getCardsPerHand() {
        return cardsPerHand;
    }

    public Board getBoard(){return board;}

    public Card[] getSolution() {
        Card temp[] = new Card[3];
        temp[0] = solution[0];
        temp[1] = solution[1];
        temp[2] = solution[2];
        return temp;
    }

    //setters
    /*public void setPlayerBoard(int m, int n, int i, int j, int playerID)
    {
                    playerBoard[m][n] = -1;
                    playerBoard[i][j] = playerID;
    }
    */

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
