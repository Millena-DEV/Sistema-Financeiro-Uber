import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes';
import { LOCALE_ID } from '@angular/core';
import { registerLocaleData } from '@angular/common';
import localePt from '@angular/common/locales/pt';
import { provideHttpClient, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './app/core/auth.interceptor';
import { provideEchartsCore } from 'ngx-echarts';
import * as echarts from 'echarts/core';
import { BarChart, LineChart } from 'echarts/charts';
import { GridComponent, LegendComponent, TooltipComponent, TitleComponent } from 'echarts/components';
import { CanvasRenderer } from 'echarts/renderers';

registerLocaleData(localePt);

echarts.use([BarChart, LineChart, GridComponent, LegendComponent, TooltipComponent, TitleComponent, CanvasRenderer]);

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),             // ativa roteamento
    provideHttpClient(),               // ativa HttpClient
    provideEchartsCore({ echarts }),
    { provide: LOCALE_ID, useValue: 'pt-BR' },
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true } // registra interceptor
  ]
});
