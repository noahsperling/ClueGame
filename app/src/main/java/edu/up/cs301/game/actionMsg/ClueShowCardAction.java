package edu.up.cs301.game.actionMsg;

import edu.up.cs301.game.Card;
import edu.up.cs301.game.GamePlayer;

/**
 * Created by Eric Imperio on 11/8/2016.
 */

public class ClueShowCardAction extends ClueNonTurnAction{
    private static final long serialVersionUID = 30672024L;

    private String room;
    private String suspect;
    private String weapon;

    public String getRoom() {
        return room;
    }
    public String getSuspect() {
        return suspect;
    }
    public String getWeapon() {
     return weapon;
    }

    public void setRoom(String newRoom) {
        room = newRoom;
    }
    public void setSuspect(String newSuspect) {
        suspect = newSuspect;
    }
    public void setWeapon(String newWeapon) {
        weapon = newWeapon;
    }
    public ClueShowCardAction(GamePlayer player){
        super(player);
    }
}
