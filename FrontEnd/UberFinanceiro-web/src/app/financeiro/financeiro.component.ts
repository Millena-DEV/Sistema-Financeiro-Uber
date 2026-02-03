import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgxEchartsModule } from 'ngx-echarts';
import { EChartsOption } from 'echarts';
import { forkJoin } from 'rxjs';
import {
  FinanceiroService,
  RegistroFinanceiro,
  RegistroFinanceiroPayload,
  ResumoFinanceiro
} from './financeiro.service';

@Component({
  selector: 'app-financeiro',
  standalone: true,
  imports: [CommonModule, FormsModule, NgxEchartsModule],
  templateUrl: './financeiro.component.html',
  styleUrls: ['./financeiro.component.scss']
})
export class FinanceiroComponent implements OnInit {
  inicio = '';
  fim = '';

  resumo?: ResumoFinanceiro;
  registros: RegistroFinanceiro[] = [];

  carregando = false;
  erro = '';
  sucesso = '';

  lucroOptions: EChartsOption = {};
  kmOptions: EChartsOption = {};

  novoRegistro: RegistroFinanceiroPayload = {
    data: '',
    kmRodado: null,
    ganhosBrutos: null,
    precoGasolina: null,
    litrosAbastecidos: null,
    horasTrabalhadas: null
  };

  constructor(private financeiroService: FinanceiroService) {}

  ngOnInit(): void {
    const hoje = new Date();
    const seteDiasAtras = new Date();
    seteDiasAtras.setDate(hoje.getDate() - 6);

    this.inicio = this.formatDate(seteDiasAtras);
    this.fim = this.formatDate(hoje);
    this.novoRegistro.data = this.formatDate(hoje);

    this.carregarDados();
  }

  carregarDados(): void {
    this.carregando = true;
    this.erro = '';

    forkJoin({
      resumo: this.financeiroService.getResumo(this.inicio, this.fim),
      registros: this.financeiroService.getRegistros(this.inicio, this.fim)
    }).subscribe({
      next: ({ resumo, registros }) => {
        this.registros = registros;
        this.resumo = this.calcularResumoLocal(registros, this.inicio, this.fim);
        this.atualizarGraficos();
        this.carregando = false;
      },
      error: () => {
        this.erro = 'Falha ao carregar dados financeiros.';
        this.resumo = undefined;
        this.registros = [];
        this.atualizarGraficos();
        this.carregando = false;
      }
    });
  }

  salvarRegistro(): void {
    this.erro = '';
    this.sucesso = '';

    if (!this.novoRegistro.data) {
      this.erro = 'Informe a data do registro.';
      return;
    }

    this.financeiroService.criarRegistro(this.novoRegistro).subscribe({
      next: () => {
        this.sucesso = 'Registro salvo com sucesso.';
        this.novoRegistro.kmRodado = null;
        this.novoRegistro.ganhosBrutos = null;
        this.novoRegistro.precoGasolina = null;
        this.novoRegistro.litrosAbastecidos = null;
        this.novoRegistro.horasTrabalhadas = null;
        this.carregarDados();
      },
      error: () => {
        this.erro = 'Falha ao salvar o registro.';
      }
    });
  }

  private atualizarGraficos(): void {
    const agregados = this.agruparPorData(this.registros);
    const categorias = agregados.map(item => item.data);
    const lucroSerie = agregados.map(item => item.lucroLiquido);
    const gastoSerie = agregados.map(item => item.gastoGasolina);
    const kmSerie = agregados.map(item => item.kmRodado);

    this.lucroOptions = {
      title: { text: 'Lucro x Gasto de Gasolina' },
      tooltip: {
        trigger: 'axis',
        valueFormatter: (value) => this.formatarMoeda(value as number)
      },
      legend: { data: ['Lucro', 'Gasolina'] },
      xAxis: { type: 'category', data: categorias },
      yAxis: {
        type: 'value',
        axisLabel: { formatter: (value: number) => this.formatarMoeda(value) }
      },
      series: [
        { name: 'Lucro', type: 'line', data: lucroSerie, smooth: true },
        { name: 'Gasolina', type: 'line', data: gastoSerie, smooth: true }
      ]
    };

    this.kmOptions = {
      title: { text: 'KM Rodado por Dia' },
      tooltip: { trigger: 'axis' },
      xAxis: { type: 'category', data: categorias },
      yAxis: { type: 'value' },
      series: [
        { name: 'KM', type: 'bar', data: kmSerie }
      ]
    };
  }

  private formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  private formatarMoeda(valor: number): string {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL',
      minimumFractionDigits: 2,
      maximumFractionDigits: 2
    }).format(valor ?? 0);
  }

  private calcularResumoLocal(registros: RegistroFinanceiro[], inicio: string, fim: string): ResumoFinanceiro | undefined {
    if (!registros.length) {
      return undefined;
    }

    let kmTotal = 0;
    let ganhosBrutosTotal = 0;
    let gastoGasolinaTotal = 0;
    let lucroLiquidoTotal = 0;
    let horasTotal = 0;

    for (const registro of registros) {
      kmTotal += registro.kmRodado ?? 0;
      ganhosBrutosTotal += registro.ganhosBrutos ?? 0;
      gastoGasolinaTotal += registro.gastoGasolina ?? 0;
      lucroLiquidoTotal += registro.lucroLiquido ?? 0;
      horasTotal += registro.horasTrabalhadas ?? 0;
    }

    const resumo: ResumoFinanceiro = {
      inicio,
      fim,
      kmTotal,
      ganhosBrutosTotal,
      gastoGasolinaTotal,
      lucroLiquidoTotal,
      horasTrabalhadasTotal: horasTotal
    };

    if (kmTotal > 0) {
      resumo.custoPorKm = gastoGasolinaTotal / kmTotal;
      resumo.lucroPorKm = lucroLiquidoTotal / kmTotal;
    }

    if (horasTotal > 0) {
      resumo.ganhoPorHora = lucroLiquidoTotal / horasTotal;
    }

    return resumo;
  }

  private agruparPorData(registros: RegistroFinanceiro[]): Array<{
    data: string;
    kmRodado: number;
    gastoGasolina: number;
    lucroLiquido: number;
  }> {
    const mapa = new Map<string, { kmRodado: number; gastoGasolina: number; lucroLiquido: number }>();

    for (const registro of registros) {
      const chave = registro.data;
      const atual = mapa.get(chave) ?? { kmRodado: 0, gastoGasolina: 0, lucroLiquido: 0 };
      atual.kmRodado += registro.kmRodado ?? 0;
      atual.gastoGasolina += registro.gastoGasolina ?? 0;
      atual.lucroLiquido += registro.lucroLiquido ?? 0;
      mapa.set(chave, atual);
    }

    return Array.from(mapa.entries()).map(([data, valores]) => ({
      data,
      kmRodado: valores.kmRodado,
      gastoGasolina: valores.gastoGasolina,
      lucroLiquido: valores.lucroLiquido
    }));
  }
}
