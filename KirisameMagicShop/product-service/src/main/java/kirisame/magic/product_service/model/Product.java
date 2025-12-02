package kirisame.magic.product_service.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    private String descripcion; // Desc
    
    @Column(nullable = false)
    private Double precio;
    
    @Column(nullable = false)
    private Integer stock; // Cantidad

    // --- NUEVOS CAMPOS ---
    private String category; // Categoria


    // Constructors
    public Product() {}

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<ProductSize> sizes = new ArrayList<>();

    // Getters and Setters (Asegúrate de generarlos para los nuevos campos)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public List<ProductSize> getSizes() { return sizes; }
    public void setSize(String size) { this.sizes = sizes; }
    public List<ProductImage> getImages() { return images; }
    public void setImages(List<ProductImage> newImages) {
        // 1. Inicializar si es null (por seguridad)
        if (this.images == null) {
            this.images = new ArrayList<>();
        }

        this.images.clear();

        if (newImages != null) {
            this.images.addAll(newImages);
            for (ProductImage img : newImages) {
                img.setProduct(this); // Vincula la imagen al producto padre
            }
        }
    }

    public void setSizes(List<ProductSize> newSizes) {
        if (this.sizes == null) {
            this.sizes = new ArrayList<>();
        }
        this.sizes.clear();
        if (newSizes != null) {
            this.sizes.addAll(newSizes);
            for (ProductSize s : newSizes) {
                s.setProduct(this);
            }
        }
        recalculateTotalStock(); // Actualizar el total al setear tallas
    }

    // Método mágico para actualizar el stock total
    public void recalculateTotalStock() {
        if (this.sizes == null || this.sizes.isEmpty()) {
            // Si no hay tallas, mantenemos el stock manual o 0
            if (this.stock == null) this.stock = 0;
        } else {
            // Sumar el stock de todas las tallas
            this.stock = this.sizes.stream()
                .mapToInt(ProductSize::getStock)
                .sum();
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", category='" + category + '\'' +
                ", size='" + sizes + '\'' +
                '}';
    }
}