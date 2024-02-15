import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Canvas extends JPanel {
    private Particle particle;
    private JPanel buttonPanel;

    public Canvas() {
        setLayout(new BorderLayout());

        // Create the particle simulation panel
        JPanel particlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw a rectangle representing the canvas
                g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            }
        };
        particlePanel.setBackground(new Color(250, 219, 216));
        particlePanel.setPreferredSize(new Dimension(1280, 720));
        add(particlePanel, BorderLayout.CENTER);


        //================Button Panel======================

        // Create the button panel
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(100, 720));
        add(buttonPanel, BorderLayout.EAST);

        // Text Fields   
        JTextField xy_1WallTextField = new JTextField(5);
        JTextField xy_2WallTextField = new JTextField(5);
        JLabel xy_1WallLabel = new JLabel("(x1, y1): ");
        JLabel xy_2WallLabel = new JLabel("(x2, y2): ");
        
        // Buttons for Wall and Particle
        JButton addParticlebtn = new JButton("Add Particle");
        JButton addWallbtn = new JButton("Add Wall");

        // Add Particle input components
        buttonPanel.add(addParticlebtn);

        // Add Wall input components
        buttonPanel.add(xy_1WallLabel);
        buttonPanel.add(xy_1WallTextField);
        buttonPanel.add(xy_2WallLabel);
        buttonPanel.add(xy_2WallTextField);
        buttonPanel.add(addWallbtn);

        //===============End of Button Panel=====================



        // Set up timer to animate particle movement (Possibly for threads)
        
    }
}
     