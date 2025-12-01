package kirisame.magic.sale_service.service;

import kirisame.magic.sale_service.model.Sale;
import kirisame.magic.sale_service.model.SaleItem;
import kirisame.magic.sale_service.model.Product; // Importante
import kirisame.magic.sale_service.repository.saleRepository;
import kirisame.magic.sale_service.repository.productRepository; // Importante
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class saleService {

    @Autowired
    private saleRepository SaleRepository;
    
    @Autowired
    private productRepository ProductRepository; // Inyectamos el repo de productos

    public List<Sale> getAllSales() {
        return SaleRepository.findAll();
    }

    public Optional<Sale> getSaleById(Long id) {
        return SaleRepository.findById(id);
    }

    public List<Sale> getSalesByUserId(Long userId) {
        return SaleRepository.findByUserId(userId);
    }

    // Añadimos @Transactional para que si algo falla, se revierta todo (incluida la resta de stock)
    @Transactional
    public Sale createSale(Sale sale) throws Exception { // Añadimos throws Exception
        // 1. Validar y Restar Stock
        if (sale.getItems() != null) {
            for (SaleItem item : sale.getItems()) {
                // Buscamos el producto en la BD
                Product product = ProductRepository.findById(item.getProductId())
                    .orElseThrow(() -> new Exception("Producto no encontrado ID: " + item.getProductId()));

                // Verificamos si hay suficiente stock
                if (product.getStock() < item.getQuantity()) {
                    throw new Exception("Stock insuficiente para el producto ID: " + item.getProductId());
                }

                // Restamos el stock
                product.setStock(product.getStock() - item.getQuantity());
                
                // Guardamos el producto actualizado
                ProductRepository.save(product);
                
                // Calculamos subtotal del item para la venta
                item.calculateSubtotal();
            }
        }

        // 2. Calcular total de la venta si no viene
        if (sale.getTotal() == null || sale.getTotal() == 0) {
            double total = sale.getItems().stream()
                .mapToDouble(SaleItem::getSubtotal)
                .sum();
            sale.setTotal(total);
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