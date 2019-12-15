import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IHistoricoDeTrabalho, HistoricoDeTrabalho } from 'app/shared/model/historico-de-trabalho.model';
import { HistoricoDeTrabalhoService } from './historico-de-trabalho.service';
import { ITrabalho } from 'app/shared/model/trabalho.model';
import { TrabalhoService } from 'app/entities/trabalho/trabalho.service';
import { IDepartamento } from 'app/shared/model/departamento.model';
import { DepartamentoService } from 'app/entities/departamento/departamento.service';
import { IEmpregado } from 'app/shared/model/empregado.model';
import { EmpregadoService } from 'app/entities/empregado/empregado.service';

@Component({
  selector: 'jhi-historico-de-trabalho-update',
  templateUrl: './historico-de-trabalho-update.component.html'
})
export class HistoricoDeTrabalhoUpdateComponent implements OnInit {
  isSaving: boolean;

  trabalhos: ITrabalho[];

  departamentos: IDepartamento[];

  empregados: IEmpregado[];

  editForm = this.fb.group({
    id: [],
    dataInicial: [],
    dataFinal: [],
    lingua: [],
    trabalho: [],
    departamento: [],
    empregado: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected historicoDeTrabalhoService: HistoricoDeTrabalhoService,
    protected trabalhoService: TrabalhoService,
    protected departamentoService: DepartamentoService,
    protected empregadoService: EmpregadoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ historicoDeTrabalho }) => {
      this.updateForm(historicoDeTrabalho);
    });
    this.trabalhoService.query({ filter: 'historicodetrabalho-is-null' }).subscribe(
      (res: HttpResponse<ITrabalho[]>) => {
        if (!this.editForm.get('trabalho').value || !this.editForm.get('trabalho').value.id) {
          this.trabalhos = res.body;
        } else {
          this.trabalhoService
            .find(this.editForm.get('trabalho').value.id)
            .subscribe(
              (subRes: HttpResponse<ITrabalho>) => (this.trabalhos = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.departamentoService.query({ filter: 'historicodetrabalho-is-null' }).subscribe(
      (res: HttpResponse<IDepartamento[]>) => {
        if (!this.editForm.get('departamento').value || !this.editForm.get('departamento').value.id) {
          this.departamentos = res.body;
        } else {
          this.departamentoService
            .find(this.editForm.get('departamento').value.id)
            .subscribe(
              (subRes: HttpResponse<IDepartamento>) => (this.departamentos = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.empregadoService.query({ filter: 'historicodetrabalho-is-null' }).subscribe(
      (res: HttpResponse<IEmpregado[]>) => {
        if (!this.editForm.get('empregado').value || !this.editForm.get('empregado').value.id) {
          this.empregados = res.body;
        } else {
          this.empregadoService
            .find(this.editForm.get('empregado').value.id)
            .subscribe(
              (subRes: HttpResponse<IEmpregado>) => (this.empregados = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  updateForm(historicoDeTrabalho: IHistoricoDeTrabalho) {
    this.editForm.patchValue({
      id: historicoDeTrabalho.id,
      dataInicial: historicoDeTrabalho.dataInicial != null ? historicoDeTrabalho.dataInicial.format(DATE_TIME_FORMAT) : null,
      dataFinal: historicoDeTrabalho.dataFinal != null ? historicoDeTrabalho.dataFinal.format(DATE_TIME_FORMAT) : null,
      lingua: historicoDeTrabalho.lingua,
      trabalho: historicoDeTrabalho.trabalho,
      departamento: historicoDeTrabalho.departamento,
      empregado: historicoDeTrabalho.empregado
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const historicoDeTrabalho = this.createFromForm();
    if (historicoDeTrabalho.id !== undefined) {
      this.subscribeToSaveResponse(this.historicoDeTrabalhoService.update(historicoDeTrabalho));
    } else {
      this.subscribeToSaveResponse(this.historicoDeTrabalhoService.create(historicoDeTrabalho));
    }
  }

  private createFromForm(): IHistoricoDeTrabalho {
    return {
      ...new HistoricoDeTrabalho(),
      id: this.editForm.get(['id']).value,
      dataInicial:
        this.editForm.get(['dataInicial']).value != null ? moment(this.editForm.get(['dataInicial']).value, DATE_TIME_FORMAT) : undefined,
      dataFinal:
        this.editForm.get(['dataFinal']).value != null ? moment(this.editForm.get(['dataFinal']).value, DATE_TIME_FORMAT) : undefined,
      lingua: this.editForm.get(['lingua']).value,
      trabalho: this.editForm.get(['trabalho']).value,
      departamento: this.editForm.get(['departamento']).value,
      empregado: this.editForm.get(['empregado']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHistoricoDeTrabalho>>) {
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

  trackDepartamentoById(index: number, item: IDepartamento) {
    return item.id;
  }

  trackEmpregadoById(index: number, item: IEmpregado) {
    return item.id;
  }
}
