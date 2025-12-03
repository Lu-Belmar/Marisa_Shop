package kirisame.magic.product_service.repository;

import kirisame.magic.product_service.model.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSize, Long> {
    // Buscar una talla específica de un producto por su nombre
    Optional<ProductSize> findByProductIdAndName(Long productId, String name);
}