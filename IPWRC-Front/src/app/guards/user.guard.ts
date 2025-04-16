import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
    providedIn: 'root'
})
export class UserGuard implements CanActivate {
    constructor(private auth: AuthService, private router: Router) {}

    canActivate(): boolean {
        const token = this.auth.getToken();
        if (token) {
            return true; // ✅ Logged in user (any role)
        }

        this.router.navigate(['/login']);
        return false;
    }
}
