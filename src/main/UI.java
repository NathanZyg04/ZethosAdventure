package main;

import objects.OBJ_Key;
import objects.SuperObject;
import objects.heart;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

// UI for the game
public class UI {

    GamePanel gp;
    BufferedImage keyImage;
    Graphics2D g2;

    // UI messages
    public String message = "";
    public boolean messageOn = false;
    private Font arial_40;
    private Font arial_80B;

    Font pixelFont;

    private boolean gameOver = false;

    double playTime = 0;
    int messageCounter = 0;

    public String currDialogue = " ";

    public int commandNum = 0;

    private final Color titleColor = new Color(219,186,22);

    // Heart Health Images
    BufferedImage heartFull, heartHalf, heartEmpty;

    public UI(GamePanel gp) throws IOException {
        this.gp = gp;
        OBJ_Key key = new OBJ_Key("key");
        keyImage = key.image;

        //fonts
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD,80);

        // Load the font
        InputStream is = getClass().getResourceAsStream("/font/VCR_OSD_MONO_1.001.ttf");
        try {
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        }

        // Hud elements
        SuperObject heart = new heart(gp);
        heartFull = heart.image;
        heartHalf = heart.image2;
        heartEmpty = heart.image3;

    }


    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void endGame() {
        gameOver = true;
        gp.playEffect(6);
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;
        g2.setFont(pixelFont);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);

        // Title state
        if(gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // Play state
        if(gp.gameState == gp.playState) {
            // do play state stuff
            drawHealthBar();
        }

        // Pause state
        if(gp.gameState == gp.pauseState) {
            drawHealthBar();
            drawPauseScreen();
        }

        // Dialogue state
        if(gp.gameState == gp.dialogueState) {
            drawHealthBar();
            drawDialogueScreen();
        }

        // Character selection screen
        if(gp.gameState == gp.characterScreenState) {
            drawClassScreen();
        }
    }

    private void drawHealthBar() {

        int x, y;
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        int i = 0;

        // Draw blank heart
        while (i < gp.player.maxLife/2) {
            g2.drawImage(heartEmpty,x,y,null);
            i++;
            x += gp.tileSize*2;
        }

        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        // draw current life

        while (i < gp.player.life) {
            g2.drawImage(heartHalf,x,y,null);
            i++;
            if(i < gp.player.life) {
                g2.drawImage(heartFull,x,y,null);
            }
            i++;
            x += gp.tileSize*2;
        }

    }


    // Title screen drawing
    private void drawTitleScreen() {

        // image background for title
        int scale = 250;
        for(int i = 0;i<gp.maxScreenCol;i++) {

            for(int j = 0;j<gp.maxWorldRow;j++) {

                g2.drawImage(gp.tileM.tile[40].image,i*scale,j*scale,scale,scale,null);
            }
        }

        // Title name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, gp.tileSize*2));
        String text1 = "Zetho's";
        String text2 = "Adventure";
        int x1,x2,y1,y2;

        x1 = getXforCenteredText(text1);
        y1 = gp.tileSize*4;

        x2 = getXforCenteredText(text2);
        y2 = gp.tileSize*6;

        // Shadow
        g2.setColor(Color.BLACK);
        g2.drawString(text1,x1+5,y1+5);
        g2.drawString(text2,x2+5,y2+5);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, gp.tileSize*2));

        g2.setColor(titleColor);
        g2.drawString(text1,x1,y1);
        g2.drawString(text2,x2,y2);

        // Character image

        x1 = gp.screenWidth/2 - (gp.tileSize*2)/2;
        y1 += gp.tileSize*4;
        g2.drawImage(gp.player.down1,x1,y1,gp.tileSize*2,gp.tileSize*2,null);

        // Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, gp.tileSize));

        text1 = "NEW GAME";
        x1 = getXforCenteredText(text1);
        y1 += gp.tileSize*6;

        // Shadow
        g2.setColor(Color.BLACK);
        g2.drawString(text1,x1+5,y1+5);

        // Main text
        g2.setColor(titleColor);
        g2.drawString(text1,x1,y1);

        if(commandNum == 0) {
            g2.drawString(">",x1-gp.tileSize,y1);
        }

        text1 = "LOAD SAVE";
        x1 = getXforCenteredText(text1);
        y1 += gp.tileSize+25;

        // Shadow
        g2.setColor(Color.BLACK);
        g2.drawString(text1,x1+5,y1+5);

        // Main text
        g2.setColor(titleColor);
        g2.drawString(text1,x1,y1);

        if(commandNum == 1) {
            g2.drawString(">",x1-gp.tileSize,y1);
        }

        text1 = "QUIT";
        x1 = getXforCenteredText(text1);
        y1 += gp.tileSize+25;

        // Shadow
        g2.setColor(Color.BLACK);
        g2.drawString(text1,x1+5,y1+5);

        // Main text
        g2.setColor(titleColor);
        g2.drawString(text1,x1,y1);

        if(commandNum == 2) {
            g2.drawString(">",x1-gp.tileSize,y1);
        }


    }

    // Class selection Screen
    private void drawClassScreen() {

        int scale = 250;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, gp.tileSize));
        g2.setColor(Color.white);

        for(int i = 0;i<gp.maxScreenCol;i++) {

            for(int j = 0;j<gp.maxWorldRow;j++) {

                g2.drawImage(gp.tileM.tile[46].image,i*scale,j*scale,scale,scale,null);
            }
        }

        String text = "Select your Class: ";

        int x, y;
        int width, height;

        x = getXforCenteredText(text);
        y = gp.tileSize*4;

        g2.drawString(text,x,y);

        y += gp.tileSize*2;
        x = gp.tileSize*7 + 20;

        width = gp.screenWidth - (gp.tileSize*15);
        height = gp.tileSize*15;

        drawSubWindow(x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, (float) gp.tileSize /2));
        text = "Warrior";




        x = getXforCenteredText(text);
        y += gp.tileSize*3;

        if(commandNum == 3) {
            g2.drawString(">",x-gp.tileSize,y);
        }

        g2.drawString(text,x,y);



        text = "Sorcerer";
        x = getXforCenteredText(text);
        y += gp.tileSize;

        if(commandNum == 4) {
            g2.drawString(">",x-gp.tileSize,y);
        }

        g2.drawString(text,x,y);

        text = "Hunter";
        x = getXforCenteredText(text);
        y += gp.tileSize;

        if(commandNum == 5) {
            g2.drawString(">",x-gp.tileSize,y);
        }

        g2.drawString(text,x,y);





    }

    // Dialogue Screen
    public void drawDialogueScreen() {

        int x, y;
        int width, height;

        x = gp.tileSize*2;
        y = gp.tileSize/2;
        width = gp.screenWidth - (gp.tileSize*4);
        height = gp.tileSize*5;

        drawSubWindow(x,y,width,height);


        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, (float) gp.tileSize /2));
        x += gp.tileSize;
        y += gp.tileSize + 10;

        for(String line : currDialogue.split("\n"))  {
            g2.drawString(line,x,y);
            y += 40;
        }


    }

    // Draws a dialogue subtitle window
    public void drawSubWindow(int x, int y, int width, int height) {

        Color c = new Color(0,0,0,215);

        g2.setColor(c);


        g2.fillRoundRect(x,y,width,height,35,35);

        c = new Color(255,255,255,230);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
    }


    // Pause Screen
    public void drawPauseScreen() {

        String text = "PAUSED";
        int x, y;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,80F));
        x = getXforCenteredText(text);
        y = gp.screenHeight/2;


        g2.drawString(text,x,y);
    }

    public int getXforCenteredText(String text) {

        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;

    }



}
