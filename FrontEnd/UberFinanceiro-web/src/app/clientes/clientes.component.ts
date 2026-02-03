import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ClientesService } from '../clientes/clientes.service';


// Angular Material
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [CommonModule, FormsModule, MatFormFieldModule, MatInputModule, MatButtonModule,MatCardModule ],
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.scss']
})
export class ClientesComponent {
  nome = '';
  email = '';
  senha = '';
  errorMessage = '';
  successMessage = '';

  constructor(private clientesService: ClientesService, private router: Router) {}

 cadastrar() {
  this.clientesService.cadastrar(this.nome, this.email, this.senha).subscribe({
    next: () => {
      this.successMessage = 'Cadastro realizado com sucesso!';
      this.errorMessage = '';
      setTimeout(() => this.router.navigate(['/login']), 1500);
    },
    error: () => {
      this.errorMessage = 'Erro ao cadastrar cliente.';
      this.successMessage = '';
    }
  });
}

}
