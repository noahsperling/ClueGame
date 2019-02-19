package edu.up.cs301.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

import java.awt.font.TextAttribute;

/**
 * Created by Paige on 11/13/16.
 */
//Used to Draw the Board and Players
public class ClueBoardView extends SurfaceView
{
    private Board board; //Board to draw

    public ClueBoardView(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
        setWillNotDraw(false); //set to true in super
        board = new Board(); //sets up new blank board
    }

    /*Updates Board to a new Board
     *Called when the board in the state has been modified*/
    public void updateBoard(Board board)
    {
        this.board = board;
        invalidate(); //redraw
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        Paint p = new Paint();
        p.setColor(Color.argb(127, 255, 255, 255)); //grey
        canvas.drawRect(0, 0, getWidth(), getHeight(), p); //draw background
        super.onDraw(canvas);
        onDrawBoard(canvas); //draw the Board
    }

    //Draws the Board and calls onDrawPlayers to draw the players as well as onDrawTile to draw the tiles
    public void onDrawBoard(Canvas c)
    {
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        //p.setColor(Color.BLUE)
;        p.setTextSize(30); //any text draw will be size 30, Used to draw the Room names
        int adjustedX = ((c.getWidth()-(25*Board.TILE_SIZE))/2)+Board.TILE_SIZE; //Centers board, adjustedX is the spot of the first column
        int adjustedY = Board.TILE_SIZE; //Sets location of first Row

        for (int i = 0; i<27; i++)
        {
            for(int j = 0; j<27;j++)
            {
                if(board.getBoard()[j][i] != null)
                {
                    onDrawTile(board.getBoard()[j][i],c); //draw the tile
                }
            }
        }

        //draws the Room text
        //The x values have been adjusted to reflect the new GUI size and shape
        c.drawText(Card.STUDY.getName(),adjustedX+(2f*Board.TILE_SIZE),adjustedY+(1.5f*Board.TILE_SIZE),p);
        c.drawText(Card.HALL.getName(),adjustedX+(10f*Board.TILE_SIZE),adjustedY+(3*Board.TILE_SIZE),p);
        c.drawText(Card.LOUNGE.getName(),adjustedX+(17*Board.TILE_SIZE),adjustedY+(3*Board.TILE_SIZE),p);
        c.drawText(Card.LIBRARY.getName(),adjustedX+(2*Board.TILE_SIZE),adjustedY+(8*Board.TILE_SIZE),p);
        c.drawText(Card.BILLIARD_ROOM.getName(),adjustedX+(Board.TILE_SIZE),adjustedY+(14*Board.TILE_SIZE),p);
        c.drawText(Card.CONSERVATORY.getName(),adjustedX+(Board.TILE_SIZE/2),adjustedY+(21*Board.TILE_SIZE),p);
        c.drawText(Card.BALLROOM.getName(),adjustedX+(9*Board.TILE_SIZE),adjustedY+(20*Board.TILE_SIZE),p);
        c.drawText(Card.KITCHEN.getName(), (float) (adjustedX+(19*Board.TILE_SIZE)),adjustedY+(20.5f*Board.TILE_SIZE),p);
        c.drawText(Card.DINING_ROOM.getName(),adjustedX+(17*Board.TILE_SIZE),adjustedY+(12*Board.TILE_SIZE),p);
        //draws "Clue" in the middle of the Board
        c.drawText("Clue",(c.getWidth()/2)-Board.TILE_SIZE*2,(c.getHeight()/2)-10,p);

        //draws the Players
        for(int i = 0; i < 27; i++) {
            for(int j = 0; j < 27; j++) {
                if(board.getPlayerBoard()[j][i] != -1) {
                    onDrawPlayer(board.getPlayerBoard()[j][i], i, j, c); //draw player
                }
            }
        }
    }

