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

}
