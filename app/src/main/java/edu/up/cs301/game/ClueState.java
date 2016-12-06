package edu.up.cs301.game;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import edu.up.cs301.game.infoMsg.GameState;

/**
 * Created by Noah on 10/25/2016.
 */

public class ClueState extends GameState implements Serializable{

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
    private int cardsPerHand;
    private Hand cards[];
    private Card solution[] = new Card[3];
    private ArrayList<Card> allCards = new ArrayList<Card>();
    private boolean gameOver;
    private Board board;
    private boolean newToRoom[];
    private boolean playerStillInGame[];
    private boolean inCornerRoom[];
    private String[] suggestCards;
    private int playerIDWhoSuggested;
    private String[] cardToShow;
    private boolean[] checkCardToSend;
    private String playerWhoShowedCard = "";
    private boolean usedPassageway[];
    private boolean[] inRoom;
    private int winnerIndex;
    private boolean[] onDoorTile;

    // to satisfy Serializable interface - IDK if necessary
    private static final long serialVersionUID = 7737393762469851826L;


    public ClueState(int initNumPlayers, String initPlayerNames[], int initTurnID) {
        //sets up initial integer values
        turnID = initTurnID;
        dieValue = 0;
        spacesMoved = 0;
        numPlayers = initNumPlayers;
        playerIDWhoSuggested = -1;

        //tells the game how many cards to try and give each player
        if(numPlayers == 3) {
            cardsPerHand = 6;
        }else if(numPlayers == 4) {
            cardsPerHand = 5;
        }else if(numPlayers == 5) {
            cardsPerHand = 4;
        }else {
            cardsPerHand = 3;
        }

        //sets up arrays before loops assign values
        playerIDs = new int[numPlayers];
        playerNames = new String[numPlayers];
        canSuggest = new boolean[numPlayers];
        canRoll = new boolean[numPlayers];
        checkboxes = new boolean[numPlayers][21];
        cards = new Hand[numPlayers];
        newToRoom = new boolean[numPlayers];
        suggestCards = new String[3];
        cardToShow = new String[numPlayers];
        checkCardToSend = new boolean[numPlayers];
        playerStillInGame = new boolean[numPlayers];
        inRoom = new boolean[numPlayers];
        onDoorTile = new boolean[numPlayers];

        //says that all players are still in the game
        for(int i=0;i<numPlayers;i++){
            playerStillInGame[i] = true;
        }

        for(int i =0; i < numPlayers;i++){
            cards[i] = new Hand();
        }
        notes = new String[numPlayers];

        for(Card c: Card.values()) {
            allCards.add(c);
        }
        allCards = shuffle(allCards);
        allCards = shuffle(allCards); //a second time just to be thorough

        //sets up a solution that actually works
        Card[] rooms = {Card.BALLROOM, Card.BILLIARD_ROOM, Card.CONSERVATORY, Card.DINING_ROOM, Card.HALL, Card.KITCHEN, Card.LOUNGE, Card.LIBRARY, Card.STUDY};
        Card[] suspects = {Card.MISS_SCARLET, Card.COL_MUSTARD, Card.MR_GREEN, Card.MRS_PEACOCK, Card.MRS_WHITE, Card.PROF_PLUM};
        Card[] weapons = {Card.WRENCH, Card.KNIFE, Card.CANDLESTICK, Card.REVOLVER, Card.ROPE, Card.LEAD_PIPE};
        Random rand = new Random();
        solution[0] = suspects[rand.nextInt(6)];
        solution[1] = weapons[rand.nextInt(6)];
        solution[2] = rooms[rand.nextInt(9)];
        allCards.remove(solution[0]);
        allCards.remove(solution[1]);
        allCards.remove(solution[2]);

        //creates board, which stores board tiles and player locations
        board = new Board();

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

        for(int i = 0; i < 3; i++) {
            suggestCards[i] = null;
        }

        //says that the game isn't over
        gameOver = false;

        //sets up boolean arrays
        inCornerRoom = new boolean[numPlayers];
        usedPassageway = new boolean[numPlayers];

        //sets up more arrays, all of length numPlayers
        for(int i = 0; i < numPlayers; i++) {
            playerIDs[i] = i;
            canSuggest[i] = false;
            canRoll[i] = false;
            notes[i] = "";
            cardToShow[i] = "";
            playerNames[i] = initPlayerNames[i]+"";
            for(int j = 0; j < 21; j++) {
                checkboxes[i][j] = false;
            }
        }
        //turnID is the same value as the playerID, which is the same as the index in the array
        canRoll[turnID] = true;

        winnerIndex = -1;
    }

