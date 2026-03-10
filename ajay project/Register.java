
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;

public class Register extends JFrame {

    private JPanel contentPane;
    private JTextField nameField;
    private JTextField usernameField;
    private JTextField passwordField;
    private static Register frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = new Register();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Register() {
        setTitle("Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 548, 418);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblName = new JLabel("Enter Name");
        lblName.setFont(new Font("Arial Black", Font.PLAIN, 15));
        lblName.setBounds(45, 20, 109, 39);
        contentPane.add(lblName);

        JLabel lblUsername = new JLabel("Enter UserName");
        lblUsername.setFont(new Font("Arial Black", Font.PLAIN, 15));
        lblUsername.setBounds(45, 97, 138, 41);
        contentPane.add(lblUsername);

        JLabel lblPassword = new JLabel("Enter Password");
        lblPassword.setFont(new Font("Arial Black", Font.PLAIN, 15));
        lblPassword.setBounds(45, 164, 138, 39);
        contentPane.add(lblPassword);

        nameField = new JTextField();
        nameField.setBounds(224, 22, 193, 39);
        contentPane.add(nameField);
        nameField.setColumns(10);

        usernameField = new JTextField();
        usernameField.setColumns(10);
        usernameField.setBounds(224, 100, 193, 39);
        contentPane.add(usernameField);

        passwordField = new JTextField();
        passwordField.setColumns(10);
        passwordField.setBounds(224, 166, 193, 39);
        contentPane.add(passwordField);

        JButton btnRegister = new JButton("Register");
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String username = usernameField.getText();
                String pass = passwordField.getText(); // Should ideally use JPasswordField

                if (name.isEmpty() || username.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(btnRegister, "All fields are required!");
                    return;
                }

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/contact", "root",
                            "Shyam@123");

                    // 1. Create a table specifically for this user's contacts
                    // Use executeUpdate but careful with table names. SQL injection risk if
                    // username contains spaces/special chars.
                    // Validate username first: alphanumeric only.
                    if (!username.matches("[a-zA-Z0-9]+")) {
                        JOptionPane.showMessageDialog(btnRegister, "Username must be alphanumeric!");
                        return;
                    }

                    Statement stm = con.createStatement();
                    // Using username directly as table name requires escaping or validation
                    // Ideally use a generic contacts table with user_id, but keeping logic close to
                    // original intent
                    String createTableSQL = "CREATE TABLE IF NOT EXISTS " + username
                            + " (name VARCHAR(20), number VARCHAR(10), address VARCHAR(20))";
                    stm.executeUpdate(createTableSQL);

                    // 2. Insert user into users table (for login)
                    PreparedStatement ps = con.prepareStatement("INSERT INTO users VALUES(?,?,?)");
                    ps.setString(1, name);
                    ps.setString(2, username);
                    ps.setString(3, pass);

                    int count = ps.executeUpdate();

                    if (count > 0) {
                        JOptionPane.showMessageDialog(btnRegister, "User Registered Successfully");
                        // Clear fields or navigate
                        nameField.setText("");
                        usernameField.setText("");
                        passwordField.setText("");
                    }

                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(btnRegister,
                            "Error: MySQL Driver not found!\nPlease run the application using 'run.bat' to fix this.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(btnRegister, "Database Error: " + ex.getMessage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(btnRegister, "Error: " + ex.getMessage());
                }
            }
        });
        btnRegister.setFont(new Font("Arial Black", Font.PLAIN, 15));
        btnRegister.setBounds(128, 310, 116, 39);
        contentPane.add(btnRegister);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Login loginFrame = new Login();
                    loginFrame.setVisible(true);
                    dispose(); // Close current frame
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(btnBack, "Could not open Login screen: " + ex.getMessage());
                }
            }
        });
        btnBack.setFont(new Font("Arial Black", Font.PLAIN, 15));
        btnBack.setBounds(301, 310, 116, 39);
        contentPane.add(btnBack);
    }
}