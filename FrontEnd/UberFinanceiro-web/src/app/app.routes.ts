import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { ClientesComponent } from './clientes/clientes.component';
import { MenuComponent } from './menu/menu.component';
import { FinanceiroComponent } from './financeiro/financeiro.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: 'menu',
    component: MenuComponent,
    children: [
      { path: 'financeiro', component: FinanceiroComponent },
      { path: '', redirectTo: 'financeiro', pathMatch: 'full' }
    ]
  },
  { path: 'clientes', component: ClientesComponent }, // nova rota
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' }
];
