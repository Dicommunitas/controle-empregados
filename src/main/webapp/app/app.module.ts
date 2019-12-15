import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { ControleEmpregadosSharedModule } from 'app/shared/shared.module';
import { ControleEmpregadosCoreModule } from 'app/core/core.module';
import { ControleEmpregadosAppRoutingModule } from './app-routing.module';
import { ControleEmpregadosHomeModule } from './home/home.module';
import { ControleEmpregadosEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    ControleEmpregadosSharedModule,
    ControleEmpregadosCoreModule,
    ControleEmpregadosHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    ControleEmpregadosEntityModule,
    ControleEmpregadosAppRoutingModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [JhiMainComponent]
})
export class ControleEmpregadosAppModule {}
