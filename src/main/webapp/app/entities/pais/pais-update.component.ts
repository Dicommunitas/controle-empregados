import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IPais, Pais } from 'app/shared/model/pais.model';
import { PaisService } from './pais.service';
import { IRegiao } from 'app/shared/model/regiao.model';
import { RegiaoService } from 'app/entities/regiao/regiao.service';

@Component({
  selector: 'jhi-pais-update',
  templateUrl: './pais-update.component.html'
})
export class PaisUpdateComponent implements OnInit {
  isSaving: boolean;

  regiaos: IRegiao[];

  editForm = this.fb.group({
    id: [],
    nome: [],
    regiao: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected paisService: PaisService,
    protected regiaoService: RegiaoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pais }) => {
      this.updateForm(pais);
    });
    this.regiaoService.query({ filter: 'pais-is-null' }).subscribe(
      (res: HttpResponse<IRegiao[]>) => {
        if (!this.editForm.get('regiao').value || !this.editForm.get('regiao').value.id) {
          this.regiaos = res.body;
        } else {
          this.regiaoService
            .find(this.editForm.get('regiao').value.id)
            .subscribe(
              (subRes: HttpResponse<IRegiao>) => (this.regiaos = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  updateForm(pais: IPais) {
    this.editForm.patchValue({
      id: pais.id,
      nome: pais.nome,
      regiao: pais.regiao
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pais = this.createFromForm();
    if (pais.id !== undefined) {
      this.subscribeToSaveResponse(this.paisService.update(pais));
    } else {
      this.subscribeToSaveResponse(this.paisService.create(pais));
    }
  }

  private createFromForm(): IPais {
    return {
      ...new Pais(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      regiao: this.editForm.get(['regiao']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPais>>) {
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

  trackRegiaoById(index: number, item: IRegiao) {
    return item.id;
  }
}
