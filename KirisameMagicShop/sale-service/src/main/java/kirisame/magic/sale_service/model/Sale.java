package kirisame.magic.sale_service.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    private List<SaleItem> items;
    
    @Column(nullable = false)
    private Double total;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fecha;
    
    // Constructors
    public Sale() {
        this.fecha = new Date();
    }
    
    public Sale(Long userId, List<SaleItem> items, Double total) {
        this.userId = userId;
        this.items = items;
        this.total = total;
        this.fecha = new Date();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public List<SaleItem> getItems() { return items; }
    public void setItems(List<SaleItem> items) { this.items = items; }
    
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    
    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", userId=" + userId +
                ", items=" + items +
                ", total=" + total +
                ", fecha=" + fecha +
                '}';
    }
}