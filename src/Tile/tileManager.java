package Tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class tileManager {

    GamePanel gp;
    public tile[] tile;
    public int[][] mapTileNum;

    int tileArraySize = 36;


    public tileManager(GamePanel gp) {
        this.gp = gp;

        tile = new tile[tileArraySize];



        // init the 2d array for map tile info
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();

        loadMap("/maps/world01.txt");
    }

    public void getTileImage() {

        try {

            for(int i = 0;i<tileArraySize;i++) {
                tile[i] = new tile();
            }


            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/grass01.png")));
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/floor01.png")));
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/water01.png")));
            tile[2].collision = true;
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/earth.png")));
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/tree.png")));
            tile[4].collision = true;
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/road00.png")));
            tile[6].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/road01.png")));
            tile[7].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/road02.png")));
            tile[8].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/road03.png")));
            tile[9].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/road04.png")));
            tile[10].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/road05.png")));
            tile[11].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/road06.png")));
            tile[12].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/road07.png")));
            tile[13].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/road08.png")));
            tile[14].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/road09.png")));
            tile[15].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/road10.png")));
            tile[16].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/road11.png")));
            tile[17].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/road12.png")));
            tile[18].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/table01.png")));
            tile[19].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/hut.png")));
            tile[20].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/wall.png")));
            tile[20].collision = true;
            tile[21].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/grass00.png")));
            tile[22].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/water02.png")));
            tile[23].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/water03.png")));
            tile[24].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/water04.png")));
            tile[25].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/water05.png")));
            tile[26].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/water06.png")));
            tile[27].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/water07.png")));
            tile[28].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/water08.png")));
            tile[29].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/water09.png")));
            tile[30].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/water10.png")));
            tile[31].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/water11.png")));
            tile[32].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/water12.png")));
            tile[33].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/water13.png")));








        } catch(IOException e)
        {
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

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            }


            worldCol++;


            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;

                worldRow++;

            }

        }

    }

}
