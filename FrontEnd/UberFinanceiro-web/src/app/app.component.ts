import { Component } from '@angular/core';
import { RouterModule, Router, RouterOutlet } from '@angular/router';
import { LoginService } from './login/login.service';  // Verifique o caminho correto
import { FormsModule } from '@angular/forms';  // Importando FormsModule diretamente aqui

@Component({
  selector: 'app-root',
  standalone: true,  // Tornando o componente standalone
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
   imports: [RouterOutlet],
})
export class AppComponent {
  title = 'UberFinanceiro-web';
  email = '';
  senha = '';

  constructor(
    private loginService: LoginService,
    private router: Router
  ) {}

   login() {
    this.loginService.login(this.email, this.senha).subscribe({
      next: () => {
        // Navega para a página de produtos após o login bem-sucedido
        this.router.navigate(['/menu']);
      },
      error: () => {
        alert('Email ou senha inválidos');
      }
    });
  }
}
