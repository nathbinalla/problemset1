import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;

public class Canvas extends JPanel {
    private JPanel buttonPanel;
    private ArrayList<Particle> particles;

    public Canvas() {
        setLayout(new BorderLayout());
        particles = new ArrayList<>();

        // Create the particle simulation panel
        JPanel particlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw a rectangle representing the canvas
                g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
                for (Particle particle : particles) {
                    particle.draw(g);
                }
            }
        };
        particlePanel.setBackground(new Color(250, 219, 216));
        particlePanel.setPreferredSize(new Dimension(1280, 720));
        add(particlePanel, BorderLayout.CENTER);


        //================Button Panel======================

        // Create the button panel
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(150, 720));
        add(buttonPanel, BorderLayout.EAST);

        //Labels
        JLabel x1y1particLabel = new JLabel("start point: ");
        JLabel x2y2particLabel = new JLabel("end point: ");
        JLabel angLabel = new JLabel("angle: ");
        JLabel velocityLabel = new JLabel("velocity: ");
        JLabel startLabel = new JLabel("start: ");
        JLabel endLabel = new JLabel("end: ");

        // Text Fields
        JTextField n = new JTextField(5);
        JTextField x1PartTextField = new JTextField(3);
        JTextField y1PartTextField = new JTextField(3);
        JTextField x2PartTextField = new JTextField(3);
        JTextField y2PartTextField = new JTextField(3);
        JTextField start = new JTextField(5);
        JTextField end = new JTextField(5);
        JTextField angle = new JTextField(5);
        JTextField velocity = new JTextField(5);
        JTextField initx = new JTextField(3);
        JTextField inity = new JTextField(3);
        JTextField initangle = new JTextField(5);
        JTextField initvelo = new JTextField(5);   
        JTextField x1WallTextField = new JTextField(3);
        JTextField y1WallTextField = new JTextField(3);
        JTextField x2WallTextField = new JTextField(3);
        JTextField y2WallTextField = new JTextField(3);

        
        // Buttons for Wall and Particle
        JButton addParticlebtn = new JButton("Add Particle");
        JButton addBatch = new JButton("Add Particle Batch");
        JButton addWallbtn = new JButton("Add Wall");

        // Dropdown (Combo box) for Forms
        String[] forms = {"Form 1", "Form 2", "Form 3"};
        JComboBox<String> FormsComboBox = new JComboBox<>(forms);

        addParticlebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Particle particle = new Particle(100, 100, 45, 10, particlePanel);
                particles.add(particle);
                Thread particleThread = new Thread(particle);
                particleThread.start();
            }
        });

        // Add Particle input components
        addLabelTextFieldPair("Forms:", FormsComboBox);
        addLabelTextFieldPair("n:", n);
        addLabelTextFieldPairforHide2(x1y1particLabel, x1PartTextField, y1PartTextField);
        addLabelTextFieldPairforHide2(x2y2particLabel, x2PartTextField, y2PartTextField);
        addLabelTextFieldPairforHide(startLabel, start);
        addLabelTextFieldPairforHide(endLabel, end);
        addLabelTextFieldPairforHide(angLabel, angle);
        addLabelTextFieldPairforHide(velocityLabel, velocity);
        
        //Set form 1 default
        x1y1particLabel.setVisible(true); x2y2particLabel.setVisible(true);
        x1PartTextField.setVisible(true); y1PartTextField.setVisible(true);
        x2PartTextField.setVisible(true); y2PartTextField.setVisible(true);
        startLabel.setVisible(false); start.setVisible(false);
        endLabel.setVisible(false); end.setVisible(false);
        velocityLabel.setVisible(true);
        velocity.setVisible(true);
        angLabel.setVisible(true);
        angle.setVisible(true);

        buttonPanel.add(addBatch);

        addLabelTextFieldPair2("start point: ", initx, inity);
        addLabelTextFieldPair("angle: ", initangle);
        addLabelTextFieldPair("velocity: ", initvelo);
        
        buttonPanel.add(addParticlebtn);

        // Add Wall input components
        addLabelTextFieldPair2("(x1, y1): ", x1WallTextField, y1WallTextField);
        addLabelTextFieldPair2("(x2, y2): ", x2WallTextField, y2WallTextField);
        buttonPanel.add(addWallbtn);

        // Action listener for the combo box
        FormsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) FormsComboBox.getSelectedItem();
                if (selectedItem.equals("Form 1")) {
                    x1y1particLabel.setVisible(true); x2y2particLabel.setVisible(true);
                    x1PartTextField.setVisible(true); y1PartTextField.setVisible(true);
                    x2PartTextField.setVisible(true); y2PartTextField.setVisible(true);
                    startLabel.setVisible(false); start.setVisible(false);
                    endLabel.setVisible(false); end.setVisible(false);
                    velocityLabel.setVisible(true);
                    velocity.setVisible(true);
                    angLabel.setVisible(true);
                    angle.setVisible(true);
                } else if (selectedItem.equals("Form 2")) {
                    x1y1particLabel.setVisible(true); x2y2particLabel.setVisible(false);
                    x1PartTextField.setVisible(true); y1PartTextField.setVisible(true);
                    x2PartTextField.setVisible(false); y2PartTextField.setVisible(false);
                    startLabel.setVisible(true); start.setVisible(true);
                    endLabel.setVisible(true); end.setVisible(true);
                    angLabel.setVisible(false);
                    angle.setVisible(false);
                    velocityLabel.setVisible(true);
                    velocity.setVisible(true);
                } else if(selectedItem.equals("Form 3")){
                    x1y1particLabel.setVisible(true); x2y2particLabel.setVisible(false);
                    x1PartTextField.setVisible(true); y1PartTextField.setVisible(true);
                    x2PartTextField.setVisible(false); y2PartTextField.setVisible(false);
                    startLabel.setVisible(true); start.setVisible(true);
                    endLabel.setVisible(true); end.setVisible(true);
                    velocityLabel.setVisible(false);
                    velocity.setVisible(false);
                    angLabel.setVisible(true);
                    angle.setVisible(true);
                }
            }
        });

        //===============End of Button Panel=====================

        // Set up timer to animate particle movement (Possibly for threads)
        
    }

    private void addLabelTextFieldPairforHide(JLabel label, JComponent textField) {
        JPanel pairPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pairPanel.add(label);
        pairPanel.add(textField);
        buttonPanel.add(pairPanel);
    }

    private void addLabelTextFieldPairforHide2(JLabel label, JComponent textField, JComponent textfield2) {
        JPanel pairPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pairPanel.add(label);
        pairPanel.add(textField);
        pairPanel.add(textfield2);
        buttonPanel.add(pairPanel);
    }

    private void addLabelTextFieldPair(String labelText, JComponent textField) {
        JPanel pairPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pairPanel.add(new JLabel(labelText));
        pairPanel.add(textField);
        buttonPanel.add(pairPanel);
    }

    private void addLabelTextFieldPair2(String labelText, JComponent textField, JComponent textField2) {
        JPanel pairPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pairPanel.add(new JLabel(labelText));
        pairPanel.add(textField);
        pairPanel.add(textField2);
        buttonPanel.add(pairPanel);
    }

   
}
     