package kirisame.magic.sale_service.repository;

import kirisame.magic.sale_service.model.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface productSizeRepository extends JpaRepository<ProductSize, Long> {
    Optional<ProductSize> findByProductIdAndName(Long productId, String name);
}