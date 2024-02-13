import java.awt.*;
import javax.swing.JPanel;

public class Canvas extends JPanel{
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Set the color for drawing
        g.setColor(Color.BLACK);
        
        // Draw a rectangle representing the canvas
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        
        // Draw a line from (0,0) to (1280,720)
        g.drawLine(0, getHeight(), getWidth(), 0);
        
        // Draw text at specific coordinates
        g.drawString("(0,0)", 10, getHeight() - 10);
        g.drawString("(1280,720)", getWidth() - 70, 20);
    }
}
