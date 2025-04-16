import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from "../models/product.model";
import { Category } from "../models/category.model";

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private apiUrlProducts = 'http://localhost:8080/api/products'; //
  private apiUrlCategory = 'http://localhost:8080/api/categories'; //
  // private apiUrlProducts = 'https://ipwrc25back.onrender.com/api/products'; //

  constructor(private http: HttpClient) {}

  getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.apiUrlProducts);
  }

  getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.apiUrlProducts}/${id}`);
  }

  createProduct(product: {
    img: string;
    year: number;
    price: number;
    name: string;
    description: string;
    id?: number;
    type: string;
    category: { id: number | undefined } | null
  }): Observable<Product> {
    return this.http.post<Product>(this.apiUrlProducts, product);
  }

  deleteProduct(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrlProducts}/${id}`);
  }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.apiUrlCategory}`);
  }

  updateProduct(product: Product): Observable<Product> {
    return this.http.put<Product>(`${this.apiUrlProducts}/${product.id}`, product);
  }


  getProductsByCategory(categoryId: number): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrlCategory}/${categoryId}/products`);
  }



  addCategory(newCategory: Category) {
    return this.http.post<Category>(`${this.apiUrlCategory}`, newCategory);
  }

  deleteCategory(id: number) {
    return this.http.delete<void>(`${this.apiUrlCategory}/${id}`);
  }
}
