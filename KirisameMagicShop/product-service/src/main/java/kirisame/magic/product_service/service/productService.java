package kirisame.magic.product_service.service;

import kirisame.magic.product_service.model.Product;
import kirisame.magic.product_service.repository.productRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class productService {

    @Autowired
    private productRepository ProductRepository;

    public List<Product> getAllProducts() {
        return ProductRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return ProductRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return ProductRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = ProductRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        
        product.setNombre(productDetails.getNombre());
        product.setDescripcion(productDetails.getDescripcion());
        product.setPrecio(productDetails.getPrecio());
        product.setStock(productDetails.getStock());
        product.setCategory(productDetails.getCategory());
        product.setImage(productDetails.getImage());
        return ProductRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = ProductRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        ProductRepository.delete(product);
    }

    public List<Product> searchProductsByName(String nombre) {
        return ProductRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Product> getProductsByPriceRange(Double minPrecio, Double maxPrecio) {
        return ProductRepository.findByPrecioBetween(minPrecio, maxPrecio);
    }

    public List<Product> getProductsInStock() {
        return ProductRepository.findByStockGreaterThan(0);
    }
}