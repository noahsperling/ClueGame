package edu.up.cs301.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
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
        board.onDraw(canvas);
    }
}