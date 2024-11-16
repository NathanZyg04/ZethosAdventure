package main;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Zethos Adventure");


        // create a new gamePanel object
        GamePanel gamePanel = new GamePanel();

        // add it to the window
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // game set up with setting up the objects
        gamePanel.setUpGame();
        // start the game clock (thread)
        gamePanel.startGameThread();

    }

}
