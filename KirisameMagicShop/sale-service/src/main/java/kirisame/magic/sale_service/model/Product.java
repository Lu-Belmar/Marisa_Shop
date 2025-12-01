package kirisame.magic.sale_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    private Long id;
    
    @Column(nullable = false)
    private Integer stock;

    // Solo necesitamos Id y Stock para esta operación
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}