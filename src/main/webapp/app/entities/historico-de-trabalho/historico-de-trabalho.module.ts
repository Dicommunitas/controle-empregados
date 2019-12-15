import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ControleEmpregadosSharedModule } from 'app/shared/shared.module';
import { HistoricoDeTrabalhoComponent } from './historico-de-trabalho.component';
import { HistoricoDeTrabalhoDetailComponent } from './historico-de-trabalho-detail.component';
import { HistoricoDeTrabalhoUpdateComponent } from './historico-de-trabalho-update.component';
import { HistoricoDeTrabalhoDeleteDialogComponent } from './historico-de-trabalho-delete-dialog.component';
import { historicoDeTrabalhoRoute } from './historico-de-trabalho.route';

@NgModule({
  imports: [ControleEmpregadosSharedModule, RouterModule.forChild(historicoDeTrabalhoRoute)],
  declarations: [
    HistoricoDeTrabalhoComponent,
    HistoricoDeTrabalhoDetailComponent,
    HistoricoDeTrabalhoUpdateComponent,
    HistoricoDeTrabalhoDeleteDialogComponent
  ],
  entryComponents: [HistoricoDeTrabalhoDeleteDialogComponent]
})
export class ControleEmpregadosHistoricoDeTrabalhoModule {}
