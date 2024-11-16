package main;

import entity.Entity;

public class CollisionHandler {

    GamePanel gp;

    public CollisionHandler(GamePanel gp) {

        this.gp = gp;
    }

    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.worldX + entity.hitBox.x;
        int entityRightWorldX = entity.worldX + entity.hitBox.x + entity.hitBox.width;
        int entityTopWorldY = entity.worldY + entity.hitBox.y;
        int entityBottomWorldY = entity.worldY + entity.hitBox.y + entity.hitBox.height;

        System.out.println("X: " + entity.worldX + " Y: " + entity.worldY);

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;


        int tileNum1, tileNum2;


        switch (entity.direction) {
            case "up":
                // predict what tile the player is trying to step in
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
                // top left side of player
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                // top right side of player
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];

                // if either for those tiles it hits have collision prevent the player from moving
                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }

                break;
            case "down":

                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;

                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];

                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }

                break;
            case "left":

                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;

                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];

                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }

                break;
            case "right":

                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;

                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];

                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }


                break;


        }


    }
}
