package edu.up.cs301.game;

import java.io.Serializable;

/**
 * Created by Eric Imperio on 11/13/2016.
 */
/* Class that contains all information about the board
 * Two pieces to the Board:
 *      2-dimensional array of Tiles called board
 *          Holds the position of every Tile
 *      2-dimensional array of integers called playerBoard
 *          Holds the position of every player by playerID
 *          -1 if there is no player at that Tile
 *Implements Serializable for network play
 */
public class Board implements Serializable
{
    private Tile[][] board = new Tile[27][27]; //Holds the position of every Tile
    private int[][] playerBoard = new int[27][27]; //Holds the position of every player by playerID,  -1 if there is no player at that Tile
    //This used to be  39, changing it for testing purposes
    public static int TILE_SIZE = 30; //Pixel size of each Tile
    private static final long serialVersionUID = 43290595034145095L; //Long for network play

    /*Note:new Tile is passed:
     *  int TileType
     *      1 is a Room
     *      0 is a Hallway
     *  boolean isDoor
     *  Card room
     *      Must be a Room Card
     *  Point location
     *      Location of Tile x,y*/
    /*Note: j before i in double nested for loops
     *  so board is stored the same way in data as it is visually represented*/
    public Board()
    {
        for(int i = 0; i <27; i++) //buffer
        {
            board[0][i] = null;
            board[25][i] = null;
            board[26][i] = null;
            board[i][0] = null;
            board[i][25] = null;
            board[i][26] = null;
        }

        //Study
        for(int i = 1;i<8;i++)
        {
            for(int j=1;j<5;j++)
            {
                board[j][i] = new Tile(1, false, Card.STUDY, new Point(TILE_SIZE * i, TILE_SIZE*j));
            }
        }
        //Study door bottom right-hand corner
        board[4][7].setIsDoor(true);
        //Spaces between Study and Hall
        board[1][8] = new Tile(0,false,null,new Point(TILE_SIZE*8,TILE_SIZE));
        board[1][9] = null;
        //Study Walls
        for(int i = 1;i<8;i++)
        { //Bottom and Top Walls
            board[1][i].setTopWall(true); //top
            if(!board[4][i].getIsDoor())
            { //Do not want to add a wall to the bottom side of the Study door
                board[4][i].setBottomWall(true); //bottom
            }
        }
        for(int j = 1; j<5;j++)
        { //Left and Right Walls
            board[j][1].setLeftWall(true); //left
            board[j][7].setRightWall(true); //right
        }


        //Hall
        for(int i = 10; i<16;i++)
        {
            for(int j=1;j<8;j++)
            {
                board[j][i] = new Tile(1,false,Card.HALL, new Point(TILE_SIZE*i, TILE_SIZE*j));
            }
        }
        //Hall door middle Left side
        board[5][10].setIsDoor(true);
        //Left Hall door middle Bottom side
        board[7][12].setIsDoor(true);
        //Right Hall door middle Bottom side
        board[7][13].setIsDoor(true);
        //Spaces between Hall and Lounge
        board[1][16] = null;
        board[1][17] = new Tile(0,false,null,new Point(TILE_SIZE*17,TILE_SIZE));
        //Hall walls
        for(int i = 10; i<16; i++)
        { //Bottom and Top Walls
            board[1][i].setTopWall(true); //top
            if(!board[7][i].getIsDoor())
            { //Do not want to add a wall to the bottom side of the Hall bottom doors
                board[7][i].setBottomWall(true); //bottom
            }
        }
        for(int j=1;j<8;j++)
        { //Left and Right Walls
            board[j][15].setRightWall(true); //right
            if(!board[j][10].getIsDoor())
            { //Do not want to add a wall to the left side of the Hall left door
                board[j][10].setLeftWall(true); //left
            }
        }

        //Lounge
        for(int i = 18;i<25;i++)
        {
            for(int j = 1;j<7;j++)
            {
                board[j][i] = new Tile(1,false,Card.LOUNGE, new Point(TILE_SIZE*i,TILE_SIZE*j));
            }
        }
        //Lounge door bottom left-hand corner
        board[6][18].setIsDoor(true);
        //Spaces between lounge and Dining Room
        board[7][24] = null;
        board[8][24] = new Tile(0,false,null,new Point(24*TILE_SIZE,8*TILE_SIZE));
        board[9][24] = null;
        //Lounge Walls
        for(int i=18;i<25;i++)
        { //Bottom and Top Walls
            board[1][i].setTopWall(true); //top
            if(!board[6][i].getIsDoor())
            { //Do not want to add a wall to the bottom side of the Lounge door
                board[6][i].setBottomWall(true); //bottom
            }
        }
        for(int j=1;j<7;j++)
        { //Left and Right Walls
            board[j][18].setLeftWall(true); //left
            board[j][24].setRightWall(true); //right
        }

        //Spaces between Study and Library
        board[5][1] = null;
        board[6][1] = new Tile(0,false,null,new Point(TILE_SIZE,6*TILE_SIZE));
        //hallway between Study and Library runs to Hall (horizontal)
        for(int i = 2; i < 10; i++)
        {
            board[5][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,TILE_SIZE*5));
            board[6][i] = new Tile(0,false, null, new Point(i*TILE_SIZE,TILE_SIZE*6));
        }

