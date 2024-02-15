import java.awt.*;
import javax.swing.JPanel;


public class Particle implements Runnable{
    private int x;
    private int y;
    private double angle;
    private double radians;
    private double velocity;
    private double dx;
    private double dy;
    private JPanel canvas;

    private static int PARTICLE_SIZE = 10;

    public Particle(int x, int y, double angle, double velocity, JPanel canvas) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.velocity = velocity;
        this.radians = Math.toRadians(this.angle);
        this.dx = this.velocity * Math.cos(this.radians);
        this.dy = this.velocity * Math.sin(this.radians);
        this.canvas = canvas;
    }

    public void move(){
        x += dx;
        y += dy;

        // Check if the particle reaches the boundaries
        if (x <= 0 || x >= 1280 - 5) {
            dx = -dx; // Reverse the x-direction if hitting the horizontal boundaries
        }
        if (y <= 0 || y >= 720 - 5) {
            dy = -dy; // Reverse the y-direction if hitting the vertical boundaries
        }

        canvas.repaint();
    }

    public void draw(Graphics g) {   
        g.setColor(Color.RED);
        // Draw the heart-shaped particle
        int[] xPoints = {x,  x + PARTICLE_SIZE, x + PARTICLE_SIZE / 2};
        int[] yPoints = {y + PARTICLE_SIZE / 4,  y + PARTICLE_SIZE / 4, y + PARTICLE_SIZE};
        g.fillPolygon(xPoints, yPoints, 3);
        g.fillArc(x, y, PARTICLE_SIZE / 2, PARTICLE_SIZE / 2, 0, 180);
        g.fillArc(x + PARTICLE_SIZE / 2, y, PARTICLE_SIZE / 2, PARTICLE_SIZE / 2, 0, 180);
    }

    @Override
    public void run() {
        // Run the particle movement continuously
        while (true) {
            move();
            try {
                Thread.sleep(10); // Adjust sleep time as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
