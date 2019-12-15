import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHistoricoDeTrabalho } from 'app/shared/model/historico-de-trabalho.model';

@Component({
  selector: 'jhi-historico-de-trabalho-detail',
  templateUrl: './historico-de-trabalho-detail.component.html'
})
export class HistoricoDeTrabalhoDetailComponent implements OnInit {
  historicoDeTrabalho: IHistoricoDeTrabalho;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ historicoDeTrabalho }) => {
      this.historicoDeTrabalho = historicoDeTrabalho;
    });
  }

  previousState() {
    window.history.back();
  }
}
