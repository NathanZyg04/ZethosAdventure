package main;

import objects.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

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

    private boolean gameOver = false;

    double playTime = 0;
    int messageCounter = 0;

    public UI(GamePanel gp) throws IOException {
        this.gp = gp;
        OBJ_Key key = new OBJ_Key("key");
        keyImage = key.image;

        //fonts
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD,80);
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
        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if(gp.gameState == gp.playState) {
            // do play state stuff

        }
        if(gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
    }


    //
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
