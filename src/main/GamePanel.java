package main;

import Tile.tileManager;
import entity.Player;
import objects.SuperObject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable{

    // Screen settings
    final int originalTileSize = 16;
    final int scale = 3;

    // scale up the original tile size by the scale so it looks better on modern monitors
    public final int tileSize = originalTileSize * scale;

    //world map settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // screen dimensions
    public final int maxScreenCol = 26;
    public final int maxScreenRow = 26;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // game clock
    Thread gameThread;

    // Collision handler
    public CollisionHandler colHandler = new CollisionHandler(this);



    KeyHandler keyH = new KeyHandler();

    final double FPS = 60;

    public tileManager tileM = new tileManager(this);


    // new player Object
    public Player player = new Player(this,keyH);

    // array of ten slots of objects
    // ten means we can have a max of ten objects loaded at once
    public SuperObject[] obj = new SuperObject[10];

    // new assetSetter obj pass it this instance of gamePanel
    public AssetSetter aSetter = new AssetSetter(this);


    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);


    }

    public void setUpGame() throws IOException {

        aSetter.setObj();
    }


    public void startGameThread() {

        // pass the thread the current instance of the GamePanel class
        gameThread = new Thread(this);

        // this will call the run() method
        gameThread.start();


    }


    // from the implements Runnable interface
    // this is where the main game loop is
    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        // as long as the gameThread exists
        while(gameThread != null)
        {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            timer += (currentTime - lastTime);

            lastTime = currentTime;




            if(delta >= 1)
            {
                // 1 Update information like character positions,
                update();
                // 2 Draw the screen with updated information
                // this calls the paintComponent method
                repaint();
                delta--;
                drawCount++;
            }


            // display FPS to console
            if(timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }



        }
    }


    public void update() {

        // call the player update method
        player.update();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // draw the tiles
        tileM.draw(g2);

        //draw the objects
        for(int i = 0;i< obj.length;i++) {

            if(obj[i] != null) {

                obj[i].draw(g2, this);

            }

        }

        // draw the player
        player.draw(g2);
        // save memory
        g2.dispose();


    }



}
