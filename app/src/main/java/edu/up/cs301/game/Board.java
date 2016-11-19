package edu.up.cs301.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by Eric Imperio on 11/13/2016.
 */

public class Board {
    private Tile[][] board = new Tile[27][27];
    private int playerBoard[][] = new int[27][27];
    public static int TILE_SIZE = 39;

    public Board(){
        for(int i = 0; i <27; i++){ //buffer
            board[0][i] = null;
            board[25][i] = null;
            board[26][i] = null;
            board[i][0] = null;
            board[i][25] = null;
            board[i][26] = null;
        }

        //Study
        for(int i = 1;i<8;i++){
            for(int j=1;j<5;j++) {
                board[j][i] = new Tile(1, false, Card.STUDY, new Point(TILE_SIZE * i, TILE_SIZE*j));
            }
        }
        board[4][7].setIsDoor(true);
        //between Study and Hall
        board[1][8] = new Tile(0,false,null,new Point(TILE_SIZE*8,TILE_SIZE));
        board[1][9] = null;
        //Study Walls
        for(int i = 1;i<8;i++){ //Bottom and Top Wall
            board[1][i].setTopWall(true); //top
            if(!board[4][i].getIsDoor()) {
                board[4][i].setBottomWall(true); //bottom
            }
        }
        for(int j = 1; j<5;j++){
            board[j][1].setLeftWall(true); //left
            board[j][7].setRightWall(true); //right
        }


        //Hall
        for(int i = 10; i<16;i++){
            for(int j=1;j<8;j++){
                board[j][i] = new Tile(1,false,Card.HALL, new Point(TILE_SIZE*i, TILE_SIZE*j));
            }
        }
        board[5][10].setIsDoor(true);
        board[7][12].setIsDoor(true);
        board[7][13].setIsDoor(true);
        //between Hall and Lounge
        board[1][16] = null;
        board[1][17] = new Tile(0,false,null,new Point(TILE_SIZE*17,TILE_SIZE));
        //Hall walls
        for(int i = 10; i<16; i++){
            board[1][i].setTopWall(true); //top
            if(!board[7][i].getIsDoor()){
                board[7][i].setBottomWall(true); //bottom
            }
        }
        for(int j=1;j<8;j++){
            if(!board[j][10].getIsDoor()) {
                board[j][10].setLeftWall(true); //left
            }

            board[j][15].setRightWall(true); //right
        }

        //Lounge
        for(int i = 18;i<25;i++){
            for(int j = 1;j<7;j++){
                board[j][i] = new Tile(1,false,Card.LOUNGE, new Point(TILE_SIZE*i,TILE_SIZE*j));
            }
        }
        board[6][18].setIsDoor(true);
        //between lounge and Dining Room
        board[7][24] = null;
        board[8][24] = new Tile(0,false,null,new Point(24*TILE_SIZE,8*TILE_SIZE));
        board[9][24] = null;
        //Lounge Walls
        for(int i=18;i<25;i++){
            board[1][i].setTopWall(true);
            if(!board[6][i].getIsDoor()){
                board[6][i].setBottomWall(true);
            }
        }
        for(int j=1;j<7;j++){
            board[j][18].setLeftWall(true);
            board[j][24].setRightWall(true);
        }

        //between Study and Library
        board[5][1] = null;
        board[6][1] = new Tile(0,false,null,new Point(TILE_SIZE,6*TILE_SIZE));
        //hallway between Study and Library runs to Hall
        for(int i = 2; i < 10; i++){
            board[5][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,TILE_SIZE*5));
            board[6][i] = new Tile(0,false, null, new Point(i*TILE_SIZE,TILE_SIZE*6));
        }

        //hallway between Study and Hall runs to bottom of the board
        for(int j = 2; j < 25; j++){
            board[j][8] = new Tile(0,false,null,new Point(8*TILE_SIZE,j*TILE_SIZE));
            board[j][9] = new Tile(0,false,null,new Point(9*TILE_SIZE,j*TILE_SIZE));
        }
        board[7][7] = new Tile(0,false,null,new Point(7*TILE_SIZE,7*TILE_SIZE));
        board[11][7] = new Tile(0,false,null,new Point(7*TILE_SIZE,11*TILE_SIZE));

