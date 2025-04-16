package org.basvalk.ipwrcback.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "orders")
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<OrderItem> items;

    // --- GETTERS / SETTERS ---
    public void setItems(List<OrderItem> orderItems) {
        this.items = orderItems;

        // 🔥 FIX: Keep bi-directional relation synced
        for (OrderItem item : orderItems) {
            item.setOrder(this);
        }
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<OrderItem> getItems() { return items; }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCreatedAt(LocalDateTime now) {
        this.createdAt = now;
    }
}

