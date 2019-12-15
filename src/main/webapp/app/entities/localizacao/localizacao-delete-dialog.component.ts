import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILocalizacao } from 'app/shared/model/localizacao.model';
import { LocalizacaoService } from './localizacao.service';

@Component({
  templateUrl: './localizacao-delete-dialog.component.html'
})
export class LocalizacaoDeleteDialogComponent {
  localizacao: ILocalizacao;

  constructor(
    protected localizacaoService: LocalizacaoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.localizacaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'localizacaoListModification',
        content: 'Deleted an localizacao'
      });
      this.activeModal.dismiss(true);
    });
  }
}
