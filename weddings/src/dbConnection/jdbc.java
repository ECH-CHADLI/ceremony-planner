package dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class jdbc {
    private static Connection connection;
    private static final String url = "jdbc:mysql://localhost:3306/ceremony";
    private static final String username = "root";
    private static final String password = "JVprog97#";

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }

    public static void main(String[] args) {
        try {
            Connection connection = jdbc.getConnection();
            if (connection != null) {
                System.out.println("Connection successful!");
            } else {
                System.out.println("Connection failed!");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }
}
