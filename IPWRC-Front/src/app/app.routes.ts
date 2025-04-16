import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { ProductDetailComponent } from './pages/product-detail/product-detail.component';
import { AdminGuard } from './guards/admin.guard';
import { AdminControlComponent } from './components/admin-control/admin-control.component';
import { LoginComponent } from './pages/login/login.component';
import { CartComponent } from "./pages/cart/cart.component";
import { CustomerControlComponent } from "./components/customer-control/customer-control.component";
import { UserGuard } from "./guards/user.guard";

export const routes: Routes = [
  { path: '', component: HomeComponent }, // Homepage
  { path: 'product/:id', component: ProductDetailComponent }, // Product detail page
  { path: 'login', component: LoginComponent }, // Login page
  { path: 'cart', component: CartComponent }, // Shopping cart page
  { path: 'admin', component: AdminControlComponent, canActivate: [AdminGuard] }, // Admin control panel
  { path: 'my-orders', component: CustomerControlComponent, canActivate: [UserGuard] },

];
