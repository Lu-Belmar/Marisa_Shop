package kirisame.magic.sale_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sale_items")
public class SaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "product_id", nullable = false)
    private Long productId;
    
    @Column(nullable = false)
    private String productName;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(name = "product_size") // Nombre de la columna en DB
    private String size;

    @Column(nullable = false)
    private Double price;
    
    @Column(nullable = false)
    private Double subtotal;
    
    // Constructors
    public SaleItem() {}
    
    public SaleItem(Long productId, String productName, Integer quantity, Double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = price * quantity;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { 
        this.quantity = quantity;
        this.subtotal = this.price * quantity;
    }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { 
        this.price = price;
        this.subtotal = price * this.quantity;
    }
    
    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
    
    @PrePersist
    @PreUpdate
    public void calculateSubtotal() {
        this.subtotal = this.price * this.quantity;
    }
    
    @Override
    public String toString() {
        return "SaleItem{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", subtotal=" + subtotal +
                '}';
    }
}