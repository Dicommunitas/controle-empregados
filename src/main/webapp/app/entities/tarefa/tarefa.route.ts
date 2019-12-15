import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Tarefa } from 'app/shared/model/tarefa.model';
import { TarefaService } from './tarefa.service';
import { TarefaComponent } from './tarefa.component';
import { TarefaDetailComponent } from './tarefa-detail.component';
import { TarefaUpdateComponent } from './tarefa-update.component';
import { ITarefa } from 'app/shared/model/tarefa.model';

@Injectable({ providedIn: 'root' })
export class TarefaResolve implements Resolve<ITarefa> {
  constructor(private service: TarefaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITarefa> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((tarefa: HttpResponse<Tarefa>) => tarefa.body));
    }
    return of(new Tarefa());
  }
}

export const tarefaRoute: Routes = [
  {
    path: '',
    component: TarefaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'controleEmpregadosApp.tarefa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TarefaDetailComponent,
    resolve: {
      tarefa: TarefaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'controleEmpregadosApp.tarefa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TarefaUpdateComponent,
    resolve: {
      tarefa: TarefaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'controleEmpregadosApp.tarefa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TarefaUpdateComponent,
    resolve: {
      tarefa: TarefaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'controleEmpregadosApp.tarefa.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
