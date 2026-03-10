import java.sql.*;

public class CheckUsers {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/contact";
        String user = "root";
        String password = "Shyam@123";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();

            System.out.println("\n=== REGISTERED USERS ===");
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            boolean hasUsers = false;
            while (rs.next()) {
                hasUsers = true;
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Username: " + rs.getString("username"));
                System.out.println("Password: " + rs.getString("password"));
                System.out.println("-------------------------");
            }

            if (!hasUsers) {
                System.out.println("No users found in the database yet.");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
