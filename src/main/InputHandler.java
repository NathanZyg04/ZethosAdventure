package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputHandler implements KeyListener, MouseListener {


    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;

    public boolean checkDrawTime = false;

    private GamePanel gp;
    private int tileSize;

    // X and Y pos for mouse click handler
    public int screenX;
    public int screenY;

    public InputHandler(GamePanel gp, int tileSize) {
        this.gp = gp;
        this.tileSize = tileSize;
    }

    // you have to add these methods from the KeyListener abstract methods
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        // Title state
        if(gp.gameState == gp.titleState) {
            titleScreen(code);
        }

        // Character state
        if(gp.gameState == gp.characterScreenState){

            characterScreen(code);
        }

        // Play state
        if(gp.gameState == gp.playState) {
            playScreen(code);
        }

        // Pause state
        if(gp.gameState == gp.pauseState) {
            pauseScreen(code);
        }

        // Dialogue state
        if(gp.gameState == gp.dialogueState) {
            dialogueScreen(code);
        }


    }

    private void titleScreen(int code) {

            // Up and down select controls
            if(code == KeyEvent.VK_UP){
                if(gp.ui.commandNum == 0) {
                    gp.ui.commandNum = 2;
                } else {
                    gp.ui.commandNum--;
                }

            }

            if(code == KeyEvent.VK_DOWN) {
                if(gp.ui.commandNum == 2) {
                    gp.ui.commandNum = 0;
                } else {
                    gp.ui.commandNum++;
                }
            }


            // Enter Controls
            if(code == KeyEvent.VK_ENTER) {
                // New game
                if(gp.ui.commandNum == 0)
                {
                    gp.gameState = gp.characterScreenState;

                }

                // Load Game
                if(gp.ui.commandNum == 1)
                {
                    gp.gameState = gp.playState;
                    // Start music and play effect
                    gp.playMusic(0);
                    gp.playEffect(5);
                }

                // Quit
                if(gp.ui.commandNum == 2) {
                    System.exit(0);
                }
            }
    }

    private void playScreen(int code) {

        if(code == KeyEvent.VK_W) {

            upPressed = true;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if(code == KeyEvent.VK_T) {
            if(!checkDrawTime) {
                checkDrawTime = true;
            }
            else if(checkDrawTime) {
                checkDrawTime = false;
            }
        }
        // pause and uppause
        if(code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.pauseState;
        }
        // Enter logic
        if(code == KeyEvent.VK_ENTER ) {
           enterPressed = true;

        }

    }

    public void pauseScreen(int code) {

        if(code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
    }

    private void characterScreen(int code) {



        // Up and down select controls
        if(code == KeyEvent.VK_UP){
            if(gp.ui.commandNum == 0) {
                gp.ui.commandNum = 2;
            } else {
                gp.ui.commandNum--;
            }

        }

        if(code == KeyEvent.VK_DOWN) {
            if(gp.ui.commandNum == 2) {
                gp.ui.commandNum = 0;
            } else {
                gp.ui.commandNum++;
            }
        }

        // Enter Controls
        if(code == KeyEvent.VK_ENTER) {
            // New game
            System.out.println("Hello");
            if(gp.ui.commandNum == 3)
            {
                gp.player.classNum = gp.player.warriorClass;
                gp.gameState = gp.playState;
                // Start music and play effect
                gp.playMusic(0);
                gp.playEffect(5);
            }

            // Load Game
            else if(gp.ui.commandNum == 4)
            {
                gp.player.classNum = gp.player.sorcererClass;
                gp.gameState = gp.playState;
                // Start music and play effect
                gp.playMusic(0);
                gp.playEffect(5);

            }

            // Quit
            else if(gp.ui.commandNum == 5) {
                gp.player.classNum = gp.player.hunterClass;
                gp.gameState = gp.playState;
                // Start music and play effect
                gp.playMusic(0);
                gp.playEffect(5);
            }





        }
        if(gp.ui.commandNum < 3) {
            gp.ui.commandNum = 3;
        }
    }

    private void dialogueScreen(int code) {

        if(code == KeyEvent.VK_SPACE) {
            System.out.println("Hello");
            gp.npc[gp.player.npcIndex].Speak();
        }

        if(code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.playState;
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {

            upPressed = false;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }

    }

    // Mouse Handler

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {


        screenX = e.getX();
        screenY = e.getY();

        System.out.println("Click: " + screenX + ", " + screenY );

        // check if the mouse was within the bounds
        mouseClickCheck(screenX,screenY);


    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    // X and Y pos of the mouse click
    public void mouseClickCheck(int x, int y) {

        // Proportion of screen for the clickable ara
        double boxWidthRatio = 0.2;
        double boxHeightRatio = 0.2;

        // Find the dimensions of the box
        int boxHeight = (int) (gp.screenHeight * boxHeightRatio);
        int boxWidth = (int) (gp.screenWidth * boxHeightRatio);

        // vertices of the box
        int boxLeft = gp.player.screenX - boxWidth / 2;
        int boxRight = gp.player.screenX + boxWidth / 2;
        int boxTop = gp.player.screenY - boxHeight / 2;
        int boxBottom = gp.player.screenY + boxHeight / 2;


        // Check if the mouse click is within the box
        if (x >= boxLeft && x <= boxRight && y >= boxTop && y <= boxBottom) {
            gp.colHandler.clickOnEntity(gp.npc);
        }


    }
}
