import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { ControleEmpregadosTestModule } from '../../../test.module';
import { HistoricoDeTrabalhoComponent } from 'app/entities/historico-de-trabalho/historico-de-trabalho.component';
import { HistoricoDeTrabalhoService } from 'app/entities/historico-de-trabalho/historico-de-trabalho.service';
import { HistoricoDeTrabalho } from 'app/shared/model/historico-de-trabalho.model';

describe('Component Tests', () => {
  describe('HistoricoDeTrabalho Management Component', () => {
    let comp: HistoricoDeTrabalhoComponent;
    let fixture: ComponentFixture<HistoricoDeTrabalhoComponent>;
    let service: HistoricoDeTrabalhoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControleEmpregadosTestModule],
        declarations: [HistoricoDeTrabalhoComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(HistoricoDeTrabalhoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HistoricoDeTrabalhoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HistoricoDeTrabalhoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new HistoricoDeTrabalho(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.historicoDeTrabalhos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new HistoricoDeTrabalho(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.historicoDeTrabalhos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should re-initialize the page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new HistoricoDeTrabalho(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);
      comp.reset();

      // THEN
      expect(comp.page).toEqual(0);
      expect(service.query).toHaveBeenCalledTimes(2);
      expect(comp.historicoDeTrabalhos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,asc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
    });
  });
});
