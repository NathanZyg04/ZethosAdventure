package main;

import objects.OBJ_Key;

import java.io.IOException;

public class AssetSetter {

    public GamePanel gp;
    public int objArrayIndex = 0;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObj(int objID, int x, int y) throws IOException {

        // check if adding the object is within the bounds of the array
        if(objArrayIndex < gp.objCount) {
            gp.obj[objArrayIndex] = new OBJ_Key(objID);
            gp.obj[objArrayIndex].worldX = x * gp.tileSize;
            gp.obj[objArrayIndex].worldY = y * gp.tileSize;

            objArrayIndex++;

        }



//        // create a new key
//        gp.obj[0] = new OBJ_Key(1);
//        // where the object is in the world map
//        gp.obj[0].worldX = 23 * gp.tileSize;
//        gp.obj[0].worldY = 7 * gp.tileSize;
//
//        // another key
//        gp.obj[1] = new OBJ_Key(2);
//        gp.obj[1].worldX = 23 * gp.tileSize;
//        gp.obj[1].worldY = 43 * gp.tileSize;
//        // another key
//        gp.obj[2] = new OBJ_Key(3);
//        gp.obj[2].worldX = 23 * gp.tileSize;
//        gp.obj[2].worldY = 33 * gp.tileSize;
//
//
//        gp.obj[3] = new OBJ_Key(4);
//        gp.obj[3].worldX = 10 * gp.tileSize;
//        gp.obj[3].worldY = 11 * gp.tileSize;

    }
}
