package kirisame.magic.sale_service.repository;

import kirisame.magic.sale_service.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface saleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByUserId(Long userId);
    
    @Query("SELECT s FROM Sale s WHERE s.fecha BETWEEN :startDate AND :endDate")
    List<Sale> findSalesByDateRange(@Param("startDate") java.util.Date startDate, 
                                   @Param("endDate") java.util.Date endDate);
    
    @Query("SELECT SUM(s.total) FROM Sale s WHERE s.userId = :userId")
    Double getTotalSalesByUser(@Param("userId") Long userId);
}