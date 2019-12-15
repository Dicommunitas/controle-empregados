import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPais } from 'app/shared/model/pais.model';
import { PaisService } from './pais.service';

@Component({
  templateUrl: './pais-delete-dialog.component.html'
})
export class PaisDeleteDialogComponent {
  pais: IPais;

  constructor(protected paisService: PaisService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.paisService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'paisListModification',
        content: 'Deleted an pais'
      });
      this.activeModal.dismiss(true);
    });
  }
}
