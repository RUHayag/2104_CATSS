
package menu;


import javax.swing.JFrame;
import login.login;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Payment.payment;
import Stocks.stocks;


public class menu extends javax.swing.JFrame {

    private String user_name;
    
    public menu() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        tpaneMENU.setBackground(new java.awt.Color(150,100,80));
        panelMenu.setBackground(new java.awt.Color(208,187,148));
        panelCOFFEEMenu.setBackground(new java.awt.Color(208,187,148));
        panelTEAMenu.setBackground(new java.awt.Color(208,187,148));
        panelPASTRYMenu.setBackground(new java.awt.Color(208,187,148));
        panelCART.setBackground(new java.awt.Color(208,187,148));
    }
    
    public void setUserName(String userName) {
        this.user_name = userName;
        lWELCOME.setText("WELCOME " + userName + " TO CPR COFFEE & TEA SHOP!");
    }
    
    private Connection connectToDatabase() {
        String url = "jdbc:mysql://localhost:3306/coffeetea";
        String user = "root";
        String password = "";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    
    private void searchCoffeeInDatabase(String productName) {
        String query = "SELECT * FROM stocks WHERE ProductName = ?";
        try (Connection conn = connectToDatabase();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
         
            pstmt.setString(1, productName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int currentQuantity = rs.getInt("ProductQuantity");
                System.out.println("Current Quantity: " + currentQuantity);
            } else {
                JOptionPane.showMessageDialog(this, "No coffee found with the name: " + productName, "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }   
    
    private void addOrderToDatabase(String productName, int quantity, String size) {
        double price = getProductPrice(productName); // Get the base price

    // Adjust price based on size
        if (size.equalsIgnoreCase("L")) {
            price += 10; // Add 10 for large size
        } else if (size.equalsIgnoreCase("B")) {
            price += 25; // Add 25 for box size (for tea)
        }

        String insertOrderQuery = "INSERT INTO cart (Username, Orders, Quantity, Size, Price) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connectToDatabase();
             PreparedStatement insertStmt = conn.prepareStatement(insertOrderQuery)) {

            insertStmt.setString(1, user_name);
            insertStmt.setString(2, productName);
            insertStmt.setInt(3, quantity);
            insertStmt.setString(4, size);
            insertStmt.setDouble(5, price); // Include adjusted price
            insertStmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Order added to cart successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //walaa pa ito
    private void deductQuantityFromDatabase(String productName, String size, int quantity) {
        String updateQuery = "UPDATE stocks SET ProductQuantity = ProductQuantity - ? WHERE ProductName = ?";
        String insertOrderQuery = "INSERT INTO orders (Orders, Size, Quantity) VALUES (?, ?, ?)";
        try (Connection conn = connectToDatabase();
            PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
            PreparedStatement insertStmt = conn.prepareStatement(insertOrderQuery)) {
         
        // Deduct quantity
            updateStmt.setInt(1, quantity);
            updateStmt.setString(2, productName);
            
            int rowsAffected = updateStmt.executeUpdate();

            if (rowsAffected > 0) {
            // Record the order
                insertStmt.setString(1, productName);
                insertStmt.setString(2, size);
                insertStmt.setInt(3, quantity);
                insertStmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Order placed successfully! Quantity updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update quantity. Please check the stock.", "Update Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void displayCartItems() {
        DefaultTableModel model = (DefaultTableModel) CartTbl.getModel();
        model.setRowCount(0); // Clear existing rows

        String query = "SELECT * FROM cart WHERE Username = ?"; // Adjust query to filter by user
        try (Connection conn = connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, user_name); // Set the current user's name
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String productName = rs.getString("Orders");
                int quantity = rs.getInt("Quantity");
                String size = rs.getString("Size");
                double price = rs.getDouble("Price"); // Fetch the adjusted price from the database

                // Calculate total
                double total = price * quantity;

                // Add a row to the table model
                model.addRow(new Object[]{productName, price, quantity, size, total}); // Add total to the new column
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving cart items: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double getProductPrice(String productName) {
        double price = 0.0;
        String query = "SELECT ProductPrice FROM stocks WHERE ProductName = ?"; // Adjust based on your schema
        try (Connection conn = connectToDatabase();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, productName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                price = rs.getDouble("ProductPrice");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }

    private void removeAllOrdersFromCart() {
        int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove all orders from your cart?", "Confirm Removal", JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            String deleteQuery = "DELETE FROM cart WHERE Username = ?";
            try (Connection conn = connectToDatabase();
                 PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                pstmt.setString(1, user_name); // Assuming user_name is set when the user logs in

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "All orders removed from cart successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    displayCartItems(); // Refresh the cart display
                } else {
                    JOptionPane.showMessageDialog(this, "No orders found in the cart.", "Remove Error", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void fetchAndShowCartData() {
    String query = "SELECT * FROM cart WHERE Username = ?"; // Adjust query to filter by user
    try (Connection conn = connectToDatabase();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, user_name); // Set the current user's name
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            String productName = rs.getString("Orders");
            double price = rs.getDouble("Price");
            int quantity = rs.getInt("Quantity");
            String size = rs.getString("Size");
            double total = price * quantity;

            // Create and show the payment frame with the cart data
            payment paymentFrame = new payment(productName, price, quantity, size, total);
            paymentFrame.setVisible(true);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error retrieving cart items: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuPanel1 = new javax.swing.JPanel();
        lWELCOME = new javax.swing.JLabel();
        lPLEASE = new javax.swing.JLabel();
        tpaneMENU = new javax.swing.JTabbedPane();
        panelMenu = new javax.swing.JPanel();
        btnTea = new javax.swing.JButton();
        btnCoffee = new javax.swing.JButton();
        btnPastry = new javax.swing.JButton();
        labelCoffee = new javax.swing.JLabel();
        labelTea = new javax.swing.JLabel();
        labelPastry = new javax.swing.JLabel();
        btnCART = new javax.swing.JButton();
        labelMenu = new javax.swing.JLabel();
        btnCHKStocks = new javax.swing.JButton();
        panelCOFFEEMenu = new javax.swing.JPanel();
        btnCoffee1 = new javax.swing.JButton();
        btnCoffee2 = new javax.swing.JButton();
        btnCoffee3 = new javax.swing.JButton();
        btnCoffee4 = new javax.swing.JButton();
        btnCoffee5 = new javax.swing.JButton();
        btnCoffee6 = new javax.swing.JButton();
        labelESPRESSO = new javax.swing.JLabel();
        labelMOCHA = new javax.swing.JLabel();
        labelAMERICANO = new javax.swing.JLabel();
        labelMACCHIATO = new javax.swing.JLabel();
        labelCAPPUCCINO = new javax.swing.JLabel();
        labelLATTE = new javax.swing.JLabel();
        btnBACK1 = new javax.swing.JButton();
        priceCOF1 = new javax.swing.JLabel();
        priceCOF2 = new javax.swing.JLabel();
        priceCOF3 = new javax.swing.JLabel();
        priceCOF5 = new javax.swing.JLabel();
        priceCOF6 = new javax.swing.JLabel();
        priceCOF4 = new javax.swing.JLabel();
        jPanelCof = new javax.swing.JPanel();
        cartID4 = new javax.swing.JLabel();
        cartID6 = new javax.swing.JLabel();
        cartID7 = new javax.swing.JLabel();
        txtCoffeeName = new javax.swing.JTextField();
        txtQuanCoffee = new javax.swing.JTextField();
        txtSize = new javax.swing.JTextField();
        btnAddCoffee = new javax.swing.JButton();
        panelTEAMenu = new javax.swing.JPanel();
        btnTea1 = new javax.swing.JButton();
        btnTea2 = new javax.swing.JButton();
        btnTea3 = new javax.swing.JButton();
        btnTea4 = new javax.swing.JButton();
        btnTea5 = new javax.swing.JButton();
        btnTea6 = new javax.swing.JButton();
        labelBLACK = new javax.swing.JLabel();
        labelGREEN = new javax.swing.JLabel();
        labelSTRAWBERRY = new javax.swing.JLabel();
        labelGINGER = new javax.swing.JLabel();
        labelLEMON = new javax.swing.JLabel();
        labelMINT = new javax.swing.JLabel();
        btnBACK2 = new javax.swing.JButton();
        priceTEA1 = new javax.swing.JLabel();
        priceTEA2 = new javax.swing.JLabel();
        priceTEA3 = new javax.swing.JLabel();
        priceTEA4 = new javax.swing.JLabel();
        priceTEA5 = new javax.swing.JLabel();
        priceTEA6 = new javax.swing.JLabel();
        jPanelTea = new javax.swing.JPanel();
        cartID5 = new javax.swing.JLabel();
        cartID8 = new javax.swing.JLabel();
        cartID9 = new javax.swing.JLabel();
        txtTeaName = new javax.swing.JTextField();
        txtQuanTea = new javax.swing.JTextField();
        txtSize2 = new javax.swing.JTextField();
        btnAddTea = new javax.swing.JButton();
        panelPASTRYMenu = new javax.swing.JPanel();
        btnPastry1 = new javax.swing.JButton();
        btnPastry2 = new javax.swing.JButton();
        btnPastry3 = new javax.swing.JButton();
        btnPastry4 = new javax.swing.JButton();
        btnPastry5 = new javax.swing.JButton();
        btnPastry6 = new javax.swing.JButton();
        labelCROSSAINT = new javax.swing.JLabel();
        labelMUFFIN = new javax.swing.JLabel();
        labelTART = new javax.swing.JLabel();
        labelCOOKIE = new javax.swing.JLabel();
        labelBROWNIES = new javax.swing.JLabel();
        labelCINROLL = new javax.swing.JLabel();
        btnBACK3 = new javax.swing.JButton();
        pricePTY1 = new javax.swing.JLabel();
        pricePTY2 = new javax.swing.JLabel();
        pricePTY3 = new javax.swing.JLabel();
        pricePTY4 = new javax.swing.JLabel();
        pricePTY5 = new javax.swing.JLabel();
        pricePTY6 = new javax.swing.JLabel();
        jPanelPast = new javax.swing.JPanel();
        cartID10 = new javax.swing.JLabel();
        cartID11 = new javax.swing.JLabel();
        cartID12 = new javax.swing.JLabel();
        txtPastryName = new javax.swing.JTextField();
        txtQuanPastry = new javax.swing.JTextField();
        txtSize3 = new javax.swing.JTextField();
        btnAddPastry = new javax.swing.JButton();
        panelCART = new javax.swing.JPanel();
        btnBACK4 = new javax.swing.JButton();
        btnPayment = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        CartTbl = new javax.swing.JTable();
        cartID = new javax.swing.JLabel();
        PRODUCTNAME = new javax.swing.JTextField();
        cartID2 = new javax.swing.JLabel();
        QUANTITY = new javax.swing.JTextField();
        btnCartUpdate = new javax.swing.JButton();
        btnCartRemove = new javax.swing.JButton();
        cartID3 = new javax.swing.JLabel();
        SIZE = new javax.swing.JTextField();
        btnCartAdd = new javax.swing.JButton();
        btnSearchPname = new javax.swing.JButton();
        btnCartRemoveAll = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenuPanel1.setBackground(new java.awt.Color(111, 78, 55));

        lWELCOME.setFont(new java.awt.Font("Segoe Script", 1, 36)); // NOI18N
        lWELCOME.setText("WELCOME TO CPR COFFEE & TEA SHOP!");

        lPLEASE.setFont(new java.awt.Font("Yu Gothic UI Light", 3, 18)); // NOI18N
        lPLEASE.setText("PLEASE SELECT YOUR ORDER.");

        javax.swing.GroupLayout jMenuPanel1Layout = new javax.swing.GroupLayout(jMenuPanel1);
        jMenuPanel1.setLayout(jMenuPanel1Layout);
        jMenuPanel1Layout.setHorizontalGroup(
            jMenuPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jMenuPanel1Layout.createSequentialGroup()
                .addGroup(jMenuPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jMenuPanel1Layout.createSequentialGroup()
                        .addGap(634, 634, 634)
                        .addComponent(lPLEASE))
                    .addGroup(jMenuPanel1Layout.createSequentialGroup()
                        .addGap(280, 280, 280)
                        .addComponent(lWELCOME)))
                .addContainerGap(820, Short.MAX_VALUE))
        );
        jMenuPanel1Layout.setVerticalGroup(
            jMenuPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jMenuPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(lWELCOME, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lPLEASE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        tpaneMENU.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

        panelMenu.setBackground(new java.awt.Color(255, 255, 255));

        btnTea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menu/teavertical.jpg"))); // NOI18N
        btnTea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTeaActionPerformed(evt);
            }
        });

        btnCoffee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menu/coffee-vertical.jpg"))); // NOI18N
        btnCoffee.setBorder(null);
        btnCoffee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCoffeeActionPerformed(evt);
            }
        });

        btnPastry.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menu/pastry-vert.jpg"))); // NOI18N
        btnPastry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPastryActionPerformed(evt);
            }
        });

        labelCoffee.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        labelCoffee.setText("COFFEE");

        labelTea.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        labelTea.setText("TEA");

        labelPastry.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        labelPastry.setText("PASTRY");

        btnCART.setBackground(new java.awt.Color(255, 153, 51));
        btnCART.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menu/CART.jpg"))); // NOI18N
        btnCART.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCARTActionPerformed(evt);
            }
        });

        labelMenu.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        labelMenu.setText("MENU");

        btnCHKStocks.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCHKStocks.setForeground(new java.awt.Color(0, 102, 51));
        btnCHKStocks.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronia\\Downloads\\lists.png")); // NOI18N
        btnCHKStocks.setText("CHECK STOCKS");
        btnCHKStocks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCHKStocksActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelMenuLayout = new javax.swing.GroupLayout(panelMenu);
        panelMenu.setLayout(panelMenuLayout);
        panelMenuLayout.setHorizontalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMenuLayout.createSequentialGroup()
                        .addGap(310, 310, 310)
                        .addComponent(labelCoffee)
                        .addGap(362, 362, 362)
                        .addComponent(labelTea)
                        .addGap(372, 372, 372)
                        .addComponent(labelPastry))
                    .addGroup(panelMenuLayout.createSequentialGroup()
                        .addGap(248, 248, 248)
                        .addComponent(btnCoffee, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(218, 218, 218)
                        .addComponent(btnTea, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(218, 218, 218)
                        .addComponent(btnPastry, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(360, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMenuLayout.createSequentialGroup()
                .addGap(0, 703, Short.MAX_VALUE)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCHKStocks, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMenuLayout.createSequentialGroup()
                        .addComponent(labelMenu)
                        .addGap(612, 612, 612)
                        .addComponent(btnCART, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(151, 151, 151))
        );
        panelMenuLayout.setVerticalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMenuLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnCART, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelMenuLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(labelMenu)))
                .addGap(86, 86, 86)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnTea, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCoffee, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPastry, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCoffee)
                    .addComponent(labelTea)
                    .addComponent(labelPastry))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addComponent(btnCHKStocks, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );

        tpaneMENU.addTab("MENU", panelMenu);

        btnCoffee1.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronia\\Downloads\\icons8-espresso-100.png")); // NOI18N
        btnCoffee1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCoffee1ActionPerformed(evt);
            }
        });

        btnCoffee2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menu/icons8-mocha-100.png"))); // NOI18N
        btnCoffee2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCoffee2ActionPerformed(evt);
            }
        });

        btnCoffee3.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronia\\Downloads\\icons8-macchiato-100.png")); // NOI18N
        btnCoffee3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCoffee3ActionPerformed(evt);
            }
        });

        btnCoffee4.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronia\\Downloads\\icons8-cappuccino-100.png")); // NOI18N
        btnCoffee4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCoffee4ActionPerformed(evt);
            }
        });

        btnCoffee5.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronia\\Downloads\\icons8-latte-100.png")); // NOI18N
        btnCoffee5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCoffee5ActionPerformed(evt);
            }
        });

        btnCoffee6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menu/icons8-americano-100.png"))); // NOI18N
        btnCoffee6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCoffee6ActionPerformed(evt);
            }
        });

        labelESPRESSO.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        labelESPRESSO.setText("ESPRESSO");

        labelMOCHA.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        labelMOCHA.setText("MOCHA");

        labelAMERICANO.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        labelAMERICANO.setText("AMERICANO");

        labelMACCHIATO.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        labelMACCHIATO.setText("MACCHIATO");

        labelCAPPUCCINO.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        labelCAPPUCCINO.setText("CAPPUCCINO");

        labelLATTE.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        labelLATTE.setText("LATTE");

        btnBACK1.setBackground(new java.awt.Color(51, 153, 255));
        btnBACK1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnBACK1.setForeground(new java.awt.Color(255, 255, 255));
        btnBACK1.setText("BACK");
        btnBACK1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBACK1ActionPerformed(evt);
            }
        });

        priceCOF1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        priceCOF1.setText("₱ 60.25");

        priceCOF2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        priceCOF2.setText("₱ 63.50");

        priceCOF3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        priceCOF3.setText("₱ 65.00");

        priceCOF5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        priceCOF5.setText("₱ 68.25");

        priceCOF6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        priceCOF6.setText("₱ 67.75");

        priceCOF4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        priceCOF4.setText("₱ 60.50");

        jPanelCof.setBackground(new java.awt.Color(150, 100, 80));

        cartID4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cartID4.setText("QUANTITY :");

        cartID6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cartID6.setText("SIZE :  M(16oz) / L(24oz +10)");

        cartID7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cartID7.setText("PRODUCT :");

        txtCoffeeName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCoffeeName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCoffeeName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCoffeeNameActionPerformed(evt);
            }
        });

        txtQuanCoffee.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtQuanCoffee.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtQuanCoffee.setText("0");
        txtQuanCoffee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuanCoffeeActionPerformed(evt);
            }
        });

        txtSize.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSize.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSizeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelCofLayout = new javax.swing.GroupLayout(jPanelCof);
        jPanelCof.setLayout(jPanelCofLayout);
        jPanelCofLayout.setHorizontalGroup(
            jPanelCofLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCofLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCofLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cartID4)
                    .addComponent(cartID7)
                    .addComponent(cartID6))
                .addContainerGap(40, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCofLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(txtQuanCoffee, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanelCofLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(txtSize, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanelCofLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(txtCoffeeName, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelCofLayout.setVerticalGroup(
            jPanelCofLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCofLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(cartID7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCoffeeName, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cartID4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtQuanCoffee, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cartID6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSize, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        btnAddCoffee.setBackground(new java.awt.Color(51, 153, 255));
        btnAddCoffee.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddCoffee.setForeground(new java.awt.Color(255, 255, 255));
        btnAddCoffee.setText("ADD TO CART");
        btnAddCoffee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCoffeeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCOFFEEMenuLayout = new javax.swing.GroupLayout(panelCOFFEEMenu);
        panelCOFFEEMenu.setLayout(panelCOFFEEMenuLayout);
        panelCOFFEEMenuLayout.setHorizontalGroup(
            panelCOFFEEMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCOFFEEMenuLayout.createSequentialGroup()
                .addGroup(panelCOFFEEMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCOFFEEMenuLayout.createSequentialGroup()
                        .addGroup(panelCOFFEEMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelCOFFEEMenuLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jPanelCof, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCoffee1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelCOFFEEMenuLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCoffee4, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(120, 120, 120)
                        .addGroup(panelCOFFEEMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelCOFFEEMenuLayout.createSequentialGroup()
                                .addComponent(btnCoffee2, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(120, 120, 120)
                                .addComponent(btnCoffee3, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelCOFFEEMenuLayout.createSequentialGroup()
                                .addComponent(btnCoffee5, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCoffee6, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelCOFFEEMenuLayout.createSequentialGroup()
                        .addGroup(panelCOFFEEMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCOFFEEMenuLayout.createSequentialGroup()
                                .addGap(404, 404, 404)
                                .addComponent(labelCAPPUCCINO)
                                .addGap(202, 202, 202))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCOFFEEMenuLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(priceCOF4)
                                .addGap(233, 233, 233)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelCOFFEEMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelLATTE)
                            .addComponent(priceCOF5))
                        .addGroup(panelCOFFEEMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCOFFEEMenuLayout.createSequentialGroup()
                                .addGap(314, 314, 314)
                                .addComponent(labelAMERICANO)
                                .addGap(87, 87, 87))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCOFFEEMenuLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(priceCOF6)
                                .addGap(112, 112, 112)))))
                .addComponent(btnBACK1)
                .addGap(150, 150, 150))
            .addGroup(panelCOFFEEMenuLayout.createSequentialGroup()
                .addGroup(panelCOFFEEMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelCOFFEEMenuLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelESPRESSO)
                        .addGap(315, 315, 315))
                    .addGroup(panelCOFFEEMenuLayout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(btnAddCoffee)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(priceCOF1)
                        .addGap(327, 327, 327)))
                .addGroup(panelCOFFEEMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelCOFFEEMenuLayout.createSequentialGroup()
                        .addComponent(labelMOCHA)
                        .addGap(310, 310, 310)
                        .addComponent(labelMACCHIATO)
                        .addGap(317, 317, 317))
                    .addGroup(panelCOFFEEMenuLayout.createSequentialGroup()
                        .addComponent(priceCOF2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(priceCOF3)
                        .addGap(342, 342, 342))))
        );
        panelCOFFEEMenuLayout.setVerticalGroup(
            panelCOFFEEMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCOFFEEMenuLayout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addGroup(panelCOFFEEMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelCOFFEEMenuLayout.createSequentialGroup()
                        .addComponent(btnBACK1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51))
                    .addGroup(panelCOFFEEMenuLayout.createSequentialGroup()
                        .addGroup(panelCOFFEEMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCOFFEEMenuLayout.createSequentialGroup()
                                .addComponent(jPanelCof, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17)
                                .addComponent(btnAddCoffee, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelCOFFEEMenuLayout.createSequentialGroup()
                                .addGroup(panelCOFFEEMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnCoffee2, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnCoffee1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnCoffee3, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelCOFFEEMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelCOFFEEMenuLayout.createSequentialGroup()
                                        .addComponent(labelMOCHA, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(priceCOF2))
                                    .addGroup(panelCOFFEEMenuLayout.createSequentialGroup()
                                        .addGroup(panelCOFFEEMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(labelMACCHIATO, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(labelESPRESSO, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panelCOFFEEMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(priceCOF3)
                                            .addComponent(priceCOF1))))
                                .addGap(35, 35, 35)
                                .addGroup(panelCOFFEEMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnCoffee5, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnCoffee4, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnCoffee6, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelCOFFEEMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelCAPPUCCINO, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelLATTE, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelAMERICANO, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelCOFFEEMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelCOFFEEMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(priceCOF4)
                                        .addComponent(priceCOF5))
                                    .addComponent(priceCOF6))))
                        .addGap(62, 62, 62))))
        );

        tpaneMENU.addTab("COFFEE", panelCOFFEEMenu);

        btnTea1.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronia\\Downloads\\icons8-black-tea-100.png")); // NOI18N
        btnTea1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTea1ActionPerformed(evt);
            }
        });

        btnTea2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menu/icons8-green-tea-100.png"))); // NOI18N
        btnTea2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTea2ActionPerformed(evt);
            }
        });

        btnTea3.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronia\\Downloads\\icons8-ginger-100.png")); // NOI18N
        btnTea3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTea3ActionPerformed(evt);
            }
        });

        btnTea4.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronia\\Downloads\\icons8-lemon-100.png")); // NOI18N
        btnTea4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTea4ActionPerformed(evt);
            }
        });

        btnTea5.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronia\\Downloads\\icons8-mint-tea-100.png")); // NOI18N
        btnTea5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTea5ActionPerformed(evt);
            }
        });

        btnTea6.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronia\\Downloads\\icons8-strawberry-100.png")); // NOI18N
        btnTea6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTea6ActionPerformed(evt);
            }
        });

        labelBLACK.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        labelBLACK.setText("BLACK TEA");

        labelGREEN.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        labelGREEN.setText("GREEN TEA");

        labelSTRAWBERRY.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        labelSTRAWBERRY.setText("STRAWBERRY TEA");

        labelGINGER.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        labelGINGER.setText("GINGER TEA");

        labelLEMON.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        labelLEMON.setText("LEMON TEA");

        labelMINT.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        labelMINT.setText("MINT TEA");

        btnBACK2.setBackground(new java.awt.Color(51, 153, 255));
        btnBACK2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnBACK2.setForeground(new java.awt.Color(255, 255, 255));
        btnBACK2.setText("BACK");
        btnBACK2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBACK2ActionPerformed(evt);
            }
        });

        priceTEA1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        priceTEA1.setText("₱ 45.25");

        priceTEA2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        priceTEA2.setText("₱ 49.50");

        priceTEA3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        priceTEA3.setText("₱ 50.25");

        priceTEA4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        priceTEA4.setText("₱ 53.50");

        priceTEA5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        priceTEA5.setText("₱ 55.75");

        priceTEA6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        priceTEA6.setText("₱ 60.50");

        jPanelTea.setBackground(new java.awt.Color(150, 100, 80));

        cartID5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cartID5.setText("QUANTITY :");

        cartID8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cartID8.setText("SIZE :   M(16oz) / L(24oz +10)");

        cartID9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cartID9.setText("PRODUCT :");

        txtTeaName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTeaName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTeaName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTeaNameActionPerformed(evt);
            }
        });

        txtQuanTea.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtQuanTea.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtQuanTea.setText("0");
        txtQuanTea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuanTeaActionPerformed(evt);
            }
        });

        txtSize2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSize2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSize2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSize2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTeaLayout = new javax.swing.GroupLayout(jPanelTea);
        jPanelTea.setLayout(jPanelTeaLayout);
        jPanelTeaLayout.setHorizontalGroup(
            jPanelTeaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTeaLayout.createSequentialGroup()
                .addGroup(jPanelTeaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTeaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelTeaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cartID5)
                            .addComponent(cartID9)
                            .addComponent(cartID8)))
                    .addGroup(jPanelTeaLayout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(txtSize2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelTeaLayout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(txtQuanTea, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelTeaLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(txtTeaName, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanelTeaLayout.setVerticalGroup(
            jPanelTeaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTeaLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(cartID9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTeaName, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cartID5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtQuanTea, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cartID8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSize2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        btnAddTea.setBackground(new java.awt.Color(51, 153, 255));
        btnAddTea.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddTea.setForeground(new java.awt.Color(255, 255, 255));
        btnAddTea.setText("ADD TO CART");
        btnAddTea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTeaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTEAMenuLayout = new javax.swing.GroupLayout(panelTEAMenu);
        panelTEAMenu.setLayout(panelTEAMenuLayout);
        panelTEAMenuLayout.setHorizontalGroup(
            panelTEAMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTEAMenuLayout.createSequentialGroup()
                .addGroup(panelTEAMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTEAMenuLayout.createSequentialGroup()
                        .addGroup(panelTEAMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelTEAMenuLayout.createSequentialGroup()
                                .addGroup(panelTEAMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(panelTEAMenuLayout.createSequentialGroup()
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnTea4, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelTEAMenuLayout.createSequentialGroup()
                                        .addGap(17, 17, 17)
                                        .addComponent(jPanelTea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnTea1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(120, 120, 120)
                                .addGroup(panelTEAMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(panelTEAMenuLayout.createSequentialGroup()
                                        .addComponent(btnTea2, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(120, 120, 120)
                                        .addComponent(btnTea3, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelTEAMenuLayout.createSequentialGroup()
                                        .addComponent(btnTea5, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnTea6, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(panelTEAMenuLayout.createSequentialGroup()
                                .addGroup(panelTEAMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(panelTEAMenuLayout.createSequentialGroup()
                                        .addGap(417, 417, 417)
                                        .addComponent(labelBLACK)
                                        .addGap(184, 184, 184))
                                    .addGroup(panelTEAMenuLayout.createSequentialGroup()
                                        .addGap(98, 98, 98)
                                        .addComponent(btnAddTea)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(priceTEA1)
                                        .addGap(206, 206, 206)))
                                .addGroup(panelTEAMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelTEAMenuLayout.createSequentialGroup()
                                        .addGap(115, 115, 115)
                                        .addComponent(labelGREEN)
                                        .addGap(239, 239, 239))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTEAMenuLayout.createSequentialGroup()
                                        .addComponent(priceTEA2)
                                        .addGap(260, 260, 260)))
                                .addGroup(panelTEAMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelTEAMenuLayout.createSequentialGroup()
                                        .addGap(60, 60, 60)
                                        .addComponent(labelGINGER))
                                    .addGroup(panelTEAMenuLayout.createSequentialGroup()
                                        .addGap(82, 82, 82)
                                        .addComponent(priceTEA3)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelTEAMenuLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelTEAMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTEAMenuLayout.createSequentialGroup()
                                .addComponent(labelLEMON)
                                .addGap(226, 226, 226))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTEAMenuLayout.createSequentialGroup()
                                .addComponent(priceTEA4)
                                .addGap(251, 251, 251)))
                        .addGroup(panelTEAMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelTEAMenuLayout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addComponent(labelMINT)
                                .addGap(276, 276, 276)
                                .addComponent(labelSTRAWBERRY)
                                .addGap(63, 63, 63))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTEAMenuLayout.createSequentialGroup()
                                .addComponent(priceTEA5)
                                .addGap(337, 337, 337)
                                .addComponent(priceTEA6)
                                .addGap(118, 118, 118)))))
                .addComponent(btnBACK2)
                .addGap(150, 150, 150))
        );
        panelTEAMenuLayout.setVerticalGroup(
            panelTEAMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTEAMenuLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(panelTEAMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTEAMenuLayout.createSequentialGroup()
                        .addComponent(jPanelTea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addComponent(btnAddTea, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelTEAMenuLayout.createSequentialGroup()
                        .addGroup(panelTEAMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnTea2, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTea1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTea3, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelTEAMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelTEAMenuLayout.createSequentialGroup()
                                .addGroup(panelTEAMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelBLACK, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelGREEN, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelGINGER, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelTEAMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(priceTEA3)
                                    .addComponent(priceTEA2)
                                    .addComponent(priceTEA1))
                                .addGap(35, 35, 35)
                                .addGroup(panelTEAMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnTea5, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnTea6, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnTea4, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelTEAMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelLEMON, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelMINT, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelSTRAWBERRY, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelTEAMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(priceTEA5)
                                    .addComponent(priceTEA4)
                                    .addComponent(priceTEA6)))
                            .addComponent(btnBACK2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        tpaneMENU.addTab("TEA", panelTEAMenu);

        btnPastry1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menu/icons8-croissant-100.png"))); // NOI18N
        btnPastry1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPastry1ActionPerformed(evt);
            }
        });

        btnPastry2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menu/icons8-muffin-100.png"))); // NOI18N
        btnPastry2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPastry2ActionPerformed(evt);
            }
        });

        btnPastry3.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronia\\Downloads\\icons8-cookies-100.png")); // NOI18N
        btnPastry3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPastry3ActionPerformed(evt);
            }
        });

        btnPastry4.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronia\\Downloads\\icons8-brownies-100.png")); // NOI18N
        btnPastry4.setBorder(null);
        btnPastry4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPastry4ActionPerformed(evt);
            }
        });

        btnPastry5.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronia\\Downloads\\icons8-cinnamon-roll-100.png")); // NOI18N
        btnPastry5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPastry5ActionPerformed(evt);
            }
        });

        btnPastry6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menu/icons8-tart-100.png"))); // NOI18N
        btnPastry6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPastry6ActionPerformed(evt);
            }
        });

        labelCROSSAINT.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        labelCROSSAINT.setText("CROSSAINT");

        labelMUFFIN.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        labelMUFFIN.setText("MUFFIN");

        labelTART.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        labelTART.setText("TARTS");

        labelCOOKIE.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        labelCOOKIE.setText("COOKIES");

        labelBROWNIES.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        labelBROWNIES.setText("BROWNIES");

        labelCINROLL.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        labelCINROLL.setText("CINNAMON ROLL");

        btnBACK3.setBackground(new java.awt.Color(51, 153, 255));
        btnBACK3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnBACK3.setForeground(new java.awt.Color(255, 255, 255));
        btnBACK3.setText("BACK");
        btnBACK3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBACK3ActionPerformed(evt);
            }
        });

        pricePTY1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        pricePTY1.setText("₱ 25.00");

        pricePTY2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        pricePTY2.setText("₱ 20.00");

        pricePTY3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        pricePTY3.setText("₱ 8.00");

        pricePTY4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        pricePTY4.setText("₱ 12.00");

        pricePTY5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        pricePTY5.setText("₱ 30.00");

        pricePTY6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        pricePTY6.setText("₱ 11.00");

        jPanelPast.setBackground(new java.awt.Color(150, 100, 80));

        cartID10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cartID10.setText("QUANTITY :");

        cartID11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cartID11.setText("SIZE :  P(Pack) / B(Box +25) ");

        cartID12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cartID12.setText("PRODUCT :");

        txtPastryName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtPastryName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPastryName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPastryNameActionPerformed(evt);
            }
        });

        txtQuanPastry.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtQuanPastry.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtQuanPastry.setText("0");
        txtQuanPastry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuanPastryActionPerformed(evt);
            }
        });

        txtSize3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSize3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSize3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSize3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelPastLayout = new javax.swing.GroupLayout(jPanelPast);
        jPanelPast.setLayout(jPanelPastLayout);
        jPanelPastLayout.setHorizontalGroup(
            jPanelPastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPastLayout.createSequentialGroup()
                .addGroup(jPanelPastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPastLayout.createSequentialGroup()
                        .addContainerGap(25, Short.MAX_VALUE)
                        .addComponent(txtPastryName, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelPastLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelPastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cartID11)
                            .addComponent(cartID10)
                            .addComponent(cartID12))))
                .addContainerGap(26, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPastLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(txtQuanPastry, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanelPastLayout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(txtSize3, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelPastLayout.setVerticalGroup(
            jPanelPastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPastLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(cartID12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPastryName, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cartID10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtQuanPastry, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cartID11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSize3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        btnAddPastry.setBackground(new java.awt.Color(51, 153, 255));
        btnAddPastry.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddPastry.setForeground(new java.awt.Color(255, 255, 255));
        btnAddPastry.setText("ADD TO CART");
        btnAddPastry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPastryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPASTRYMenuLayout = new javax.swing.GroupLayout(panelPASTRYMenu);
        panelPASTRYMenu.setLayout(panelPASTRYMenuLayout);
        panelPASTRYMenuLayout.setHorizontalGroup(
            panelPASTRYMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPASTRYMenuLayout.createSequentialGroup()
                .addGroup(panelPASTRYMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panelPASTRYMenuLayout.createSequentialGroup()
                        .addGap(414, 414, 414)
                        .addComponent(labelCROSSAINT)
                        .addGap(203, 203, 203))
                    .addGroup(panelPASTRYMenuLayout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(btnAddPastry)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pricePTY1)
                        .addGap(224, 224, 224)))
                .addGap(114, 114, 114)
                .addGroup(panelPASTRYMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPASTRYMenuLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(pricePTY2)
                        .addGap(346, 346, 346)
                        .addComponent(pricePTY3)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelPASTRYMenuLayout.createSequentialGroup()
                        .addComponent(labelMUFFIN)
                        .addGap(328, 328, 328)
                        .addComponent(labelCOOKIE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPASTRYMenuLayout.createSequentialGroup()
                .addGroup(panelPASTRYMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelPASTRYMenuLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(panelPASTRYMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(panelPASTRYMenuLayout.createSequentialGroup()
                                .addComponent(jPanelPast, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnPastry1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnPastry4, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(120, 120, 120)
                        .addGroup(panelPASTRYMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelPASTRYMenuLayout.createSequentialGroup()
                                .addComponent(btnPastry2, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(120, 120, 120)
                                .addComponent(btnPastry3, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelPASTRYMenuLayout.createSequentialGroup()
                                .addComponent(btnPastry5, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnPastry6, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelPASTRYMenuLayout.createSequentialGroup()
                        .addGroup(panelPASTRYMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPASTRYMenuLayout.createSequentialGroup()
                                .addGap(421, 421, 421)
                                .addComponent(labelBROWNIES)
                                .addGap(160, 160, 160))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPASTRYMenuLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pricePTY4)
                                .addGap(179, 179, 179)))
                        .addGroup(panelPASTRYMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPASTRYMenuLayout.createSequentialGroup()
                                .addGap(110, 110, 110)
                                .addComponent(labelCINROLL))
                            .addGroup(panelPASTRYMenuLayout.createSequentialGroup()
                                .addGap(156, 156, 156)
                                .addComponent(pricePTY5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelPASTRYMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelTART, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pricePTY6, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(109, 109, 109)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBACK3)
                .addGap(150, 150, 150))
        );
        panelPASTRYMenuLayout.setVerticalGroup(
            panelPASTRYMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPASTRYMenuLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(panelPASTRYMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPASTRYMenuLayout.createSequentialGroup()
                        .addGroup(panelPASTRYMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnPastry3, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPastry2, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPastry1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelPASTRYMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPASTRYMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPASTRYMenuLayout.createSequentialGroup()
                                    .addGap(31, 31, 31)
                                    .addComponent(btnPastry4, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panelPASTRYMenuLayout.createSequentialGroup()
                                    .addComponent(labelCROSSAINT, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(4, 4, 4)
                                    .addComponent(pricePTY1)
                                    .addGap(37, 37, 37)
                                    .addGroup(panelPASTRYMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnPastry6, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnPastry5, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(panelPASTRYMenuLayout.createSequentialGroup()
                                .addGroup(panelPASTRYMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelCOOKIE, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelMUFFIN, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelPASTRYMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(pricePTY3)
                                    .addComponent(pricePTY2)))))
                    .addGroup(panelPASTRYMenuLayout.createSequentialGroup()
                        .addComponent(jPanelPast, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addComponent(btnAddPastry, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(panelPASTRYMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPASTRYMenuLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panelPASTRYMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelCINROLL, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelBROWNIES, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelTART, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelPASTRYMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pricePTY5)
                            .addComponent(pricePTY6)
                            .addComponent(pricePTY4))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPASTRYMenuLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addComponent(btnBACK3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51))))
        );

        tpaneMENU.addTab("PASTRY", panelPASTRYMenu);

        btnBACK4.setBackground(new java.awt.Color(51, 153, 255));
        btnBACK4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnBACK4.setForeground(new java.awt.Color(255, 255, 255));
        btnBACK4.setText("BACK");
        btnBACK4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBACK4ActionPerformed(evt);
            }
        });

        btnPayment.setBackground(new java.awt.Color(102, 255, 102));
        btnPayment.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnPayment.setForeground(new java.awt.Color(255, 255, 255));
        btnPayment.setText("PAY HERE");
        btnPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaymentActionPerformed(evt);
            }
        });

        CartTbl.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        CartTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "PRODUCT NAME", "PRICE", "QUANTITY", "SIZE", "SUBTOTAL"
            }
        ));
        jScrollPane3.setViewportView(CartTbl);

        cartID.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cartID.setText("PRODUCT NAME:");

        cartID2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cartID2.setText("QUANTITY :");

        btnCartUpdate.setBackground(new java.awt.Color(51, 153, 255));
        btnCartUpdate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCartUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnCartUpdate.setText("UPDATE");
        btnCartUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCartUpdateActionPerformed(evt);
            }
        });

        btnCartRemove.setBackground(new java.awt.Color(255, 51, 51));
        btnCartRemove.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCartRemove.setForeground(new java.awt.Color(255, 255, 255));
        btnCartRemove.setText("REMOVE");
        btnCartRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCartRemoveActionPerformed(evt);
            }
        });

        cartID3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cartID3.setText("SIZE : ");

        btnCartAdd.setBackground(new java.awt.Color(51, 153, 255));
        btnCartAdd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCartAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnCartAdd.setText("ADD");
        btnCartAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCartAddActionPerformed(evt);
            }
        });

        btnSearchPname.setBackground(new java.awt.Color(51, 153, 255));
        btnSearchPname.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSearchPname.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchPname.setText("SEARCH");
        btnSearchPname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchPnameActionPerformed(evt);
            }
        });

        btnCartRemoveAll.setBackground(new java.awt.Color(255, 51, 51));
        btnCartRemoveAll.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCartRemoveAll.setForeground(new java.awt.Color(255, 255, 255));
        btnCartRemoveAll.setText("REMOVE ALL");
        btnCartRemoveAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCartRemoveAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCARTLayout = new javax.swing.GroupLayout(panelCART);
        panelCART.setLayout(panelCARTLayout);
        panelCARTLayout.setHorizontalGroup(
            panelCARTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCARTLayout.createSequentialGroup()
                .addGroup(panelCARTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCARTLayout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(panelCARTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cartID)
                            .addComponent(cartID2)
                            .addComponent(cartID3)
                            .addGroup(panelCARTLayout.createSequentialGroup()
                                .addGroup(panelCARTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelCARTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                                        .addComponent(QUANTITY)
                                        .addComponent(PRODUCTNAME))
                                    .addGroup(panelCARTLayout.createSequentialGroup()
                                        .addComponent(btnCartUpdate)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnCartAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(panelCARTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnCartRemove)
                                    .addComponent(btnSearchPname)))))
                    .addGroup(panelCARTLayout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addComponent(btnCartRemoveAll)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 865, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(btnBACK4)
                .addGap(177, 177, 177))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCARTLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(155, 155, 155))
        );
        panelCARTLayout.setVerticalGroup(
            panelCARTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCARTLayout.createSequentialGroup()
                .addGroup(panelCARTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCARTLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(btnPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(92, 92, 92)
                        .addComponent(cartID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelCARTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PRODUCTNAME, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearchPname))
                        .addGap(27, 27, 27)
                        .addComponent(cartID2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(QUANTITY, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(cartID3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addGroup(panelCARTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCartUpdate)
                            .addComponent(btnCartAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCartRemove))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCartRemoveAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCARTLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)))
                .addComponent(btnBACK4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );

        tpaneMENU.addTab("CART", panelCART);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tpaneMENU, javax.swing.GroupLayout.PREFERRED_SIZE, 1647, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jMenuPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jMenuPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tpaneMENU, javax.swing.GroupLayout.PREFERRED_SIZE, 747, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 187, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTeaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTeaActionPerformed
        tpaneMENU.setSelectedIndex(2);
    }//GEN-LAST:event_btnTeaActionPerformed

    private void btnCoffeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCoffeeActionPerformed
        tpaneMENU.setSelectedIndex(1);
    }//GEN-LAST:event_btnCoffeeActionPerformed

    private void btnPastryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPastryActionPerformed
        tpaneMENU.setSelectedIndex(3);
    }//GEN-LAST:event_btnPastryActionPerformed

    private void btnCARTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCARTActionPerformed
        tpaneMENU.setSelectedIndex(4);
        displayCartItems();
        
    }//GEN-LAST:event_btnCARTActionPerformed

    private void btnCHKStocksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCHKStocksActionPerformed
        login loginFrame = new login();

        loginFrame.setVisible(true);
    }//GEN-LAST:event_btnCHKStocksActionPerformed

    private void btnCoffee1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCoffee1ActionPerformed
        String productName = "ESPRESSO";
        txtCoffeeName.setText(productName);
        searchCoffeeInDatabase(productName);
    }//GEN-LAST:event_btnCoffee1ActionPerformed

    private void btnCoffee2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCoffee2ActionPerformed
        String productName = "MOCHA";
        txtCoffeeName.setText(productName);
        searchCoffeeInDatabase(productName);
    }//GEN-LAST:event_btnCoffee2ActionPerformed

    private void btnCoffee3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCoffee3ActionPerformed
        String productName = "MACCHIATO";
        txtCoffeeName.setText(productName);
        searchCoffeeInDatabase(productName);
    }//GEN-LAST:event_btnCoffee3ActionPerformed

    private void btnCoffee4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCoffee4ActionPerformed
        String productName = "CAPPUCCINO";
        txtCoffeeName.setText(productName);
        searchCoffeeInDatabase(productName);
    }//GEN-LAST:event_btnCoffee4ActionPerformed

    private void btnCoffee5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCoffee5ActionPerformed
        String productName = "LATTE";
        txtCoffeeName.setText(productName);
        searchCoffeeInDatabase(productName);
    }//GEN-LAST:event_btnCoffee5ActionPerformed

    private void btnCoffee6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCoffee6ActionPerformed
        String productName = "AMERICANO";
        txtCoffeeName.setText(productName);
        searchCoffeeInDatabase(productName);
    }//GEN-LAST:event_btnCoffee6ActionPerformed

    private void btnBACK1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBACK1ActionPerformed
        tpaneMENU.setSelectedIndex(0);
    }//GEN-LAST:event_btnBACK1ActionPerformed

    private void btnTea1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTea1ActionPerformed
        String productName = "BLACK TEA";
        txtTeaName.setText(productName);
        searchCoffeeInDatabase(productName);
    }//GEN-LAST:event_btnTea1ActionPerformed

    private void btnTea2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTea2ActionPerformed
        String productName = "GREEN TEA";
        txtTeaName.setText(productName);
        searchCoffeeInDatabase(productName);
    }//GEN-LAST:event_btnTea2ActionPerformed

    private void btnTea3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTea3ActionPerformed
        String productName = "GINGER TEA";
        txtTeaName.setText(productName);
        searchCoffeeInDatabase(productName);
    }//GEN-LAST:event_btnTea3ActionPerformed

    private void btnTea4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTea4ActionPerformed
        String productName = "LEMON TEA";
        txtTeaName.setText(productName);
        searchCoffeeInDatabase(productName);
    }//GEN-LAST:event_btnTea4ActionPerformed

    private void btnTea5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTea5ActionPerformed
        String productName = "MINT TEA";
        txtTeaName.setText(productName);
        searchCoffeeInDatabase(productName);
    }//GEN-LAST:event_btnTea5ActionPerformed

    private void btnTea6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTea6ActionPerformed
        String productName = "STRAWBERRY TEA";
        txtTeaName.setText(productName);
        searchCoffeeInDatabase(productName);
    }//GEN-LAST:event_btnTea6ActionPerformed

    private void btnBACK2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBACK2ActionPerformed
        tpaneMENU.setSelectedIndex(0);
    }//GEN-LAST:event_btnBACK2ActionPerformed

    private void btnPastry1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPastry1ActionPerformed
        String productName = "CROSSAINT";
        txtPastryName.setText(productName);
        searchCoffeeInDatabase(productName);
    }//GEN-LAST:event_btnPastry1ActionPerformed

    private void btnPastry2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPastry2ActionPerformed
        String productName = "MUFFIN";
        txtPastryName.setText(productName);
        searchCoffeeInDatabase(productName);
    }//GEN-LAST:event_btnPastry2ActionPerformed

    private void btnPastry3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPastry3ActionPerformed
        String productName = "COOKIES";
        txtPastryName.setText(productName);
        searchCoffeeInDatabase(productName);
    }//GEN-LAST:event_btnPastry3ActionPerformed

    private void btnPastry4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPastry4ActionPerformed
        String productName = "BROWNIES";
        txtPastryName.setText(productName);
        searchCoffeeInDatabase(productName);
    }//GEN-LAST:event_btnPastry4ActionPerformed

    private void btnPastry5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPastry5ActionPerformed
        String productName = "CINNAMON ROLL";
        txtPastryName.setText(productName);
        searchCoffeeInDatabase(productName);
    }//GEN-LAST:event_btnPastry5ActionPerformed

    private void btnPastry6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPastry6ActionPerformed
        String productName = "TARTS";
        txtPastryName.setText(productName);
        searchCoffeeInDatabase(productName);
    }//GEN-LAST:event_btnPastry6ActionPerformed

    private void btnBACK3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBACK3ActionPerformed
        tpaneMENU.setSelectedIndex(0);
    }//GEN-LAST:event_btnBACK3ActionPerformed

    private void btnPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaymentActionPerformed
        fetchAndShowCartData();
        
    }//GEN-LAST:event_btnPaymentActionPerformed

    private void btnBACK4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBACK4ActionPerformed
        tpaneMENU.setSelectedIndex(0);
    }//GEN-LAST:event_btnBACK4ActionPerformed

    private void btnCartUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCartUpdateActionPerformed
        String productName = PRODUCTNAME.getText().trim();
        String quantityStr = QUANTITY.getText().trim();
        String size = SIZE.getText().trim();

        if (productName.isEmpty() || quantityStr.isEmpty() || size.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter product name, quantity, and size.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String updateQuery = "UPDATE cart SET Quantity = ?, Size = ? WHERE Username = ? AND Orders = ?";
        try (Connection conn = connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setInt(1, quantity);
            pstmt.setString(2, size);
            pstmt.setString(3, user_name); // Assuming user_name is set when the user logs in
            pstmt.setString(4, productName);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Cart updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                displayCartItems(); // Refresh the cart display
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update cart. Please check the product name.", "Update Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        PRODUCTNAME.setText("");
        QUANTITY.setText("");
        SIZE.setText("");
    }//GEN-LAST:event_btnCartUpdateActionPerformed

    private void btnCartRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCartRemoveActionPerformed
        String productName = PRODUCTNAME.getText().trim();

        if (productName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a product name to remove.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String deleteQuery = "DELETE FROM cart WHERE Username = ? AND Orders = ?";
        try (Connection conn = connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
            pstmt.setString(1, user_name); // Assuming user_name is set when the user logs in
            pstmt.setString(2, productName);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Product removed from cart successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                displayCartItems(); // Refresh the cart display
            } else {
                JOptionPane.showMessageDialog(this, "No product found with that name in the cart.", "Remove Error", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCartRemoveActionPerformed

    private void txtQuanCoffeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuanCoffeeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuanCoffeeActionPerformed

    private void txtQuanTeaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuanTeaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuanTeaActionPerformed

    private void txtQuanPastryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuanPastryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuanPastryActionPerformed

    private void btnAddTeaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTeaActionPerformed
        String productName = txtTeaName.getText().trim();
        String size = txtSize2.getText().trim();
        String quantityStr = txtQuanTea.getText().trim();

        if (productName.isEmpty() || quantityStr.isEmpty() || size.isEmpty()) {
            JOptionPane.showMessageDialog(menu.this, "Please enter both tea name and quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(menu.this, "Please enter a valid quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

    // Instead of deducting, insert the order into the database
        addOrderToDatabase(productName, quantity, size);
        
        txtTeaName.setText("");
        txtQuanTea.setText("0");
        txtSize2.setText("");
    }//GEN-LAST:event_btnAddTeaActionPerformed

    private void txtTeaNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTeaNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTeaNameActionPerformed

    private void txtCoffeeNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCoffeeNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCoffeeNameActionPerformed

    private void txtPastryNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPastryNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPastryNameActionPerformed

    private void btnAddCoffeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCoffeeActionPerformed
        String productName = txtCoffeeName.getText().trim();
        String size = txtSize.getText().trim();
        String quantityStr = txtQuanCoffee.getText().trim();

        if (productName.isEmpty() || quantityStr.isEmpty() || size.isEmpty()) {
            JOptionPane.showMessageDialog(menu.this, "Please enter both coffee name and quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(menu.this, "Please enter a valid quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        addOrderToDatabase(productName, quantity, size);
        
        txtCoffeeName.setText("");
        txtQuanCoffee.setText("0");
        txtSize.setText("");
    }//GEN-LAST:event_btnAddCoffeeActionPerformed

    private void txtSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSizeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSizeActionPerformed

    private void txtSize3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSize3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSize3ActionPerformed

    private void txtSize2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSize2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSize2ActionPerformed

    private void btnAddPastryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPastryActionPerformed
        String productName = txtPastryName.getText().trim();
        String size = txtSize3.getText().trim();
        String quantityStr = txtQuanPastry.getText().trim();

        if (productName.isEmpty() || quantityStr.isEmpty() || size.isEmpty()) {
            JOptionPane.showMessageDialog(menu.this, "Please enter both pastry name and quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(menu.this, "Please enter a valid quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

    // Instead of deducting, insert the order into the database
        addOrderToDatabase(productName, quantity, size);
        
        txtPastryName.setText("");
        txtQuanPastry.setText("0");
        txtSize3.setText("");
    }//GEN-LAST:event_btnAddPastryActionPerformed

    private void btnCartAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCartAddActionPerformed
        tpaneMENU.setSelectedIndex(0);
    }//GEN-LAST:event_btnCartAddActionPerformed

    private void btnSearchPnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchPnameActionPerformed
        String productName = PRODUCTNAME.getText().trim();
    
        if (productName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a product name to search.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "SELECT Quantity, Size FROM cart WHERE Username = ? AND Orders = ?";
        try (Connection conn = connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, user_name); // Assuming user_name is set when the user logs in
            pstmt.setString(2, productName);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int quantity = rs.getInt("Quantity");
                String size = rs.getString("Size");

                QUANTITY.setText(String.valueOf(quantity));
                SIZE.setText(size);
            } else {
                JOptionPane.showMessageDialog(this, "No order found for the product: " + productName, "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSearchPnameActionPerformed

    private void btnCartRemoveAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCartRemoveAllActionPerformed
        removeAllOrdersFromCart();
    }//GEN-LAST:event_btnCartRemoveAllActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable CartTbl;
    private javax.swing.JTextField PRODUCTNAME;
    private javax.swing.JTextField QUANTITY;
    private javax.swing.JTextField SIZE;
    private javax.swing.JButton btnAddCoffee;
    private javax.swing.JButton btnAddPastry;
    private javax.swing.JButton btnAddTea;
    private javax.swing.JButton btnBACK1;
    private javax.swing.JButton btnBACK2;
    private javax.swing.JButton btnBACK3;
    private javax.swing.JButton btnBACK4;
    private javax.swing.JButton btnCART;
    private javax.swing.JButton btnCHKStocks;
    private javax.swing.JButton btnCartAdd;
    private javax.swing.JButton btnCartRemove;
    private javax.swing.JButton btnCartRemoveAll;
    private javax.swing.JButton btnCartUpdate;
    private javax.swing.JButton btnCoffee;
    private javax.swing.JButton btnCoffee1;
    private javax.swing.JButton btnCoffee2;
    private javax.swing.JButton btnCoffee3;
    private javax.swing.JButton btnCoffee4;
    private javax.swing.JButton btnCoffee5;
    private javax.swing.JButton btnCoffee6;
    private javax.swing.JButton btnPastry;
    private javax.swing.JButton btnPastry1;
    private javax.swing.JButton btnPastry2;
    private javax.swing.JButton btnPastry3;
    private javax.swing.JButton btnPastry4;
    private javax.swing.JButton btnPastry5;
    private javax.swing.JButton btnPastry6;
    private javax.swing.JButton btnPayment;
    private javax.swing.JButton btnSearchPname;
    private javax.swing.JButton btnTea;
    private javax.swing.JButton btnTea1;
    private javax.swing.JButton btnTea2;
    private javax.swing.JButton btnTea3;
    private javax.swing.JButton btnTea4;
    private javax.swing.JButton btnTea5;
    private javax.swing.JButton btnTea6;
    private javax.swing.JLabel cartID;
    private javax.swing.JLabel cartID10;
    private javax.swing.JLabel cartID11;
    private javax.swing.JLabel cartID12;
    private javax.swing.JLabel cartID2;
    private javax.swing.JLabel cartID3;
    private javax.swing.JLabel cartID4;
    private javax.swing.JLabel cartID5;
    private javax.swing.JLabel cartID6;
    private javax.swing.JLabel cartID7;
    private javax.swing.JLabel cartID8;
    private javax.swing.JLabel cartID9;
    private javax.swing.JPanel jMenuPanel1;
    private javax.swing.JPanel jPanelCof;
    private javax.swing.JPanel jPanelPast;
    private javax.swing.JPanel jPanelTea;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lPLEASE;
    private javax.swing.JLabel lWELCOME;
    private javax.swing.JLabel labelAMERICANO;
    private javax.swing.JLabel labelBLACK;
    private javax.swing.JLabel labelBROWNIES;
    private javax.swing.JLabel labelCAPPUCCINO;
    private javax.swing.JLabel labelCINROLL;
    private javax.swing.JLabel labelCOOKIE;
    private javax.swing.JLabel labelCROSSAINT;
    private javax.swing.JLabel labelCoffee;
    private javax.swing.JLabel labelESPRESSO;
    private javax.swing.JLabel labelGINGER;
    private javax.swing.JLabel labelGREEN;
    private javax.swing.JLabel labelLATTE;
    private javax.swing.JLabel labelLEMON;
    private javax.swing.JLabel labelMACCHIATO;
    private javax.swing.JLabel labelMINT;
    private javax.swing.JLabel labelMOCHA;
    private javax.swing.JLabel labelMUFFIN;
    private javax.swing.JLabel labelMenu;
    private javax.swing.JLabel labelPastry;
    private javax.swing.JLabel labelSTRAWBERRY;
    private javax.swing.JLabel labelTART;
    private javax.swing.JLabel labelTea;
    private javax.swing.JPanel panelCART;
    private javax.swing.JPanel panelCOFFEEMenu;
    private javax.swing.JPanel panelMenu;
    private javax.swing.JPanel panelPASTRYMenu;
    private javax.swing.JPanel panelTEAMenu;
    private javax.swing.JLabel priceCOF1;
    private javax.swing.JLabel priceCOF2;
    private javax.swing.JLabel priceCOF3;
    private javax.swing.JLabel priceCOF4;
    private javax.swing.JLabel priceCOF5;
    private javax.swing.JLabel priceCOF6;
    private javax.swing.JLabel pricePTY1;
    private javax.swing.JLabel pricePTY2;
    private javax.swing.JLabel pricePTY3;
    private javax.swing.JLabel pricePTY4;
    private javax.swing.JLabel pricePTY5;
    private javax.swing.JLabel pricePTY6;
    private javax.swing.JLabel priceTEA1;
    private javax.swing.JLabel priceTEA2;
    private javax.swing.JLabel priceTEA3;
    private javax.swing.JLabel priceTEA4;
    private javax.swing.JLabel priceTEA5;
    private javax.swing.JLabel priceTEA6;
    private javax.swing.JTabbedPane tpaneMENU;
    private javax.swing.JTextField txtCoffeeName;
    private javax.swing.JTextField txtPastryName;
    private javax.swing.JTextField txtQuanCoffee;
    private javax.swing.JTextField txtQuanPastry;
    private javax.swing.JTextField txtQuanTea;
    private javax.swing.JTextField txtSize;
    private javax.swing.JTextField txtSize2;
    private javax.swing.JTextField txtSize3;
    private javax.swing.JTextField txtTeaName;
    // End of variables declaration//GEN-END:variables

}