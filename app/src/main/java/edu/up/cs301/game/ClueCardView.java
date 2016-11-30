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
    public ClueCardView(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
        setWillNotDraw(false);
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
        onDrawHand(canvas);
    }

    public void onDrawHand(Canvas c){
        Paint p = new Paint();

        for(int i=0;i<hand.getCards().length;i++) {
            p.setColor(hand.getCards()[i].getColor());
            int width = (c.getHeight()*3/4);
            c.drawRect((width*i), 0, width*(i+1), c.getHeight(), p);
            p.setTextSize(25);
            if(Card.ROPE == hand.getCards()[i] || Card.MRS_WHITE == hand.getCards()[i]) {
                    p.setColor(Color.BLACK);
            }else {
                p.setColor(Color.WHITE);
            }
            c.drawText(hand.getCards()[i].getName(), (width/2)-(hand.getCards()[i].getName().length()*5)+(width*i), c.getHeight()/2, p);
        }
    }
}
