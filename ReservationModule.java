package oasis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class ReservationModule extends JFrame {

    private static final long serialVersionUID = 1L;

    JTextField nameField, ageField, trainNoField, fromField, toField;
    JComboBox<String> genderBox, classBox;
    JLabel trainNameLabel;
    JButton insertBtn;
    JSpinner dateSpinner;

    public ReservationModule() {

        setTitle("Reservation Form - Online Reservation System");
        setSize(650, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        mainPanel.setBackground(Color.WHITE);

        // Title
        JLabel title = new JLabel("Ticket Reservation", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fields
        nameField = new JTextField();
        ageField = new JTextField();
        trainNoField = new JTextField();
        fromField = new JTextField();
        toField = new JTextField();

        genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        classBox = new JComboBox<>(new String[]{"AC", "Sleeper", "3 Tier AC"});

        trainNameLabel = new JLabel("-");
        trainNameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Date Spinner
        SpinnerDateModel model = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        dateSpinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "dd-MM-yyyy");
        dateSpinner.setEditor(editor);

        // Set Fonts
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        setFieldFont(fieldFont, nameField, ageField, trainNoField, fromField, toField);
        genderBox.setFont(fieldFont);
        classBox.setFont(fieldFont);
        dateSpinner.setFont(fieldFont);

        // Add Form Rows
        addRow(formPanel, gbc, 0, "Passenger Name:", nameField, labelFont);
        addRow(formPanel, gbc, 1, "Age:", ageField, labelFont);
        addRow(formPanel, gbc, 2, "Gender:", genderBox, labelFont);
        addRow(formPanel, gbc, 3, "Train Number:", trainNoField, labelFont);
        addRow(formPanel, gbc, 4, "Train Name (Auto):", trainNameLabel, labelFont);
        addRow(formPanel, gbc, 5, "Class Type:", classBox, labelFont);
        addRow(formPanel, gbc, 6, "Journey Date:", dateSpinner, labelFont);
        addRow(formPanel, gbc, 7, "From:", fromField, labelFont);
        addRow(formPanel, gbc, 8, "To:", toField, labelFont);

        // Button Panel
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);

        insertBtn = new JButton("INSERT RESERVATION");
        insertBtn.setFont(new Font("Arial", Font.BOLD, 16));
        insertBtn.setPreferredSize(new Dimension(260, 45));
        insertBtn.setFocusPainted(false);
        insertBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnPanel.add(insertBtn);

        // Auto Train Name Logic
        trainNoField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                try {
                    int tn = Integer.parseInt(trainNoField.getText());
                    trainNameLabel.setText(getTrainName(tn));
                } catch (Exception ex) {
                    trainNameLabel.setText("-");
                }
            }
        });

        insertBtn.addActionListener(e -> insertReservation());

        // Add to main
        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent field, Font labelFont) {

        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 0.7;
        panel.add(field, gbc);
    }

    private void setFieldFont(Font font, JTextField... fields) {
        for (JTextField f : fields) {
            f.setFont(font);
            f.setPreferredSize(new Dimension(250, 30));
        }
    }

    String getTrainName(int trainNo) {
        if (trainNo == 12001) return "Shatabdi Express";
        if (trainNo == 12951) return "Rajdhani Express";
        if (trainNo == 12627) return "Karnataka Express";
        if (trainNo == 19019) return "Dehradun Express";
        return "Superfast Express";
    }

    void insertReservation() {
        try {
            Connection con = DBConnection.getConnection();
            Random rand = new Random();
            int pnr = rand.nextInt(900000) + 100000;

            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String gender = (String) genderBox.getSelectedItem();
            int trainNo = Integer.parseInt(trainNoField.getText());
            String trainName = trainNameLabel.getText();
            String classType = (String) classBox.getSelectedItem();

            Date selectedDate = (Date) dateSpinner.getValue();
            String date = new java.text.SimpleDateFormat("dd-MM-yyyy").format(selectedDate);

            String from = fromField.getText();
            String to = toField.getText();

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO reservations VALUES (?,?,?,?,?,?,?,?,?,?)");

            ps.setInt(1, pnr);
            ps.setString(2, name);
            ps.setInt(3, age);
            ps.setString(4, gender);
            ps.setInt(5, trainNo);
            ps.setString(6, trainName);
            ps.setString(7, classType);
            ps.setString(8, date);
            ps.setString(9, from);
            ps.setString(10, to);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    "Ticket Booked Successfully!\nPNR: " + pnr);

            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Reservation Failed: " + ex.getMessage());
        }
    }
}
