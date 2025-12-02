package kirisame.magic.product_service.service;

import kirisame.magic.product_service.model.Product;
import kirisame.magic.product_service.repository.productRepository; // Nota: Deberías renombrar tu clase repository a ProductRepository (Mayúscula) por convención
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class productService {

    @Autowired
    private productRepository ProductRepository;

    @Autowired
    private productImageService ProductImageService;

    public List<Product> getAllProducts() {
        return ProductRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return ProductRepository.findById(id);
    }

    public Product createProduct(Product product) {
        // La lógica de setImages en el modelo se encarga de vincular las imágenes
        return ProductRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = ProductRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        
        // Actualizar campos básicos
        product.setNombre(productDetails.getNombre());
        product.setDescripcion(productDetails.getDescripcion());
        product.setPrecio(productDetails.getPrecio());
        product.setStock(productDetails.getStock());
        product.setCategory(productDetails.getCategory());
        
        // --- FALTABA ESTA LÍNEA ---
        product.setSize(productDetails.getSize()); 
        
        // Actualizar imágenes (usará el setter corregido en el paso 1)
        product.setImages(productDetails.getImages());

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