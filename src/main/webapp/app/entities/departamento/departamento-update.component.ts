import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IDepartamento, Departamento } from 'app/shared/model/departamento.model';
import { DepartamentoService } from './departamento.service';
import { ILocalizacao } from 'app/shared/model/localizacao.model';
import { LocalizacaoService } from 'app/entities/localizacao/localizacao.service';

@Component({
  selector: 'jhi-departamento-update',
  templateUrl: './departamento-update.component.html'
})
export class DepartamentoUpdateComponent implements OnInit {
  isSaving: boolean;

  localizacaos: ILocalizacao[];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    localizacao: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected departamentoService: DepartamentoService,
    protected localizacaoService: LocalizacaoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ departamento }) => {
      this.updateForm(departamento);
    });
    this.localizacaoService.query({ filter: 'departamento-is-null' }).subscribe(
      (res: HttpResponse<ILocalizacao[]>) => {
        if (!this.editForm.get('localizacao').value || !this.editForm.get('localizacao').value.id) {
          this.localizacaos = res.body;
        } else {
          this.localizacaoService
            .find(this.editForm.get('localizacao').value.id)
            .subscribe(
              (subRes: HttpResponse<ILocalizacao>) => (this.localizacaos = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  updateForm(departamento: IDepartamento) {
    this.editForm.patchValue({
      id: departamento.id,
      nome: departamento.nome,
      localizacao: departamento.localizacao
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const departamento = this.createFromForm();
    if (departamento.id !== undefined) {
      this.subscribeToSaveResponse(this.departamentoService.update(departamento));
    } else {
      this.subscribeToSaveResponse(this.departamentoService.create(departamento));
    }
  }

  private createFromForm(): IDepartamento {
    return {
      ...new Departamento(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      localizacao: this.editForm.get(['localizacao']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepartamento>>) {
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

  trackLocalizacaoById(index: number, item: ILocalizacao) {
    return item.id;
  }
}
