import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ITarefa, Tarefa } from 'app/shared/model/tarefa.model';
import { TarefaService } from './tarefa.service';
import { ITrabalho } from 'app/shared/model/trabalho.model';
import { TrabalhoService } from 'app/entities/trabalho/trabalho.service';

@Component({
  selector: 'jhi-tarefa-update',
  templateUrl: './tarefa-update.component.html'
})
export class TarefaUpdateComponent implements OnInit {
  isSaving: boolean;

  trabalhos: ITrabalho[];

  editForm = this.fb.group({
    id: [],
    titulo: [],
    descricao: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected tarefaService: TarefaService,
    protected trabalhoService: TrabalhoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tarefa }) => {
      this.updateForm(tarefa);
    });
    this.trabalhoService
      .query()
      .subscribe((res: HttpResponse<ITrabalho[]>) => (this.trabalhos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(tarefa: ITarefa) {
    this.editForm.patchValue({
      id: tarefa.id,
      titulo: tarefa.titulo,
      descricao: tarefa.descricao
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tarefa = this.createFromForm();
    if (tarefa.id !== undefined) {
      this.subscribeToSaveResponse(this.tarefaService.update(tarefa));
    } else {
      this.subscribeToSaveResponse(this.tarefaService.create(tarefa));
    }
  }

  private createFromForm(): ITarefa {
    return {
      ...new Tarefa(),
      id: this.editForm.get(['id']).value,
      titulo: this.editForm.get(['titulo']).value,
      descricao: this.editForm.get(['descricao']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarefa>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackTrabalhoById(index: number, item: ITrabalho) {
    return item.id;
  }

  getSelected(selectedVals: any[], option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
