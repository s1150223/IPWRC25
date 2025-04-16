package org.basvalk.ipwrcback.Controller;

import org.basvalk.ipwrcback.Model.OrderItemDTO;
import org.basvalk.ipwrcback.Model.OrderModel;
import org.basvalk.ipwrcback.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/my")
    public List<OrderModel> getMyOrders(@AuthenticationPrincipal UserDetails userDetails) {
        return orderService.getOrdersByUsername(userDetails.getUsername());
    }

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody List<OrderItemDTO> items
    ) {
        orderService.placeOrder(userDetails.getUsername(), items);
        return ResponseEntity.ok(Collections.singletonMap("message", "Order placed successfully"));

    }

}
