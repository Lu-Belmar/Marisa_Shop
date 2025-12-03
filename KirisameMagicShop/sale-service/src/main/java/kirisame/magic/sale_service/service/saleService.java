package kirisame.magic.sale_service.service;

import kirisame.magic.sale_service.model.Sale;
import kirisame.magic.sale_service.model.SaleItem;
import kirisame.magic.sale_service.model.Product; // Importante
import kirisame.magic.sale_service.repository.saleRepository;
import kirisame.magic.sale_service.repository.productRepository; // Importante
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante
import kirisame.magic.sale_service.model.ProductSize;
import kirisame.magic.sale_service.repository.productSizeRepository;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class saleService {

    @Autowired
    private saleRepository SaleRepository;
    
    @Autowired
    private productRepository ProductRepository; // Inyectamos el repo de productos

    @Autowired
    private productSizeRepository ProductSizeRepository;
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
 @Transactional(rollbackFor = Exception.class) // Importante: rollback si falla stock
    public Sale createSale(Sale sale) throws Exception {
        if (sale.getItems() != null) {
            for (SaleItem item : sale.getItems()) {
                // 1. Obtener el producto principal (Padre)
                Product product = ProductRepository.findById(item.getProductId())
                    .orElseThrow(() -> new Exception("Producto no encontrado ID: " + item.getProductId()));

                // 2. Lógica de Stock por Talla
                if (item.getSize() != null && !item.getSize().isEmpty()) {
                    // Buscar la talla específica
                    ProductSize sizeEntity = ProductSizeRepository.findByProductIdAndName(item.getProductId(), item.getSize())
                        .orElseThrow(() -> new Exception("Talla '" + item.getSize() + "' no encontrada para el producto ID: " + item.getProductId()));

                    // Validar stock de la talla
                    if (sizeEntity.getStock() < item.getQuantity()) {
                        throw new Exception("Stock insuficiente para la talla " + item.getSize() + ". Stock actual: " + sizeEntity.getStock());
                    }

                    // Descontar stock de la talla
                    sizeEntity.setStock(sizeEntity.getStock() - item.getQuantity());
                    ProductSizeRepository.save(sizeEntity);
                } 
                
                // 3. Validación y Descuento del Stock Global (siempre se hace para mantener sincronía)
                // Nota: Si usas tallas, el stock global debería ser la suma de tallas, así que también se reduce.
                if (product.getStock() < item.getQuantity()) {
                    throw new Exception("Stock global insuficiente para el producto ID: " + item.getProductId());
                }

                product.setStock(product.getStock() - item.getQuantity());
                ProductRepository.save(product);
                
                // Calcular subtotal
                item.calculateSubtotal();
            }
        }

        // Calcular total si no viene
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