import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ControleEmpregadosTestModule } from '../../../test.module';
import { LocalizacaoUpdateComponent } from 'app/entities/localizacao/localizacao-update.component';
import { LocalizacaoService } from 'app/entities/localizacao/localizacao.service';
import { Localizacao } from 'app/shared/model/localizacao.model';

describe('Component Tests', () => {
  describe('Localizacao Management Update Component', () => {
    let comp: LocalizacaoUpdateComponent;
    let fixture: ComponentFixture<LocalizacaoUpdateComponent>;
    let service: LocalizacaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControleEmpregadosTestModule],
        declarations: [LocalizacaoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LocalizacaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LocalizacaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocalizacaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Localizacao(123);
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
        const entity = new Localizacao();
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
