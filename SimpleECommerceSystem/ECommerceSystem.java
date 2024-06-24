import javax.swing.*;
import java.awt.*;
import java.util.*;

class Product {
    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return id + ". " + name + " - $" + price;
    }
}

class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return product.getName() + " - $" + product.getPrice() + " x " + quantity + " = $" + getTotalPrice();
    }
}

class ShoppingCart {
    private ArrayList<CartItem> items;

    public ShoppingCart() {
        items = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == product.getId()) {
                item.addQuantity(quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    public void removeItem(int productId) {
        items.removeIf(item -> item.getProduct().getId() == productId);
    }

    public double getTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public void clear() {
        items.clear();
    }

    public ArrayList<CartItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (CartItem item : items) {
            sb.append(item.toString()).append("\n");
        }
        sb.append("Total: $").append(getTotal());
        return sb.toString();
    }
}

public class ECommerceSystem extends JFrame {
    private static ArrayList<Product> products = new ArrayList<>();
    private static ShoppingCart cart = new ShoppingCart();

    private JTextArea displayArea;
    private JTextField productIdField;
    private JTextField quantityField;

    public ECommerceSystem() {
        setTitle("E-Commerce System");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(1, 6, 10, 10));
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel welcomeLabel = new JLabel("WELCOME", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(welcomeLabel, BorderLayout.NORTH);

        JButton browseButton = new JButton("Browse Products");
        JButton addToCartButton = new JButton("Add to Cart");
        JButton removeFromCartButton = new JButton("Remove from Cart");
        JButton viewCartButton = new JButton("View Cart");
        JButton checkoutButton = new JButton("Checkout");
        JButton exitButton = new JButton("Exit");

        Dimension buttonSize = new Dimension(50, 10);
        browseButton.setPreferredSize(buttonSize);
        addToCartButton.setPreferredSize(buttonSize);
        removeFromCartButton.setPreferredSize(buttonSize);
        viewCartButton.setPreferredSize(buttonSize);
        checkoutButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        browseButton.addActionListener(e -> showProducts());
        addToCartButton.addActionListener(e -> addToCart());
        removeFromCartButton.addActionListener(e -> removeFromCart());
        viewCartButton.addActionListener(e -> showCart());
        checkoutButton.addActionListener(e -> checkout());
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(browseButton);
        buttonPanel.add(addToCartButton);
        buttonPanel.add(removeFromCartButton);
        buttonPanel.add(viewCartButton);
        buttonPanel.add(checkoutButton);
        buttonPanel.add(exitButton);

        JLabel productIdLabel = new JLabel("Product ID:");
        JLabel quantityLabel = new JLabel("Quantity:");
        productIdField = new JTextField();
        quantityField = new JTextField();

        Dimension inputSize = new Dimension(50, 30);
        productIdField.setPreferredSize(inputSize);
        quantityField.setPreferredSize(inputSize);

        inputPanel.add(productIdLabel);
        inputPanel.add(productIdField);
        inputPanel.add(quantityLabel);
        inputPanel.add(quantityField);

        displayArea = new JTextArea(10, 30);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);
        mainPanel.add(inputPanel, BorderLayout.EAST);

        add(mainPanel);

        initializeProducts();
    }

    private static void initializeProducts() {
        products.add(new Product(1, "Laptop", 800.00));
        products.add(new Product(2, "Smartphone", 500.00));
        products.add(new Product(3, "Headphones", 50.00));
        products.add(new Product(4, "Keyboard", 30.00));
        products.add(new Product(5, "Mouse", 20.00));
    }

    private void showProducts() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Product Listings ---\n");
        for (Product product : products) {
            sb.append(product).append("\n");
        }
        displayArea.setText(sb.toString());
    }

    private void addToCart() {
        try {
            int productId = Integer.parseInt(productIdField.getText());
            int quantity = Integer.parseInt(quantityField.getText());

            for (Product product : products) {
                if (product.getId() == productId) {
                    cart.addItem(product, quantity);
                    displayArea.setText("Product added to cart.");
                    productIdField.setText(""); 
                    quantityField.setText("");
                    return;
                }
            }
            displayArea.setText("Product not found.");
        } catch (NumberFormatException e) {
            displayArea.setText("Invalid input. Please enter valid product ID and quantity.");
        }
    }

    private void removeFromCart() {
    ArrayList<CartItem> items = cart.getItems();
    if (items.isEmpty()) {
        displayArea.setText("Cart is empty.");
        return;
    }

    JFrame removeFrame = new JFrame("Remove Item from Cart");
    removeFrame.setSize(400, 100 * items.size()); 
    removeFrame.setLayout(new GridLayout(items.size(), 2, 10, 10)); 
    for (CartItem item : items) {
        JButton itemButton = new JButton(item.toString());
        itemButton.setPreferredSize(new Dimension(200, 50)); 
        itemButton.addActionListener(e -> {
            cart.removeItem(item.getProduct().getId());
            displayArea.setText("Product removed from cart.");
            removeFrame.dispose();
        });
        removeFrame.add(itemButton);
    }

    removeFrame.setVisible(true);
   }


    private void showCart() {
        ArrayList<CartItem> items = cart.getItems();
        if (items.isEmpty()) {
            displayArea.setText("Cart is empty.");
            return;
        }

        String[] columnNames = {"Serial No", "Product ID", "Product Name", "Quantity", "Price", "Total Price"};
        Object[][] data = new Object[items.size() + 1][6];
        double totalAmount = 0;
        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            data[i][0] = i + 1;
            data[i][1] = item.getProduct().getId();
            data[i][2] = item.getProduct().getName();
            data[i][3] = item.getQuantity();
            data[i][4] = item.getProduct().getPrice();
            data[i][5] = item.getTotalPrice();
            totalAmount += item.getTotalPrice();
        }
        data[items.size()][0] = "Total";
        data[items.size()][1] = "";
        data[items.size()][2] = "";
        data[items.size()][3] = "";
        data[items.size()][4] = "";
        data[items.size()][5] = totalAmount;

        JTable cartTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(cartTable);

        JOptionPane.showMessageDialog(this, scrollPane, "Shopping Cart", JOptionPane.PLAIN_MESSAGE);
    }

    private void checkout() {
        int response = JOptionPane.showConfirmDialog(this, "Do you want to proceed with the purchase?", "Checkout", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            displayArea.setText("Purchase completed. Thank you!\n" + cart);
            cart.clear();
        } else {
            displayArea.setText("Checkout cancelled.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ECommerceSystem().setVisible(true));
    }
}
