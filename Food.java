import java.awt.*;
import java.util.Random;

public class Food {
    private final int UNIT_SIZE = 25;   // Grid size (same as snake's body)
    private int food_x, food_y;
    private Random random;

    public Food(int food_width, int food_height){
        random = new Random();
        spawnFood(food_width, food_height);
    }

    // Generate a new food position
    public void spawnFood(int food_width, int food_height){
        food_x = random.nextInt((int)(food_width/UNIT_SIZE))*UNIT_SIZE;
        food_y = random.nextInt((int)(food_height/UNIT_SIZE))*UNIT_SIZE;
    }

    // Draw the food on the screen
    public void drawFood(Graphics g){
        g.setColor(Color.RED);
        g.fillOval(food_x, food_y, UNIT_SIZE, UNIT_SIZE);  // Draw a circle for food
    }

    // Getters for food position
    public int getFoodX(){
        return food_x;
    }
    public int getFoodY(){
        return food_y;
    }
}
