import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class Dashboard extends JFrame {

    public Dashboard(String name) {
        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        getContentPane().setLayout(null);

        JLabel lblWelcome = new JLabel("Welcome, " + name + "!");
        lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(50, 50, 500, 50);
        getContentPane().add(lblWelcome);

        JLabel lblInfo = new JLabel("You have successfully logged in.");
        lblInfo.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
        lblInfo.setBounds(50, 100, 500, 30);
        getContentPane().add(lblInfo);

        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener((ActionEvent e) -> {
            Login loginFrame = new Login();
            loginFrame.setVisible(true);
            dispose();
        });
        btnLogout.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnLogout.setBounds(250, 250, 100, 40);
        getContentPane().add(btnLogout);
    }
}
