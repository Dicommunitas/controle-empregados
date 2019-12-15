import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ControleEmpregadosTestModule } from '../../../test.module';
import { TrabalhoUpdateComponent } from 'app/entities/trabalho/trabalho-update.component';
import { TrabalhoService } from 'app/entities/trabalho/trabalho.service';
import { Trabalho } from 'app/shared/model/trabalho.model';

describe('Component Tests', () => {
  describe('Trabalho Management Update Component', () => {
    let comp: TrabalhoUpdateComponent;
    let fixture: ComponentFixture<TrabalhoUpdateComponent>;
    let service: TrabalhoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControleEmpregadosTestModule],
        declarations: [TrabalhoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TrabalhoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TrabalhoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrabalhoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Trabalho(123);
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
        const entity = new Trabalho();
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
