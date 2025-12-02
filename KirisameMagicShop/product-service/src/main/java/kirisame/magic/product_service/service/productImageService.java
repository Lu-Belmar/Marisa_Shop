package kirisame.magic.product_service.service;

import kirisame.magic.product_service.model.ProductImage;
import kirisame.magic.product_service.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class productImageService {
    
    @Autowired
    private ProductImageRepository productImageRepository;
    
    public List<ProductImage> getImagesByProductId(Long productId) {
        return productImageRepository.findByProductId(productId);
    }
    
    @Transactional
    public void saveAllImages(List<ProductImage> images) {
        productImageRepository.saveAll(images);
    }
    
    @Transactional
    public void deleteImagesByProductId(Long productId) {
        productImageRepository.deleteByProductId(productId);
    }
    
    public ProductImage saveImage(ProductImage image) {
        return productImageRepository.save(image);
    }
    
    public void deleteImage(Long imageId) {
        productImageRepository.deleteById(imageId);
    }
}