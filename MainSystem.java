package oasis;

import javax.swing.*;
import java.awt.*;

public class MainSystem extends JFrame {

    private static final long serialVersionUID = 1L;

    public MainSystem() {

        setTitle("Main Menu - Online Reservation System");
        setSize(520, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 60, 25, 60));

        // Title
        JLabel title = new JLabel("Main Menu", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons
        JButton insertBtn = createMenuButton("Insert Reservation");
        JButton cancelBtn = createMenuButton("Cancel Ticket");
        JButton exitBtn = createMenuButton("Exit");

        // Button Actions
        insertBtn.addActionListener(e -> new ReservationModule());
        cancelBtn.addActionListener(e -> new CancellationForm());
        exitBtn.addActionListener(e -> System.exit(0));

        // Add Components
        mainPanel.add(title);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        mainPanel.add(insertBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 18)));

        mainPanel.add(cancelBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 18)));

        mainPanel.add(exitBtn);

        add(mainPanel);
        setVisible(true);
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);

        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.setMaximumSize(new Dimension(320, 55));
        btn.setPreferredSize(new Dimension(320, 55));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        return btn;
    }
}
