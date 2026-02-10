package oasis;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginModule extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField userField;
    private JPasswordField passField;
    private JButton loginBtn;

    public LoginModule() {

        setTitle("Login - Online Reservation System");
        setSize(520, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 1, 12, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 70, 30, 70));
        panel.setBackground(new Color(245, 245, 245));

        JLabel title = new JLabel("Online Reservation Login", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 15));

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 15));

        userField = new JTextField();
        passField = new JPasswordField();

        userField.setFont(new Font("Arial", Font.PLAIN, 16));
        passField.setFont(new Font("Arial", Font.PLAIN, 16));

        loginBtn = new JButton("LOGIN");
        loginBtn.setFont(new Font("Arial", Font.BOLD, 16));
        loginBtn.setPreferredSize(new Dimension(220, 45));

        loginBtn.setFocusPainted(false);

        loginBtn.addActionListener(e -> login());

        panel.add(title);
        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(loginBtn);

        add(panel);
        setVisible(true);
    }

    void login() {
        String user = userField.getText().trim();
        String pass = new String(passField.getPassword()).trim();

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username/Password cannot be empty ❌");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM users WHERE username=? AND password=?");

            ps.setString(1, user);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful !!");
                dispose();
                new MainSystem();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials ❌");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new LoginModule();
    }
}
