package oasis;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/oasis_reservation",
                "root",
                "Shivam@4525"
            );
        } catch (Exception e) {
            System.out.println("Database Connection Failed");
            return null;
        }
    }
}