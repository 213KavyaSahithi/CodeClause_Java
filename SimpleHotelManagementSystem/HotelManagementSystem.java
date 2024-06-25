import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HotelManagementSystem extends JFrame {
    private JButton loginButton;
    private JButton checkInButton;
    private JButton checkOutButton;
    private JButton viewStatusButton;
    private JButton logoutButton;

    private boolean[] rooms;
    private boolean loggedIn;

    public HotelManagementSystem() {
        setTitle("Hotel Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(5, 1));

        rooms = new boolean[10]; // Assuming there are 10 rooms in the hotel
        loggedIn = false;

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (username.equals("admin") && password.equals("admin123")) {
                    loggedIn = true;
                    JOptionPane.showMessageDialog(HotelManagementSystem.this, "Login successful.");
                    // Clear the username and password fields
                    usernameField.setText("");
          	    passwordField.setText("");
                } else {
                    JOptionPane.showMessageDialog(HotelManagementSystem.this, "Invalid username or password.");
                }
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5));
        buttonPanel.add(loginButton);
        checkInButton = new JButton("Check In");
        checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!loggedIn) {
                    JOptionPane.showMessageDialog(HotelManagementSystem.this, "Please login first.");
                    return;
                }
                int roomNumber = Integer.parseInt(JOptionPane.showInputDialog("Enter room number: "));
                if (roomNumber >= 1 && roomNumber <= 10) {
                    if (!rooms[roomNumber - 1]) {
                        rooms[roomNumber - 1] = true;
                        JOptionPane.showMessageDialog(HotelManagementSystem.this, "Room " + roomNumber + " checked in.");
                    } else {
                        JOptionPane.showMessageDialog(HotelManagementSystem.this, "Room " + roomNumber + " is already occupied.");
                    }
                } else {
                    JOptionPane.showMessageDialog(HotelManagementSystem.this, "Invalid room number.");
                }
            }
        });
        buttonPanel.add(checkInButton);

        checkOutButton = new JButton("Check Out");
        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!loggedIn) {
                    JOptionPane.showMessageDialog(HotelManagementSystem.this, "Please login first.");
                    return;
                }
                int roomNumber = Integer.parseInt(JOptionPane.showInputDialog("Enter room number: "));
                if (roomNumber >= 1 && roomNumber <= 10) {
                    if (rooms[roomNumber - 1]) {
                        rooms[roomNumber - 1] = false;
                        JOptionPane.showMessageDialog(HotelManagementSystem.this, "Room " + roomNumber + " checked out.");
                    } else {
                        JOptionPane.showMessageDialog(HotelManagementSystem.this, "Room " + roomNumber + " is not occupied.");
                    }
                } else {
                    JOptionPane.showMessageDialog(HotelManagementSystem.this, "Invalid room number.");
                }
            }
        });
        buttonPanel.add(checkOutButton);

        viewStatusButton = new JButton("View Room Status");
        viewStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!loggedIn) {
                    JOptionPane.showMessageDialog(HotelManagementSystem.this, "Please login first.");
                    return;
                }
                StringBuilder status = new StringBuilder();
                for (int i = 0; i < rooms.length; i++) {
                    status.append("Room ").append(i + 1).append(": ").append(rooms[i] ? "Occupied" : "Vacant").append("\n");
                }
                JOptionPane.showMessageDialog(HotelManagementSystem.this, status.toString());
            }
        });
        buttonPanel.add(viewStatusButton);

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loggedIn = false;
                JOptionPane.showMessageDialog(HotelManagementSystem.this, "Logged out successfully.");
            }
        });
        buttonPanel.add(logoutButton);

        add(loginPanel);
        add(buttonPanel);

        // Set background color for the panels
        loginPanel.setBackground(Color.CYAN);
        buttonPanel.setBackground(Color.LIGHT_GRAY);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HotelManagementSystem().setVisible(true);
            }
        });
    }
}
