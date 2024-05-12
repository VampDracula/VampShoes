package fr.vamp.shoes;

import fr.vamp.shoes.objects.Brand;
import fr.vamp.shoes.objects.Order;
import fr.vamp.shoes.objects.Product;
import fr.vamp.shoes.objects.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;

public class EditEntryFrame extends JFrame {
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

    JButton submitButton = new JButton("Éditer");

    public EditEntryFrame(DbType dbType, int id) throws HeadlessException {
        this.dbType = dbType;

        this.setTitle(String.format("Édition d'un %s", dbType.name()));
        this.setSize(250, 400);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setBackground(new Color(255, 255, 255, 255));
        this.setContentPane(new EditEntryPanel(dbType, id));
        this.setVisible(true);
        this.setResizable(false);

        if(dbType == DbType.User) {
            User user = Shoes.getInstance().getDatabaseHandler().getUsers().get(id);

            userUsernameField.setText(user.getUsername());
            userPasswordField.setText(user.getPassword());
        } else if(dbType == DbType.Brand) {
            Brand brand = Shoes.getInstance().getDatabaseHandler().getBrands().get(id);

            brandNameField.setText(brand.getName());
        } else if(dbType == DbType.Order) {
            Order order = Shoes.getInstance().getDatabaseHandler().getOrders().get(id);

            orderProductIdField.setText(String.valueOf(order.getProduct().getId()));
            orderQuantityField.setText(String.valueOf(order.getQuantity()));
            orderStatusCombo.setSelectedItem(order.getStatus());
            orderUserIdField.setText(String.valueOf(order.getUser().getId()));
        } else if(dbType == DbType.Product) {
            Product product = Shoes.getInstance().getDatabaseHandler().getProducts().get(id);

            productNameField.setText(product.getName());
            productSizeField.setText(product.getSize());
            productColorField.setText(product.getSize());
            productPriceField.setText(String.valueOf(product.getPrice()));
            productQuantityField.setText(String.valueOf(product.getQuantity()));
            productSupplierField.setText(String.valueOf(product.getSupplier().getId()));
            productBrandField.setText(String.valueOf(product.getBrand().getId()));
        }

    }

    class EditEntryPanel extends JPanel {
        public EditEntryPanel(DbType dbType, int id) {
            ActionListener al = new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    if (ae.getSource() == submitButton) {
                        if (dbType == DbType.User) {
                            Statement stmt = null;
                            try {
                                stmt = Shoes.getInstance().getDatabaseHandler().getConnection().createStatement();
                                int res = stmt.executeUpdate(
                                        String.format(
                                                "UPDATE users SET username = '%s', password = '%s' WHERE id=%d",
                                                userUsernameField.getText(),
                                                userPasswordField.getText(),
                                                id
                                        )
                                );
                                JOptionPane.showMessageDialog(null,
                                        String.format("%d ligne éditée(s)", res)
                                );
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(null, e.getMessage());
                            }
                        } else if (dbType == DbType.Brand) {
                            Statement stmt = null;
                            try {
                                stmt = Shoes.getInstance().getDatabaseHandler().getConnection().createStatement();
                                int res = stmt.executeUpdate(
                                        String.format(
                                                "UPDATE brands SET name = '%s' WHERE id=%d",
                                                brandNameField.getText(),
                                                id
                                        )
                                );
                                JOptionPane.showMessageDialog(null,
                                        String.format("%d ligne éditée(s)", res)
                                );
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(null, e.getMessage());
                            }
                        } else if (dbType == DbType.Order) {
                            Statement stmt = null;
                            try {
                                stmt = Shoes.getInstance().getDatabaseHandler().getConnection().createStatement();
                                int res = stmt.executeUpdate(
                                        String.format(
                                                "UPDATE orders SET timestamp = %d, productId = %d, quantity = %d, status = %d, userId = %d WHERE id=%d",
                                                Shoes.getInstance().getDatabaseHandler().getOrders().get(id).getTimestamp(),
                                                Integer.parseInt(orderProductIdField.getText()),
                                                Integer.parseInt(orderQuantityField.getText()),
                                                Arrays.asList(OrderStatus.values()).indexOf((OrderStatus) orderStatusCombo.getSelectedItem()),
                                                Integer.parseInt(orderUserIdField.getText()),
                                                id
                                        )
                                );
                                JOptionPane.showMessageDialog(null,
                                        String.format("%d ligne éditée(s)", res)
                                );
                            } catch (SQLException | NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, e.getMessage());
                            }
                        } else if (dbType == DbType.Product) {
                            Statement stmt = null;
                            try {
                                stmt = Shoes.getInstance().getDatabaseHandler().getConnection().createStatement();
                                int res = stmt.executeUpdate(
                                        String.format(
                                                "UPDATE products SET name = '%s', size = '%s', color = '%s', price = %f, quantity = '%d', supplierId = %d, brandId= %d WHERE id=%d",
                                                productNameField.getText(),
                                                productSizeField.getText(),
                                                productColorField.getText(),
                                                Float.parseFloat(productPriceField.getText()),
                                                Integer.parseInt(productQuantityField.getText()),
                                                Integer.parseInt(productSupplierField.getText()),
                                                Integer.parseInt(productBrandField.getText()),
                                                id
                                        )
                                );
                                JOptionPane.showMessageDialog(null,
                                        String.format("%d ligne éditée(s)", res)
                                );
                            } catch (SQLException | NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, e.getMessage());
                            }
                        } else if (dbType == DbType.Supplier) {
                            Statement stmt = null;
                            try {
                                stmt = Shoes.getInstance().getDatabaseHandler().getConnection().createStatement();
                                int res = stmt.executeUpdate(
                                        String.format(
                                                "UPDATE suppliers SET name = '%s', contact = '%s', address = '%s' WHERE id=%d",
                                                supplierNameField.getText(),
                                                supplierContactField.getText(),
                                                supplierAddressField.getText(),
                                                id
                                        )
                                );
                                JOptionPane.showMessageDialog(null,
                                        String.format("%d ligne éditée(s)", res)
                                );
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(null, e.getMessage());
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
