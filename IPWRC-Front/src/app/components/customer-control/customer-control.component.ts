import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Order } from '../../models/order.model';
import { OrderService } from "../../services/order.service";
@Component({
  selector: 'app-customer-portal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './customer-control.component.html',
})
export class CustomerControlComponent {
  orders: Order[] = [];

  constructor(private http: HttpClient,
              private orderService: OrderService) {
  }

  ngOnInit() {
    this.orderService.getMyOrders().subscribe({
      next: (data) => this.orders = data,
      error: () => alert('❌ Failed to load orders')
    });
  }
}
