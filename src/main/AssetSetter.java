package main;

import objects.OBJ_Key;

import java.io.IOException;

public class AssetSetter {

    public GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObj() throws IOException {

        // create a new key
        gp.obj[0] = new OBJ_Key(0);
        // where the object is in the world map
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;

        // another key
        gp.obj[1] = new OBJ_Key(0);
        gp.obj[1].worldX = 23 * gp.tileSize;
        gp.obj[1].worldY = 43 * gp.tileSize;

    }
}
