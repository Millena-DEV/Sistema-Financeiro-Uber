import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClientesService {
  private apiUrl = 'http://localhost:8080/clientes'; // sua rota do backend

  constructor(private http: HttpClient) {}

  cadastrar(nome: string, email: string, senha: string): Observable<any> {
  const body = {
    nome,
    email,
    senha,
    role: 'CLIENTE' // envia role também, opcional se backend já define
  };
  return this.http.post<any>('http://localhost:8080/clientes', body);
}

}
