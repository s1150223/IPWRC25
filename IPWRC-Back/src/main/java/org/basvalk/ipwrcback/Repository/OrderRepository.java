package org.basvalk.ipwrcback.Repository;

import org.basvalk.ipwrcback.Model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

    public interface OrderRepository extends JpaRepository<OrderModel, Long> {
        List<OrderModel> findByUsername(String username);
    }
