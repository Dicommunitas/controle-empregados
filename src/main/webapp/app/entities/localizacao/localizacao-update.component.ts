import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ILocalizacao, Localizacao } from 'app/shared/model/localizacao.model';
import { LocalizacaoService } from './localizacao.service';
import { IPais } from 'app/shared/model/pais.model';
import { PaisService } from 'app/entities/pais/pais.service';

@Component({
  selector: 'jhi-localizacao-update',
  templateUrl: './localizacao-update.component.html'
})
export class LocalizacaoUpdateComponent implements OnInit {
  isSaving: boolean;

  pais: IPais[];

  editForm = this.fb.group({
    id: [],
    endereco: [],
    cep: [],
    cidade: [],
    bairro: [],
    pais: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected localizacaoService: LocalizacaoService,
    protected paisService: PaisService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ localizacao }) => {
      this.updateForm(localizacao);
    });
    this.paisService.query({ filter: 'localizacao-is-null' }).subscribe(
      (res: HttpResponse<IPais[]>) => {
        if (!this.editForm.get('pais').value || !this.editForm.get('pais').value.id) {
          this.pais = res.body;
        } else {
          this.paisService
            .find(this.editForm.get('pais').value.id)
            .subscribe(
              (subRes: HttpResponse<IPais>) => (this.pais = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  updateForm(localizacao: ILocalizacao) {
    this.editForm.patchValue({
      id: localizacao.id,
      endereco: localizacao.endereco,
      cep: localizacao.cep,
      cidade: localizacao.cidade,
      bairro: localizacao.bairro,
      pais: localizacao.pais
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const localizacao = this.createFromForm();
    if (localizacao.id !== undefined) {
      this.subscribeToSaveResponse(this.localizacaoService.update(localizacao));
    } else {
      this.subscribeToSaveResponse(this.localizacaoService.create(localizacao));
    }
  }

  private createFromForm(): ILocalizacao {
    return {
      ...new Localizacao(),
      id: this.editForm.get(['id']).value,
      endereco: this.editForm.get(['endereco']).value,
      cep: this.editForm.get(['cep']).value,
      cidade: this.editForm.get(['cidade']).value,
      bairro: this.editForm.get(['bairro']).value,
      pais: this.editForm.get(['pais']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocalizacao>>) {
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

  trackPaisById(index: number, item: IPais) {
    return item.id;
  }
}
