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

    // check if the player is hittiwng an object and return the index of that object
    public int checkObject(Entity entity, boolean player) {

        int index = 999;

        // go through the object array
        for(int i = 0;i<gp.obj.length;i++) {

            // check if that object exsists
            if(gp.obj[i] != null) {

                // Get entity's hit box pos
                entity.hitBox.x = entity.worldX + entity.hitBox.x;
                entity.hitBox.y = entity.worldY + entity.hitBox.y;

                // objects X and Y pos
                gp.obj[i].hitBox.x = gp.obj[i].worldX + gp.obj[i].hitBox.x;
                gp.obj[i].hitBox.y = gp.obj[i].worldY + gp.obj[i].hitBox.y;

                switch (entity.direction) {

                    case "up":
                        entity.hitBox.y -= entity.speed;

                        // checks if the object's hitbox intersects with the player entities object
                        // .intersects is from the rectangle class
                        if(entity.hitBox.intersects(gp.obj[i].hitBox)) {
                            if(gp.obj[i].collision) {

                                entity.collisionOn = true;

                            }
                            if(player) {
                                index = i;
                            }
                        }

                        break;
                    case "down":
                        entity.hitBox.y += entity.speed;

                        if(entity.hitBox.intersects(gp.obj[i].hitBox)) {
                            if(gp.obj[i].collision) {

                                entity.collisionOn = true;

                            }
                            if(player) {
                                index = i;
                            }
                        }

                        break;
                    case "left":
                        entity.hitBox.x -= entity.speed;

                        if(entity.hitBox.intersects(gp.obj[i].hitBox)) {
                            if(gp.obj[i].collision) {

                                entity.collisionOn = true;

                            }
                            if(player) {
                                index = i;
                            }
                        }

                        break;
                    case "right":
                        entity.hitBox.x += entity.speed;

                        if(entity.hitBox.intersects(gp.obj[i].hitBox)) {
                            if(gp.obj[i].collision) {

                                entity.collisionOn = true;

                            }
                            if(player) {
                                index = i;
                            }
                        }

                        break;

                }

                // reset the entity x and y value
                entity.hitBox.x = entity.hitBoxDefaultX;
                entity.hitBox.y = entity.hitBoxDefaultY;

                gp.obj[i].hitBox.x = gp.obj[i].hitBoxDefaultX;
                gp.obj[i].hitBox.y = gp.obj[i].hitBoxDefaultY;


            }


        }

        return index;


    }

    // NPC or monster collision
    public int checkEntity(Entity entity, Entity[] target) {
        return 0;
    }
}
