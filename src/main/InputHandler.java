package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputHandler implements KeyListener, MouseListener {


    public boolean upPressed, downPressed, leftPressed, rightPressed;

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

        // Title Screen
        if(gp.gameState == gp.titleState) {

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
                    gp.gameState = gp.playState;
                    // Start music and play effect
                    gp.playMusic(0);
                    gp.playEffect(5);
                }

                // Load Game
                if(gp.ui.commandNum == 1)
                {

                }

                // Quit
                if(gp.ui.commandNum == 2) {
                    System.exit(0);
                }

            }


        }

        // Play state
        if(gp.gameState == gp.playState) {

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
        }

        // Pause state
        else if(gp.gameState == gp.pauseState) {
            if(code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
            }
        }

        // Dialogue state
        else if(gp.gameState == gp.dialogueState) {

            if(code == KeyEvent.VK_SPACE) {
                System.out.println("Hello");
                gp.npc[gp.player.npcIndex].Speak();
            }

            if(code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
            }
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

    }

    // Mouse Handler

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        screenX = e.getX();
        screenY = e.getY();

        // only click on the entity if it is within a 200x200 box around the player
        if(screenX >= 530 && screenX <= 730 && screenY >= 565 && screenY <= 765) {
            gp.colHandler.clickOnEntity(gp.npc);
            System.out.println("Click: " + screenX + ", " + screenY );
        }


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
}
