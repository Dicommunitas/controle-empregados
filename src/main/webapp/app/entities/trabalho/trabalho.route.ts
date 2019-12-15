import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Trabalho } from 'app/shared/model/trabalho.model';
import { TrabalhoService } from './trabalho.service';
import { TrabalhoComponent } from './trabalho.component';
import { TrabalhoDetailComponent } from './trabalho-detail.component';
import { TrabalhoUpdateComponent } from './trabalho-update.component';
import { ITrabalho } from 'app/shared/model/trabalho.model';

@Injectable({ providedIn: 'root' })
export class TrabalhoResolve implements Resolve<ITrabalho> {
  constructor(private service: TrabalhoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITrabalho> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((trabalho: HttpResponse<Trabalho>) => trabalho.body));
    }
    return of(new Trabalho());
  }
}

export const trabalhoRoute: Routes = [
  {
    path: '',
    component: TrabalhoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'controleEmpregadosApp.trabalho.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TrabalhoDetailComponent,
    resolve: {
      trabalho: TrabalhoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'controleEmpregadosApp.trabalho.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TrabalhoUpdateComponent,
    resolve: {
      trabalho: TrabalhoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'controleEmpregadosApp.trabalho.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TrabalhoUpdateComponent,
    resolve: {
      trabalho: TrabalhoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'controleEmpregadosApp.trabalho.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
