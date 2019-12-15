import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ControleEmpregadosTestModule } from '../../../test.module';
import { TrabalhoDeleteDialogComponent } from 'app/entities/trabalho/trabalho-delete-dialog.component';
import { TrabalhoService } from 'app/entities/trabalho/trabalho.service';

describe('Component Tests', () => {
  describe('Trabalho Management Delete Component', () => {
    let comp: TrabalhoDeleteDialogComponent;
    let fixture: ComponentFixture<TrabalhoDeleteDialogComponent>;
    let service: TrabalhoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControleEmpregadosTestModule],
        declarations: [TrabalhoDeleteDialogComponent]
      })
        .overrideTemplate(TrabalhoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TrabalhoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrabalhoService);
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
