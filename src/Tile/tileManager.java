package Tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class tileManager {

    GamePanel gp;
    public tile[] tile;
    public int[][] mapTileNum;

    int tileArraySize = 50;


    public tileManager(GamePanel gp) {
        this.gp = gp;

        tile = new tile[tileArraySize];



        // init the 2d array for map tile info
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();

        loadMap("/maps/worldV2.txt");
    }

    public void getTileImage() {

            for(int i = 0;i<tileArraySize;i++) {
                tile[i] = new tile();
            }


            // pre scale the images to improve render preformanace
        setup(0,"grass00", false);
        setup(1,"grass00", false);
        setup(2,"grass00", false);
        setup(3,"grass00", false);
        setup(4,"grass00", false);
        setup(5,"grass00", false);
        setup(6,"grass00", false);
        setup(7,"grass00", false);
        setup(8,"grass00", false);
        setup(9,"grass00", false);
        setup(10,"grass00", false);
        setup(11,"grass01", false);
        setup(12,"water00", true);
        setup(13,"water01",true);
        setup(14,"water02", true);
        setup(15,"water03", true);
        setup(16,"water04", true);
        setup(17,"water05", true);
        setup(18,"water06", true);
        setup(19,"water07", true);
        setup(20,"water08", true);
        setup(21,"water09", true);
        setup(22,"water10", true);
        setup(23,"water11", true);
        setup(24,"water12", true);
        setup(25,"water13", true);
        setup(26,"road00", false);
        setup(27,"road01", false);
        setup(28,"road02", false);
        setup(29,"road03", false);
        setup(30,"road04", false);
        setup(31,"road05", false);
        setup(32,"road06", false);
        setup(33,"road07", false);
        setup(34,"road08", false);
        setup(35,"road09", false);
        setup(36,"road10", false);
        setup(37,"road11", false);
        setup(38,"road12", false);
        setup(39,"earth", false);
        setup(40,"wall", true);
        setup(41,"tree", true);
        setup(42,"table01", false);
        setup(43,"hut", false);
        setup(44,"grass00", false);
        setup(45,"floor01", false);
        setup(46,"background01", false);

    }

    public void setup(int index, String imageName, boolean collision) {

        UtilityTool uTool = new UtilityTool();

        try {

            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/" + imageName + ".png")));
            tile[index].image = uTool.scaledImage(tile[index].image, gp.tileSize,gp.tileSize);
            tile[index].collision = collision;

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    // load the map data from file
    public void loadMap(String filePath) {

        try {
            // read the map data file
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader( new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {


                // read a line from the file
                String line = br.readLine();

                while(col < gp.maxWorldCol) {

                    // splits the string line around the given regular expression in this case a space
                    String[] numbers = line.split(" ");



                    // change the string into an int
                    int num = Integer.parseInt(numbers[col]);

                    // save that int to the 2d map data array
                    mapTileNum[col][row] = num;
                    col++;
                }

                // once it gets to the end of the col go to the next row
                if(col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }

            }

            br.close();

        }catch(IOException e) {
            e.printStackTrace();
        }

    }


    // drawing the world around the player, and moving the map around the player

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            // world X and Y is the cords of the tile with respect to the entire world map
            // Screen X and Y is the cords of the tile on the game screen

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;


            // make a boundary around the player and as long as the player is within this boundary we draw the tile,
            // This is to prevent tiles off screen from being drawn to improve preformance
            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY,null);

            }


            worldCol++;


            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;

                worldRow++;

            }

        }

    }

}
