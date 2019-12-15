import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ControleEmpregadosTestModule } from '../../../test.module';
import { RegiaoComponent } from 'app/entities/regiao/regiao.component';
import { RegiaoService } from 'app/entities/regiao/regiao.service';
import { Regiao } from 'app/shared/model/regiao.model';

describe('Component Tests', () => {
  describe('Regiao Management Component', () => {
    let comp: RegiaoComponent;
    let fixture: ComponentFixture<RegiaoComponent>;
    let service: RegiaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControleEmpregadosTestModule],
        declarations: [RegiaoComponent],
        providers: []
      })
        .overrideTemplate(RegiaoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RegiaoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RegiaoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Regiao(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.regiaos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
