package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class creates a login panel to start this APP
 */
public class LogInUI extends JFrame implements ActionListener {

    private ImageIcon myImage;
    private JPanel panel;
    private JButton button;
    private JLabel userLabel;
    private JTextField userText;
    private JLabel passwordLabel;
    private JTextField passwordText;
    private JLabel success;

    // EFFECTS: Creates a lab inventory frame with an image icon and login in information
    public LogInUI() {
        super("Lab Inventory");
        setUp();
    }

    // EFFECTS: Sets up a frame with an image and a panel with labels on it
    public void setUp() {
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myImage = new ImageIcon("./data/Inventory.png");
        add(new JLabel(myImage), BorderLayout.NORTH);

        panel = new JPanel();
        add(panel, BorderLayout.CENTER);
        panel.setLayout(null);
        labelSetUp();

        setVisible(true);
    }


    // EFFECTS: Sets up different log in labels on the panel, including username, password, and login button
    public void labelSetUp() {

        userLabel = new JLabel("User Name: ");
        userLabel.setFont(new Font("MV Boli", Font.BOLD, 15));
        userLabel.setBounds(100, 80, 200, 50);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(250, 80, 300, 50);
        panel.add(userText);

        passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(new Font("MV Boli", Font.BOLD, 15));
        passwordLabel.setBounds(100, 150, 200, 50);
        panel.add(passwordLabel);

        passwordText = new JPasswordField();
        passwordText.setBounds(250, 150, 300, 50);
        panel.add(passwordText);

        button = new JButton("Login");
        button.setFont(new Font("MV Boli", Font.BOLD, 15));
        button.setBounds(100, 250, 200, 60);
        button.addActionListener(this);
        panel.add(button);

        success = new JLabel("");
        success.setBounds(100, 300, 300, 50);
        panel.add(success);
    }

    // EFFECTS: the action related to the Login button; if it is successful, it will start the main page of app;
    //          if not successful, it will inform the user.
    @Override
    public void actionPerformed(ActionEvent e) {
        String user = userText.getText();
        String password = passwordText.getText();

        if (user.equals("shumin") && password.equals("123456")) {
            success.setText("Login successful!");
            new LabInventoryAppUI();
            setVisible(false);
        } else {
            success.setText("Incorrect username or password!");
        }
    }

}
