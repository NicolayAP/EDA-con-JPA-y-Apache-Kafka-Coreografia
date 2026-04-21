package uptc.edu.co.paymentservice.model;

public class OrderDTO {
    private Long orderId;
    private Double amount;
    // Agrega aquí otros campos si Nicolás los incluyó en su JSON
    
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
}