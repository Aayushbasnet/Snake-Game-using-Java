import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalTime;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private final int PANEL_WIDTH = 600;    // Game panel width
    private final int PANEL_HEIGHT = 600;   // Game panel height
    private final int UNIT_SIZE = 25;   // Size of each snake segment and apple segment (Grid size)
    private final int DELAY = 100;  // Game speed (Lower = faster)
    private Timer timer;    // Timer to refresh game at intervals
    private Snake snake;    // Snake object
    private Food food;
    private Boolean is_game_over = false;

    private LocalTime localTime;
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
        localTime = LocalTime.now();
        timer = new Timer(DELAY, this); // Create a timer that triggers every 100ms (time specified in Delay)
        timer.start();  // Start the game loop
    }

    // Draw game components (Snake & Food will be added Later)
    @Override()
    public void paintComponent(Graphics g){
        super.paintComponent(g);    // Call superclass method to ensure proper rendering

        g.setColor(Color.GRAY);
        // Draw a grid (for debugging)
        for(int i = 0; i < PANEL_WIDTH/UNIT_SIZE; i++){
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, PANEL_HEIGHT);
            g.drawLine(0, i*UNIT_SIZE, PANEL_WIDTH, i*UNIT_SIZE);
        }

        if(!is_game_over){
            snake.drawSnake(g); // Draw the snake
            food.drawFood(g);   // Draw the food
        }else{
            drawGameOver(g);
        }
    }

    // This method runs when the timer triggers an event
    @Override
    public void actionPerformed(ActionEvent e) {
        // for debug
        snake.move();   // Move the snake

        // Check if snake eats the food
        if(snake.getSnakeBody().get(0).x*UNIT_SIZE == food.getFoodX() && snake.getSnakeBody().get(0).y*UNIT_SIZE == food.getFoodY()){
            snake.grow();   //Grow the snake
            food.spawnFood(PANEL_WIDTH,PANEL_HEIGHT);    // Generate new food
        }

        // Check if snake collides with its body or wall
        if(snake.isCollided(PANEL_WIDTH, PANEL_HEIGHT)){
            is_game_over = true;
            timer.stop();
        }
        repaint(); // Refresh the screen (for now, we just paint the grid)
    }

    // KeyListener methods (We will use this later for movement)
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Placeholder: This will be used for controlling the snake
        switch(e.getExtendedKeyCode()){
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
    }
}
