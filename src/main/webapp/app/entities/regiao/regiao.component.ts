import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRegiao } from 'app/shared/model/regiao.model';
import { RegiaoService } from './regiao.service';
import { RegiaoDeleteDialogComponent } from './regiao-delete-dialog.component';

@Component({
  selector: 'jhi-regiao',
  templateUrl: './regiao.component.html'
})
export class RegiaoComponent implements OnInit, OnDestroy {
  regiaos: IRegiao[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected regiaoService: RegiaoService,
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
      this.regiaoService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IRegiao[]>) => (this.regiaos = res.body));
      return;
    }
    this.regiaoService.query().subscribe((res: HttpResponse<IRegiao[]>) => {
      this.regiaos = res.body;
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
    this.registerChangeInRegiaos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRegiao) {
    return item.id;
  }

  registerChangeInRegiaos() {
    this.eventSubscriber = this.eventManager.subscribe('regiaoListModification', () => this.loadAll());
  }

  delete(regiao: IRegiao) {
    const modalRef = this.modalService.open(RegiaoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.regiao = regiao;
  }
}
