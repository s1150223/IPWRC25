package org.basvalk.ipwrcback.Service;

import jakarta.transaction.Transactional;
import org.basvalk.ipwrcback.Model.OrderItem;
import org.basvalk.ipwrcback.Model.OrderItemDTO;
import org.basvalk.ipwrcback.Model.OrderModel;
import org.basvalk.ipwrcback.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepo;

    public List<OrderModel> getOrdersByUsername(String username) {
        return orderRepo.findByUsername(username);
    }

    @Transactional
    public void placeOrder(String username, List<OrderItemDTO> items) {
        OrderModel order = new OrderModel();
        order.setUsername(username);
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> orderItems = items.stream().map(dto -> {
            OrderItem item = new OrderItem();
            item.setProductName(dto.productName);
            item.setQuantity(dto.quantity);
            item.setPrice(dto.price);
            return item;
        }).collect(Collectors.toList());

        order.setItems(orderItems); //
        orderRepo.save(order);      //

        System.out.println("✅ Order saved for user: " + username);
    }

}
