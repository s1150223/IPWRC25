import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CartService, CartItem } from '../../services/cart.service';
import { HttpClient } from "@angular/common/http";
import { Route, Router } from "@angular/router";
import { OrderService } from "../../services/order.service";
import { OrderItem } from "../../models/order.model";

interface ValidateCartItem extends CartItem {
  invalidQuantity?: boolean;
}

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cart.component.html',
})
export class CartComponent {
  cartItems: ValidateCartItem[] = [];
  quantityRegex = /^[1-9]\d*$/;

  constructor(private cartService: CartService,
              private orderService: OrderService,
              private router: Router
              ) {}

  placeOrder() {
    const items: OrderItem[] = this.cartItems.map(item => ({
      productName: item.product.name,
      quantity: item.quantity,
      price: item.product.price
    }));

    this.orderService.placeOrder(items).subscribe({
      next: (res) => {
        console.log('✅ Backend responded with:', res);
        this.cartService.clearCart();
        alert('✅ Order placed!');
        this.router.navigate(['/my-orders']);
      },
    });

  }
  ngOnInit() {
    this.cartService.getCart().subscribe(items => {
      this.cartItems = items.map(item => ({
        ...item,
        invalidQuantity: false
      }));
    });
  }

  updateQuantity(item: ValidateCartItem, event: Event) {
    const input = event.target as HTMLInputElement;
    const newQty = Number(input.value);
    item.quantity = newQty;

    this.validateQ(item);

    if (!item.invalidQuantity && item.product.id !== undefined) {
      this.cartService.setQuantity(item.product.id, item.quantity);
    }
  }


  validateQ(item: ValidateCartItem) {
    const isValid = this.quantityRegex.test(item.quantity?.toString() || '');
    item.invalidQuantity = !isValid;
  }

  removeItem(productId: number) {
    this.cartService.removeFromCart(productId);
  }

  clearCart() {
    this.cartService.clearCart();
  }

  getTotal(): number {
    return this.cartItems.reduce(
        (sum, item) => sum + item.product.price * item.quantity,
        0
    );
  }

  hasInvalidQuantities() {
    return this.cartItems.some(item => item.invalidQuantity);
  }
}
