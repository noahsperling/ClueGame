package edu.up.cs301.game;

import android.content.Context;
import android.graphics.Canvas;
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
    }

    public void updateBoard(Board board){
        this.board = board;
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        board.onDraw(canvas);
    }
}