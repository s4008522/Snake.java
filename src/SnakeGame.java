import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // Background
    int gameWidth;
    int gameHeight;
    int tileSize = 25;
    
    // Snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;
    
    // Food
    Tile food;
    Random random;

    // Game Logic
    Timer gameLoop;
    int speedX;
    int speedY;
    boolean checkGameOver = false;

   // Constructor
    SnakeGame(int gameWidth, int gameHeight) {
        this.gameHeight = gameHeight;
        this.gameWidth = gameWidth;
        setPreferredSize(new Dimension(this.gameWidth, this.gameHeight));
        setBackground(Color.black);   
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();
        
        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        speedX = 0;
        speedY = 0;

        gameLoop = new Timer(60, this); // Change the number in the brackets, the higher/lower you go the slower/faster the snake goes respectively.
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        /* 
        // Grid Lines
        for(int i = 0; i < gameWidth/tileSize; i++) {
            // Vertical Grid Lines
            g.drawLine(i * tileSize, 0, i * tileSize, i * gameHeight);
            // Horizontal Grid Lines
            g.drawLine(0, i * tileSize, gameWidth, i * tileSize);
            // Grid Color
            g.setColor(Color.DARK_GRAY);
        }
        */



    // Food
    g.setColor(Color.RED);

    // Comment out which one you don't want out of the 2
    // g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
    g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);

    
    // Snake Head
    g.setColor(Color.GREEN);

    // Comment out which one you don't want out of the 2
    g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);
    // g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);


    // Snake Body
    for (int i = 0; i < snakeBody.size(); i++) {
        Tile snakePart = snakeBody.get(i);

        // Comment out which one you don't want out of the 2
        g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
        // g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
    }

    // SCORE AND GAME OVER
    g.setFont(new Font("Arial", Font.PLAIN, 18));
    if (checkGameOver) {
        g.setColor(Color.ORANGE);
        g.drawString("GAME OVER! Your Score Was: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
    }
    else {
        g.setColor(Color.YELLOW);
        g.drawString("SCORE: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
    }
}
    public void placeFood() {
        food.x = random.nextInt(gameWidth/tileSize);    // (24 possible x positions)
        food.y = random.nextInt(gameHeight/tileSize);   // (24 possible y positions)
                                                        // (576 possible positions!)
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        // Eat Food
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        // Snake Body
        for (int i = snakeBody.size()-1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else {
                Tile prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }


        // Snake Head
        snakeHead.x += speedX;
        snakeHead.y += speedY;

        // Game Over Condition
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);

            if (collision(snakeHead, snakePart)) {
                checkGameOver = true;
            }
        }

        if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > gameWidth || 
            snakeHead.y * tileSize < 0 || snakeHead.y * tileSize > gameHeight) {
            checkGameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (checkGameOver) {
            gameLoop.stop();
        }
     }
     

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && speedY != 1) {
            speedX = 0;
            speedY = -1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && speedY != -1) {
            speedX = 0;
            speedY = 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && speedX != 1) {
            speedX = -1;
            speedY = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && speedX != -1) {
            speedX = 1;
            speedY = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}