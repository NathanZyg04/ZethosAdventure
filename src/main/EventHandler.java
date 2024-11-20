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
        if(hit(27,15,"right")) {
            // event happens
            System.out.println("HIT");
            damagePit(gp.dialogueState);
        }
    }

    // checks if an event is handled when a key in pressed
    public void checkEventWithKey(int index) {

        // index is the specific event it should check
        switch (index) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;


        }
    }




    public boolean hit(int eventCol, int eventRow, String reqDireciton) {

        // eventCol and eventRow is the column and row number in the entire world map of where the hitbox is
        boolean hit = false;

        // Add the worldX and the x and y pos the hitbox to correctly position the hitbox within the player sprite
        gp.player.hitBox.x =  gp.player.worldX + gp.player.hitBox.x;
        gp.player.hitBox.y =  gp.player.worldY + gp.player.hitBox.y;

        eventBox.x = eventCol*gp.tileSize + eventBox.x;
        eventBox.y = eventRow*gp.tileSize + eventBox.y;

        if(gp.player.hitBox.intersects(eventBox)) {

            if(gp.player.direction.contentEquals(reqDireciton) || reqDireciton.contentEquals("any")) {
                hit = true;
            }

        }


        // reset the x and y of the hit boxes to be used again
        gp.player.hitBox.x = gp.player.hitBoxDefaultX;
        gp.player.hitBox.y = gp.player.hitBoxDefaultY;

        // reset the x and y of the event box
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
