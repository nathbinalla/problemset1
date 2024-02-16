import java.awt.*;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.util.ArrayList;


public class Particle implements Runnable{
    private int x;
    private int y;
    private double angle;
    private double radians;
    private double velocity;
    private double dx;
    private double dy;
    private JPanel canvas;
    private ArrayList<Wall> walls;

    private static int PARTICLE_SIZE = 10;

    public Particle(int x, int y, double angle, double velocity, JPanel canvas, ArrayList<Wall> walls) {
        this.x = x;
        this.y = y;
        this.angle = -angle;
        this.velocity = velocity;
        this.radians = Math.toRadians(this.angle);
        this.dx = this.velocity * Math.cos(this.radians);
        this.dy = this.velocity * Math.sin(this.radians);
        this.canvas = canvas;
        this.walls = walls;
    }

    public void move(){
        x += dx;
        y += dy;

        // Check if the particle reaches the boundaries
        if (x < 0 || x >= 1280 - PARTICLE_SIZE) {
            dx = -dx; // Reverse the x-direction if hitting the horizontal boundaries
        }
        if (y < 0 || y >= 720 - PARTICLE_SIZE) {
            dy = -dy; // Reverse the y-direction if hitting the vertical boundaries
        }
    }

    public void draw(Graphics g) {   
        g.setColor(Color.RED);
    // Adjust the coordinates to match the desired coordinate system
        int adjustedX = x;
        int adjustedY = 720 - y - PARTICLE_SIZE;

        // Draw the heart-shaped particle
        int[] xPoints = {adjustedX,  adjustedX + PARTICLE_SIZE, adjustedX + PARTICLE_SIZE / 2};
        int[] yPoints = {adjustedY + PARTICLE_SIZE / 4,  adjustedY + PARTICLE_SIZE / 4, adjustedY + PARTICLE_SIZE};
        g.fillPolygon(xPoints, yPoints, 3);
        g.fillArc(adjustedX, adjustedY, PARTICLE_SIZE / 2, PARTICLE_SIZE / 2, 0, 180);
        g.fillArc(adjustedX + PARTICLE_SIZE / 2, adjustedY, PARTICLE_SIZE / 2, PARTICLE_SIZE / 2, 0, 180);
    }

    public void checkWallCollision() {
        // Check for collision with each wall
        for (Wall wall : walls) {
            if (wall.intersects(x, y)) {
                double wallNormalX = wall.getX2() - wall.getX1();
                double wallNormalY = wall.getY2() - wall.getY1();

                // Calculate the dot product of the velocity vector and the wall normal
                double dotProduct = dx * wallNormalX + dy * wallNormalY;

                // Calculate the reflection of the velocity vector
                double reflectedDX = dx - 2 * dotProduct * wallNormalX / (wallNormalX * wallNormalX + wallNormalY * wallNormalY);
                double reflectedDY = dy - 2 * dotProduct * wallNormalY / (wallNormalX * wallNormalX + wallNormalY * wallNormalY);

                // Update the particle's velocity vector to the reflected values
                dx = reflectedDX;
                dy = reflectedDY;
                break; // Exit loop after the first collision (assuming particles cannot intersect multiple walls simultaneously)
            }
        }
    }

    public void update(){
        canvas.repaint();
    }

    @Override
    public void run() {
        // Run the particle movement continuously
        while (true) {
            move();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    update();
                }
            });
            try {
                Thread.sleep(10); // Adjust sleep time as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}