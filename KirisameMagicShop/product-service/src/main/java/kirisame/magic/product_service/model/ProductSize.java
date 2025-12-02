package kirisame.magic.product_service.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "product_sizes")
public class ProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Ej: "S", "M", "42"

    @Column(nullable = false)
    private Integer stock; // Stock específico de esta talla

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonBackReference
    private Product product;

    // Constructores
    public ProductSize() {}

    public ProductSize(String name, Integer stock, Product product) {
        this.name = name;
        this.stock = stock;
        this.product = product;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}