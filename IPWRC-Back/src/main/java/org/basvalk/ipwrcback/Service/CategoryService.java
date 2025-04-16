package org.basvalk.ipwrcback.Service;

import org.basvalk.ipwrcback.Model.CategoryModel;
import org.basvalk.ipwrcback.Repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepo;

    public CategoryService(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public List<CategoryModel> getAllCategories() {
        return categoryRepo.findAll();
    }

    public CategoryModel createCategory(CategoryModel category) {
        return categoryRepo.save(category);
    }
}