    private ArrayList<Card> shuffle(ArrayList<Card> cards) {
        Random rand = new Random();
        ArrayList<Card> rtnCards = cards;
        for(int i = 0; i<rtnCards.size();i++){
            int transferToIdx = rand.nextInt(rtnCards.size());
            Card thisCard = rtnCards.get(i);
            Card idxCard = rtnCards.get(transferToIdx);
            rtnCards.set(i, idxCard);
            rtnCards.set(transferToIdx, thisCard);
        }
        return  rtnCards;
    }

    public ClueState(ClueState s) {
        turnID = s.turnID;
        numPlayers = s.getNumPlayers();
        playerIDs = new int[numPlayers];
        dieValue = s.getDieValue();
        spacesMoved = s.getSpacesMoved();
        notes = new String[numPlayers];
        playerNames = new String[numPlayers];
        canSuggest = new boolean[numPlayers];
        canRoll = new boolean[numPlayers];
        checkboxes = new boolean[numPlayers][21];
        cardsPerHand = s.getCardsPerHand();
        cards = new Hand[numPlayers];
        solution = s.getSolution();
        gameOver = s.getGameOver();
        board = new Board();
        newToRoom = new boolean[numPlayers];
        playerStillInGame = new boolean[numPlayers];
        inCornerRoom = new boolean[numPlayers];
        suggestCards = new String[3];
        playerIDWhoSuggested = s.getPlayerIDWhoSuggested();
        cardToShow = new String[numPlayers];
        checkCardToSend = new boolean[numPlayers];
        usedPassageway = new boolean[numPlayers];
        inRoom = new boolean[numPlayers];
        winnerIndex = s.getWinnerIndex();
        onDoorTile = s.getOnDoorTile();
        playerWhoShowedCard = s.getPlayerWhoShowedCard();


        for(int i = 0; i < numPlayers; i++) {
            playerIDs[i] = s.getPlayerID(i);
            notes[i] = s.getNotes(i);
            playerNames[i] = s.getPlayerName(i);
            canSuggest[i] = s.getCanSuggest(i);
            canRoll[i] = s.getCanRoll(i);
            newToRoom[i] = s.getNewToRoom(i);
            playerStillInGame[i] = s.getPlayerStillInGame(i);
            inCornerRoom[i] = s.getInCornerRoom(i);
            cardToShow[i] = s.getCardToShow(i);
            checkCardToSend[i] = s.getCheckCardToSend(i);
            usedPassageway[i] = s.getUsedPassageway(i);
            inRoom[i] = s.getInRoom(i);
        }

        for(int i = 0; i < numPlayers; i++) {
            cards[i] = new Hand();
            for (int j = 0; j < cardsPerHand; j++) {
                if(s.getCards(i) != null && s.getCards(i).getCards() != null && s.getCards(i).getCards().length > j && s.getCards(i).getCards()[j] != null) {
                    cards[i].addCard(s.getCards(i).getCards()[j]);
                }
            }
        }

        for(int i = 0; i < numPlayers; i++) {
            for(int j = 0; j < 21; j++) {
                checkboxes[i][j] = s.getCheckBox(i, j);
            }
        }

        for(int i =0;i<3;i++){
            suggestCards[i] = s.getSuggestCards(i);
        }


        board.setBoard(s.getBoard().getBoardArr());
        board.setPlayerBoard(s.getBoard().getPlayerBoard());
    }

    private String getSuggestCards(int i) {
        return suggestCards[i];
    }

    private boolean getInRoom(int i) {
        return inRoom[i];
    }

    private boolean getUsedPassageway(int i) {
        return usedPassageway[i];
    }

    private boolean getCheckCardToSend(int i) {
        return checkCardToSend[i];
    }

