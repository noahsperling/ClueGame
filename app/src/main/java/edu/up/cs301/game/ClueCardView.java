package edu.up.cs301.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by Eric Imperio on 11/19/2016.
 */

public class ClueCardView extends SurfaceView {

    private Hand hand = new Hand();
    public Context context;
    public ClueCardView(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
        setWillNotDraw(false);
        this.context = context.getApplicationContext();
        hand.addCard(Card.BALLROOM);
        hand.addCard(Card.CANDLESTICK);
        hand.addCard(Card.COL_MUSTARD);
        hand.addCard(Card.BILLIARD_ROOM);
        hand.addCard(Card.DINING_ROOM);
        hand.addCard(Card.MR_GREEN);
    }

    public void updateCards(Hand hand){
        this.hand = hand;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        Paint p = new Paint();
        p.setColor(Color.argb(127, 255, 255, 255)); //grey
        canvas.drawRect(0, 0, getWidth(), getHeight(), p);
        super.onDraw(canvas);
        hand.onDraw(canvas);
    }
}
