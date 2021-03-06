import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ControleEmpregadosTestModule } from '../../../test.module';
import { PaisDeleteDialogComponent } from 'app/entities/pais/pais-delete-dialog.component';
import { PaisService } from 'app/entities/pais/pais.service';

describe('Component Tests', () => {
  describe('Pais Management Delete Component', () => {
    let comp: PaisDeleteDialogComponent;
    let fixture: ComponentFixture<PaisDeleteDialogComponent>;
    let service: PaisService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControleEmpregadosTestModule],
        declarations: [PaisDeleteDialogComponent]
      })
        .overrideTemplate(PaisDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaisDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaisService);
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
