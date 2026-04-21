package uptc.edu.co.inventoryservice.model;

public class OrderDTO {
    private Long orderId;
    private Double amount;
    private Long productId;
    
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
}