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
        if(hit(27,15,"any")) {
            // event happens
            System.out.println("HIT");
            damagePit(gp.dialogueState);
        }
    }

    public boolean hit(int eventCol, int eventRow, String reqDireciton) {

        boolean hit = false;

        gp.player.hitBox.x =  gp.player.worldX + gp.player.hitBox.x;
        gp.player.hitBox.y =  gp.player.worldY + gp.player.hitBox.y;

        eventBox.x = eventCol*gp.tileSize + eventBox.x;
        eventBox.y = eventRow*gp.tileSize + eventBox.y;

        if(gp.player.hitBox.intersects(eventBox)) {

            if(gp.player.direction.contentEquals(reqDireciton) || reqDireciton.contentEquals("any")) {
                hit = true;
            }

        }


        // reset the x and y of the hit boxes
        gp.player.hitBox.x = gp.player.hitBoxDefaultX;
        gp.player.hitBox.y = gp.player.hitBoxDefaultY;

        eventBox.x = eventBoxDefaultX;
        eventBox.y = eventBoxDefaultY;



        return hit;
    }

    public void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.ui.currDialogue = "You fall into a pit!";
        gp.player.life -= 1;
    }
}
