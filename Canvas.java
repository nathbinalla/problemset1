import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;

public class Canvas extends JPanel {
    private JPanel buttonPanel;
    private ArrayList<Particle> particles;
    private ArrayList<Wall> walls;

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
        JLabel x2y2particLabel = new JLabel("end poin   t: ");
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
                if (!initx.getText().isEmpty() && !inity.getText().isEmpty() && !initangle.getText().isEmpty() && !initvelo.getText().isEmpty()){
                    String xStr = initx.getText();
                    String yStr = inity.getText();
                    String angleStr = initangle.getText();
                    String velocityStr = initvelo.getText();

                    int x = Integer.parseInt(xStr);
                    int y = Integer.parseInt(yStr);
                    double angle = Double.parseDouble(angleStr);
                    double velocity = Double.parseDouble(velocityStr);


                    Particle particle = new Particle(x, y, angle, velocity, particlePanel, walls);
                    particles.add(particle);
                    Thread particleThread = new Thread(particle);
                    particleThread.start();
                }
            }
        });

        addBatch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) FormsComboBox.getSelectedItem();
                String x1Str = x1PartTextField.getText();
                String y1Str = y1PartTextField.getText();
                String x2Str = x2PartTextField.getText();
                String y2Str = y2PartTextField.getText();
                String angleStr = angle.getText();
                String veloStr = velocity.getText();
                String startStr = start.getText();
                String endStr = end.getText();
                String nStr = n.getText();

                int num = 0;
                double x1 = 0;
                double y1 = 0;
                double x2 = 0;
                double y2 = 0;
                double ang = 0;
                double velo = 0;
                double strt = 0;
                double nd = 0;

                if (!nStr.isEmpty()){
                    num = Integer.parseInt(nStr);
                }
                if (!x1Str.isEmpty()){
                    x1 = Double.parseDouble(x1Str);
                }
                if (!y1Str.isEmpty()){
                    y1 = Double.parseDouble(y1Str);
                }
                if (!x2Str.isEmpty()){
                    x2 = Double.parseDouble(x2Str);
                }
                if (!y2Str.isEmpty()){    
                    y2 = Double.parseDouble(y2Str);
                }
                if (!angleStr.isEmpty()){
                    ang = Double.parseDouble(angleStr);
                }
                if (!veloStr.isEmpty()){    
                    velo = Double.parseDouble(veloStr);
                }
                if (!startStr.isEmpty()){
                    strt = Double.parseDouble(startStr);
                }
                if (!endStr.isEmpty()){
                    nd = Double.parseDouble(endStr);
                }
                
                switch (selectedItem) {
                    case "Form 1":
                        if (!nStr.isEmpty() && !x1Str.isEmpty() && !y1Str.isEmpty() && 
                            !x2Str.isEmpty() && !y2Str.isEmpty() &&
                            !angleStr.isEmpty() && !veloStr.isEmpty()){
                            //logic here
                            double dx = x2 - x1;
                            double dy = y2 - y1;

                            dx = dx/(num-1);
                            dy = dy/(num-1);

                            

                            for (int i = 0; i < num; i++){
                                if (i==0){
                                    Particle particle = new Particle(x1, y1, ang, velo, particlePanel, walls);
                                    particles.add(particle);
                                    Thread particleThread = new Thread(particle);
                                    particleThread.start();
                                } else {
                                    x1 += dx;
                                    y1 += dy;
                                    Particle particle = new Particle(x1, y1, ang, velo, particlePanel, walls);
                                    particles.add(particle);
                                    Thread particleThread = new Thread(particle);
                                    particleThread.start();
                                }
                            }
                        }
                        break;
                    
                    case "Form 2":
                        if (!nStr.isEmpty() && !x1Str.isEmpty() && !y1Str.isEmpty() &&
                            !startStr.isEmpty() && !endStr.isEmpty() && !veloStr.isEmpty()){
                            //logic here

                            double dAng = (nd-strt)/(num-1);
                            

                            for (int i = 0; i < num; i++){
                                double angl = strt + i * dAng;
                                Particle particle = new Particle(x1, y1, angl, velo, particlePanel, walls);
                                particles.add(particle);
                                Thread particleThread = new Thread(particle);
                                particleThread.start();
                            }
                        }
                        break;

                    case "Form 3":
                        if (!nStr.isEmpty() && !x1Str.isEmpty() && !y1Str.isEmpty() && 
                            !startStr.isEmpty() && !endStr.isEmpty() && !angleStr.isEmpty()){
                            //logic here
                            double dVelo = (nd-strt)/(num-1);
                            
                            for (int i = 0; i < num; i++){
                                double vel = strt + i * dVelo;
                                Particle particle = new Particle(x1, y1, ang, vel, particlePanel, walls);
                                particles.add(particle);
                                Thread particleThread = new Thread(particle);
                                particleThread.start();
                            }
                        }
                        break;
                
                    default:
                        break;
                }
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
     