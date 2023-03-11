package guis;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import java.awt.GridLayout;


public class homeScreen extends JFrame {

    public homeScreen() {
        super("LoginPage");

        String[] labels = { "Adress: ", "Port: ", "Name: " };

        JPanel panel = new JPanel(new SpringLayout());
        for (String label : labels) {
            JLabel l = new JLabel(label, JLabel.TRAILING);
            panel.add(l);
            JTextField textField = new JTextField(10);
            l.setLabelFor(textField);
            panel.add(textField);
        }

        getContentPane().setLayout(new GridLayout(3, 2));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up the content pane.
        panel.setOpaque(true); // content panes must be opaque
        setContentPane(panel);

        // Display the window.
        pack();
        setVisible(true);
    }
}