    //Draws a Tile
    public void onDrawTile(Tile tile, Canvas c)
    {
        Paint p = new Paint();
        p.setColor(Color.argb(0,0,0,0));
        //NK: The value multiplied by Board.TILE_SIZE used to be 27. Changing it to see what happens.

        int adjustedX = tile.getLocation().x+((c.getWidth()-(27*Board.TILE_SIZE))/2); //Adjusts X from tile.getLocation().x to draw x with respect to the center board
        int adjustedY = tile.getLocation().y-(Board.TILE_SIZE/2); //Adjusts Y from tile.getLocation().x to draw x with respect to the center board

        if(tile.getTileType() == 0)
        { //draws Hallway Tiles (same color)
            p.setColor(Color.rgb(245,203,167)); //Tan
            c.drawRect(adjustedX,adjustedY,adjustedX+Board.TILE_SIZE,adjustedY+Board.TILE_SIZE,p); //Tile
            p.setColor(Color.BLACK); //Black
            //Draw Border Line
            c.drawLine(adjustedX,adjustedY,adjustedX,adjustedY+Board.TILE_SIZE,p); //Left
            c.drawLine(adjustedX,adjustedY,adjustedX+Board.TILE_SIZE,adjustedY,p); //Top
            c.drawLine(adjustedX,adjustedY+Board.TILE_SIZE,adjustedX+Board.TILE_SIZE,adjustedY+Board.TILE_SIZE,p); //Bottom
            c.drawLine(adjustedX+Board.TILE_SIZE,adjustedY+Board.TILE_SIZE,adjustedX+Board.TILE_SIZE,adjustedY,p); //Right

        }
        else if (tile.getTileType() == 1)
        { //draws Room Tiles (same method to get color) (tile.getRoom().getColor())
            p.setColor(tile.getRoom().getColor()); //Room color from Card class
            c.drawRect(adjustedX,adjustedY,adjustedX+Board.TILE_SIZE,adjustedY+Board.TILE_SIZE,p); //Draws Tile
            p.setColor(Color.BLACK); //Black

            //Draw Any Walls at 1/8 the TILE_SIZE
            if(tile.getTopWall())
            {//top
                c.drawRect(adjustedX,adjustedY,adjustedX+Board.TILE_SIZE,adjustedY+(Board.TILE_SIZE/8),p);
            }

            if(tile.getRightWall())
            {//right
                c.drawRect(adjustedX+Board.TILE_SIZE-(Board.TILE_SIZE/8),adjustedY,adjustedX+Board.TILE_SIZE,adjustedY+Board.TILE_SIZE,p);
            }

            if(tile.getBottomWall())
            {//bottom
                c.drawRect(adjustedX,adjustedY+Board.TILE_SIZE-(Board.TILE_SIZE/8),adjustedX+Board.TILE_SIZE,adjustedY+Board.TILE_SIZE,p);
            }

            if(tile.getLeftWall())
            { //left
                c.drawRect(adjustedX, adjustedY, adjustedX + (Board.TILE_SIZE / 8), adjustedY + Board.TILE_SIZE, p);
            }
        }
    }

    //draws a Player
    public void onDrawPlayer(int playerID, int posX, int posY, Canvas c)
    {
        Paint p = new Paint();
        posX = Board.TILE_SIZE * posX; //Where the player's x is
        posY = Board.TILE_SIZE * posY; //Where the player's y is
        int adjustedX = (posX+((c.getWidth()-(27*Board.TILE_SIZE))/2)+1); //adjust X to draw x with respect to the center board
        int adjustedY = (posY-(Board.TILE_SIZE/2)+1); //adjust Y to draw y with respect to the center board
        p.setColor(Color.BLACK); //black
        c.drawCircle(((adjustedX) + (Board.TILE_SIZE / 2)), ((adjustedY) + (Board.TILE_SIZE/2)), (Board.TILE_SIZE/2)-2, p); //draw border for the player
        p.setColor(Color.WHITE); //white

        //set the appropriate player card based on the playerID get color from Card.("the person").getColor()
        switch(playerID)
        {
            case 0:
                p.setColor(Card.MISS_SCARLET.getColor()); //red
                break;
            case 1:
                p.setColor(Card.COL_MUSTARD.getColor()); //yellow
                break;
            case 2:
                p.setColor(Card.MRS_WHITE.getColor()); //white
                break;
            case 3:
                p.setColor(Card.MR_GREEN.getColor()); //green
                break;
            case 4:
                p.setColor(Card.MRS_PEACOCK.getColor()); //blue
                break;
            case 5:
                p.setColor(Card.PROF_PLUM.getColor()); //purple
                break;
        }

        //draw player
        c.drawCircle(((adjustedX) + (Board.TILE_SIZE / 2)), ((adjustedY) + (Board.TILE_SIZE/2)), (Board.TILE_SIZE/2)-4, p);
    }
}