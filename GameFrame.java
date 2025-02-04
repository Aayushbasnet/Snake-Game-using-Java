import javax.swing.JFrame;  // Import JFrame to create the game window
public class GameFrame extends JFrame{
    public GameFrame(){
        // Set the title of the game window
        this.setTitle("Snake Game");

        // Set the default operation when closing the window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Prevent the user from resizing the window
        this.setResizable(false);

        // Set the size of the game window
        this.setSize(600, 600);

        // Add GamePanel to the frame
        this.add(new GamePanel());
        this.pack();    // Adjust the window to fit the panel (Game Panel)

        // Center the window on the screen
        this.setLocationRelativeTo(null);

        // Make the window visible
        this.setVisible(true);


    }
}
