import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Canvas extends JPanel{
    Particle particle = new Particle(100, 100, 45, 5, this);

    public Canvas() {
        // Set the background color to pink
        setBackground(new Color(250, 219, 216));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw a rectangle representing the canvas
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        particle.draw(g);

        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                particle.move(); // Call moveParticle method of the Canvas panel
            }
        });
        timer.start();
    }
}
