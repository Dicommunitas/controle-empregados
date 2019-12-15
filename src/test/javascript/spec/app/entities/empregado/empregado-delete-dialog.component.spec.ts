import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ControleEmpregadosTestModule } from '../../../test.module';
import { EmpregadoDeleteDialogComponent } from 'app/entities/empregado/empregado-delete-dialog.component';
import { EmpregadoService } from 'app/entities/empregado/empregado.service';

describe('Component Tests', () => {
  describe('Empregado Management Delete Component', () => {
    let comp: EmpregadoDeleteDialogComponent;
    let fixture: ComponentFixture<EmpregadoDeleteDialogComponent>;
    let service: EmpregadoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControleEmpregadosTestModule],
        declarations: [EmpregadoDeleteDialogComponent]
      })
        .overrideTemplate(EmpregadoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmpregadoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmpregadoService);
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
