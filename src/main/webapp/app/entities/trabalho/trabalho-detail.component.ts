import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrabalho } from 'app/shared/model/trabalho.model';

@Component({
  selector: 'jhi-trabalho-detail',
  templateUrl: './trabalho-detail.component.html'
})
export class TrabalhoDetailComponent implements OnInit {
  trabalho: ITrabalho;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ trabalho }) => {
      this.trabalho = trabalho;
    });
  }

  previousState() {
    window.history.back();
  }
}
