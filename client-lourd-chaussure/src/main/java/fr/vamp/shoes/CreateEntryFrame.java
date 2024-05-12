package fr.vamp.shoes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;

public class CreateEntryFrame extends JFrame {
    private DbType dbType;

    JTextField userUsernameField = new JTextField();
    JTextField userPasswordField = new JTextField();

    JTextField brandNameField = new JTextField();

    JTextField orderProductIdField = new JTextField();
    JTextField orderQuantityField = new JTextField();
    JComboBox<OrderStatus> orderStatusCombo = new JComboBox<OrderStatus>();
    JTextField orderUserIdField = new JTextField();

    JTextField productNameField = new JTextField();
    JTextField productSizeField = new JTextField();
    JTextField productColorField = new JTextField();
    JTextField productPriceField = new JTextField();
    JTextField productQuantityField = new JTextField();
    JTextField productSupplierField = new JTextField();
    JTextField productBrandField = new JTextField();

    JTextField supplierNameField = new JTextField();
    JTextField supplierContactField = new JTextField();
    JTextField supplierAddressField = new JTextField();

    JButton submitButton = new JButton("Créer");

    public CreateEntryFrame(DbType dbType) throws HeadlessException {
        this.dbType = dbType;

        this.setTitle(String.format("Création d'un %s", dbType.name()));
        this.setSize(250, 400);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setBackground(new Color(255, 255, 255, 255));
        this.setContentPane(new CreateEntryPanel(dbType));
        this.setVisible(true);
        this.setResizable(false);

    }

    class CreateEntryPanel extends JPanel {
        public CreateEntryPanel(DbType dbType) {
            ActionListener al = new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    if (ae.getSource() == submitButton) {
                        if (dbType == DbType.User) {
                            Statement stmt = null;
                            try {
                                stmt = Shoes.getInstance().getDatabaseHandler().getConnection().createStatement();
                                int res = stmt.executeUpdate(
                                        String.format(
                                                "INSERT INTO users (username, password) VALUES ('%s', '%s')",
                                                userUsernameField.getText(),
                                                userPasswordField.getText()
                                        )
                                );
                                javax.swing.JOptionPane.showMessageDialog(null,
                                        String.format("%d ligne ajoutée(s)", res)
                                );
                            } catch (SQLException e) {
                                javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
                            }
                        } else if (dbType == DbType.Brand) {
                            Statement stmt = null;
                            try {
                                stmt = Shoes.getInstance().getDatabaseHandler().getConnection().createStatement();
                                int res = stmt.executeUpdate(
                                        String.format(
                                                "INSERT INTO brands (name) VALUES ('%s')",
                                                brandNameField.getText()
                                        )
                                );
                                javax.swing.JOptionPane.showMessageDialog(null,
                                        String.format("%d ligne ajoutée(s)", res)
                                );
                            } catch (SQLException e) {
                                javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
                            }
                        } else if (dbType == DbType.Order) {
                            Statement stmt = null;
                            try {
                                stmt = Shoes.getInstance().getDatabaseHandler().getConnection().createStatement();
                                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                                int res = stmt.executeUpdate(
                                        String.format(
                                                "INSERT INTO orders (timestamp, productId, quantity, status, userId) VALUES (%d, %d, %d, %d, %d)",
                                                timestamp.getTime(),
                                                Integer.parseInt(orderProductIdField.getText()),
                                                Integer.parseInt(orderQuantityField.getText()),
                                                Arrays.asList(OrderStatus.values()).indexOf((OrderStatus) orderStatusCombo.getSelectedItem()),
                                                Integer.parseInt(orderUserIdField.getText())
                                        )
                                );
                                javax.swing.JOptionPane.showMessageDialog(null,
                                        String.format("%d ligne ajoutée(s)", res)
                                );
                            } catch (SQLException | NumberFormatException e) {
                                javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
                            }
                        } else if (dbType == DbType.Product) {
                            Statement stmt = null;
                            try {
                                stmt = Shoes.getInstance().getDatabaseHandler().getConnection().createStatement();
                                int res = stmt.executeUpdate(
                                        String.format(
                                                "INSERT INTO products (name, size, color, price, quantity, supplierId, brandId) VALUES ('%s', '%s', '%s', %f, %d, %d, %d)",
                                                productNameField.getText(),
                                                productSizeField.getText(),
                                                productColorField.getText(),
                                                Float.parseFloat(productPriceField.getText()),
                                                Integer.parseInt(productQuantityField.getText()),
                                                Integer.parseInt(productSupplierField.getText()),
                                                Integer.parseInt(productBrandField.getText())
                                        )
                                );
                                javax.swing.JOptionPane.showMessageDialog(null,
                                        String.format("%d ligne ajoutée(s)", res)
                                );
                            } catch (SQLException | NumberFormatException e) {
                                javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
                            }
                        } else if (dbType == DbType.Supplier) {
                            Statement stmt = null;
                            try {
                                stmt = Shoes.getInstance().getDatabaseHandler().getConnection().createStatement();
                                int res = stmt.executeUpdate(
                                        String.format(
                                                "INSERT INTO suppliers (name, contact, address) VALUES ('%s', '%s', '%s')",
                                                supplierNameField.getText(),
                                                supplierContactField.getText(),
                                                supplierAddressField.getText()
                                        )
                                );
                                javax.swing.JOptionPane.showMessageDialog(null,
                                        String.format("%d ligne ajoutée(s)", res)
                                );
                            } catch (SQLException e) {
                                javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
                            }
                        }
                    }
                }
            };

            submitButton.addActionListener(al);

            if (dbType == DbType.User) {
                this.setLayout(new GridLayout(5, 1));
                this.add(new JLabel("Nom d'utilisateur"));
                this.add(userUsernameField);
                this.add(new JLabel("Mot de passe"));
                this.add(userPasswordField);
                this.add(submitButton);
            } else if (dbType == DbType.Brand) {
                this.setLayout(new GridLayout(3, 1));
                this.add(new JLabel("Nom de la marque"));
                this.add(brandNameField);
                this.add(submitButton);
            } else if (dbType == DbType.Order) {
                this.setLayout(new GridLayout(9, 1));
                this.add(new JLabel("ID Produit"));
                this.add(orderProductIdField);
                this.add(new JLabel("Quantitée"));
                this.add(orderQuantityField);
                this.add(new JLabel("Status"));
                for (OrderStatus status : OrderStatus.values()) {
                    orderStatusCombo.addItem(status);
                }
                this.add(orderStatusCombo);
                this.add(new JLabel("ID Utilisateur"));
                this.add(orderUserIdField);
                this.add(submitButton);
            } else if (dbType == DbType.Product) {
                this.setLayout(new GridLayout(15, 1));
                this.add(new JLabel("Nom"));
                this.add(productNameField);
                this.add(new JLabel("Taille"));
                this.add(productSizeField);
                this.add(new JLabel("Couleur"));
                this.add(productColorField);
                this.add(new JLabel("Prix"));
                this.add(productPriceField);
                this.add(new JLabel("Quantitée"));
                this.add(productQuantityField);
                this.add(new JLabel("ID Fournisseur"));
                this.add(productSupplierField);
                this.add(new JLabel("ID Marque"));
                this.add(productBrandField);
                this.add(submitButton);
            } else if (dbType == DbType.Supplier) {
                this.setLayout(new GridLayout(7, 1));
                this.add(new JLabel("Nom"));
                this.add(supplierNameField);
                this.add(new JLabel("Contact"));
                this.add(supplierContactField);
                this.add(new JLabel("Adresse"));
                this.add(supplierAddressField);
                this.add(submitButton);
            }
        }
    }
}