        //hallway between Study and Hall runs to bottom of the board (vertical)
        for(int j = 2; j < 25; j++)
        {
            board[j][8] = new Tile(0,false,null,new Point(8*TILE_SIZE,j*TILE_SIZE));
            board[j][9] = new Tile(0,false,null,new Point(9*TILE_SIZE,j*TILE_SIZE));
        }
        board[7][7] = new Tile(0,false,null,new Point(7*TILE_SIZE,7*TILE_SIZE)); //Hallway Tile above Library (2 above Right Door)
        board[11][7] = new Tile(0,false,null,new Point(7*TILE_SIZE,11*TILE_SIZE)); //Hallway Tile below Library (2 below Right Door)

        //Library
        for (int i = 1; i<7; i++)
        {
            for(int j = 7; j<12; j++)
            {
                board[j][i] = new Tile(1,false,Card.LIBRARY,new Point(i*TILE_SIZE,j*TILE_SIZE));
            }
        }
        //Jut-out of Library contains Library Right door
        board[8][7] = new Tile(1,false,Card.LIBRARY,new Point(7*TILE_SIZE,8*TILE_SIZE));
        board[9][7] = new Tile(1,true,Card.LIBRARY,new Point(7*TILE_SIZE,9*TILE_SIZE)); //Library right Door
        board[10][7] = new Tile(1,false,Card.LIBRARY,new Point(7*TILE_SIZE,10*TILE_SIZE));
        //Library bottom door
        board[11][4].setIsDoor(true);

        //Library Walls
        for(int i=1;i<7;i++)
        { //Bottom and Top Walls
            board[7][i].setTopWall(true); //top
            if(!board[7][i].getIsDoor())
            { //Do not want to add a wall to the bottom side of the Library bottom door
                board[11][i].setBottomWall(true); //bottom
            }
        }
        board[7][6].setRightWall(true);
        board[11][4].setBottomWall(false);
        board[11][6].setRightWall(true);
        for(int j=7;j<12;j++)
        { //Left Wall
            board[j][1].setLeftWall(true);
        }
        //Set Wall on Library Jut-out
        board[8][7].setRightWall(true);
        board[8][7].setTopWall(true);
        board[10][7].setRightWall(true);
        board[10][7].setBottomWall(true);

