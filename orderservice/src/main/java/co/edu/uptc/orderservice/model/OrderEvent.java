package co.edu.uptc.orderservice.model;

// DTO que viaja dentro de los mensajes Kafka entre microservicios
public class OrderEvent {

    private Long orderId;
    private String customerDocument;
    private String productName;
    private int quantity;
    private double totalPrice;
    private String status;

    public OrderEvent() {}

    public OrderEvent(Long orderId, String customerDocument, String productName,
                      int quantity, double totalPrice, String status) {
        this.orderId = orderId;
        this.customerDocument = customerDocument;
        this.productName = productName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

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
        return "OrderEvent{orderId=" + orderId + ", customer='" + customerDocument
                + "', product='" + productName + "', qty=" + quantity
                + ", total=" + totalPrice + ", status='" + status + "'}";
    }
}