import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDepartamento } from 'app/shared/model/departamento.model';

@Component({
  selector: 'jhi-departamento-detail',
  templateUrl: './departamento-detail.component.html'
})
export class DepartamentoDetailComponent implements OnInit {
  departamento: IDepartamento;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ departamento }) => {
      this.departamento = departamento;
    });
  }

  previousState() {
    window.history.back();
  }
}