        //Library
        for (int i = 1; i<7; i++){
            for(int j = 7; j<12; j++){
                board[j][i] = new Tile(1,false,Card.LIBRARY,new Point(i*TILE_SIZE,j*TILE_SIZE));
            }
        }
        board[8][7] = new Tile(1,false,Card.LIBRARY,new Point(7*TILE_SIZE,8*TILE_SIZE));
        board[9][7] = new Tile(1,true,Card.LIBRARY,new Point(7*TILE_SIZE,9*TILE_SIZE));
        board[10][7] = new Tile(1,false,Card.LIBRARY,new Point(7*TILE_SIZE,10*TILE_SIZE));
        board[11][4].setIsDoor(true);
        //Library Walls
        for(int i=1;i<7;i++){
            board[7][i].setTopWall(true);
            board[11][i].setBottomWall(true);
        }
        board[7][6].setRightWall(true);
        board[11][6].setRightWall(true);
        for(int j=7;j<12;j++){
            if(!board[j][1].getIsDoor()) {
                board[j][1].setLeftWall(true);
            }
        }
        board[8][7].setRightWall(true);
        board[8][7].setTopWall(true);
        board[10][7].setRightWall(true);
        board[10][7].setBottomWall(true);

        //hallway between Library and Billiard Room runs to middle
        board[12][1] = null;
        for(int i = 2; i<8;i++){
            board[12][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,12*TILE_SIZE));
        }

        //hallway in front of Billiards Room run from Library to Conservatory
        for(int j = 13; j<24; j++){
            board[j][7] = new Tile(0,false,null,new Point(7*TILE_SIZE,j*TILE_SIZE));
        }

        //billiards room
        for(int i= 1; i<7; i++){
            for(int j=13; j<18;j++) {
                board[j][i] = new Tile(1, false, Card.BILLIARD_ROOM, new Point(i*TILE_SIZE, j*TILE_SIZE));
            }
        }
        board[13][2].setIsDoor(true);
        board[16][6].setIsDoor(true);

        //Billiards room walls
        for(int i=1;i<7;i++){
            if(!board[13][i].getIsDoor()){
                board[13][i].setTopWall(true);
            }
            board[17][i].setBottomWall(true);
        }
        for(int j=13;j<18;j++){
            board[j][1].setLeftWall(true);
            if(!board[j][6].getIsDoor()){
                board[j][6].setRightWall(true);
            }
        }

        //between billiards room and conservatory
        board[18][1] = null;
        board[19][1] = new Tile(0,false,null,new Point(1*TILE_SIZE,19*TILE_SIZE));

        //hallway between billiards room and conservatory run to Ballroom
        for(int i = 2; i<7; i++){
            board[18][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,18*TILE_SIZE));
            board[19][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,19*TILE_SIZE));
        }


        //Conservatory
        for(int i = 1; i<7;i++){
            for(int j = 20; j<25;j++){
                board[j][i] = new Tile(1,false,Card.CONSERVATORY,new Point(i*TILE_SIZE,j*TILE_SIZE));
            }
        }
        board[20][6] = new Tile(0,false,null,new Point(6*TILE_SIZE,20*TILE_SIZE));
        //Conservatory Wall
        for(int i=1;i<6;i++){
            board[20][i].setTopWall(true);
            board[24][i].setBottomWall(true);
        }
        board[24][6].setBottomWall(true);
        for(int j=20;j<25;j++){
            board[j][1].setLeftWall(true);
            if(j>20) {
                board[j][6].setRightWall(true);
            }
        }
        board[21][6].setTopWall(true);

        board[20][5].setIsDoor(true);

        //hallways below Ballroom
        board[24][10] = new Tile(0,false,null,new Point(10*TILE_SIZE,24*TILE_SIZE));
        board[25][10] = new Tile(0,false,null,new Point(10*TILE_SIZE,25*TILE_SIZE));
        board[24][15] = new Tile(0,false,null,new Point(15*TILE_SIZE,24*TILE_SIZE));
        board[25][15] = new Tile(0,false,null,new Point(15*TILE_SIZE,25*TILE_SIZE));

        //hallway on right side of board
        for(int j = 2; j<25; j++){
            board[j][16] = new Tile(0,false,null,new Point(16*TILE_SIZE,j*TILE_SIZE));
            board[j][17] = new Tile(0,false,null,new Point(17*TILE_SIZE,j*TILE_SIZE));
        }

        //Ballroom
        for(int i = 9; i<17; i++){
            for(int j = 18; j<24;j++){
                board[j][i] = new Tile(1,false,Card.BALLROOM,new Point(i*TILE_SIZE,j*TILE_SIZE));
            }
        }
        for(int i = 11; i <15; i++) {
            board[24][i] = new Tile(1, false, Card.BALLROOM, new Point(i*TILE_SIZE, 24*TILE_SIZE));
        }
        board[18][10].setIsDoor(true);
        board[18][15].setIsDoor(true);
        board[21][9].setIsDoor(true);
        board[21][16].setIsDoor(true);
        //Ballroom Walls
        for(int i=9;i<17;i++){ //top
            if(!board[18][i].getIsDoor()){
                board[18][i].setTopWall(true);
            }

            if(i<11 || i>14){ //bottom
                board[23][i].setBottomWall(true);
            }
            if(i>10 && i<15){
                board[24][i].setBottomWall(true);
            }
        }
        for(int j=18;j<24;j++){
            if(!board[j][9].getIsDoor()){
                board[j][9].setLeftWall(true);
                board[j][16].setRightWall(true);
            }
        }
        board[24][11].setLeftWall(true);
        board[24][14].setRightWall(true);

        //hallway in front of Ballroom
        for(int i=10; i<16;i++){
            board[16][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,16*TILE_SIZE));
            board[17][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,17*TILE_SIZE));
        }

        //between Ballroom and Kitchen
        board[24][7] = null;
        board[24][18] = null;

        //hallway in front of hall
        for(int i=10;i<16;i++){
            board[8][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,8*TILE_SIZE));
        }
        //hallway beside clue middle
        for(int j=8;j<16;j++){
            board[j][15] = new Tile(0,false,null,new Point(15*TILE_SIZE,j*TILE_SIZE));
        }

        //Clue middle
        for(int i=10;i<15;i++){
            for(int j=9;j<16;j++){
                board[j][i] = null;

            }
        }

        //hallway below lounge
        for(int i=18; i<24;i++){
            for(int j=7;j<10;j++){
                board[j][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,j*TILE_SIZE));
            }
        }

        //Dining Room
        for(int i=17;i<25;i++){
            for(int j=10;j<17;j++){
                board[j][i] = new Tile(1,false,Card.DINING_ROOM,new Point(i*TILE_SIZE,j*TILE_SIZE));

            }
        }
        board[10][18].setIsDoor(true);
        board[13][17].setIsDoor(true);
        //Dining Room Walls
        for(int i=17;i<25;i++){
            if(!board[10][i].getIsDoor()){
                board[10][i].setTopWall(true);
            }
            if(i<20){
                board[15][i].setBottomWall(true);
            }else{
                board[16][i].setBottomWall(true);
            }
        }
        board[16][20].setLeftWall(true);
        for(int j=10;j<17;j++){
            if(!board[j][17].getIsDoor()){
                board[j][17].setLeftWall(true);
            }
            board[j][24].setRightWall(true);
        }

        //hallway below Dining Room
        for(int i=17;i<20;i++){
            board[16][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,16*TILE_SIZE));

        }

        //hallway beside Kitchen runs to Dining Room
        for(int j=17;j<24;j++){
            board[j][18] = new Tile(0,false,null,new Point(18*TILE_SIZE,j*TILE_SIZE));

        }

        //hallway between Kitchen and Dining Room runs to Ballroom
        for(int i=19;i<24;i++){
            board[17][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,17*TILE_SIZE));
            board[18][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,18*TILE_SIZE));
        }
        board[17][24] = null;
        board[18][24] = new Tile(0,false,null,new Point(24*TILE_SIZE,18*TILE_SIZE));

        //Kitchen
        for(int i=19;i<25;i++){
            for(int j=19;j<25;j++){
                board[j][i] = new Tile(1,false,Card.KITCHEN,new Point(i*TILE_SIZE,j*TILE_SIZE));
            }
        }
        board[19][20].setIsDoor(true);
        //Kitchen Walls
        for(int i=19;i<25;i++){
            if(!board[19][i].getIsDoor()){
                board[19][i].setTopWall(true);
            }
            board[24][i].setBottomWall(true);
        }
        for(int j=19;j<25;j++){
            board[j][19].setLeftWall(true);
            board[j][24].setRightWall(true);
        }

        //sets up integer playerBoard that stores player locations
        for(int k = 0; k < 27; k++) {
            for(int l = 0; l < 27; l++) {
                playerBoard[k][l] = -1;
            }
        }

        playerBoard[1][17] = 0;
        playerBoard[8][24] = 1;
        playerBoard[25][15] = 2;
        playerBoard[25][10] = 3;
        playerBoard[19][1] = 4;
        playerBoard[6][1] = 5;

    }

    /*public void setUpPlayers(int initNumPlayers){
        switch(initNumPlayers)
        {
            case 1: playerBoard[17][1] = 0; //Player 0 starts at mrs.peacocks spot on the board.
                break;
            case 2: playerBoard[17][1] = 0;
                playerBoard[19][1] = 1;
                break;
            case 3: playerBoard[17][1] = 0;
                playerBoard[19][1] = 1;
                break;
            case 4: playerBoard[17][1] = 0;
                playerBoard[19][1] = 1;
                playerBoard[24][8] = 2;
                playerBoard[15][25] = 3;
                break;
            case 5: playerBoard[17][1] = 0;
                playerBoard[19][1] = 1;
                playerBoard[24][8] = 2;
                playerBoard[15][25] = 3;
                playerBoard[10][25] = 4;
                break;
                playerBoard[24][8] = 2;
            case 6: playerBoard[17][1] = 0;
                playerBoard[19][1] = 1;
                playerBoard[24][8] = 2;
                playerBoard[15][25] = 3;
                playerBoard[10][25] = 4;
                playerBoard[1][6] = 5;
                break;
        }
    }*/

    public void drawPlayer(int playerID, int posX, int posY, Canvas c) {
        Paint p = new Paint();
        posX = TILE_SIZE * posX;
        posY = TILE_SIZE * posY;
        int adjustedX = (posX+((c.getWidth()-(27*TILE_SIZE))/2)+1);
        int adjustedY = (posY-(TILE_SIZE/2)+1);

        p.setColor(Color.BLACK);
        c.drawCircle(((adjustedX) + (TILE_SIZE / 2)), ((adjustedY) + (TILE_SIZE/2)), (TILE_SIZE/2)-2, p);
        p.setColor(Color.WHITE);
        switch(playerID) {
            case 0:
                p.setColor(Card.MISS_SCARLET.getColor());
                break;
            case 1:
                p.setColor(Card.COL_MUSTARD.getColor());
                break;
            case 2:
                p.setColor(Card.MRS_WHTE.getColor());
                break;
            case 3:
                p.setColor(Card.MR_GREEN.getColor());
                break;
            case 4:
                p.setColor(Card.MRS_PEACOCK.getColor());
                break;
            case 5:
                p.setColor(Card.PROF_PLUM.getColor());
                break;
        }
        c.drawCircle(((adjustedX) + (TILE_SIZE / 2)), ((adjustedY) + (TILE_SIZE/2)), (TILE_SIZE/2)-4, p);
    }

    public void onDraw(Canvas c){
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setTextSize(30);
        int adjustedX = ((c.getWidth()-(27*TILE_SIZE))/2)+TILE_SIZE;
        int adjustedY = TILE_SIZE;
        for (int i = 0; i<27; i++){
            for(int j = 0; j<27;j++){
                if(board[j][i] != null) {
                    board[j][i].onDraw(c);
                }
            }
        }

        c.drawText(Card.STUDY.getName(),adjustedX+(2.5f*TILE_SIZE),adjustedY+(1.5f*TILE_SIZE),p);
        c.drawText(Card.HALL.getName(),adjustedX+(11.5f*TILE_SIZE),adjustedY+(3*TILE_SIZE),p);
        c.drawText(Card.LOUNGE.getName(),adjustedX+(20*TILE_SIZE),adjustedY+(3*TILE_SIZE),p);
        c.drawText(Card.LIBRARY.getName(),adjustedX+(2*TILE_SIZE),adjustedY+(8*TILE_SIZE),p);
        c.drawText(Card.BILLIARD_ROOM.getName(),adjustedX+(TILE_SIZE),adjustedY+(14*TILE_SIZE),p);
        c.drawText(Card.CONSERVATORY.getName(),adjustedX+(TILE_SIZE/2),adjustedY+(21*TILE_SIZE),p);
        c.drawText(Card.BALLROOM.getName(),adjustedX+(11*TILE_SIZE),adjustedY+(20*TILE_SIZE),p);
        c.drawText(Card.KITCHEN.getName(), (float) (adjustedX+(19.5*TILE_SIZE)),adjustedY+(20.5f*TILE_SIZE),p);
        c.drawText(Card.DINING_ROOM.getName(),adjustedX+(18*TILE_SIZE),adjustedY+(12*TILE_SIZE),p);
        c.drawText("Clue",(c.getWidth()/2)-TILE_SIZE*2,(c.getHeight()/2)-10,p);

        //draw Players
        for(int i = 0; i < 27; i++) {
            for(int j = 0; j < 27; j++) {
                if(playerBoard[j][i] != -1) {
                    drawPlayer(playerBoard[j][i], i, j, c);
                }
            }
        }
    }

    public Tile[][] getBoardArr()
    {
        return board;
    }

    public int[][] getPlayerBoard() {
        return playerBoard;
    }

    public void setPlayerBoard(int x, int y, int m, int n, int playerID) {
        playerBoard[x][y] = playerID;
        playerBoard[m][n] = -1;
    }


}
