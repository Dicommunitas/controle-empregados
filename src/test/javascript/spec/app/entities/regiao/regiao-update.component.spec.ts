import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ControleEmpregadosTestModule } from '../../../test.module';
import { RegiaoUpdateComponent } from 'app/entities/regiao/regiao-update.component';
import { RegiaoService } from 'app/entities/regiao/regiao.service';
import { Regiao } from 'app/shared/model/regiao.model';

describe('Component Tests', () => {
  describe('Regiao Management Update Component', () => {
    let comp: RegiaoUpdateComponent;
    let fixture: ComponentFixture<RegiaoUpdateComponent>;
    let service: RegiaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControleEmpregadosTestModule],
        declarations: [RegiaoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RegiaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RegiaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RegiaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Regiao(123);
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
        const entity = new Regiao();
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
