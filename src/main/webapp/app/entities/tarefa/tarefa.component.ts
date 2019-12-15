import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITarefa } from 'app/shared/model/tarefa.model';
import { TarefaService } from './tarefa.service';
import { TarefaDeleteDialogComponent } from './tarefa-delete-dialog.component';

@Component({
  selector: 'jhi-tarefa',
  templateUrl: './tarefa.component.html'
})
export class TarefaComponent implements OnInit, OnDestroy {
  tarefas: ITarefa[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected tarefaService: TarefaService,
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
      this.tarefaService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ITarefa[]>) => (this.tarefas = res.body));
      return;
    }
    this.tarefaService.query().subscribe((res: HttpResponse<ITarefa[]>) => {
      this.tarefas = res.body;
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
    this.registerChangeInTarefas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITarefa) {
    return item.id;
  }

  registerChangeInTarefas() {
    this.eventSubscriber = this.eventManager.subscribe('tarefaListModification', () => this.loadAll());
  }

  delete(tarefa: ITarefa) {
    const modalRef = this.modalService.open(TarefaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tarefa = tarefa;
  }
}
