package org.basvalk.ipwrcback.Controller;

import org.basvalk.ipwrcback.Model.ProductModel;
import org.basvalk.ipwrcback.Repository.ProductModelRepository;
import org.basvalk.ipwrcback.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductModelRepository productRepo;

    @GetMapping
    public List<ProductModel> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductModel> getProductById(@PathVariable Long id) {
        System.out.println(" Fetching product with ID: " + id);
        ProductModel product = productService.getProductById(id);

        if (product == null) {
            System.out.println("Product not found for ID: " + id);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product);
    }


    @PostMapping
    public ResponseEntity<ProductModel> createProduct(@RequestBody ProductModel product) {
        System.out.println("🛠️ Received product: " + product.getName() + " Category ID: " + (product.getCategory() != null ? product.getCategory().getId() : "null"));
        return ResponseEntity.ok(productRepo.save(product));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductModel> updateProduct(@PathVariable Long id, @RequestBody ProductModel productDetails) {
        try {
            ProductModel updatedProduct = productService.updateProduct(id, productDetails);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
