package co.edu.uptc.shippingservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "shipments")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "customer_document", nullable = false)
    private String customerDocument;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    // SHIPPED o FAILED
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "tracking_code")
    private String trackingCode;

    public Shipment() {}

    public Shipment(Long orderId, String customerDocument, String productName, int quantity) {
        this.orderId = orderId;
        this.customerDocument = customerDocument;
        this.productName = productName;
        this.quantity = quantity;
        this.status = "SHIPPED";
        // Código de rastreo simulado
        this.trackingCode = "TRK-" + orderId + "-" + System.currentTimeMillis();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getCustomerDocument() { return customerDocument; }
    public void setCustomerDocument(String customerDocument) { this.customerDocument = customerDocument; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTrackingCode() { return trackingCode; }
    public void setTrackingCode(String trackingCode) { this.trackingCode = trackingCode; }

    @Override
    public String toString() {
        return "Shipment{id=" + id + ", orderId=" + orderId + ", customer='" + customerDocument
                + "', product='" + productName + "', qty=" + quantity
                + ", status='" + status + "', tracking='" + trackingCode + "'}";
    }
}