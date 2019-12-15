import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILocalizacao } from 'app/shared/model/localizacao.model';
import { LocalizacaoService } from './localizacao.service';
import { LocalizacaoDeleteDialogComponent } from './localizacao-delete-dialog.component';

@Component({
  selector: 'jhi-localizacao',
  templateUrl: './localizacao.component.html'
})
export class LocalizacaoComponent implements OnInit, OnDestroy {
  localizacaos: ILocalizacao[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected localizacaoService: LocalizacaoService,
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
      this.localizacaoService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ILocalizacao[]>) => (this.localizacaos = res.body));
      return;
    }
    this.localizacaoService.query().subscribe((res: HttpResponse<ILocalizacao[]>) => {
      this.localizacaos = res.body;
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
    this.registerChangeInLocalizacaos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILocalizacao) {
    return item.id;
  }

  registerChangeInLocalizacaos() {
    this.eventSubscriber = this.eventManager.subscribe('localizacaoListModification', () => this.loadAll());
  }

  delete(localizacao: ILocalizacao) {
    const modalRef = this.modalService.open(LocalizacaoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.localizacao = localizacao;
  }
}
