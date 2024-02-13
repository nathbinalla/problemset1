import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Canvas Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Canvas canvasPanel = new Canvas();
        canvasPanel.setPreferredSize(new Dimension(1280, 720));

        frame.getContentPane().add(canvasPanel);
        frame.pack();
        frame.setVisible(true);
    }
}

