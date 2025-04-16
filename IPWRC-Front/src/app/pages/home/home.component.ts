import { Component } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { Product } from '../../models/product.model';
import { Category } from "../../models/category.model";
import { FormsModule } from "@angular/forms";
import { CartService } from "../../services/cart.service";


@Component({
  standalone: true,
  selector: 'app-home',
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent {
  products: Product[] = [];
  filteredProducts: Product[] = [];
  categories: Category[] = [];
  selectedCategoryId: number | null = null;
  constructor(private productService: ProductService,
              private cartService: CartService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.loadProducts();
    this.loadCategories();
  }

  goToProductDetail(productId: number) {
    this.router.navigate(['/products', productId]);
  }

  loadProducts() {
    this.productService.getAllProducts().subscribe({
      next: (data) => {
        this.products = data;
        this.filteredProducts = data;
      },
      error: () => alert('❌ Failed to load products')
    });
  }

  loadCategories() {
    this.productService.getCategories().subscribe({
      next: (data) => this.categories = data,
      error: () => alert('❌ Failed to load categories')
    });
  }

  filterByCategory() {
    if (this.selectedCategoryId == null) {
      this.filteredProducts = this.products;
    } else {
      this.filteredProducts = this.products.filter(
          p => p.category?.id === this.selectedCategoryId
      );
    }
  }

  addToCart(product: Product) {
    this.cartService.addToCart(product, 1);
  }

}
