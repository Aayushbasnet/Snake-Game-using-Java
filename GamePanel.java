import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private final int PANEL_WIDTH = 600;    // Game panel width
    private final int PANEL_HEIGHT = 600;   // Game panel height
    private final int UNIT_SIZE = 25;   // Size of each snake segment and apple segment (Grid size)
    private final int DELAY = 100;  // Game speed (Lower = faster)
    private Timer timer;    // Timer to refresh game at intervals
    private Snake snake;    // Snake
    private Food food;  // Food
    private Boolean is_game_over = false;   // Game Over flag
    private int score = 0;  // Default score

    public GamePanel(){
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT)); // Set panel size
        this.setBackground(Color.BLACK);    // Set background color
        this.setFocusable(true);    // Allow panel to receive key inputs
        this.addKeyListener(this);  // Add KeyListener to detect key presses

        snake = new Snake();    // Initialize the snake
        food = new Food(PANEL_WIDTH, PANEL_HEIGHT);
        startGame();    // Start the game
    }

    // Method to start the game
    public void startGame(){
        timer = new Timer(DELAY, this); // Create a timer that triggers every 100ms (time specified in Delay)
        timer.start();  // Start the game loop
    }

    // Method to reset the game
    public void resetGame(){
        score = 0;  // Reset score
        is_game_over = false;   // Reset gameover flag
        snake = new Snake();    // Create a new snake
        food = new Food(PANEL_WIDTH, PANEL_HEIGHT); // Generate new food
    }

    // Draw game components (Snake & Food will be added Later)
    @Override()
    public void paintComponent(Graphics g){
        super.paintComponent(g);    // Call superclass method to ensure proper rendering

        if(!is_game_over){
            snake.drawSnake(g); // Draw the snake
            food.drawFood(g);   // Draw the food
            drawScore(g);
        }else{
            drawGameOver(g);
        }
    }

    // This method runs when the timer triggers an event
    @Override
    public void actionPerformed(ActionEvent e) {
        // for debug
        snake.move();   // Move the snake

        // Check if snake collides with its body or wall
        if(snake.isCollided(PANEL_WIDTH, PANEL_HEIGHT)){
            is_game_over = true;
            timer.stop();   // stop the game loop
        }

        // Check if snake eats the food
        if(snake.getSnakeBody().get(0).x*UNIT_SIZE == food.getFoodX() && snake.getSnakeBody().get(0).y*UNIT_SIZE == food.getFoodY()){
            score++;
            snake.grow();   //Grow the snake
            food.spawnFood(PANEL_WIDTH,PANEL_HEIGHT);    // Generate new food
        }

        repaint(); // Refresh the screen (for now, we just paint the grid)
    }

    // KeyListener methods (We will use this later for movement)
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!is_game_over){
            switch(e.getKeyCode()){
                case KeyEvent.VK_UP :
                case KeyEvent.VK_W:
                    snake.setDirection('U'); break;
                case KeyEvent.VK_DOWN :
                case KeyEvent.VK_S:
                    snake.setDirection('D'); break;
                case KeyEvent.VK_LEFT :
                case KeyEvent.VK_A:
                    snake.setDirection('L'); break;
                case KeyEvent.VK_RIGHT :
                case KeyEvent.VK_D:
                    snake.setDirection('R'); break;
            }
        }else{
            // Press "R" to restart the game
           if(e.getKeyCode() == KeyEvent.VK_R){
               resetGame(); // Reset the game
               startGame(); // Restart the game
               repaint();
           }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    // Display Game Over Message
    public void drawGameOver(Graphics g){
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        g.drawString("Game Over", (PANEL_WIDTH - metrics.stringWidth("Game Over"))/2, (PANEL_HEIGHT - metrics.getHeight())/2);
        g.setFont(new Font("Arial", Font.PLAIN, 25));
        g.drawString("Press 'R' to restart the game.", (PANEL_WIDTH - metrics.stringWidth("Game Over"))/2, (PANEL_HEIGHT - metrics.getHeight() + 100)/2);
    }

    // Display game score
    public void drawScore(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Ink Free", Font.ITALIC, 25));
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        g.drawString("Score: "+ score, (PANEL_WIDTH - metrics.stringWidth("Score: "+score) -UNIT_SIZE), UNIT_SIZE);
    }
}
