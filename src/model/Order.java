package model;

import java.util.Date;
import java.util.Map;

public class Order {
    private int id;
    private int userId;
    private Map<Integer, Integer> products; // productId -> quantity
    private double totalPrice;
    private String status; // pending, processing, shipped, delivered
    private Date orderDate;
    private Date deliveryDate;
    private String shippingAddress;

    public Order(int id, int userId, Map<Integer, Integer> products, double totalPrice, 
                 String status, Date orderDate, String shippingAddress) {
        this.id = id;
        this.userId = userId;
        this.products = products;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Map<Integer, Integer> getProducts() { return products; }
    public void setProducts(Map<Integer, Integer> products) { this.products = products; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public Date getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(Date deliveryDate) { this.deliveryDate = deliveryDate; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
}