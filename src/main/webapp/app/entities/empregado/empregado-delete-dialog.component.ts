import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmpregado } from 'app/shared/model/empregado.model';
import { EmpregadoService } from './empregado.service';

@Component({
  templateUrl: './empregado-delete-dialog.component.html'
})
export class EmpregadoDeleteDialogComponent {
  empregado: IEmpregado;

  constructor(protected empregadoService: EmpregadoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.empregadoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'empregadoListModification',
        content: 'Deleted an empregado'
      });
      this.activeModal.dismiss(true);
    });
  }
}
