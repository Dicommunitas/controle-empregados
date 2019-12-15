import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ControleEmpregadosTestModule } from '../../../test.module';
import { LocalizacaoComponent } from 'app/entities/localizacao/localizacao.component';
import { LocalizacaoService } from 'app/entities/localizacao/localizacao.service';
import { Localizacao } from 'app/shared/model/localizacao.model';

describe('Component Tests', () => {
  describe('Localizacao Management Component', () => {
    let comp: LocalizacaoComponent;
    let fixture: ComponentFixture<LocalizacaoComponent>;
    let service: LocalizacaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControleEmpregadosTestModule],
        declarations: [LocalizacaoComponent],
        providers: []
      })
        .overrideTemplate(LocalizacaoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LocalizacaoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocalizacaoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Localizacao(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.localizacaos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
