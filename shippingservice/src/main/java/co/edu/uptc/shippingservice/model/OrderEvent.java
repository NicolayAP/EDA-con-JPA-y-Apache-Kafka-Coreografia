package co.edu.uptc.shippingservice.model;

// DTO compartido: misma estructura que usa Order Service en sus eventos
public class OrderEvent {

    private Long orderId;
    private String customerDocument;
    private String productName;
    private int quantity;
    private double totalPrice;
    private String status;

    public OrderEvent() {}

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
                + "', product='" + productName + "', qty=" + quantity + ", status='" + status + "'}";
    }
}