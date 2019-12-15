import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPais } from 'app/shared/model/pais.model';
import { PaisService } from './pais.service';
import { PaisDeleteDialogComponent } from './pais-delete-dialog.component';

@Component({
  selector: 'jhi-pais',
  templateUrl: './pais.component.html'
})
export class PaisComponent implements OnInit, OnDestroy {
  pais: IPais[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected paisService: PaisService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll() {
    if (this.currentSearch) {
      this.paisService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IPais[]>) => (this.pais = res.body));
      return;
    }
    this.paisService.query().subscribe((res: HttpResponse<IPais[]>) => {
      this.pais = res.body;
      this.currentSearch = '';
    });
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.loadAll();
  }

  clear() {
    this.currentSearch = '';
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInPais();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPais) {
    return item.id;
  }

  registerChangeInPais() {
    this.eventSubscriber = this.eventManager.subscribe('paisListModification', () => this.loadAll());
  }

  delete(pais: IPais) {
    const modalRef = this.modalService.open(PaisDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pais = pais;
  }
}
