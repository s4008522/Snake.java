import javax.swing.*;

public class Main {
    public static void main(String[] args) throws Exception {

        int gameWidth =  600;
        int gameHeight = gameWidth;
        int tileSize = 25;

        JFrame UI = new JFrame("Alebron Snake");
        UI.setVisible(true);
        UI.setSize(gameHeight, gameWidth);
        UI.setLocationRelativeTo(null);
        UI.setResizable(false);
        UI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame snakeGame = new SnakeGame(gameHeight, gameWidth);
        UI.add(snakeGame);
        UI.pack();
        snakeGame.requestFocus();
    }       
}

/*

SOURCE:     https://www.youtube.com/watch?v=Y62MJny9LHg
CREATOR:    Kenny Yip Coding

*/


