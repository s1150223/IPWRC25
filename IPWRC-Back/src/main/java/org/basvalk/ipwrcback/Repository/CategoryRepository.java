package org.basvalk.ipwrcback.Repository;

import org.basvalk.ipwrcback.Model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {
}
