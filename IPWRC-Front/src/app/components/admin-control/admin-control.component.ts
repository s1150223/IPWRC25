import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { User, UserService } from '../../services/user.service';
import { Product, ProductService } from '../../services/product.service';

@Component({
  selector: 'app-admin-control',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './admin-control.component.html',
  styleUrl: './admin-control.component.css'
})
export class AdminControlComponent {
  users: User[] = [];
  products: Product[] = [];
  newUser: User = {
    username: '',
    password: '',
    role: 'ROLE_USER'
  };
  newProduct: Product = {
    name: '',
    description: '',
    year: 2024,
    type: '',
    price: 0,
    img: ''
  };

  constructor(private http: HttpClient,
              private userService: UserService,
              private productService: ProductService) {
  }

  ngOnInit() {
    this.loadUsers();
    this.loadProducts();
  }

  loadUsers() {
    this.userService.getAllUsers().subscribe({
      next: (data) => this.users = data,
    });
  }

  loadProducts() {
    this.productService.getAllProducts().subscribe({
      next: (data) => this.products = data,
    });
  }


  createUser() {
    this.userService.createUser(this.newUser).subscribe({
      next: () => {
        alert('User Added');
        this.newUser = { username: '', password: '', role: 'ROLE_USER' };
      },
      error: () => alert('Failed to add user')
    })
  }

  createProduct() {
    this.productService.createProduct(this.newProduct).subscribe({
      next: () => {
        alert('Product Added');
        this.newProduct = { name: '', description: '', year: 2024, type: '', price: 0, img: '' };
      },
      error: () => alert('Failed to add product')
    })
  }
}
