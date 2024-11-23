package main;

import java.awt.*;

public class EventHandler {

    GamePanel gp;


    EventRect eventRect[][];

    int prevEventX, prevEventY;
    boolean canTouchEvent = true;


    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];


        for(int i = 0;i < gp.maxWorldCol;i++) {

            for(int j = 0;j < gp.maxWorldRow;j++) {
                eventRect[i][j] = new EventRect();
                eventRect[i][j].x = 23;
                eventRect[i][j].y = 23;
                eventRect[i][j].width = 2;
                eventRect[i][j].height = 2;
                eventRect[i][j].eventRectDefaultX = eventRect[i][j].x;
                eventRect[i][j].eventRectDefaultY = eventRect[i][j].y;

            }
        }




    }

    public void checkEvent() {

        //Check if the player is more than 1 tile away from the last event
        int xDist = Math.abs(gp.player.worldX - prevEventX);
        int yDist = Math.abs(gp.player.worldY - prevEventY);
        int dist = Math.max(xDist,yDist);

        if(dist > gp.tileSize) {
            canTouchEvent = true;
        }

        if(canTouchEvent) {

            if(hit(27,15,"any")) { damagePit(27,15,gp.dialogueState); }
            if(hit(23,12,"up")) { healingPool(27,12,gp.dialogueState); }
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




    public boolean hit(int col, int row, String reqDireciton) {

        // eventCol and eventRow is the column and row number in the entire world map of where the hitbox is
        boolean hit = false;

        // Add the worldX and the x and y pos the hitbox to correctly position the hitbox within the player sprite
        gp.player.hitBox.x =  gp.player.worldX + gp.player.hitBox.x;
        gp.player.hitBox.y =  gp.player.worldY + gp.player.hitBox.y;

        eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;

        if(gp.player.hitBox.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone) {

            if(gp.player.direction.contentEquals(reqDireciton) || reqDireciton.contentEquals("any")) {
                hit = true;

                prevEventX = gp.player.worldX;
                prevEventY = gp.player.worldY;
            }

        }


        // reset the x and y of the hit boxes to be used again
        gp.player.hitBox.x = gp.player.hitBoxDefaultX;
        gp.player.hitBox.y = gp.player.hitBoxDefaultY;

        // reset the x and y of the event box
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;



        return hit;
    }

    public void damagePit(int col, int row, int gameState) {
        gp.gameState = gameState;
        gp.ui.currDialogue = "You fall into a pit!";
        gp.player.life -= 1;

        // set the event to be done so it can only happen once
        //eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }

    public void healingPool(int col, int row, int gameState) {

        if(gp.keyH.enterPressed) {
            gp.gameState = gameState;
            gp.ui.currDialogue = "You drink the water\n Your life has been recovered.";
            gp.player.life = gp.player.maxLife;


        }

        gp.keyH.enterPressed = false;

    }
}
