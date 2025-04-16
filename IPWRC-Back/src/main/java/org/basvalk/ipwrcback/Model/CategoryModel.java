package org.basvalk.ipwrcback.Model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class CategoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Optional: Een categorie kan meerdere producten hebben
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<ProductModel> products;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }
}
