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

        System.out.println("Click: " + screenX + ", " + screenY );
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
