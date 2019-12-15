import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'regiao',
        loadChildren: () => import('./regiao/regiao.module').then(m => m.ControleEmpregadosRegiaoModule)
      },
      {
        path: 'pais',
        loadChildren: () => import('./pais/pais.module').then(m => m.ControleEmpregadosPaisModule)
      },
      {
        path: 'localizacao',
        loadChildren: () => import('./localizacao/localizacao.module').then(m => m.ControleEmpregadosLocalizacaoModule)
      },
      {
        path: 'departamento',
        loadChildren: () => import('./departamento/departamento.module').then(m => m.ControleEmpregadosDepartamentoModule)
      },
      {
        path: 'tarefa',
        loadChildren: () => import('./tarefa/tarefa.module').then(m => m.ControleEmpregadosTarefaModule)
      },
      {
        path: 'empregado',
        loadChildren: () => import('./empregado/empregado.module').then(m => m.ControleEmpregadosEmpregadoModule)
      },
      {
        path: 'trabalho',
        loadChildren: () => import('./trabalho/trabalho.module').then(m => m.ControleEmpregadosTrabalhoModule)
      },
      {
        path: 'historico-de-trabalho',
        loadChildren: () =>
          import('./historico-de-trabalho/historico-de-trabalho.module').then(m => m.ControleEmpregadosHistoricoDeTrabalhoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class ControleEmpregadosEntityModule {}
