package edu.up.cs301.game;

import android.graphics.Canvas;
import android.graphics.Point;

/**
 * Created by Eric Imperio on 11/13/2016.
 */

public class Board {
    private Tile[][] board = new Tile[27][27];

    public Board(){
        for(int i = 0; i <27; i++){
            board[0][i] = null;
            board[26][i] = null;
            board[i][0] = null;
            board[i][25] = null;
            board[i][26] = null;
        }

        //Study
        for(int i = 1;i<8;i++){
            for(int j=1;j<5;j++) {
                board[j][i] = new Tile(1, false, Card.STUDY, new Point(10 * i, 10*j));
            }
        }
        board[4][7].setIsDoor(true);
        board[1][8] = new Tile(0,false,null,new Point(10,80));
        board[1][9] = null;


        //hall
        for(int i = 10; i<16;i++){
            for(int j=1;j<8;j++){
                board[j][i] = new Tile(1,false,Card.HALL, new Point(10*i, 10*j));
            }
        }
        board[5][10].setIsDoor(true);
        board[7][12].setIsDoor(true);
        board[7][13].setIsDoor(true);
        board[1][16] = null;
        board[1][17] = new Tile(0,false,null,new Point(10,170));

        //Lounge
        for(int i = 18;i<25;i++){
            for(int j = 1;j<7;j++){
                board[j][i] = new Tile(1,false,Card.LOUNGE, new Point(10*i,10*j));
            }
        }
        board[6][18].setIsDoor(true);
        board[7][24] = null;
        board[8][24] = new Tile(0,false,null,new Point(80,240));
        board[9][24] = null;

    }

    public void onDraw(Canvas c){

    }
}
