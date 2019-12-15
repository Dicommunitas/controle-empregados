import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILocalizacao } from 'app/shared/model/localizacao.model';

@Component({
  selector: 'jhi-localizacao-detail',
  templateUrl: './localizacao-detail.component.html'
})
export class LocalizacaoDetailComponent implements OnInit {
  localizacao: ILocalizacao;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ localizacao }) => {
      this.localizacao = localizacao;
    });
  }

  previousState() {
    window.history.back();
  }
}
