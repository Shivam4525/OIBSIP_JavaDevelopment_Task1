package oasis;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class CancellationForm extends JFrame {
	
	private static final long serialVersionUID = 1L;

    JTextField pnrField;
    JTextArea infoArea;
    JButton fetchBtn, cancelBtn;

    public CancellationForm() {

        setTitle("Cancellation Form");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel(new GridLayout(1, 3, 10, 10));
        top.add(new JLabel("PNR Number:"));
        pnrField = new JTextField();
        top.add(pnrField);
        fetchBtn = new JButton("Submit");
        top.add(fetchBtn);

        add(top, BorderLayout.NORTH);

        infoArea = new JTextArea();
        infoArea.setEditable(false);
        add(new JScrollPane(infoArea), BorderLayout.CENTER);

        cancelBtn = new JButton("OK (Cancel Ticket)");
        add(cancelBtn, BorderLayout.SOUTH);

        fetchBtn.addActionListener(e -> fetchDetails());
        cancelBtn.addActionListener(e -> cancelTicket());

        setVisible(true);
    }

    void fetchDetails() {
        try {
            Connection con = DBConnection.getConnection();
            int pnr = Integer.parseInt(pnrField.getText());

            PreparedStatement ps =
                con.prepareStatement("SELECT * FROM reservations WHERE pnr=?");
            ps.setInt(1, pnr);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                infoArea.setText(
                    "PNR: " + rs.getInt("pnr") + "\n" +
                    "Name: " + rs.getString("passenger_name") + "\n" +
                    "Age: " + rs.getInt("age") + "\n" +
                    "Gender: " + rs.getString("gender") + "\n" +
                    "Train No: " + rs.getInt("train_no") + "\n" +
                    "Train Name: " + rs.getString("train_name") + "\n" +
                    "Class: " + rs.getString("class_type") + "\n" +
                    "Date: " + rs.getString("journey_date") + "\n" +
                    "From: " + rs.getString("source") + "\n" +
                    "To: " + rs.getString("destination")
                );
            } else {
                infoArea.setText("PNR Not Found");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage());
        }
    }

    void cancelTicket() {
        try {
            Connection con = DBConnection.getConnection();
            int pnr = Integer.parseInt(pnrField.getText());

            int confirm = JOptionPane.showConfirmDialog(
                this, "Confirm Cancellation?",
                "Confirm", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                PreparedStatement del =
                    con.prepareStatement("DELETE FROM reservations WHERE pnr=?");
                del.setInt(1, pnr);
                del.executeUpdate();

                JOptionPane.showMessageDialog(this,
                    "Ticket Cancelled Successfully!");

                dispose();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Cancellation Error: " + ex.getMessage());
        }
    }
}