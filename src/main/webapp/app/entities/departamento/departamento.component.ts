import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDepartamento } from 'app/shared/model/departamento.model';
import { DepartamentoService } from './departamento.service';
import { DepartamentoDeleteDialogComponent } from './departamento-delete-dialog.component';

@Component({
  selector: 'jhi-departamento',
  templateUrl: './departamento.component.html'
})
export class DepartamentoComponent implements OnInit, OnDestroy {
  departamentos: IDepartamento[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected departamentoService: DepartamentoService,
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
      this.departamentoService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IDepartamento[]>) => (this.departamentos = res.body));
      return;
    }
    this.departamentoService.query().subscribe((res: HttpResponse<IDepartamento[]>) => {
      this.departamentos = res.body;
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
    this.registerChangeInDepartamentos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDepartamento) {
    return item.id;
  }

  registerChangeInDepartamentos() {
    this.eventSubscriber = this.eventManager.subscribe('departamentoListModification', () => this.loadAll());
  }

  delete(departamento: IDepartamento) {
    const modalRef = this.modalService.open(DepartamentoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.departamento = departamento;
  }
}
