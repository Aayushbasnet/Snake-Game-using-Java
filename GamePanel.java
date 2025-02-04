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
    private final int UNIT_SIZE = 20;   // Size of each snake segment and apple segment (Grid size)
    private final int DELAY = 100;  // Game speed (Lower = faster)
    private Timer timer;    // Timer to refresh game at intervals
    private Snake snake;    // Snake object

    private LocalTime localTime;
    public GamePanel(){
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT)); // Set panel size
        this.setBackground(Color.BLACK);    // Set background color
        this.setFocusable(true);    // Allow panel to receive key inputs
        this.addKeyListener(this);  // Add KeyListener to detect key presses

        snake = new Snake();    // Initialize the snake
        startGame();    // Start the game
    }

    // Method to start the game
    public void startGame(){
        localTime = LocalTime.now();
        System.out.println("Before timer starts" + localTime);
        timer = new Timer(DELAY, this); // Create a timer that triggers every 100ms (time specified in Delay)
        System.out.println("After timer is created" + localTime);
        timer.start();  // Start the game loop
        System.out.println("After timer starts" + localTime);
    }

    // Draw game components (Snake & Food will be added Later)
    @Override()
    public void paintComponent(Graphics g){
        super.paintComponent(g);    // Call superclass method to ensure proper rendering

        // Draw a grid (for debugging)
        for(int i = 0; i < PANEL_WIDTH/UNIT_SIZE; i++){
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, PANEL_HEIGHT);
            g.drawLine(0, i*UNIT_SIZE, PANEL_WIDTH, i*UNIT_SIZE);
        }

        snake.drawSnake(g); // Draw the snake
    }

    // This method runs when the timer triggers an event
    @Override
    public void actionPerformed(ActionEvent e) {
        // for debug
        snake.move();   // Move the snake
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
                System.out.println("Going up");
                snake.setDirection('U'); break;
            case KeyEvent.VK_DOWN :
            case KeyEvent.VK_S:
                System.out.println("Going down");
                snake.setDirection('D'); break;
            case KeyEvent.VK_LEFT :
            case KeyEvent.VK_A:
                System.out.println("Going left");
                snake.setDirection('L'); break;
            case KeyEvent.VK_RIGHT :
            case KeyEvent.VK_D:
                System.out.println("Going right");
                snake.setDirection('R'); break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