    private boolean getInCornerRoom(int i) {
        return inCornerRoom[i];
    }

    //getters
//    /*public int[][] getPlayerBoard()
//    {
//        return playerBoard;
//    }
//    */

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

    public boolean getCheckBox(int playerID, int index) {
        return checkboxes[playerID][index];
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public Hand getCards(int index) {
        return new Hand(cards[index]);
    }

    public ArrayList<Card> getAllCards() {
        return allCards;
    }

    public int getCardsPerHand() {
        return cardsPerHand;
    }

    public Board getBoard(){return board;}

    public Card[] getSolution() {
        if(solution != null) {
            Card temp[] = new Card[3];
            temp[0] = solution[0];
            temp[1] = solution[1];
            temp[2] = solution[2];
            return temp;
        }else {
            return null;
        }

    }

    public boolean[] getNewToRoom()
    {
        return newToRoom;
    }

    public boolean getNewToRoom(int playerID){ return newToRoom[playerID];}

    public boolean[] getInCornerRoom()
    {
        return inCornerRoom;
    }

    public boolean[] getUsedPassageway()
    {
        return usedPassageway;
    }

    public String[] getSuggestCards() {
        return suggestCards;
    }

    public int getPlayerIDWhoSuggested() {
        return playerIDWhoSuggested;
    }

    public String getCardToShow(int playerID) {
        return cardToShow[playerID];
    }

    public boolean getPlayerStillInGame(int playerID){
        return playerStillInGame[playerID];
    }

    public boolean[] getPlayerStillInGame()
    {
        return playerStillInGame;
    }

    public boolean[] getCheckCardToSend() {
        return checkCardToSend;
    }

    public String getPlayerWhoShowedCard() { return playerWhoShowedCard;}

    public boolean[] getInRoom () { return inRoom; }

    public boolean[] getOnDoorTile() {return onDoorTile; }

    public int getWinnerIndex() {
        return winnerIndex;
    }

    //setters
    public void setNewToRoom(int playerID, boolean newTo)
    {
        newToRoom[playerID] = newTo;
    }

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

    public void setCards(int playerID, Hand h) {
        cards[playerID] = h;
    }

    public void setInCornerRoom(int playerID, boolean inRoom)
    {
        inCornerRoom[playerID] = inRoom;
    }

    public void setUsedPassageway(int playerID, boolean used)
    {
        usedPassageway[playerID] = used;
    }

    public void setSuggestCards(String[] newSuggestCards) {
        suggestCards = newSuggestCards;
    }

    public void setPlayerIDWhoSuggested(int newID) {
        playerIDWhoSuggested = newID;
    }

    public void setCardToShow(String newCardToShow, int playerID) {
        cardToShow[playerID] = newCardToShow;
    }

    public void setCheckCardToSend(int playerID, boolean value) {
        checkCardToSend[playerID] = value;
    }

    public void setPlayerWhoShowedCard (int playerID) {playerWhoShowedCard = "";}
//    if (playerID == 0) {
//        playerWhoShowedCard = "Miss Scarlet";
//    } else if (playerID == 1) {
//        playerWhoShowedCard = "Col. Mustard";
//    } else if (playerID == 2) {
//        playerWhoShowedCard = "Mrs. White";
//    } else if (playerID == 3) {
//        playerWhoShowedCard = "Mr. Green";
//    } else if (playerID == 4) {
//        playerWhoShowedCard = "Mrs. Peacock";
//    } else if (playerID == 5) {
//        playerWhoShowedCard = "Prof. Plum";
//    } else {
//        playerWhoShowedCard = "";
//    }


    public void setGameOver(boolean newGameOver) {
        gameOver = newGameOver;
    }

    public void setPlayerStillInGame(int playerID, boolean b){
        playerStillInGame[playerID] = b;
    }

    public void setInRoom (int playerID, boolean a) {inRoom[playerID] = a;}

    public void setWinnerIndex(int newWinnerIndex) {
        winnerIndex = newWinnerIndex;
    }

    public void setOnDoorTile(int playerID, boolean onDoor) {onDoorTile[playerID] = onDoor; }

    public void setSolution(Card[] newSolution) {
        solution = newSolution;
    }

}
