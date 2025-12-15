package kirisame.magic.sale_service.controller;

import kirisame.magic.sale_service.model.Sale;
import kirisame.magic.sale_service.service.saleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "https://mercadovkeireact.onrender.com")
@RestController
@RequestMapping("/api/sales")
public class saleController {

    @Autowired
    private saleService SaleService;

    // Get all sales
    @GetMapping
    public ResponseEntity<List<Sale>> getAllSales() {
        try {
            List<Sale> sales = SaleService.getAllSales();
            return ResponseEntity.ok(sales);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get sale by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getSaleById(@PathVariable Long id) {
        try {
            Optional<Sale> sale = SaleService.getSaleById(id);
            if (sale.isPresent()) {
                return ResponseEntity.ok(sale.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Sale not found with id: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving sale: " + e.getMessage());
        }
    }

    // Create new sale
    @PostMapping
    public ResponseEntity<?> createSale(@RequestBody Sale sale) {
        try {
            // Validate required fields
            if (sale.getUserId() == null) {
                return ResponseEntity.badRequest().body("User ID is required");
            }
            if (sale.getItems() == null || sale.getItems().isEmpty()) {
                return ResponseEntity.badRequest().body("Sale must contain at least one item");
            }

            Sale savedSale = SaleService.createSale(sale);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSale);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error creating sale: " + e.getMessage());
        }
    }

    // Get sales by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Sale>> getSalesByUser(@PathVariable Long userId) {
        try {
            List<Sale> sales = SaleService.getSalesByUserId(userId);
            return ResponseEntity.ok(sales);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get sales by date range
    @GetMapping("/date-range")
    public ResponseEntity<List<Sale>> getSalesByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            List<Sale> sales = SaleService.getSalesByDateRange(startDate, endDate);
            return ResponseEntity.ok(sales);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get total sales amount by user
    @GetMapping("/user/{userId}/total")
    public ResponseEntity<Double> getTotalSalesByUser(@PathVariable Long userId) {
        try {
            Double total = SaleService.getTotalSalesByUser(userId);
            return ResponseEntity.ok(total);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get sales statistics
    @GetMapping("/statistics")
    public ResponseEntity<?> getSalesStatistics() {
        try {
            long totalSales = SaleService.getTotalSalesCount();
            List<Sale> allSales = SaleService.getAllSales();
            double totalRevenue = allSales.stream()
                .mapToDouble(Sale::getTotal)
                .sum();
            double averageSale = totalSales > 0 ? totalRevenue / totalSales : 0;
            
            // Create a proper DTO for statistics
            SalesStatistics stats = new SalesStatistics(totalSales, totalRevenue, averageSale);
            
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving statistics: " + e.getMessage());
        }
    }

    // Inner class for statistics DTO
    public static class SalesStatistics {
        private final long totalSalesCount;
        private final double totalRevenue;
        private final double averageSale;

        public SalesStatistics(long totalSalesCount, double totalRevenue, double averageSale) {
            this.totalSalesCount = totalSalesCount;
            this.totalRevenue = totalRevenue;
            this.averageSale = averageSale;
        }

        // Getters
        public long getTotalSalesCount() { return totalSalesCount; }
        public double getTotalRevenue() { return totalRevenue; }
        public double getAverageSale() { return averageSale; }
    }
}