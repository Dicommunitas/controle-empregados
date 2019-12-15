import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ControleEmpregadosTestModule } from '../../../test.module';
import { RegiaoDeleteDialogComponent } from 'app/entities/regiao/regiao-delete-dialog.component';
import { RegiaoService } from 'app/entities/regiao/regiao.service';

describe('Component Tests', () => {
  describe('Regiao Management Delete Component', () => {
    let comp: RegiaoDeleteDialogComponent;
    let fixture: ComponentFixture<RegiaoDeleteDialogComponent>;
    let service: RegiaoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControleEmpregadosTestModule],
        declarations: [RegiaoDeleteDialogComponent]
      })
        .overrideTemplate(RegiaoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RegiaoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RegiaoService);
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
