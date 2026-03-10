import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public Login() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(null);

        JLabel lblLogin = new JLabel("Login Screen");
        lblLogin.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogin.setBounds(126, 11, 186, 44);
        getContentPane().add(lblLogin);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblUsername.setBounds(50, 80, 80, 25);
        getContentPane().add(lblUsername);

        usernameField = new JTextField();
        usernameField.setBounds(140, 80, 200, 25);
        getContentPane().add(usernameField);
        usernameField.setColumns(10);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblPassword.setBounds(50, 120, 80, 25);
        getContentPane().add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(140, 120, 200, 25);
        getContentPane().add(passwordField);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter both username and password.");
                    return;
                }

                try {
                    checkLogin(username, password);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });
        btnLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnLogin.setBounds(140, 170, 90, 30);
        getContentPane().add(btnLogin);

        JButton btnRegister = new JButton("Register");
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Register registerFrame = new Register();
                registerFrame.setVisible(true);
                dispose(); // Close login window
            }
        });
        btnRegister.setBounds(250, 170, 90, 30);
        getContentPane().add(btnRegister);
    }

    private void checkLogin(String username, String password) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/contact", "root", "Shyam@123");

        PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String name = rs.getString("name");
            
            // Insert login history record
            PreparedStatement historyPs = con.prepareStatement("INSERT INTO login_history (username) VALUES (?)");
            historyPs.setString(1, username);
            historyPs.executeUpdate();

            JOptionPane.showMessageDialog(this, "Login Successful!");

            Dashboard dashboard = new Dashboard(name);
            dashboard.setVisible(true);
            this.dispose();

        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password", "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
        }

        con.close();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
