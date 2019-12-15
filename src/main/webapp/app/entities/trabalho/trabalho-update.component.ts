import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ITrabalho, Trabalho } from 'app/shared/model/trabalho.model';
import { TrabalhoService } from './trabalho.service';
import { ITarefa } from 'app/shared/model/tarefa.model';
import { TarefaService } from 'app/entities/tarefa/tarefa.service';
import { IEmpregado } from 'app/shared/model/empregado.model';
import { EmpregadoService } from 'app/entities/empregado/empregado.service';

@Component({
  selector: 'jhi-trabalho-update',
  templateUrl: './trabalho-update.component.html'
})
export class TrabalhoUpdateComponent implements OnInit {
  isSaving: boolean;

  tarefas: ITarefa[];

  empregados: IEmpregado[];

  editForm = this.fb.group({
    id: [],
    titulo: [],
    salarioMinimo: [],
    salarioMaximo: [],
    tarefas: [],
    empregado: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected trabalhoService: TrabalhoService,
    protected tarefaService: TarefaService,
    protected empregadoService: EmpregadoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ trabalho }) => {
      this.updateForm(trabalho);
    });
    this.tarefaService
      .query()
      .subscribe((res: HttpResponse<ITarefa[]>) => (this.tarefas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.empregadoService
      .query()
      .subscribe((res: HttpResponse<IEmpregado[]>) => (this.empregados = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(trabalho: ITrabalho) {
    this.editForm.patchValue({
      id: trabalho.id,
      titulo: trabalho.titulo,
      salarioMinimo: trabalho.salarioMinimo,
      salarioMaximo: trabalho.salarioMaximo,
      tarefas: trabalho.tarefas,
      empregado: trabalho.empregado
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const trabalho = this.createFromForm();
    if (trabalho.id !== undefined) {
      this.subscribeToSaveResponse(this.trabalhoService.update(trabalho));
    } else {
      this.subscribeToSaveResponse(this.trabalhoService.create(trabalho));
    }
  }

  private createFromForm(): ITrabalho {
    return {
      ...new Trabalho(),
      id: this.editForm.get(['id']).value,
      titulo: this.editForm.get(['titulo']).value,
      salarioMinimo: this.editForm.get(['salarioMinimo']).value,
      salarioMaximo: this.editForm.get(['salarioMaximo']).value,
      tarefas: this.editForm.get(['tarefas']).value,
      empregado: this.editForm.get(['empregado']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrabalho>>) {
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

  trackTarefaById(index: number, item: ITarefa) {
    return item.id;
  }

  trackEmpregadoById(index: number, item: IEmpregado) {
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
