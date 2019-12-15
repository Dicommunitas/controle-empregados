import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ControleEmpregadosTestModule } from '../../../test.module';
import { LocalizacaoDeleteDialogComponent } from 'app/entities/localizacao/localizacao-delete-dialog.component';
import { LocalizacaoService } from 'app/entities/localizacao/localizacao.service';

describe('Component Tests', () => {
  describe('Localizacao Management Delete Component', () => {
    let comp: LocalizacaoDeleteDialogComponent;
    let fixture: ComponentFixture<LocalizacaoDeleteDialogComponent>;
    let service: LocalizacaoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControleEmpregadosTestModule],
        declarations: [LocalizacaoDeleteDialogComponent]
      })
        .overrideTemplate(LocalizacaoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LocalizacaoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocalizacaoService);
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
