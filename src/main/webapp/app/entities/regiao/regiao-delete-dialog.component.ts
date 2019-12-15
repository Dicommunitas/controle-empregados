import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRegiao } from 'app/shared/model/regiao.model';
import { RegiaoService } from './regiao.service';

@Component({
  templateUrl: './regiao-delete-dialog.component.html'
})
export class RegiaoDeleteDialogComponent {
  regiao: IRegiao;

  constructor(protected regiaoService: RegiaoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.regiaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'regiaoListModification',
        content: 'Deleted an regiao'
      });
      this.activeModal.dismiss(true);
    });
  }
}
