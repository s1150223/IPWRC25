import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { NgIf } from '@angular/common';
import { CartService } from "../../services/cart.service";

@Component({
    standalone: true,
    selector: 'app-header',
    imports: [RouterModule, NgIf],
    templateUrl: './header.component.html',
    styleUrl: './header.component.css'
})
export class HeaderComponent {
    itemCount = 0;

    constructor(public authService: AuthService,
                private router: Router,
                private cartService: CartService) {
        this.cartService.getCart().subscribe(items => {
            this.itemCount = items.reduce((sum, item) => sum + item.quantity, 0);
        });
    }

    logout() {
        this.authService.logout();
        this.router.navigate(['/login']);
    }
}
