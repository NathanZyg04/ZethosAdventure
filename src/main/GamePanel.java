package main;

import Tile.tileManager;
import entity.Entity;
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

    // screen dimensions
    public final int maxScreenCol = 26;
    public final int maxScreenRow = 26;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // game clock
    Thread gameThread;
    final double FPS = 60;

    // handler things
    public CollisionHandler colHandler = new CollisionHandler(this);
    public tileManager tileM = new tileManager(this);
    InputHandler keyH = new InputHandler(this,tileSize);

    // sounds
    Sound music = new Sound();
    Sound soundEffect = new Sound();

    // UI handler
    public UI ui = new UI(this);

    // new player Object
    public Player player = new Player(this,keyH);

    // array of ten slots of objects
    // ten means we can have a max of ten objects loaded at once
    public int objCount = 20;
    public SuperObject[] obj = new SuperObject[objCount];

    // NPC
    public Entity[] npc = new Entity[10];

    // new assetSetter obj pass it this instance of gamePanel
    public AssetSetter aSetter = new AssetSetter(this);


    // GAME STATE
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;



    // constructor
    public GamePanel() throws IOException {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(keyH);
        this.setFocusable(true);
    }

    // set up game objects and play music
    public void setUpGame() throws IOException {

        gameState = playState;

        // set the NPC
        aSetter.setNPC();

        // make the objects in the array with its ID (what object is it) and its X and Y pos in the world
        aSetter.setObj("key",23,7);
        aSetter.setObj("chest",10,8);
        aSetter.setObj("red_potion",23,33);
        aSetter.setObj("key",23,40);
        aSetter.setObj("key",23,39);
        aSetter.setObj("key",23,38);
        aSetter.setObj("key",23,37);
        aSetter.setObj("key",23,36);
        aSetter.setObj("key",23,35);
        aSetter.setObj("key",23,34);
        aSetter.setObj("door",10,12);
        aSetter.setObj("boots",37,42);

        // music
        playMusic(0);
    }

    // start the game clock
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
                try {
                    update();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                // 2 Draw the screen with updated information
                // this calls the paintComponent method
                repaint();
                delta--;
                drawCount++;
            }

            // display FPS to console
            if(timer >= 1000000000) {
               // System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }


    public void update() throws IOException {


        if(gameState == playState) {

            // call the player update method
            player.update();

            // NPC
            for(int i = 0;i<npc.length;i++) {
                if(npc[i] != null) {
                    npc[i].update();
                }
            }
        }
        if(gameState == pauseState) {

        }
    }

    // drawing things on screen
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // DEBUG
        long drawStart = 0;


        if(keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }

        // draw the tiles
        tileM.draw(g2);

        //draw the objects
        for(int i = 0;i< obj.length;i++) {

            if(obj[i] != null) {


                obj[i].draw(g2, this);

            }
        }

        if(keyH.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println(("Draw Time: "+passed));
        }

        // draw the NPC
        for(int i = 0;i< npc.length;i++) {
            if(npc[i] != null) {
                npc[i].draw(g2);
                //System.out.println(npc[i].hitBox.x + ", " + npc[i].hitBox.y);
            }
        }

        // draw the player
        player.draw(g2);

        // UI
        ui.draw(g2);

        // save memory
        g2.dispose();
    }

    // play the music and loop it
    public void playMusic(int i) {

        // load the music file
        music.setFile(i);
        music.play();
        music.loop();

    }

    public void stopMusic() { music.stop(); }

    // plays a sound effect
    public void playEffect(int i) {
        soundEffect.setFile(i);
        soundEffect.play();
    }




}
