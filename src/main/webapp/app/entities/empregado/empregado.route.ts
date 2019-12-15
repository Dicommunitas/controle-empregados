import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Empregado } from 'app/shared/model/empregado.model';
import { EmpregadoService } from './empregado.service';
import { EmpregadoComponent } from './empregado.component';
import { EmpregadoDetailComponent } from './empregado-detail.component';
import { EmpregadoUpdateComponent } from './empregado-update.component';
import { IEmpregado } from 'app/shared/model/empregado.model';

@Injectable({ providedIn: 'root' })
export class EmpregadoResolve implements Resolve<IEmpregado> {
  constructor(private service: EmpregadoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmpregado> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((empregado: HttpResponse<Empregado>) => empregado.body));
    }
    return of(new Empregado());
  }
}

export const empregadoRoute: Routes = [
  {
    path: '',
    component: EmpregadoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'controleEmpregadosApp.empregado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EmpregadoDetailComponent,
    resolve: {
      empregado: EmpregadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'controleEmpregadosApp.empregado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EmpregadoUpdateComponent,
    resolve: {
      empregado: EmpregadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'controleEmpregadosApp.empregado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EmpregadoUpdateComponent,
    resolve: {
      empregado: EmpregadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'controleEmpregadosApp.empregado.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
