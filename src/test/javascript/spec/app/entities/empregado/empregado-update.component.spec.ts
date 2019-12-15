import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ControleEmpregadosTestModule } from '../../../test.module';
import { EmpregadoUpdateComponent } from 'app/entities/empregado/empregado-update.component';
import { EmpregadoService } from 'app/entities/empregado/empregado.service';
import { Empregado } from 'app/shared/model/empregado.model';

describe('Component Tests', () => {
  describe('Empregado Management Update Component', () => {
    let comp: EmpregadoUpdateComponent;
    let fixture: ComponentFixture<EmpregadoUpdateComponent>;
    let service: EmpregadoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControleEmpregadosTestModule],
        declarations: [EmpregadoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EmpregadoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmpregadoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmpregadoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Empregado(123);
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
        const entity = new Empregado();
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
