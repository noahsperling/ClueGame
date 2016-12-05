package edu.up.cs301.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.SurfaceView;


/**
 * Created by Eric Imperio on 11/19/2016.
 */

public class ClueCardView extends SurfaceView {

    private Hand hand = new Hand();
    private Drawable cardBallroomImage;
    private Drawable cardCandlestickImage;
    private Drawable cardColMustardImage;
    private Drawable cardConservatoryImage;
    private Drawable cardDiningRoomImage;
    private Drawable cardHallImage;
    private Drawable cardKitchenImage;
    private Drawable cardKnifeImage;
    private Drawable cardLeadPipeImage;
    private Drawable cardLibraryImage;
    private Drawable cardLoungeImage;
    private Drawable cardMissScarletImage;
    private Drawable cardMrGreenImage;
    private Drawable cardMrsPeacockImage;
    private Drawable cardMrsWhiteImage;
    private Drawable cardProfPlumImage;
    private Drawable cardRevolverImage;
    private Drawable cardRopeImage;
    private Drawable cardStudyImage;
    private Drawable cardWrenchImage;
    private Drawable cardBilliardRoomImage;



    public ClueCardView(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);

        cardBallroomImage = context.getResources().getDrawable(R.drawable.ballroom);
        cardCandlestickImage = context.getResources().getDrawable(R.drawable.candlestick);
        cardColMustardImage = context.getResources().getDrawable(R.drawable.col_mustard);
        cardConservatoryImage = context.getResources().getDrawable(R.drawable.conservatory);
        cardDiningRoomImage = context.getResources().getDrawable(R.drawable.dining_room);
        cardHallImage = context.getResources().getDrawable(R.drawable.hall);
        cardKitchenImage = context.getResources().getDrawable(R.drawable.kitchen);
        cardKnifeImage = context.getResources().getDrawable(R.drawable.knife);
        cardLeadPipeImage = context.getResources().getDrawable(R.drawable.lead_pipe);
        cardLibraryImage = context.getResources().getDrawable(R.drawable.library);
        cardLoungeImage = context.getResources().getDrawable(R.drawable.lounge);
        cardMissScarletImage = context.getResources().getDrawable(R.drawable.miss_scarlet);
        cardMrGreenImage = context.getResources().getDrawable(R.drawable.mr_green);
        cardMrsPeacockImage = context.getResources().getDrawable(R.drawable.mrs_peacock);
        cardMrsWhiteImage = context.getResources().getDrawable(R.drawable.mrs_white);
        cardProfPlumImage = context.getResources().getDrawable(R.drawable.prof_plum);
        cardRevolverImage = context.getResources().getDrawable(R.drawable.revolver);
        cardRopeImage = context.getResources().getDrawable(R.drawable.rope);
        cardStudyImage = context.getResources().getDrawable(R.drawable.study);
        cardWrenchImage = context.getResources().getDrawable(R.drawable.wrench);
        cardBilliardRoomImage = context.getResources().getDrawable(R.drawable.ballroom_room);


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

