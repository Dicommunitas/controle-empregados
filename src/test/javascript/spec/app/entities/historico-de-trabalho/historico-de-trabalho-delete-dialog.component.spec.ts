import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ControleEmpregadosTestModule } from '../../../test.module';
import { HistoricoDeTrabalhoDeleteDialogComponent } from 'app/entities/historico-de-trabalho/historico-de-trabalho-delete-dialog.component';
import { HistoricoDeTrabalhoService } from 'app/entities/historico-de-trabalho/historico-de-trabalho.service';

describe('Component Tests', () => {
  describe('HistoricoDeTrabalho Management Delete Component', () => {
    let comp: HistoricoDeTrabalhoDeleteDialogComponent;
    let fixture: ComponentFixture<HistoricoDeTrabalhoDeleteDialogComponent>;
    let service: HistoricoDeTrabalhoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControleEmpregadosTestModule],
        declarations: [HistoricoDeTrabalhoDeleteDialogComponent]
      })
        .overrideTemplate(HistoricoDeTrabalhoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HistoricoDeTrabalhoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HistoricoDeTrabalhoService);
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
