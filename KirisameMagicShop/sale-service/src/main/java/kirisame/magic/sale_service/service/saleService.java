package kirisame.magic.sale_service.service;

import kirisame.magic.sale_service.model.Sale;
import kirisame.magic.sale_service.model.SaleItem;
import kirisame.magic.sale_service.repository.saleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class saleService {

    @Autowired
    private saleRepository SaleRepository;

    public List<Sale> getAllSales() {
        return SaleRepository.findAll();
    }

    public Optional<Sale> getSaleById(Long id) {
        return SaleRepository.findById(id);
    }

    public List<Sale> getSalesByUserId(Long userId) {
        return SaleRepository.findByUserId(userId);
    }

    public Sale createSale(Sale sale) {
        // Calculate total if not provided
        if (sale.getTotal() == null || sale.getTotal() == 0) {
            double total = sale.getItems().stream()
                .mapToDouble(SaleItem::getSubtotal)
                .sum();
            sale.setTotal(total);
        }
        
        // Set sale reference for each item
        if (sale.getItems() != null) {
            for (SaleItem item : sale.getItems()) {
                item.calculateSubtotal();
            }
        }
        
        return SaleRepository.save(sale);
    }

    public List<Sale> getSalesByDateRange(Date startDate, Date endDate) {
        return SaleRepository.findSalesByDateRange(startDate, endDate);
    }

    public Double getTotalSalesByUser(Long userId) {
        Double total = SaleRepository.getTotalSalesByUser(userId);
        return total != null ? total : 0.0;
    }

    public long getTotalSalesCount() {
        return SaleRepository.count();
    }
}