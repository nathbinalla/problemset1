import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Reference extends JPanel implements ActionListener {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int PARTICLE_SIZE = 5;

    private List<Particle> particles;
    private List<Wall> walls;
    private Timer timer;

    public Reference() {
        particles = new ArrayList<>();
        walls = new ArrayList<>();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);

        // Example: adding walls
        walls.add(new Wall(100, 100, 500, 100));
        walls.add(new Wall(100, 100, 100, 500));
        walls.add(new Wall(500, 100, 500, 500));
        walls.add(new Wall(100, 500, 500, 500));

        // Example: adding particles
        addParticlesInBatch(10, 100, 100, 500, 500, 90, 90, 100, 200);

        // Start the simulation
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update();
                repaint();
            }
        }, 0, 1000 / 60); // 60 FPS
    }

    private void addParticlesInBatch(int n, int startX, int startY, int endX, int endY, int startAngle, int endAngle, int startVelocity, int endVelocity) {
        for (int i = 0; i < n; i++) {
            int x = startX + (endX - startX) * i / (n - 1);
            int y = startY + (endY - startY) * i / (n - 1);
            int angle = startAngle + (endAngle - startAngle) * i / (n - 1);
            int velocity = startVelocity + (endVelocity - startVelocity) * i / (n - 1);
            particles.add(new Particle(x, y, angle, velocity));
        }
    }

    private void update() {
        for (Particle particle : particles) {
            particle.move();
            checkWallCollision(particle);
        }
    }

    private void checkWallCollision(Particle particle) {
        for (Wall wall : walls) {
            if (wall.intersects(particle)) {
                wall.resolveCollision(particle);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Particle particle : particles) {
            particle.draw(g);
        }
        for (Wall wall : walls) {
            wall.draw(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Not used in this implementation
    }

    private static class Particle {
        private int x;
        private int y;
        private int angle;
        private int velocity;

        public Particle(int x, int y, int angle, int velocity) {
            this.x = x;
            this.y = y;
            this.angle = angle;
            this.velocity = velocity;
        }

        public void move() {
            double radians = Math.toRadians(angle);
            x += velocity * Math.cos(radians);
            y -= velocity * Math.sin(radians); // subtract because the y-axis is inverted in graphics
        }

        public void draw(Graphics g) {
            // Adjust particle coordinates if they are out of bounds
            int adjustedX = Math.max(PARTICLE_SIZE / 2, Math.min(x, WIDTH - PARTICLE_SIZE / 2));
            int adjustedY = Math.max(PARTICLE_SIZE / 2, Math.min(y, HEIGHT - PARTICLE_SIZE / 2));
        
            g.setColor(Color.RED);
            g.fillOval(adjustedX - PARTICLE_SIZE / 2, adjustedY - PARTICLE_SIZE / 2, PARTICLE_SIZE, PARTICLE_SIZE);
        }
    }

    private static class Wall {
        private int x1;
        private int y1;
        private int x2;
        private int y2;

        public Wall(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        public boolean intersects(Particle particle) {
            // Calculate the distance between the particle and the line formed by the wall
            double distance = Math.abs((x2 - x1) * (y1 - particle.y) - (x1 - particle.x) * (y2 - y1))
                                    / Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

            // Check if the distance is within the particle size radius
            return distance <= PARTICLE_SIZE / 2;
        }

        public void resolveCollision(Particle particle) {
            // Calculate the normal vector of the wall
            double wallNormalX = y2 - y1;
            double wallNormalY = x1 - x2;

            // Normalize the normal vector
            double length = Math.sqrt(wallNormalX * wallNormalX + wallNormalY * wallNormalY);
            wallNormalX /= length;
            wallNormalY /= length;

            // Calculate the dot product between particle velocity vector and wall normal vector
            double dotProduct = particle.velocity * (wallNormalX * Math.cos(Math.toRadians(particle.angle)) + wallNormalY * Math.sin(Math.toRadians(particle.angle)));

            // Reflect the particle velocity vector across the wall normal
            double reflectedVelocityX = particle.velocity * Math.cos(Math.toRadians(particle.angle)) - 2 * dotProduct * wallNormalX;
            double reflectedVelocityY = particle.velocity * Math.sin(Math.toRadians(particle.angle)) - 2 * dotProduct * wallNormalY;

            // Calculate the angle of the reflected velocity vector
            double reflectedAngle = Math.toDegrees(Math.atan2(reflectedVelocityY, reflectedVelocityX));

            // Set the new angle and velocity for the particle
            particle.angle = (int)reflectedAngle;
        }

        public void draw(Graphics g) {
            g.setColor(Color.BLACK);
            g.drawLine(x1, y1, x2, y2);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Particle Simulator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new Reference());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