    public void onDrawHand(Canvas c)
    {
        int width = c.getHeight()*5/6;
        int height = c.getHeight()+20;
        for (int i = 0; i < hand.getCards().length; i++)
        {
            if (hand.getCards()[i].getType() == Type.ROOM)
            {
                if (hand.getCards()[i].getName().equals("Ballroom"))
                {
                    cardBallroomImage.setBounds(0+((i)*width), 0, width*(i+1 ), height); //left top right bottom
                    cardBallroomImage.draw(c);
                }
                else if (hand.getCards()[i].getName().equals("Conservatory"))
                {
                    cardConservatoryImage.setBounds(0+(i*width), 0, width*(i+1), height); //left top right bottom
                    cardConservatoryImage.draw(c);
                }
                else if (hand.getCards()[i].getName().equals("Billiard Room"))
                {
                    cardBilliardRoomImage.setBounds(0+(i*width), 0, width*(i+1), height); //left top right bottom
                    cardBilliardRoomImage.draw(c);
                }
                else if (hand.getCards()[i].getName().equals("Library"))
                {
                    cardLibraryImage.setBounds(0+(i*width), 0, width*(i+1), height); //left top right bottom
                    cardLibraryImage.draw(c);
                }
                else if (hand.getCards()[i].getName().equals("Study"))
                {
                    cardStudyImage.setBounds(0+(i*width), 0, width*(i+1), height); //left top right bottom
                    cardStudyImage.draw(c);
                }
                else if (hand.getCards()[i].getName().equals("Hall"))
                {
                    cardHallImage.setBounds(0+(i*width), 0, width*(i+1), height); //left top right bottom
                    cardHallImage.draw(c);
                }
                else if (hand.getCards()[i].getName().equals("Lounge"))
                {
                    cardLoungeImage.setBounds(0+(i*width), 0, width*(i+1), height); //left top right bottom
                    cardLoungeImage.draw(c);
                }
                else if (hand.getCards()[i].getName().equals("Dining Room"))
                {
                    cardDiningRoomImage.setBounds(0+(i*width), 0, width*(i+1), height); //left top right bottom
                    cardDiningRoomImage.draw(c);
                }
                else if (hand.getCards()[i].getName().equals("Kitchen"))
                {
                    cardKitchenImage.setBounds(0+(i*width), 0, width*(i+1), height); //left top right bottom
                    cardKitchenImage.draw(c);
                }
            }
            else if (hand.getCards()[i].getType() == Type.PERSON)
            {
                if (hand.getCards()[i].getName().equals("Col. Mustard"))
                {
                    cardColMustardImage.setBounds(0+(i*width), 0, width*(i+1), height); //left top right bottom
                    cardColMustardImage.draw(c);
                }
                else if (hand.getCards()[i].getName().equals("Prof. Plum"))
                {
                    cardProfPlumImage.setBounds(0+(i*width), 0, width*(i+1), height); //left top right bottom
                    cardProfPlumImage.draw(c);
                }
                else if (hand.getCards()[i].getName().equals("Mr. Green"))
                {
                    cardMrGreenImage.setBounds(0+(i*width), 0, width*(i+1), height); //left top right bottom
                    cardMrGreenImage.draw(c);
                }
                else if (hand.getCards()[i].getName().equals("Mrs. Peacock"))
                {
                    cardMrsPeacockImage.setBounds(0+(i*width), 0, width*(i+1), height); //left top right bottom
                    cardMrsPeacockImage.draw(c);
                }
                else if (hand.getCards()[i].getName().equals("Miss Scarlet"))
                {
                    cardMissScarletImage.setBounds(0+(i*width), 0, width*(i+1), height); //left top right bottom
                    cardMissScarletImage.draw(c);
                }
                else if (hand.getCards()[i].getName().equals("Mrs. White"))
                {
                    cardMrsWhiteImage.setBounds(0+(i*width), 0, width*(i+1), height); //left top right bottom
                    cardMrsWhiteImage.draw(c);
                }
            }
            else if (hand.getCards()[i].getType() == Type.WEAPON)
            {
                if (hand.getCards()[i].getName().equals("Knife"))
                {
                    cardKnifeImage.setBounds(0+(i*width), 0, width*(i+1), height); //left top right bottom
                    cardKnifeImage.draw(c);
                }
                else if (hand.getCards()[i].getName().equals("Candlestick"))
                {
                    cardCandlestickImage.setBounds(0+(i*width), 0, width*(i+1), height); //left top right bottom
                    cardCandlestickImage.draw(c);
                }
                else if (hand.getCards()[i].getName().equals("Revolver"))
                {
                    cardRevolverImage.setBounds(0+(i*width), 0, width*(i+1), height); //left top right bottom
                    cardRevolverImage.draw(c);
                }
                else if (hand.getCards()[i].getName().equals("Rope"))
                {
                    cardRopeImage.setBounds(0+(i*width), 0, width*(i+1), height); //left top right bottom
                    cardRopeImage.draw(c);
                }
                else if (hand.getCards()[i].getName().equals("Lead Pipe"))
                {
                    cardLeadPipeImage.setBounds(0+(i*width), 0, width*(i+1), height); //left top right bottom
                    cardLeadPipeImage.draw(c);
                }
                else if (hand.getCards()[i].getName().equals("Wrench"))
                {
                    cardWrenchImage.setBounds(0+(i*width), 0, width*(i+1), height); //left top right bottom
                    cardWrenchImage.draw(c);
                }
            }

        }
        //cardKitchenImage.setBounds(0+(1*(c.getHeight()*3/4)), 0, (c.getHeight()*3/4)*(1), c.getHeight()); //left top right bottom
        //cardKitchenImage.draw(c);

        //cardBallroomImage.setBounds(0, 0, c.getHeight()*3/4, c.getHeight()); //left top right bottom
        //cardBallroomImage.draw(c);

//       Paint p = new Paint();
////
//        for(int i=0;i<hand.getCards().length;i++) {
//            p.setColor(hand.getCards()[i].getColor());
//            int Width = (c.getHeight()*3/4);
//            //c.drawRect((Width*i), 0, Width*(i+1), c.getHeight(), p);
//            p.setTextSize(25);
//            if(Card.ROPE == hand.getCards()[i] || Card.MRS_WHITE == hand.getCards()[i]) {
//                    p.setColor(Color.BLACK);
//            }else {
//                p.setColor(Color.WHITE);
//            }
//            c.drawText(hand.getCards()[i].getName(), (Width/2)-(hand.getCards()[i].getName().length()*5)+(Width*i), c.getHeight()/2, p);
//        }
    }
}
