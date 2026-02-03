import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface RegistroFinanceiro {
  id: number;
  data: string;
  kmRodado: number;
  ganhosBrutos: number;
  precoGasolina: number;
  litrosAbastecidos: number;
  horasTrabalhadas?: number;
  gastoGasolina: number;
  lucroLiquido: number;
  custoPorKm?: number;
  lucroPorKm?: number;
  ganhoPorHora?: number;
}

export interface RegistroFinanceiroPayload {
  data: string;
  kmRodado: number | null;
  ganhosBrutos: number | null;
  precoGasolina: number | null;
  litrosAbastecidos: number | null;
  horasTrabalhadas?: number | null;
}

export interface ResumoFinanceiro {
  inicio: string;
  fim: string;
  kmTotal: number;
  ganhosBrutosTotal: number;
  gastoGasolinaTotal: number;
  lucroLiquidoTotal: number;
  custoPorKm?: number;
  lucroPorKm?: number;
  ganhoPorHora?: number;
  horasTrabalhadasTotal?: number;
}

@Injectable({
  providedIn: 'root'
})
export class FinanceiroService {
  private readonly baseUrl = 'http://localhost:8080/financeiro';

  constructor(private http: HttpClient) {}

  getResumo(inicio: string, fim: string): Observable<ResumoFinanceiro> {
    const params = new HttpParams().set('inicio', inicio).set('fim', fim);
    return this.http.get<ResumoFinanceiro>(`${this.baseUrl}/resumo`, { params });
  }

  getRegistros(inicio: string, fim: string): Observable<RegistroFinanceiro[]> {
    const params = new HttpParams().set('inicio', inicio).set('fim', fim);
    return this.http.get<RegistroFinanceiro[]>(`${this.baseUrl}/registros`, { params });
  }

  criarRegistro(payload: RegistroFinanceiroPayload): Observable<RegistroFinanceiro> {
    return this.http.post<RegistroFinanceiro>(`${this.baseUrl}/registros`, payload);
  }
}
