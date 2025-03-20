package org.basvalk.ipwrcback.Repository;

import org.basvalk.ipwrcback.Model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductModelRepository extends JpaRepository<ProductModel, Long> {
}