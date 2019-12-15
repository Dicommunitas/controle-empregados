import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ControleEmpregadosTestModule } from '../../../test.module';
import { LocalizacaoDetailComponent } from 'app/entities/localizacao/localizacao-detail.component';
import { Localizacao } from 'app/shared/model/localizacao.model';

describe('Component Tests', () => {
  describe('Localizacao Management Detail Component', () => {
    let comp: LocalizacaoDetailComponent;
    let fixture: ComponentFixture<LocalizacaoDetailComponent>;
    const route = ({ data: of({ localizacao: new Localizacao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControleEmpregadosTestModule],
        declarations: [LocalizacaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LocalizacaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LocalizacaoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.localizacao).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
