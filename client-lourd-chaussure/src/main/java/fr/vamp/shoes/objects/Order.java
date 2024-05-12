package fr.vamp.shoes.objects;

public class Order {
    private int id;
    private long timestamp;
    private Product product;
    private int quantity;
    private int status;
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order(int id, long timestamp, Product product, int quantity, int status, User user) {
        this.id = id;
        this.timestamp = timestamp;
        this.product = product;
        this.quantity = quantity;
        this.status = status;
        this.user = user;
    }
}