        //hallway between Library and Billiard Room runs to middle (horizontal)
        board[12][1] = null;
        for(int i = 2; i<8;i++)
        {
            board[12][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,12*TILE_SIZE));
        }

        //hallway in front of Billiards Room run from Library to Conservatory (vertical)
        for(int j = 13; j<24; j++)
        {
            board[j][7] = new Tile(0,false,null,new Point(7*TILE_SIZE,j*TILE_SIZE));
        }

        //billiards room
        for(int i= 1; i<7; i++)
        {
            for(int j=13; j<18;j++)
            {
                board[j][i] = new Tile(1, false, Card.BILLIARD_ROOM, new Point(i*TILE_SIZE, j*TILE_SIZE));
            }
        }
        //Billiards room Top Door
        board[13][2].setIsDoor(true);
        //Billiards room Right Door
        board[16][6].setIsDoor(true);

        //Billiards room walls
        for(int i=1;i<7;i++)
        { //Bottom and Top Walls
            board[17][i].setBottomWall(true); //bottom
            if(!board[13][i].getIsDoor())
            { //Do not want to add a wall to the top side of the Billiards room bottom door
                board[13][i].setTopWall(true); //top
            }
        }
        for(int j=13;j<18;j++)
        { //Left and Right Walls
            board[j][1].setLeftWall(true); //left
            if(!board[j][6].getIsDoor())
            { //Do not want to add a wall to the right side of the Billiards room bottom door
                board[j][6].setRightWall(true); //right
            }
        }

        //Spaces between billiards room and conservatory
        board[18][1] = null;
        board[19][1] = new Tile(0,false,null,new Point(1*TILE_SIZE,19*TILE_SIZE));

        //hallway between billiards room and conservatory run to Ballroom (horizontal)
        for(int i = 2; i<7; i++)
        {
            board[18][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,18*TILE_SIZE));
            board[19][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,19*TILE_SIZE));
        }


        //Conservatory
        for(int i = 1; i<7;i++)
        {
            for(int j = 20; j<25;j++)
            {
                board[j][i] = new Tile(1,false,Card.CONSERVATORY,new Point(i*TILE_SIZE,j*TILE_SIZE));
            }
        }
        //Cut-out of Conservatory
        board[20][6] = new Tile(0,false,null,new Point(6*TILE_SIZE,20*TILE_SIZE));
        //Conservatory Walls
        for(int i=1;i<6;i++)
        { //Top and Bottom Walls
            board[20][i].setTopWall(true); //top
            board[24][i].setBottomWall(true); //bottom
        }
        //Jut-out of Conservatory bottom wall
        board[24][6].setBottomWall(true);
        for(int j=20;j<25;j++){ //Left and Right Walls
            board[j][1].setLeftWall(true); //left
            if(j>20)
            { //Cut-out at j=20
                board[j][6].setRightWall(true);
            }
        }
        //Jut-out of Conservatory top wall
        board[21][6].setTopWall(true);
        //Conservatory door
        board[20][5].setIsDoor(true);

        //hallways below Ballroom
        board[24][10] = new Tile(0,false,null,new Point(10*TILE_SIZE,24*TILE_SIZE));
        board[25][10] = new Tile(0,false,null,new Point(10*TILE_SIZE,25*TILE_SIZE));
        board[24][15] = new Tile(0,false,null,new Point(15*TILE_SIZE,24*TILE_SIZE));
        board[25][15] = new Tile(0,false,null,new Point(15*TILE_SIZE,25*TILE_SIZE));

        //hallway on right side of board (vertical)
        for(int j = 2; j<25; j++)
        {
            board[j][16] = new Tile(0,false,null,new Point(16*TILE_SIZE,j*TILE_SIZE));
            board[j][17] = new Tile(0,false,null,new Point(17*TILE_SIZE,j*TILE_SIZE));
        }

        //Ballroom
        for(int i = 9; i<17; i++)
        {
            for(int j = 18; j<24;j++)
            {
                board[j][i] = new Tile(1,false,Card.BALLROOM,new Point(i*TILE_SIZE,j*TILE_SIZE));
            }
        }
        //Jut-out of Ballroom
        for(int i = 11; i <15; i++)
        {
            board[24][i] = new Tile(1, false, Card.BALLROOM, new Point(i*TILE_SIZE, 24*TILE_SIZE));
        }
        //Ballroom Top Left Door
        board[18][10].setIsDoor(true);
        //Ballroom Top Right Door
        board[18][15].setIsDoor(true);
        //Ballroom Left Door
        board[21][9].setIsDoor(true);
        //Ballroom Right Door
        board[21][16].setIsDoor(true);
        //Ballroom Walls
        for(int i=9;i<17;i++)
        { //top
            if(!board[18][i].getIsDoor()){ //Do not want to add a wall to the top side of the Ballroom Top doors
                board[18][i].setTopWall(true);
            }

            if(i<11 || i>14)
            { //bottom (adjusts for Ballroom Jut-out)
                board[23][i].setBottomWall(true);
            }
            if(i>10 && i<15)
            { //bottom (Jut-out of Ballroom)
                board[24][i].setBottomWall(true);
            }
        }
        for(int j=18;j<24;j++)
        { //left and right walls
            if(!board[j][9].getIsDoor())
            { //Do not want to add a wall to the left/right side of the Ballroom left/right doors (same j value of doors)
                board[j][9].setLeftWall(true); //left
                board[j][16].setRightWall(true); //right
            }
        }
        //Left and Right walls on Jut-Out
        board[24][11].setLeftWall(true); //left
        board[24][14].setRightWall(true); //right

        //hallway in front of Ballroom (horizontal)
        for(int i=10; i<16;i++)
        {
            board[16][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,16*TILE_SIZE));
            board[17][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,17*TILE_SIZE));
        }

        //Spaces between Ballroom and Kitchen
        board[24][7] = null;
        board[24][18] = null;

        //hallway in front of hall (horizontal
        for(int i=10;i<16;i++)
        {
            board[8][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,8*TILE_SIZE));
        }
        //hallway left of clue middle
        for(int j=8;j<16;j++)
        {
            board[j][15] = new Tile(0,false,null,new Point(15*TILE_SIZE,j*TILE_SIZE));
        }

        //Clue middle (makes null)
        for(int i=10;i<15;i++)
        {
            for(int j=9;j<16;j++)
            {
                board[j][i] = null;

            }
        }

        //hallway between Lounge and Dining Room (horizontal)
        for(int i=18; i<24;i++)
        {
            for(int j=7;j<10;j++)
            {
                board[j][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,j*TILE_SIZE));
            }
        }

        //Dining Room
        for(int i=17;i<25;i++)
        {
            for(int j=10;j<17;j++)
            {
                board[j][i] = new Tile(1,false,Card.DINING_ROOM,new Point(i*TILE_SIZE,j*TILE_SIZE));

            }
        }
        //Dining Room Top Door
        board[10][18].setIsDoor(true);
        //Dining Room Bottom Door
        board[13][17].setIsDoor(true);
        //Dining Room Walls
        for(int i=17;i<25;i++)
        { //Top and Bottom Walls
            if(!board[10][i].getIsDoor())
            { //Do not want to add a wall to the top side of the Dining Room Top door
                board[10][i].setTopWall(true); //top
            }
            if(i<20)
            { //bottom wall before Jut-out
                board[15][i].setBottomWall(true); //bottom
            }
            else
            { //bottom wall after Jut-out
                board[16][i].setBottomWall(true); //bottom
            }
        }
        //end of Jut-out needs a left wall
        board[16][20].setLeftWall(true);
        for(int j=10;j<17;j++)
        { //Left and Right Door
            board[j][24].setRightWall(true); //right
            if(!board[j][17].getIsDoor())
            { //Do not want to add a wall to the left side of the Dining Room Left door
                board[j][17].setLeftWall(true); //left
            }
        }

        //Cut-out below Dining Room (horizontal)
        for(int i=17;i<20;i++)
        {
            board[16][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,16*TILE_SIZE));
        }

        //hallway beside Kitchen runs to Dining Room (vertical)
        for(int j=17;j<24;j++)
        {
            board[j][18] = new Tile(0,false,null,new Point(18*TILE_SIZE,j*TILE_SIZE));
        }

        //hallway between Kitchen and Dining Room runs to Ballroom (horizontal)
        for(int i=19;i<24;i++)
        {
            board[17][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,17*TILE_SIZE));
            board[18][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,18*TILE_SIZE));
        }
        //Spaces between Kitchan and Dining Room
        board[17][24] = null;
        board[18][24] = new Tile(0,false,null,new Point(24*TILE_SIZE,18*TILE_SIZE));

        //Kitchen
        for(int i=19;i<25;i++)
        {
            for(int j=19;j<25;j++)
            {
                board[j][i] = new Tile(1,false,Card.KITCHEN,new Point(i*TILE_SIZE,j*TILE_SIZE));
            }
        }
        //Kitchen Top Door
        board[19][20].setIsDoor(true);
        //Kitchen Walls
        for(int i=19;i<25;i++)
        { //Top and Bottom Walls
            board[24][i].setBottomWall(true); //bottom
            if(!board[19][i].getIsDoor())
            { //Do not want to add a wall to the top side of the Kitchen Top door
                board[19][i].setTopWall(true); //top
            }
        }
        for(int j=19;j<25;j++)
        { //Left and Right Walls
            board[j][19].setLeftWall(true); //Left
            board[j][24].setRightWall(true); //Right
        }

        //sets up integer playerBoard that stores player locations
        for(int k = 0; k < 27; k++)
        {
            for(int l = 0; l < 27; l++)
            {
                playerBoard[k][l] = -1;
            }
        }

        //Starting Locations of Players
        playerBoard[1][17] = 0; //Miss Scarlet
        playerBoard[8][24] = 1; //Col. Mustard
        playerBoard[25][15] = 2; //Mrs. White
        playerBoard[25][10] = 3; //Mr. Green
        playerBoard[19][1] = 4; //Mrs. Peacock
        playerBoard[6][1] = 5; //Prof. Plum

    }

    //Returns the 2-dimensional Array of the Board
    public Tile[][] getBoard()
    {
        return board;
    }

    //Returns the 2-dimensional Array of the playerID locations on the board
    public int[][] getPlayerBoard()
    {
        return playerBoard;
    }

    //Sets the playerID at new Location (y,x) on the board and removes them from the old location (n,m)
    public void setPlayerOnBoard(int y, int x, int n, int m, int playerID)
    {
        playerBoard[n][m] = -1;
        playerBoard[y][x] = playerID;

    }

    /*Sets the playerBoard to another playerBoard
     *@param int[][] playerBoard*/
    public void setPlayerBoard(int[][] playerBoard)
    {
        for(int i=0;i<27;i++){
            for(int j=0;j<27;j++){
                this.playerBoard[j][i] = playerBoard[j][i];
            }
        }
    }

    /*Set the board to another board
     *@param Tile[][] tiles*/
    public void setBoard(Tile[][] tiles){
        for(int i=0;i<27;i++){
            for(int j=0;j<27;j++){
                board[j][i] = tiles[j][i];
            }
        }
    }
}
