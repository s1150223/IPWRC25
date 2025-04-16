import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { User, UserService } from '../../services/user.service';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product.model';
import { Category } from "../../models/category.model";

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

    categories: Category[] = [];
    newCategory: Category = { name: '' }

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
        img: '',
        category: undefined,
    };

    constructor(private http: HttpClient,
                private userService: UserService,
                private productService: ProductService) {
    }

    ngOnInit() {
        this.loadUsers();
        this.loadProducts();
        this.loadCategories();
    }

    loadUsers() {
        this.userService.getAllUsers().subscribe({
            next: (data) => this.users = data,
        });
    }

    deleteUser(userId: number) {
        if (confirm('Are you sure you want to delete this user?')) {
            this.userService.deleteUser(userId).subscribe({
                next: () => {
                    this.users = this.users.filter(u => u.id !== userId);
                    alert('✅ User deleted');
                },
                error: () => alert('❌ Failed to delete user')
            });
        }
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

    private loadCategories() {
        this.productService.getCategories().subscribe({
            next: (data) => this.categories = data,
        });
    }

    addCategory() {
        this.productService.addCategory(this.newCategory).subscribe({
            next: (cat) => {
                this.categories.push(cat);
                this.newCategory = { name: '' };
                alert('✅ Category added!');
            },
            error: () => alert('❌ Failed to add category')
        });
    }

    updateProduct(product: Product) {
        this.productService.updateProduct(product).subscribe({
            next: () => alert(`✅ Product "${product.name}" updated!`),
            error: () => alert(`❌ Failed to update product`)
        });
    }

    deleteCategory(id: number) {
        if (confirm('Delete this category?')) {
            this.productService.deleteCategory(id).subscribe({
                next: () => {
                    this.categories = this.categories.filter(c => c.id !== id);
                    alert('🗑️ Category deleted');
                },
                error: () => alert('❌ Failed to delete category')
            });
        }
    }

    onCategoryChange(product: Product, newCategory: Category | undefined) {
        product.category = newCategory;
        this.productService.updateProduct(product).subscribe({
            next: updated => {
                console.log(`✅ Updated product ${updated.name} with new category: ${updated.category?.name}`);
            },
            error: () => {
                alert('❌ Failed to update category');
            }
        });
    }

}
