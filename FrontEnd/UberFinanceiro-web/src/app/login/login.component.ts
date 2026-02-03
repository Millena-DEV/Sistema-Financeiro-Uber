import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from '../login/login.service'; // CORRETO


// Angular Material
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, MatFormFieldModule, MatInputModule, MatButtonModule,MatCardModule],
  templateUrl: './login.component.html',
 styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  email = '';
  password = '';
  errorMessage = '';

  constructor(private loginService: LoginService, private router: Router) {}

  login() {
    this.loginService.login(this.email, this.password).subscribe({
      next: () => {
        // Token já salvo no localStorage pelo LoginService
        this.router.navigate(['/menu/financeiro']); // rota protegida
      },
      error: () => this.errorMessage = 'Usuário ou senha inválidos'
    });
  }

   irParaCadastro() {
    this.router.navigate(['/clientes']); // vai para a rota de cadastro
  }
}
