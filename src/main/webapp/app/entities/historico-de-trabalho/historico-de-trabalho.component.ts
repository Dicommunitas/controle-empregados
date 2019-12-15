import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHistoricoDeTrabalho } from 'app/shared/model/historico-de-trabalho.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { HistoricoDeTrabalhoService } from './historico-de-trabalho.service';
import { HistoricoDeTrabalhoDeleteDialogComponent } from './historico-de-trabalho-delete-dialog.component';

@Component({
  selector: 'jhi-historico-de-trabalho',
  templateUrl: './historico-de-trabalho.component.html'
})
export class HistoricoDeTrabalhoComponent implements OnInit, OnDestroy {
  historicoDeTrabalhos: IHistoricoDeTrabalho[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;
  currentSearch: string;

  constructor(
    protected historicoDeTrabalhoService: HistoricoDeTrabalhoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute
  ) {
    this.historicoDeTrabalhos = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll() {
    if (this.currentSearch) {
      this.historicoDeTrabalhoService
        .search({
          query: this.currentSearch,
          page: this.page,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe((res: HttpResponse<IHistoricoDeTrabalho[]>) => this.paginateHistoricoDeTrabalhos(res.body, res.headers));
      return;
    }
    this.historicoDeTrabalhoService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IHistoricoDeTrabalho[]>) => this.paginateHistoricoDeTrabalhos(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.historicoDeTrabalhos = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  clear() {
    this.historicoDeTrabalhos = [];
    this.links = {
      last: 0
    };
    this.page = 0;
    this.predicate = 'id';
    this.reverse = true;
    this.currentSearch = '';
    this.loadAll();
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.historicoDeTrabalhos = [];
    this.links = {
      last: 0
    };
    this.page = 0;
    this.predicate = '_score';
    this.reverse = false;
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInHistoricoDeTrabalhos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IHistoricoDeTrabalho) {
    return item.id;
  }

  registerChangeInHistoricoDeTrabalhos() {
    this.eventSubscriber = this.eventManager.subscribe('historicoDeTrabalhoListModification', () => this.reset());
  }

  delete(historicoDeTrabalho: IHistoricoDeTrabalho) {
    const modalRef = this.modalService.open(HistoricoDeTrabalhoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.historicoDeTrabalho = historicoDeTrabalho;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateHistoricoDeTrabalhos(data: IHistoricoDeTrabalho[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.historicoDeTrabalhos.push(data[i]);
    }
  }
}
