import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ControleEmpregadosSharedModule } from 'app/shared/shared.module';
import { LocalizacaoComponent } from './localizacao.component';
import { LocalizacaoDetailComponent } from './localizacao-detail.component';
import { LocalizacaoUpdateComponent } from './localizacao-update.component';
import { LocalizacaoDeleteDialogComponent } from './localizacao-delete-dialog.component';
import { localizacaoRoute } from './localizacao.route';

@NgModule({
  imports: [ControleEmpregadosSharedModule, RouterModule.forChild(localizacaoRoute)],
  declarations: [LocalizacaoComponent, LocalizacaoDetailComponent, LocalizacaoUpdateComponent, LocalizacaoDeleteDialogComponent],
  entryComponents: [LocalizacaoDeleteDialogComponent]
})
export class ControleEmpregadosLocalizacaoModule {}
