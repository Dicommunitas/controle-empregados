import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRegiao } from 'app/shared/model/regiao.model';

@Component({
  selector: 'jhi-regiao-detail',
  templateUrl: './regiao-detail.component.html'
})
export class RegiaoDetailComponent implements OnInit {
  regiao: IRegiao;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ regiao }) => {
      this.regiao = regiao;
    });
  }

  previousState() {
    window.history.back();
  }
}
