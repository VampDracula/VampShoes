package fr.vamp.shoes;

import fr.vamp.shoes.objects.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainPanel extends JPanel {

    DatabaseHandler databaseHandler = null;

    JButton usersButton = new JButton("Utilisateurs");
    JButton suppliersButton = new JButton("Fournisseurs");
    JButton productsButton = new JButton("Produits");
    JButton brandsButton = new JButton("Marques");
    JButton ordersButton = new JButton("Commandes");


    JButton errorButton = new JButton("Mettre en place la base de donnée");
    JPanel errorPanel = new JPanel();
    JLabel errorLabel = new JLabel("");


    JScrollPane scrollPane = new JScrollPane();
    JPanel users = new JPanel();
    JButton userCreateButton = new JButton("Ajouter un utilisateur");
    JPanel suppliers = new JPanel();
    JButton suppliersCreateButton = new JButton("Ajouter un fournisseur");
    JPanel products = new JPanel();
    JButton productsCreateButton = new JButton("Ajouter un produit");
    JPanel brands = new JPanel();
    JButton brandsCreateButton = new JButton("Ajouter une marque");
    JPanel orders = new JPanel();
    JButton ordersCreateButton = new JButton("Ajouter une commande");

    String[] userTableTitle = {"ID", "Nom d'utilisateur", "Mot de passe"};
    JTable userTable = new JTable();

    String[] suppliersTableTitle = {"ID", "Nom", "Contact", "Adresse"};
    JTable suppliersTable = new JTable();

    String[] productsTableTitle = {"ID", "Nom", "Taille", "Couleur", "Prix", "Quantitée", "Fournisseur", "Marque"};
    JTable productsTable = new JTable();

    String[] brandsTableTitle = {"ID", "Nom"};
    JTable brandsTable = new JTable();

    String[] ordersTableTitle = {"ID", "Date", "Produit", "Quantitée", "Status", "Utilisateur"};
    JTable ordersTable = new JTable();

    public MainPanel() {

        databaseHandler = Shoes.getInstance().getDatabaseHandler();

        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {

                    if (ae.getSource() == usersButton) {
                        hideAllSubPanels();
                        databaseHandler.fetchUsers();
                        Object[][] usersData = usersToArray(databaseHandler.getUsers());
                        userTable.setModel(new DefaultTableModel(usersData, userTableTitle) {
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        });
                        scrollPane.setViewportView(userTable);
                        users.setVisible(true);
                    } else if (ae.getSource() == suppliersButton) {
                        hideAllSubPanels();
                        databaseHandler.fetchSuppliers();
                        Object[][] suppliersData = suppliersToArray(databaseHandler.getSuppliers());
                        suppliersTable.setModel(new DefaultTableModel(suppliersData, suppliersTableTitle) {
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        });
                        scrollPane.setViewportView(suppliersTable);
                        suppliers.setVisible(true);
                    }else if (ae.getSource() == productsButton) {
                        hideAllSubPanels();
                        databaseHandler.fetchProducts();
                        Object[][] productsData = productsToArray(databaseHandler.getProducts());
                        productsTable.setModel(new DefaultTableModel(productsData, productsTableTitle) {
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        });
                        scrollPane.setViewportView(productsTable);
                        products.setVisible(true);
                    }else if (ae.getSource() == brandsButton) {
                        hideAllSubPanels();
                        databaseHandler.fetchBrands();
                        Object[][] brandsData = brandsToArray(databaseHandler.getBrands());
                        brandsTable.setModel(new DefaultTableModel(brandsData, brandsTableTitle) {
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        });
                        scrollPane.setViewportView(brandsTable);
                        brands.setVisible(true);
                    }else if (ae.getSource() == ordersButton) {
                        hideAllSubPanels();
                        databaseHandler.fetchOrders();
                        Object[][] ordersData = ordersToArray(databaseHandler.getOrders());
                        ordersTable.setModel(new DefaultTableModel(ordersData, ordersTableTitle));
                        scrollPane.setViewportView(ordersTable);
                        orders.setVisible(true);
                    }
                } catch (SQLException e) {
                    if(e.getMessage().contains("Table") && e.getMessage().contains("doesn't exist"))
                        errorButton.setVisible(true);
                    errorLabel.setText(e.getMessage());
                    hideAllSubPanels();
                    errorPanel.setVisible(true);
                }
                if (ae.getSource() == errorButton) {
                    try {
                        databaseHandler.createTables();
                    } catch (SQLException e) {
                        errorLabel.setText(e.getMessage());
                        hideAllSubPanels();
                        errorPanel.setVisible(true);
                    }
                } else if (ae.getSource() == userCreateButton) {
                    new CreateEntryFrame(DbType.User);
                } else if (ae.getSource() == suppliersCreateButton) {
                    new CreateEntryFrame(DbType.Supplier);
                } else if (ae.getSource() == productsCreateButton) {
                    new CreateEntryFrame(DbType.Product);
                } else if (ae.getSource() == ordersCreateButton) {
                    new CreateEntryFrame(DbType.Order);
                } else if (ae.getSource() == brandsCreateButton) {
                    new CreateEntryFrame(DbType.Brand);
                }
            }
        };

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(220, 221, 225, 255));

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));

        usersButton.addActionListener(al);
        header.add(usersButton);

        suppliersButton.addActionListener(al);
        header.add(suppliersButton);

        productsButton.addActionListener(al);
        header.add(productsButton);

        brandsButton.addActionListener(al);
        header.add(brandsButton);

        ordersButton.addActionListener(al);
        header.add(ordersButton);

        this.add(header);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        userCreateButton.addActionListener(al);
        users.setLayout(new BoxLayout(users, BoxLayout.Y_AXIS));
        users.add(userCreateButton);
        userTable.addMouseListener(new PopClickListener(DbType.User));
        users.add(userTable);

        this.add(users);


        suppliersCreateButton.addActionListener(al);
        suppliers.setLayout(new BoxLayout(suppliers, BoxLayout.Y_AXIS));
        suppliers.add(suppliersCreateButton);
        suppliersTable.addMouseListener(new PopClickListener(DbType.Supplier));
        suppliers.add(suppliersTable);

        this.add(suppliers);

        productsCreateButton.addActionListener(al);
        products.setLayout(new BoxLayout(products, BoxLayout.Y_AXIS));
        products.add(productsCreateButton);
        productsTable.addMouseListener(new PopClickListener(DbType.Product));
        products.add(productsTable);

        this.add(products);

        brandsCreateButton.addActionListener(al);
        brands.setLayout(new BoxLayout(brands, BoxLayout.Y_AXIS));
        brands.add(brandsCreateButton);
        brandsTable.addMouseListener(new PopClickListener(DbType.Brand));
        brands.add(brandsTable);

        this.add(brands);

        ordersCreateButton.addActionListener(al);
        orders.setLayout(new BoxLayout(orders, BoxLayout.Y_AXIS));
        orders.add(ordersCreateButton);
        ordersTable.addMouseListener(new PopClickListener(DbType.Order));
        orders.add(ordersTable);

        this.add(orders);


        this.add(scrollPane);

        errorPanel.setLayout(new BorderLayout());
        errorButton.addActionListener(al);
        errorPanel.add(errorLabel, BorderLayout.CENTER);
        errorPanel.add(errorButton, BorderLayout.AFTER_LAST_LINE);
        this.add(errorPanel);

        hideAllSubPanels();
    }

    private void hideAllSubPanels() {
        errorPanel.setVisible(false);
        users.setVisible(false);
        suppliers.setVisible(false);
        products.setVisible(false);
        brands.setVisible(false);
        orders.setVisible(false);
    }
    private Object[][] usersToArray(HashMap<Integer, User> users) {

        Object[][] array = new Object[users.size()][3];
        int count = 0;
        for (User value : users.values()) {
            array[count][0] = value.getId();
            array[count][1] = value.getUsername();
            array[count][2] = value.getPassword();
            count++;
        }

        return array;
    }
    private Object[][] suppliersToArray(HashMap<Integer, Supplier> suppliers) {

        Object[][] array = new Object[suppliers.size()][4];
        int count = 0;
        for (Supplier value : suppliers.values()) {
            array[count][0] = value.getId();
            array[count][1] = value.getName();
            array[count][2] = value.getContact();
            array[count][3] = value.getAddress();
            count++;
        }

        return array;
    }
    private Object[][] productsToArray(HashMap<Integer, Product> products) {

        Object[][] array = new Object[products.size()][8];
        int count = 0;
        for (Product value : products.values()) {
            array[count][0] = value.getId();
            array[count][1] = value.getName();
            array[count][2] = value.getSize();
            array[count][3] = value.getColor();
            array[count][4] = value.getPrice();
            array[count][5] = value.getQuantity();
            array[count][6] = value.getSupplier().getName();
            array[count][7] = value.getBrand().getName();
            count++;
        }

        return array;
    }
    private Object[][] ordersToArray(HashMap<Integer, Order> orders) {

        Object[][] array = new Object[orders.size()][6];
        int count = 0;
        for (Order value : orders.values()) {
            array[count][0] = value.getId();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(value.getTimestamp());
            array[count][1] = sf.format(date);
            array[count][2] = value.getProduct().getName();
            array[count][3] = value.getQuantity();
            array[count][4] = OrderStatus.values()[value.getStatus()].name();
            array[count][5] = value.getUser().getUsername();
            count++;
        }

        return array;
    }
    private Object[][] brandsToArray(HashMap<Integer, Brand> brands) {

        Object[][] array = new Object[brands.size()][2];
        int count = 0;
        for (Brand value : brands.values()) {
            array[count][0] = value.getId();
            array[count][1] = value.getName();
            count++;
        }

        return array;
    }
    class PopUpMenu extends JPopupMenu {
        JMenuItem editItem;
        JMenuItem deleteItem;
        public PopUpMenu(DbType type) {
            editItem = new JMenuItem("Modifier");
            deleteItem = new JMenuItem("Supprimer");

            ActionListener al = new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    int id = -1;

                    String table = "null";

                    if(type == DbType.User) {
                        id = (int)userTable.getValueAt(userTable.getSelectedRow(), 0);
                        table = "users";
                    } else if(type == DbType.Supplier) {
                        id = (int)suppliersTable.getValueAt(suppliersTable.getSelectedRow(), 0);
                        table = "suppliers";
                    } else if(type == DbType.Product) {
                        id = (int)productsTable.getValueAt(productsTable.getSelectedRow(), 0);
                        table = "products";
                    } else if(type == DbType.Order) {
                        id = (int)ordersTable.getValueAt(ordersTable.getSelectedRow(), 0);
                        table = "orders";
                    } else if(type == DbType.Brand) {
                        id = (int)brandsTable.getValueAt(brandsTable.getSelectedRow(), 0);
                        table = "brands";
                    }

                    if (ae.getSource() == deleteItem) {
                        int dialogResult = JOptionPane.showConfirmDialog (
                                null,
                                String.format(
                                        "Êtes-vous sur de vouloir supprimer le %s avec l'ID %d ?",
                                        type.name(),
                                        id
                                ),
                                "Supression",
                                JOptionPane.YES_NO_OPTION
                        );
                        if(dialogResult == JOptionPane.YES_OPTION){
                            Statement stmt = null;
                            try {
                                stmt = Shoes.getInstance().getDatabaseHandler().getConnection().createStatement();
                                int res = stmt.executeUpdate(
                                        String.format(
                                                "DELETE FROM %s WHERE id=%d",
                                                table,
                                                id
                                        )
                                );
                                javax.swing.JOptionPane.showMessageDialog(null,
                                        String.format("%d ligne supprimée(s)", res)
                                );
                            } catch (SQLException | NumberFormatException e) {
                                javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
                            }
                        }

                    } else if (ae.getSource() == editItem) {
                        new EditEntryFrame(type, id);
                    }
                }
            };

            deleteItem.addActionListener(al);
            editItem.addActionListener(al);

            this.add(editItem);
            this.add(deleteItem);
        }
    }
    class PopClickListener extends MouseAdapter {

        private DbType type;
        public PopClickListener(DbType type) {
            this.type = type;
        }

        public void mousePressed(MouseEvent e) {
            if (e.isPopupTrigger())
                doPop(e);
        }

        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger())
                doPop(e);
        }

        private void doPop(MouseEvent e) {
            PopUpMenu menu = new PopUpMenu(this.type);
            menu.show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
