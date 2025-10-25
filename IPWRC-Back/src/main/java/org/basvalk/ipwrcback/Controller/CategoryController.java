package org.basvalk.ipwrcback.Controller;

import org.basvalk.ipwrcback.Model.CategoryModel;
import org.basvalk.ipwrcback.Repository.CategoryRepository;
import org.basvalk.ipwrcback.Repository.ProductModelRepository;
import org.basvalk.ipwrcback.Model.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private ProductModelRepository productRepo;

    @GetMapping
    public List<CategoryModel> getAllCategories() {
        return categoryRepo.findAll();
    }

    @GetMapping("/{id}/products")
    public List<ProductModel> getProductsByCategory(@PathVariable Long id) {
        return productRepo.findByCategoryId(id);
    }

    @PostMapping
    public ResponseEntity<CategoryModel> createCategory(@RequestBody CategoryModel category) {
        CategoryModel saved = categoryRepo.save(category);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{categoryId}/products/{productId}") // Koppelt bestaand product aan bestaande categorie
    public ResponseEntity<ProductModel> assignProductToCategory(
            @PathVariable Long categoryId,
            @PathVariable Long productId) {

        return productRepo.findById(productId)
                .map(product -> {
                    // haal de category op
                    CategoryModel category = categoryRepo.findById(categoryId)
                            .orElseThrow(() -> new RuntimeException("Category not found"));

                    // koppel de categorie
                    product.setCategory(category);
                    ProductModel updated = productRepo.save(product);

                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
