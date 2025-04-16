package org.basvalk.ipwrcback.Service;

import org.basvalk.ipwrcback.Model.ProductModel;
import org.basvalk.ipwrcback.Repository.CategoryRepository;
import org.basvalk.ipwrcback.Repository.ProductModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductModelRepository productRepository;

    private CategoryRepository categoryRepos;

    public List<ProductModel> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductModel getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public ProductModel saveProduct(ProductModel product) {
        return productRepository.save(product);
    }

    public ProductModel updateProduct(Long id, ProductModel productDetails) {
        ProductModel product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setYear(productDetails.getYear());
        product.setType(productDetails.getType());
        product.setPrice(productDetails.getPrice());
        product.setImage(productDetails.getImage());

        return productRepository.save(product);
    }

    public ProductModel createProduct(ProductModel product) {
        System.out.println("🛠️ Received product: " + product.getName() +
                " Category ID: " + (product.getCategory() != null ? product.getCategory().getId() : "null"));

        if (product.getCategory() != null && product.getCategory().getId() != null) {
            Long catId = product.getCategory().getId();
            categoryRepos.findById(catId).ifPresent(product::setCategory);
        } else {
            product.setCategory(null);
        }

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
