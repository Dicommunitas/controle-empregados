import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ControleEmpregadosTestModule } from '../../../test.module';
import { HistoricoDeTrabalhoUpdateComponent } from 'app/entities/historico-de-trabalho/historico-de-trabalho-update.component';
import { HistoricoDeTrabalhoService } from 'app/entities/historico-de-trabalho/historico-de-trabalho.service';
import { HistoricoDeTrabalho } from 'app/shared/model/historico-de-trabalho.model';

describe('Component Tests', () => {
  describe('HistoricoDeTrabalho Management Update Component', () => {
    let comp: HistoricoDeTrabalhoUpdateComponent;
    let fixture: ComponentFixture<HistoricoDeTrabalhoUpdateComponent>;
    let service: HistoricoDeTrabalhoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControleEmpregadosTestModule],
        declarations: [HistoricoDeTrabalhoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(HistoricoDeTrabalhoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HistoricoDeTrabalhoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HistoricoDeTrabalhoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new HistoricoDeTrabalho(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new HistoricoDeTrabalho();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
