import java.awt.*;

public class Wall {
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

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(x1, y1, x2, y2);
    }

    public boolean intersects(int px, int py) {
        // Check if the point (px, py) is within a small distance to the wall
        double distance = Math.abs((x2 - x1) * (y1 - py) - (x1 - px) * (y2 - y1)) / Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        return distance < 5; // Adjust the threshold as needed
    }
}
