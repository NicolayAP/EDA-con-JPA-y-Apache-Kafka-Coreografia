package co.edu.uptc.orderservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_document", nullable = false)
    private String customerDocument;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    // PENDING -> PAYMENT_OK -> SHIPPED
    // PENDING -> CANCELLED  (si falla pago o inventario)
    @Column(name = "status", nullable = false)
    private String status;

    public Order() {}

    public Order(String customerDocument, String productName, int quantity, double totalPrice) {
        this.customerDocument = customerDocument;
        this.productName = productName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.status = "PENDING";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomerDocument() { return customerDocument; }
    public void setCustomerDocument(String customerDocument) { this.customerDocument = customerDocument; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Order{id=" + id + ", customer='" + customerDocument + "', product='" + productName
                + "', qty=" + quantity + ", total=" + totalPrice + ", status='" + status + "'}";
    }
}