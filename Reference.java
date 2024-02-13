import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends JPanel implements ActionListener {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int PARTICLE_SIZE = 5;

    private List<Particle> particles;
    private List<Wall> walls;
    private Timer timer;

    public Main() {
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
            g.setColor(Color.RED);
            g.fillOval(x - PARTICLE_SIZE / 2, y - PARTICLE_SIZE / 2, PARTICLE_SIZE, PARTICLE_SIZE);
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
            // Code to check if the particle intersects with the wall
            return false;
        }

        public void resolveCollision(Particle particle) {
            // Code to resolve collision between the particle and the wall
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
            frame.getContentPane().add(new Main());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
