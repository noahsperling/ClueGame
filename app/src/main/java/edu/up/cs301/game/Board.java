package edu.up.cs301.game;

import android.graphics.Canvas;
import android.graphics.Point;

/**
 * Created by Eric Imperio on 11/13/2016.
 */

public class Board {
    private Tile[][] board = new Tile[27][27];
    public static int TILE_SIZE = 39;

    public Board(){
        for(int i = 0; i <27; i++){
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
        board[1][8] = new Tile(0,false,null,new Point(TILE_SIZE*8,TILE_SIZE));
        board[1][9] = null;


        //hall
        for(int i = 10; i<16;i++){
            for(int j=1;j<8;j++){
                board[j][i] = new Tile(1,false,Card.HALL, new Point(TILE_SIZE*i, TILE_SIZE*j));
            }
        }
        board[5][10].setIsDoor(true);
        board[7][12].setIsDoor(true);
        board[7][13].setIsDoor(true);
        board[1][16] = null;
        board[1][17] = new Tile(0,false,null,new Point(TILE_SIZE*17,TILE_SIZE));

        //Lounge
        for(int i = 18;i<25;i++){
            for(int j = 1;j<7;j++){
                board[j][i] = new Tile(1,false,Card.LOUNGE, new Point(TILE_SIZE*i,TILE_SIZE*j));
            }
        }
        board[6][18].setIsDoor(true);
        board[7][24] = null;
        board[8][24] = new Tile(0,false,null,new Point(24*TILE_SIZE,8*TILE_SIZE));
        board[9][24] = null;

        board[5][1] = null;
        board[6][1] = new Tile(0,false,null,new Point(TILE_SIZE,6*TILE_SIZE));
        for(int i = 2; i < 10; i++){
            board[5][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,TILE_SIZE*5));
            board[6][i] = new Tile(0,false, null, new Point(i*TILE_SIZE,TILE_SIZE*6));
        }

        for(int j = 2; j < 25; j++){
            board[j][8] = new Tile(0,false,null,new Point(8*TILE_SIZE,j*TILE_SIZE));
            board[j][9] = new Tile(0,false,null,new Point(9*TILE_SIZE,j*TILE_SIZE));
        }
        board[7][7] = new Tile(0,false,null,new Point(7*TILE_SIZE,7*TILE_SIZE));

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
        board[11][7] = new Tile(0,false,null,new Point(7*TILE_SIZE,11*TILE_SIZE));

        board[12][1] = null;
        for(int i = 2; i<8;i++){
            board[12][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,12*TILE_SIZE));
        }

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
        board[18][1] = null;
        board[19][1] = new Tile(0,false,null,new Point(1*TILE_SIZE,19*TILE_SIZE));

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
        board[20][5].setIsDoor(true);

        board[24][10] = new Tile(0,false,null,new Point(10*TILE_SIZE,24*TILE_SIZE));
        board[25][10] = new Tile(0,false,null,new Point(10*TILE_SIZE,25*TILE_SIZE));
        board[24][15] = new Tile(0,false,null,new Point(15*TILE_SIZE,24*TILE_SIZE));
        board[25][15] = new Tile(0,false,null,new Point(15*TILE_SIZE,25*TILE_SIZE));

        for(int j = 2; j<25; j++){
            board[j][16] = new Tile(0,false,null,new Point(16*TILE_SIZE,j*TILE_SIZE));
            board[j][17] = new Tile(0,false,null,new Point(17*TILE_SIZE,j*TILE_SIZE));
        }

        //Ball Room
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

        for(int i=10; i<16;i++){
            board[16][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,16*TILE_SIZE));
            board[17][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,17*TILE_SIZE));
        }

        board[24][7] = null;
        board[24][18] = null;

        for(int i=10;i<16;i++){
            board[8][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,8*TILE_SIZE));
        }

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

        for(int i=17;i<20;i++){
            board[16][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,16*TILE_SIZE));

        }

        for(int j=17;j<24;j++){
            board[j][18] = new Tile(0,false,null,new Point(18*TILE_SIZE,j*TILE_SIZE));

        }

        for(int i=19;i<24;i++){
            board[17][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,17*TILE_SIZE));
            board[18][i] = new Tile(0,false,null,new Point(i*TILE_SIZE,18*TILE_SIZE));
        }
        board[17][24] = null;
        board[18][24] = new Tile(0,false,null,new Point(24*TILE_SIZE,18*TILE_SIZE));

        //Kitchen
        for(int i=19;i<25;i++){
            for(int j=19;j<25;j++){
                board[j][i] = new Tile(1,true,Card.KITCHEN,new Point(i*TILE_SIZE,j*TILE_SIZE));
            }
        }

    }

    public void onDraw(Canvas c){
        for (int i = 0; i<27; i++){
            for(int j = 0; j<27;j++){
                if(board[j][i] != null) {
                    board[j][i].onDraw(c);
                }
            }
        }
    }
}
