package org.basvalk.ipwrcback.Service;

import jakarta.transaction.Transactional;
import org.basvalk.ipwrcback.Model.OrderItem;
import org.basvalk.ipwrcback.Model.OrderItemDTO;
import org.basvalk.ipwrcback.Model.OrderModel;
import org.basvalk.ipwrcback.Model.ProductModel;
import org.basvalk.ipwrcback.Repository.OrderRepository;
import org.basvalk.ipwrcback.Repository.ProductModelRepository;
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

    @Autowired
    private ProductModelRepository productRepo;

    public List<OrderModel> getOrdersByUsername(String username) {
        return orderRepo.findByUsername(username);
    }

    @Transactional
    public void placeOrder(String username, List<OrderItemDTO> items) {

        for (OrderItemDTO dto : items) {
            ProductModel product = productRepo.findById(dto.productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + dto.productId));

            if (product.getStock() < dto.quantity) {
                throw new IllegalArgumentException(
                        "Niet genoeg voorraad voor " + product.getName() +
                        ". Nog " + product.getStock() + " beschikbaar."
                );
            }
        }

        OrderModel order = new OrderModel();
        order.setUsername(username);
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> orderItems = items.stream().map(dto -> {
            OrderItem item = new OrderItem();
            ProductModel product = productRepo.findById(dto.productId).orElseThrow();

            product.setStock(product.getStock() - dto.quantity);
            productRepo.save(product);

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
