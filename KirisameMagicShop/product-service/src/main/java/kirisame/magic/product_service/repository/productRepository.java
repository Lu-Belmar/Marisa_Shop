package kirisame.magic.product_service.repository;

import kirisame.magic.product_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface productRepository extends JpaRepository<Product, Long> {
    List<Product> findByNombreContainingIgnoreCase(String nombre);
    List<Product> findByPrecioBetween(Double minPrecio, Double maxPrecio);
    List<Product> findByStockGreaterThan(Integer stock);
}