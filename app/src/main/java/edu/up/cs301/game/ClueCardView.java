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

    Hand hand = new Hand();
    public ClueCardView(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
        setWillNotDraw(false);
        hand.addCard(Card.BALLROOM);
        hand.addCard(Card.CANDLESTICK);
        hand.addCard(Card.COL_MUSTARD);
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
