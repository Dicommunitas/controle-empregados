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
import { IEmpregado, Empregado } from 'app/shared/model/empregado.model';
import { EmpregadoService } from './empregado.service';
import { IDepartamento } from 'app/shared/model/departamento.model';
import { DepartamentoService } from 'app/entities/departamento/departamento.service';

@Component({
  selector: 'jhi-empregado-update',
  templateUrl: './empregado-update.component.html'
})
export class EmpregadoUpdateComponent implements OnInit {
  isSaving: boolean;

  empregados: IEmpregado[];

  departamentos: IDepartamento[];

  editForm = this.fb.group({
    id: [],
    primeiroNome: [],
    sobrenome: [],
    email: [],
    telefone: [],
    dataContratacao: [],
    salario: [],
    comissao: [],
    gerente: [],
    departamento: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected empregadoService: EmpregadoService,
    protected departamentoService: DepartamentoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ empregado }) => {
      this.updateForm(empregado);
    });
    this.empregadoService
      .query()
      .subscribe((res: HttpResponse<IEmpregado[]>) => (this.empregados = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.departamentoService
      .query()
      .subscribe(
        (res: HttpResponse<IDepartamento[]>) => (this.departamentos = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(empregado: IEmpregado) {
    this.editForm.patchValue({
      id: empregado.id,
      primeiroNome: empregado.primeiroNome,
      sobrenome: empregado.sobrenome,
      email: empregado.email,
      telefone: empregado.telefone,
      dataContratacao: empregado.dataContratacao != null ? empregado.dataContratacao.format(DATE_TIME_FORMAT) : null,
      salario: empregado.salario,
      comissao: empregado.comissao,
      gerente: empregado.gerente,
      departamento: empregado.departamento
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const empregado = this.createFromForm();
    if (empregado.id !== undefined) {
      this.subscribeToSaveResponse(this.empregadoService.update(empregado));
    } else {
      this.subscribeToSaveResponse(this.empregadoService.create(empregado));
    }
  }

  private createFromForm(): IEmpregado {
    return {
      ...new Empregado(),
      id: this.editForm.get(['id']).value,
      primeiroNome: this.editForm.get(['primeiroNome']).value,
      sobrenome: this.editForm.get(['sobrenome']).value,
      email: this.editForm.get(['email']).value,
      telefone: this.editForm.get(['telefone']).value,
      dataContratacao:
        this.editForm.get(['dataContratacao']).value != null
          ? moment(this.editForm.get(['dataContratacao']).value, DATE_TIME_FORMAT)
          : undefined,
      salario: this.editForm.get(['salario']).value,
      comissao: this.editForm.get(['comissao']).value,
      gerente: this.editForm.get(['gerente']).value,
      departamento: this.editForm.get(['departamento']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmpregado>>) {
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

  trackEmpregadoById(index: number, item: IEmpregado) {
    return item.id;
  }

  trackDepartamentoById(index: number, item: IDepartamento) {
    return item.id;
  }
}
