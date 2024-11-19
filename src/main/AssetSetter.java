package main;

import entity.NPC_oldMan;
import objects.OBJ_Key;

import java.io.IOException;

public class AssetSetter {

    public GamePanel gp;
    public int objArrayIndex = 0;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObj(String obj_name, int x, int y) throws IOException {

        // check if adding the object is within the bounds of the array
        if(objArrayIndex < gp.objCount) {
            gp.obj[objArrayIndex] = new OBJ_Key(obj_name);
            gp.obj[objArrayIndex].worldX = x * gp.tileSize;
            gp.obj[objArrayIndex].worldY = y * gp.tileSize;

            objArrayIndex++;

        }
    }


    public void replaceObj(String obj_name, int x, int y, int index) throws IOException {

        gp.obj[index] = new OBJ_Key(obj_name);
        gp.obj[index].worldX = x;
        gp.obj[index].worldY = y;


    }

    public void setNPC() {

        gp.npc[0] = new NPC_oldMan(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY =gp.tileSize*21;

    }
}
