import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Regiao } from 'app/shared/model/regiao.model';
import { RegiaoService } from './regiao.service';
import { RegiaoComponent } from './regiao.component';
import { RegiaoDetailComponent } from './regiao-detail.component';
import { RegiaoUpdateComponent } from './regiao-update.component';
import { IRegiao } from 'app/shared/model/regiao.model';

@Injectable({ providedIn: 'root' })
export class RegiaoResolve implements Resolve<IRegiao> {
  constructor(private service: RegiaoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRegiao> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((regiao: HttpResponse<Regiao>) => regiao.body));
    }
    return of(new Regiao());
  }
}

export const regiaoRoute: Routes = [
  {
    path: '',
    component: RegiaoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'controleEmpregadosApp.regiao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RegiaoDetailComponent,
    resolve: {
      regiao: RegiaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'controleEmpregadosApp.regiao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RegiaoUpdateComponent,
    resolve: {
      regiao: RegiaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'controleEmpregadosApp.regiao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RegiaoUpdateComponent,
    resolve: {
      regiao: RegiaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'controleEmpregadosApp.regiao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
