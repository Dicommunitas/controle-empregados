import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ControleEmpregadosTestModule } from '../../../test.module';
import { DepartamentoComponent } from 'app/entities/departamento/departamento.component';
import { DepartamentoService } from 'app/entities/departamento/departamento.service';
import { Departamento } from 'app/shared/model/departamento.model';

describe('Component Tests', () => {
  describe('Departamento Management Component', () => {
    let comp: DepartamentoComponent;
    let fixture: ComponentFixture<DepartamentoComponent>;
    let service: DepartamentoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControleEmpregadosTestModule],
        declarations: [DepartamentoComponent],
        providers: []
      })
        .overrideTemplate(DepartamentoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DepartamentoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DepartamentoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Departamento(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.departamentos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
