import { Component } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { NgIf } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CartService } from "../../services/cart.service";
import { Product } from "../../models/product.model";

@Component({
  standalone: true,
  selector: 'app-product-detail',
  imports: [RouterModule, NgIf, FormsModule],
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css'],
})
export class ProductDetailComponent {
  isEditing: boolean = false;
  originalProduct: any;
  product: any;

  constructor(private route: ActivatedRoute,
              private productService: ProductService,
              public authService: AuthService,
              public router: Router,
              private cartService: CartService
  ) {
  }

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.productService.getProductById(id).subscribe((data) => {
      this.product = data;
    });
  }

  addToCart() {
    this.cartService.addToCart(this.product, 1);
  }

  editProduct() {
    this.isEditing = true;
  }

  cancelEdit() {
    this.isEditing = false;
    this.product;
  }


  deleteProduct() {
    if (confirm('Are you sure you want to delete this product?')) {
      this.productService.deleteProduct(this.product.id).subscribe({
        next: () => {
          console.log('🗑️ Product deleted');
          this.router.navigate(['/']);
        },
        error: (err) => {
        }
      });
    }
  }

  saveProduct() {
    this.productService.updateProduct(this.product).subscribe(updated => {
      this.product = updated;
      this.originalProduct = { ...updated };
      this.isEditing = false;
    })
  }
}
