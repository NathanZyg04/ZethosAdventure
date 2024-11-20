package objects;


import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;

// parent class of all the objects
public class SuperObject {

    // image for the object
    public BufferedImage image, image2, image3;
    // name of object
    public String name;
    // int ID for object
    public int objID;
    public boolean collision = false;
    // pos for object on world
    public int worldX, worldY;
    public UtilityTool uTool = new UtilityTool();

    // hit box of the object for interaction
    public Rectangle hitBox = new Rectangle(0,0,48,48);
    public int hitBoxDefaultX = 0;
    public int hitBoxDefaultY = 0;

    public void draw(Graphics2D g2, GamePanel gp) {

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        }


    }

}
