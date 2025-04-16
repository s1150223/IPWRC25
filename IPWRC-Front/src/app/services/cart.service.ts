import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Product } from '../models/product.model';

export interface CartItem {
    product: Product;
    quantity: number;
}

@Injectable({
    providedIn: 'root'
})
export class CartService {
    private cartItems: CartItem[] = [];
    private cartSubject = new BehaviorSubject<CartItem[]>([]);

    constructor() {
        const stored = localStorage.getItem('shoppingCart');
        if (stored) {
            this.cartItems = JSON.parse(stored);
            this.cartSubject.next(this.cartItems);
        }
    }

    getCart() {
        return this.cartSubject.asObservable();
    }

    addToCart(product: Product, quantity: number = 1) {
        const existing = this.cartItems.find(item => item.product.id === product.id);

        if (existing) {
            existing.quantity += quantity;
        } else {
            this.cartItems.push({ product, quantity });
        }

        this.updateCart();
    }

    removeFromCart(productId: number) {
        this.cartItems = this.cartItems.filter(item => item.product.id !== productId);
        this.updateCart();
    }

    clearCart() {
        this.cartItems = [];
        this.updateCart();
    }

    setQuantity(productId: number, quantity: number) {
        const item = this.cartItems.find(i => i.product.id === productId);
        if (item && quantity > 0) {
            item.quantity = quantity;
            this.updateCart();
        }
    }


    private updateCart() {
        localStorage.setItem('shoppingCart', JSON.stringify(this.cartItems));
        this.cartSubject.next(this.cartItems);
    }
}
