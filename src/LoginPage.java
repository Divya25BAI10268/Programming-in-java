import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    // Temporary storage for registered student
    private static String registeredUsername = "";
    private static String registeredPassword = "";

    public LoginPage() {

        setTitle("Student Task Manager - Login");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createLoginScreen();
    }

    private void createLoginScreen() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        JLabel title = new JLabel(
                "STUDENT TASK MANAGER",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(
                BorderFactory.createEmptyBorder(35, 10, 10, 10)
        );

        mainPanel.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(
                new GridLayout(4, 1, 8, 8)
        );

        formPanel.setBackground(new Color(245, 247, 250));

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(30, 80, 20, 80)
        );

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));

        passwordField = new JPasswordField();

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JButton loginButton = new JButton("LOGIN");
        JButton registerButton = new JButton("REGISTER");

        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));

        loginButton.setFocusPainted(false);
        registerButton.setFocusPainted(false);

        JPanel buttonPanel = new JPanel();

        buttonPanel.setBackground(new Color(245, 247, 250));
        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(0, 10, 35, 10)
        );

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> login());

        registerButton.addActionListener(e -> register());

        add(mainPanel);
    }

    // Registration
    private void register() {

        JTextField newUsernameField = new JTextField();
        JPasswordField newPasswordField = new JPasswordField();

        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));

        panel.add(new JLabel("Create Username:"));
        panel.add(newUsernameField);

        panel.add(new JLabel("Create Password:"));
        panel.add(newPasswordField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Student Registration",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {

            String username =
                    newUsernameField.getText().trim();

            String password =
                    new String(
                            newPasswordField.getPassword()
                    );

            if (username.isEmpty() || password.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter both username and password.",
                        "Registration Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            registeredUsername = username;
            registeredPassword = password;

            JOptionPane.showMessageDialog(
                    this,
                    "Registration successful!\nYou can now login.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // Login
    private void login() {

        String username =
                usernameField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password.",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        if (username.equals(registeredUsername)
                && password.equals(registeredPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login successful!",
                    "Welcome",
                    JOptionPane.INFORMATION_MESSAGE
            );

            openTaskManager();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password.\nPlease register first.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void openTaskManager() {

        dispose();

        StudentTaskManager app =
                new StudentTaskManager();

        app.setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            LoginPage login = new LoginPage();

            login.setVisible(true);
        });
    }
}