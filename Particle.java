import java.awt.*;

public class Particle {
    private int x;
    private int y;
    private int angle;
    private int velocity;
    private int dx;
    private int dy;

    public Particle(int x, int y, int angle, int velocity) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.velocity = velocity;
        dx = 5;
        dy = 5;
    }

    public void move() {
        x += dx;
        y += dy;

        // Check if the particle reaches the boundaries
        if (x <= 0 || y >= 1280 - 5) {
            dx = -dx; // Reverse the x-direction if hitting the horizontal boundaries
        }
        if (y <= 0 || y >= 720 - 5) {
            dy = -dy; // Reverse the y-direction if hitting the vertical boundaries
        }
    }

    public void draw(Graphics g) {   
        g.setColor(Color.RED);
        g.fillOval(x, y, angle, velocity);
    }
}
