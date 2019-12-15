import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ControleEmpregadosTestModule } from '../../../test.module';
import { TarefaDeleteDialogComponent } from 'app/entities/tarefa/tarefa-delete-dialog.component';
import { TarefaService } from 'app/entities/tarefa/tarefa.service';

describe('Component Tests', () => {
  describe('Tarefa Management Delete Component', () => {
    let comp: TarefaDeleteDialogComponent;
    let fixture: ComponentFixture<TarefaDeleteDialogComponent>;
    let service: TarefaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControleEmpregadosTestModule],
        declarations: [TarefaDeleteDialogComponent]
      })
        .overrideTemplate(TarefaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TarefaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TarefaService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
