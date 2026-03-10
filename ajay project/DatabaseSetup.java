
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseSetup {
    public static void main(String[] args) {
        setupDatabase();
    }

    public static void setupDatabase() {
        String url = "jdbc:mysql://localhost:3306/"; // Connect to server, not DB yet
        String user = "root";
        String password = "Shyam@123"; // Updated user password

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();

            // Create Database
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS contact");
            System.out.println("Database 'contact' checked/created.");

            // Select Database
            stmt.executeUpdate("USE contact");

            // Create Users Table
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "name VARCHAR(50), " +
                    "username VARCHAR(50) PRIMARY KEY, " +
                    "password VARCHAR(50))";
            stmt.executeUpdate(createUsersTable);
            System.out.println("Table 'users' checked/created.");

            // Create Login History Table
            String createLoginHistoryTable = "CREATE TABLE IF NOT EXISTS login_history (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(50), " +
                    "login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (username) REFERENCES users(username))";
            stmt.executeUpdate(createLoginHistoryTable);
            System.out.println("Table 'login_history' checked/created.");

            con.close();
            System.out.println("Database setup completed successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(
                    "Database setup failed. Make sure MySQL is running on port 3306 with user 'root' and password 'root'.");
        }
    }
}
