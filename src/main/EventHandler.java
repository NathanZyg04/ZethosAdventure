package main;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    Rectangle eventBox;
    int eventBoxDefaultX, eventBoxDefaultY;



    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventBox = new Rectangle();
        eventBox.x = 23;
        eventBox.y = 23;
        eventBox.width = 2;
        eventBox.height = 2;
        eventBoxDefaultX = eventBox.x;
        eventBoxDefaultY = eventBox.y;

    }
    public void checkEvent() {

    }

    public boolean hit(int eventCol, int eventRow, String reqDireciton) {

        boolean hit = false;



        return hit;
    }
}
