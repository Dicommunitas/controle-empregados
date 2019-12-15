import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Localizacao } from 'app/shared/model/localizacao.model';
import { LocalizacaoService } from './localizacao.service';
import { LocalizacaoComponent } from './localizacao.component';
import { LocalizacaoDetailComponent } from './localizacao-detail.component';
import { LocalizacaoUpdateComponent } from './localizacao-update.component';
import { ILocalizacao } from 'app/shared/model/localizacao.model';

@Injectable({ providedIn: 'root' })
export class LocalizacaoResolve implements Resolve<ILocalizacao> {
  constructor(private service: LocalizacaoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILocalizacao> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((localizacao: HttpResponse<Localizacao>) => localizacao.body));
    }
    return of(new Localizacao());
  }
}

export const localizacaoRoute: Routes = [
  {
    path: '',
    component: LocalizacaoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'controleEmpregadosApp.localizacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LocalizacaoDetailComponent,
    resolve: {
      localizacao: LocalizacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'controleEmpregadosApp.localizacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LocalizacaoUpdateComponent,
    resolve: {
      localizacao: LocalizacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'controleEmpregadosApp.localizacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LocalizacaoUpdateComponent,
    resolve: {
      localizacao: LocalizacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'controleEmpregadosApp.localizacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
