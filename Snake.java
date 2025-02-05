import java.util.ArrayList;
import java.awt.*;

public class Snake {
    private final int UNIT_SIZE = 25;   // Size of each body part
    private ArrayList<Point> snake_body;    // List of body parts (body + tail)
    private char direction; // Direction: 'U' = Up, 'D' = Down, 'L' = Left, 'R' = Right

    public Snake(){
        snake_body = new ArrayList<>();   // Initialize snake body
        snake_body.add(new Point(5,5));   // Start position at (5,5) in grid
        direction = 'R';    // Default direction (Right)
    }

    // Move the snake in the current direction
    public void move(){
        Point snake_head = snake_body.get(0); // Get the head of the snake
        Point new_head = new Point(snake_head.x, snake_head.y); // Create new head position

        switch(direction){
            case 'U': new_head.y -= 1; break;
            case 'D': new_head.y += 1; break;
            case 'L': new_head.x -= 1; break;
            case 'R': new_head.x += 1; break;
        }

        snake_body.add(0, new_head);    // Add new head to the first
        System.out.println("After New head added" + snake_body);
        snake_body.remove(snake_body.size()-1); // Remove the last tail segment
        System.out.println("After tail is removed" + snake_body);
    }

    // Grow the snake by adding a new body segment
    public void grow(){
        snake_body.add(new Point(snake_body.get(snake_body.size()-1))); // Duplicate last segment
    }

    // Check if the snake collides with itself or the walls
    public boolean isCollided(int PANEL_WIDTH, int PANEL_HEIGHT){
        Point snake_head = snake_body.get(0);

        // Check if the snake bites itself
        for(int i = 1; i < snake_body.size(); i++){
           if(snake_head.equals(snake_body.get(i))){
               return true;
           }
        }

        // Check if the snake hits the wall
        if(snake_head.x < 0 || snake_head.x >= PANEL_WIDTH/UNIT_SIZE || snake_head.y < 0 || snake_head.y >= PANEL_HEIGHT/UNIT_SIZE){
            return true;
        }

        return false;
    }

    // Change the direction based on user input
    public void setDirection(char new_direction){
        // prevent the snake from moving in the opposite direction
        if((direction == 'L' && new_direction != 'R') ||
                (direction == 'R' && new_direction != 'L') ||
                (direction == 'D' && new_direction != 'U') ||
                (direction == 'U' && new_direction != 'D')){
            direction = new_direction;
        }
    }

    // Draw the snake on the game panel
    public void drawSnake(Graphics g){
//        Point snake_head = snake_body.get(0);
        g.setColor(Color.BLUE);
        for(Point snake_body_point : snake_body){
            g.fillRect(snake_body_point.x*UNIT_SIZE, snake_body_point.y*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
        }
    }

    // Getter for snake body (needed for collision and food logic)
    public ArrayList<Point> getSnakeBody(){
        return snake_body;
    }
}
