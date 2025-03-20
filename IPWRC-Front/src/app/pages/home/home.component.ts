import { Component } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Product } from '../../services/product.service';


@Component({
  standalone: true,
  selector: 'app-home',
  imports: [CommonModule, RouterModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent {
  products: Product[] = [];

  constructor(private ProductService: ProductService) {
    // this.ProductService.getAllProducts().subscribe((data) => {
    // this.products = data;
    // });
  }

  ngOnInit() {
    this.ProductService.getAllProducts().subscribe(
      (data) => {
        this.products = data;
      },
      (error) => {
      }
    );
  }
}
