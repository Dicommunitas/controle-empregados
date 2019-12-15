import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { HistoricoDeTrabalho } from 'app/shared/model/historico-de-trabalho.model';
import { HistoricoDeTrabalhoService } from './historico-de-trabalho.service';
import { HistoricoDeTrabalhoComponent } from './historico-de-trabalho.component';
import { HistoricoDeTrabalhoDetailComponent } from './historico-de-trabalho-detail.component';
import { HistoricoDeTrabalhoUpdateComponent } from './historico-de-trabalho-update.component';
import { IHistoricoDeTrabalho } from 'app/shared/model/historico-de-trabalho.model';

@Injectable({ providedIn: 'root' })
export class HistoricoDeTrabalhoResolve implements Resolve<IHistoricoDeTrabalho> {
  constructor(private service: HistoricoDeTrabalhoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHistoricoDeTrabalho> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((historicoDeTrabalho: HttpResponse<HistoricoDeTrabalho>) => historicoDeTrabalho.body));
    }
    return of(new HistoricoDeTrabalho());
  }
}

export const historicoDeTrabalhoRoute: Routes = [
  {
    path: '',
    component: HistoricoDeTrabalhoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'controleEmpregadosApp.historicoDeTrabalho.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: HistoricoDeTrabalhoDetailComponent,
    resolve: {
      historicoDeTrabalho: HistoricoDeTrabalhoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'controleEmpregadosApp.historicoDeTrabalho.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: HistoricoDeTrabalhoUpdateComponent,
    resolve: {
      historicoDeTrabalho: HistoricoDeTrabalhoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'controleEmpregadosApp.historicoDeTrabalho.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: HistoricoDeTrabalhoUpdateComponent,
    resolve: {
      historicoDeTrabalho: HistoricoDeTrabalhoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'controleEmpregadosApp.historicoDeTrabalho.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
