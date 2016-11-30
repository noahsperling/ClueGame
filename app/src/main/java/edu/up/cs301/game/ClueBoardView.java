package edu.up.cs301.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by Paige on 11/13/16.
 */
public class ClueBoardView extends SurfaceView
{
    private Board board;
    public ClueBoardView(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
        setWillNotDraw(false);
        board = new Board();
    }

    public void updateBoard(Board board){
        this.board = board;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.argb(127, 255, 255, 255)); //grey
        canvas.drawRect(0, 0, getWidth(), getHeight(), p);
        super.onDraw(canvas);
        onDrawBoard(canvas);
    }

    public void onDrawBoard(Canvas c){
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setTextSize(30);
        int adjustedX = ((c.getWidth()-(27*Board.TILE_SIZE))/2)+Board.TILE_SIZE;
        int adjustedY = Board.TILE_SIZE;
        for (int i = 0; i<27; i++){
            for(int j = 0; j<27;j++){
                if(board.getBoardArr()[j][i] != null) {
                    onDrawTile(board.getBoardArr()[j][i],c);
                }
            }
        }

        c.drawText(Card.STUDY.getName(),adjustedX+(2.5f*Board.TILE_SIZE),adjustedY+(1.5f*Board.TILE_SIZE),p);
        c.drawText(Card.HALL.getName(),adjustedX+(11.5f*Board.TILE_SIZE),adjustedY+(3*Board.TILE_SIZE),p);
        c.drawText(Card.LOUNGE.getName(),adjustedX+(20*Board.TILE_SIZE),adjustedY+(3*Board.TILE_SIZE),p);
        c.drawText(Card.LIBRARY.getName(),adjustedX+(2*Board.TILE_SIZE),adjustedY+(8*Board.TILE_SIZE),p);
        c.drawText(Card.BILLIARD_ROOM.getName(),adjustedX+(Board.TILE_SIZE),adjustedY+(14*Board.TILE_SIZE),p);
        c.drawText(Card.CONSERVATORY.getName(),adjustedX+(Board.TILE_SIZE/2),adjustedY+(21*Board.TILE_SIZE),p);
        c.drawText(Card.BALLROOM.getName(),adjustedX+(11*Board.TILE_SIZE),adjustedY+(20*Board.TILE_SIZE),p);
        c.drawText(Card.KITCHEN.getName(), (float) (adjustedX+(19.5*Board.TILE_SIZE)),adjustedY+(20.5f*Board.TILE_SIZE),p);
        c.drawText(Card.DINING_ROOM.getName(),adjustedX+(18*Board.TILE_SIZE),adjustedY+(12*Board.TILE_SIZE),p);
        c.drawText("Clue",(c.getWidth()/2)-Board.TILE_SIZE*2,(c.getHeight()/2)-10,p);

        //draw Players
        for(int i = 0; i < 27; i++) {
            for(int j = 0; j < 27; j++) {
                if(board.getPlayerBoard()[j][i] != -1) {
                    onDrawPlayer(board.getPlayerBoard()[j][i], i, j, c);
                }
            }
        }
    }

    public void onDrawTile(Tile tile, Canvas c)
    {
        Paint p = new Paint();
        p.setColor(Color.argb(0,0,0,0));
        int adjustedX = tile.getLocation().x+((c.getWidth()-(27*Board.TILE_SIZE))/2);
        int adjustedY = tile.getLocation().y-(Board.TILE_SIZE/2);
        if(tile.getTileType() == 0){
            p.setColor(Color.rgb(245,203,167));
            c.drawRect(adjustedX,adjustedY,adjustedX+Board.TILE_SIZE,adjustedY+Board.TILE_SIZE,p);
            p.setColor(Color.BLACK);
            c.drawLine(adjustedX,adjustedY,adjustedX,adjustedY+Board.TILE_SIZE,p);
            c.drawLine(adjustedX,adjustedY,adjustedX+Board.TILE_SIZE,adjustedY,p);
            c.drawLine(adjustedX,adjustedY+Board.TILE_SIZE,adjustedX+Board.TILE_SIZE,adjustedY+Board.TILE_SIZE,p);
            c.drawLine(adjustedX+Board.TILE_SIZE,adjustedY+Board.TILE_SIZE,adjustedX+Board.TILE_SIZE,adjustedY,p);

        }else if (tile.getTileType() == 1){
            p.setColor(tile.getRoom().getColor());
            c.drawRect(adjustedX,adjustedY,adjustedX+Board.TILE_SIZE,adjustedY+Board.TILE_SIZE,p);
            p.setColor(Color.BLACK);
            if(tile.getTopWall()){//top
                c.drawRect(adjustedX,adjustedY,adjustedX+Board.TILE_SIZE,adjustedY+(Board.TILE_SIZE/8),p);
            }

            if(tile.getRightWall()){//right
                c.drawRect(adjustedX+Board.TILE_SIZE-(Board.TILE_SIZE/8),adjustedY,adjustedX+Board.TILE_SIZE,adjustedY+Board.TILE_SIZE,p);
            }

            if(tile.getBottomWall()){//bottom
                c.drawRect(adjustedX,adjustedY+Board.TILE_SIZE-(Board.TILE_SIZE/8),adjustedX+Board.TILE_SIZE,adjustedY+Board.TILE_SIZE,p);
            }

            if(tile.getLeftWall()) { //left
                c.drawRect(adjustedX, adjustedY, adjustedX + (Board.TILE_SIZE / 8), adjustedY + Board.TILE_SIZE, p);
            }
        }
    }

    public void onDrawPlayer(int playerID, int posX, int posY, Canvas c){
        Paint p = new Paint();
        posX = Board.TILE_SIZE * posX;
        posY = Board.TILE_SIZE * posY;
        int adjustedX = (posX+((c.getWidth()-(27*Board.TILE_SIZE))/2)+1);
        int adjustedY = (posY-(Board.TILE_SIZE/2)+1);

        p.setColor(Color.BLACK);
        c.drawCircle(((adjustedX) + (Board.TILE_SIZE / 2)), ((adjustedY) + (Board.TILE_SIZE/2)), (Board.TILE_SIZE/2)-2, p);
        p.setColor(Color.WHITE);
        switch(playerID) {
            case 0:
                p.setColor(Card.MISS_SCARLET.getColor());
                break;
            case 1:
                p.setColor(Card.COL_MUSTARD.getColor());
                break;
            case 2:
                p.setColor(Card.MRS_WHITE.getColor());
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
        c.drawCircle(((adjustedX) + (Board.TILE_SIZE / 2)), ((adjustedY) + (Board.TILE_SIZE/2)), (Board.TILE_SIZE/2)-4, p);
    }
}