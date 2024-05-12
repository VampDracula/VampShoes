package fr.vamp.shoes;

import fr.vamp.shoes.objects.*;

import java.sql.*;
import java.util.HashMap;

public class DatabaseHandler {

    private Connection connection = null;
    private boolean isConnected = false;

    private HashMap<Integer, User> users = new HashMap<Integer, User>();
    private HashMap<Integer, Supplier> suppliers = new HashMap<Integer, Supplier>();
    private HashMap<Integer, Order> orders = new HashMap<Integer, Order>();
    private HashMap<Integer, Product> products = new HashMap<Integer, Product>();
    private HashMap<Integer, Brand> brands = new HashMap<Integer, Brand>();

    public DatabaseHandler() {
    }

    public void connectToDb(String host, String dbname, String username, String password) throws SQLException {
        this.connection = DriverManager.getConnection(
                String.format("jdbc:mysql://%s/%s?user=%s&password=%s", host, dbname, username, password)
        );

        isConnected = this.connection.isValid(5);
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void fetchUsers() throws SQLException {
        Statement stmt = this.connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users");

        users.clear();
        while (rs.next()) {
            int id = rs.getInt("id");
            String username = rs.getString("username");
            String password = rs.getString("password");

            User user = new User(id, username, password);


            users.put(id, user);
        }
    }
    public void fetchSuppliers() throws SQLException {
        Statement stmt = this.connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM suppliers");

        suppliers.clear();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String contact = rs.getString("contact");
            String address = rs.getString("address");

            Supplier supplier = new Supplier(id, name, contact, address);

            suppliers.put(id, supplier);
        }
    }
    public void fetchProducts() throws SQLException {
        fetchSuppliers();
        fetchBrands();

        Statement stmt = this.connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM products");

        products.clear();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String size = rs.getString("size");
            String color = rs.getString("color");
            float price = rs.getFloat("price");
            int quantity = rs.getInt("quantity");
            Supplier supplier = suppliers.get(rs.getInt("supplierId"));
            Brand brand = brands.get(rs.getInt("brandId"));

            Product product = new Product(id, name, size, color, price, quantity, supplier, brand);

            products.put(id, product);
        }
    }
    public void fetchOrders() throws SQLException {
        fetchProducts();
        fetchUsers();

        Statement stmt = this.connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM orders");

        orders.clear();
        while (rs.next()) {
            int id = rs.getInt("id");
            long timestamp = rs.getLong("timestamp");
            Product product = products.get(rs.getInt("productId"));
            int quantity = rs.getInt("quantity");
            int status = rs.getInt("status");
            User user = users.get(rs.getInt("userId"));

            Order order = new Order(id, timestamp, product, quantity, status, user);

            orders.put(id, order);
        }
    }
    public void fetchBrands() throws SQLException {

        Statement stmt = this.connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM brands");

        brands.clear();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");

            Brand brand = new Brand(id, name);

            brands.put(id, brand);
        }
    }

    public void createTables() throws SQLException {
        Statement stmt = this.connection.createStatement();
        stmt.executeUpdate("CREATE TABLE users\n" +
                "(\n" +
                "    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n" +
                "    username VARCHAR(16),\n" +
                "    password VARCHAR(32)\n" +
                ")");
        stmt = this.connection.createStatement();
        stmt.executeUpdate("CREATE TABLE products\n" +
                "(\n" +
                "    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n" +
                "    name VARCHAR(64),\n" +
                "    size VARCHAR(32),\n" +
                "    color VARCHAR(32),\n" +
                "    price FLOAT,\n" +
                "    quantity INT,\n" +
                "    supplierId INT,\n" +
                "    brandId INT\n" +
                ")");
        stmt = this.connection.createStatement();
        stmt.executeUpdate("CREATE TABLE orders\n" +
                "(\n" +
                "    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n" +
                "    timestamp BIGINT,\n" +
                "    productId INT,\n" +
                "    quantity INT,\n" +
                "    status INT,\n" +
                "    userId INT\n" +
                ")");
        stmt = this.connection.createStatement();
        stmt.executeUpdate("CREATE TABLE suppliers\n" +
                "(\n" +
                "    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n" +
                "    name VARCHAR(64),\n" +
                "    contact VARCHAR(64),\n" +
                "    address VARCHAR(64)\n" +
                ")");
        stmt = this.connection.createStatement();
        stmt.executeUpdate("CREATE TABLE brands\n" +
                "(\n" +
                "    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n" +
                "    name VARCHAR(64)\n" +
                ")");
    }

    public HashMap<Integer, User> getUsers() {
        return users;
    }

    public HashMap<Integer, Supplier> getSuppliers() {
        return suppliers;
    }

    public HashMap<Integer, Order> getOrders() {
        return orders;
    }

    public HashMap<Integer, Product> getProducts() {
        return products;
    }

    public HashMap<Integer, Brand> getBrands() {
        return brands;
    }
}
