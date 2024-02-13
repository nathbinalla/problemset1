import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Create a JFrame, which represents the window of the application
        JFrame frame = new JFrame("Simple GUI Example");

        // Set the size of the frame
        frame.setSize(1280, 720);

        // Set the default operation when the close button is clicked
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JLabel to display text
        JLabel label = new JLabel("Hello, World!");

        // Add the label to the frame
        frame.add(label);

        // Make the frame visible
        frame.setVisible(true);
    }
}