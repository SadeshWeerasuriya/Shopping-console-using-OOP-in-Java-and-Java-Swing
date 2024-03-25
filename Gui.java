
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

public class Gui extends JFrame {

    WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();  // the instance of WestminsterShoppingManager
    private JTable table;  // Declaring table as a class member variable
    private JTextArea outputTextArea;
    JComboBox<String> categoryDropdown;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Gui().createAndShowGUI();
        });
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Weerasuriya's Shopping Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Initializing WestminsterShoppingManager
        // Initializing the table before using it
        String[] columnNames = {"Product ID", "Name", "Category", "Price", "Info"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    updateOutputTextArea(selectedRow);
                }
            }
        });


        JPanel panel1 = new JPanel();


        JPanel subPanel1 = new JPanel();

        JLabel label1 = new JLabel("Select Product Category");
        subPanel1.add(label1);

        JPanel subPanel2 = new JPanel();

        String[] categories = {"All", "Electronics", "Clothing"};
        categoryDropdown = new JComboBox<>(categories);
        subPanel2.add(categoryDropdown);

        JPanel subPanel3 = new JPanel();
        JButton addButton = new JButton("Shopping cart");
        subPanel3.add(addButton);
        // Adding an ActionListener to the "Shopping cart" button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openShoppingCartFrame();
            }
        });

        panel1.setLayout(new GridLayout(1, 3));
        panel1.add(subPanel1);
        panel1.add(subPanel2);
        panel1.add(subPanel3);

        JPanel panel2 = new JPanel();



        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        categoryDropdown.addActionListener(e -> {
            updateTableFromShoppingManager();
        });

        Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
        table.setBorder(blackBorder);
        table.getTableHeader().setBorder(blackBorder);

        JScrollPane scrollPane = new JScrollPane(table);
        panel2.setLayout(new BorderLayout());
        panel2.add(scrollPane, BorderLayout.CENTER);

        JPanel panel3 = new JPanel();
        outputTextArea = new JTextArea(10, 30);  // Assigning the class member variable
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
        panel3.setLayout(new BorderLayout());
        panel3.add(outputScrollPane, BorderLayout.CENTER);

        JPanel panel4 = new JPanel();
        JButton processButton = new JButton("Add to shopping cart");
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
        panel4.add(processButton);

        frame.setLayout(new GridLayout(4, 1));
        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(panel4);

        loadData();
        updateTableFromShoppingManager();

        frame.setVisible(true);
    }

    private void updateTableFromShoppingManager() {
        try {
            List<Product> productList = shoppingManager.getProducts();
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            tableModel.setRowCount(0); // Clear existing rows
            String selectedCategory = (String) categoryDropdown.getSelectedItem();

            // Filtering and sorting products based on the selected category
            productList.stream()
                    .filter(product -> {
                        if (selectedCategory.equals("All")) {
                            return true; // Show all products
                        } else if (selectedCategory.equals("Electronics")) {
                            return product instanceof Electronics;
                        } else if (selectedCategory.equals("Clothing")) {
                            return product instanceof Clothing;
                        }
                        return false;
                    })
                    .sorted(Comparator.comparing(Product::getProductName))
                    .forEach(product -> {
                        Object[] rowData;

                        if (product instanceof Electronics) {
                            Electronics electronics = (Electronics) product;
                            rowData = new Object[]{electronics.getProductId(), electronics.getProductName(), electronics.getProductType(), electronics.getPrice(), "Brand: " + electronics.getBrand() + ", Warranty: " + electronics.getWarrantyPeriod() + " years"};
                        } else if (product instanceof Clothing) {
                            Clothing clothing = (Clothing) product;
                            rowData = new Object[]{clothing.getProductId(), clothing.getProductName(), clothing.getProductType(), clothing.getPrice(), "Size: " + clothing.getSize() + ", Colour: " + clothing.getColour()};
                        } else {
                            rowData = new Object[]{product.getProductId(), product.getProductName(), product.getProductType(), product.getPrice(), "Info"};
                        }

                        tableModel.addRow(rowData);
                    });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating table: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void updateOutputTextArea(int selectedRow) {
        try {
            TableModel model = table.getModel();
            String productId = (String) model.getValueAt(selectedRow, 0);  // Assuming the first column is Product ID

            // Finding the product in the shoppingManager based on productId
            Product selectedProduct = shoppingManager.getProducts().stream()
                    .filter(product -> product.getProductId().equals(productId))
                    .findFirst()
                    .orElse(null);

            if (selectedProduct != null) {
                // Updating the outputTextArea with the details of the selected product
                outputTextArea.setText(getProductDetails(selectedProduct));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating outputTextArea: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Adding a new method to get details of a product
    private String getProductDetails(Product product) {
        if (product instanceof Electronics) {
            Electronics electronics = (Electronics) product;
            return "Product Type: Electronics\n" +
                    "Product ID: " + electronics.getProductId() + "\n" +
                    "Product Name: " + electronics.getProductName() + "\n" +
                    "Items Available: " + electronics.getNoOfAvailableItems() + "\n" +
                    "Price: " + electronics.getPrice() + "\n" +
                    "Brand: " + electronics.getBrand() + "\n" +
                    "Warranty: " + electronics.getWarrantyPeriod() + " years";
        } else if (product instanceof Clothing) {
            Clothing clothing = (Clothing) product;
            return "Product Type: Clothing\n" +
                    "Product ID: " + clothing.getProductId() + "\n" +
                    "Product Name: " + clothing.getProductName() + "\n" +
                    "Items Available: " + clothing.getNoOfAvailableItems() + "\n" +
                    "Price: " + clothing.getPrice() + "\n" +
                    "Size: " + clothing.getSize() + "\n" +
                    "Colour: " + clothing.getColour();
        } else {
            return "Unknown Product Type";
        }
    }
    // Opening the Shopping Cart frame
    private void openShoppingCartFrame() {
        JFrame shoppingCartFrame = new JFrame("Shopping Cart");
        shoppingCartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        shoppingCartFrame.setSize(400, 300);
        // Creating a panel for the table
        JPanel tablePanel = new JPanel();
        shoppingCartFrame.add(tablePanel, BorderLayout.CENTER);

        // Creating a DefaultTableModel for the table
        String[] columnNames = {"Product", "Quantity", "Price"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);


        Object[][] data = {
                {"Item 1", 2, 20.0},
                {"Item 2", 1, 15.0},
                {"Item 3", 3, 30.0}
        };

        // Adding to the table model
        for (Object[] row : data) {
            tableModel.addRow(row);
        }

        // Creating the table using the DefaultTableModel
        JTable table = new JTable(tableModel);
        tablePanel.add(new JScrollPane(table));

        // Creating the panel for the text field
        JPanel textFieldPanel = new JPanel();
        shoppingCartFrame.add(textFieldPanel, BorderLayout.SOUTH);

        // Creating and adding a text field to the text field panel
        JTextField textField = new JTextField(20);
        textFieldPanel.add(textField);

        // Making the shopping cart frame visible
        shoppingCartFrame.setVisible(true);

        // Adding components and functionality for the shopping cart frame as needed

        //  Adding a label to the shopping cart frame
        JLabel label = new JLabel("Shopping Cart ");
        shoppingCartFrame.add(label);

        // Making the shopping cart frame visible
        shoppingCartFrame.setVisible(true);
    }

    private void loadData() {
        shoppingManager.loadData();
        System.out.println("Loaded products: " + shoppingManager.getProducts());
        updateTableFromShoppingManager();
    }
}
