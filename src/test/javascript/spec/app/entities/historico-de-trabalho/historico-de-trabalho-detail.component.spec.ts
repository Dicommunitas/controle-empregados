import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ControleEmpregadosTestModule } from '../../../test.module';
import { HistoricoDeTrabalhoDetailComponent } from 'app/entities/historico-de-trabalho/historico-de-trabalho-detail.component';
import { HistoricoDeTrabalho } from 'app/shared/model/historico-de-trabalho.model';

describe('Component Tests', () => {
  describe('HistoricoDeTrabalho Management Detail Component', () => {
    let comp: HistoricoDeTrabalhoDetailComponent;
    let fixture: ComponentFixture<HistoricoDeTrabalhoDetailComponent>;
    const route = ({ data: of({ historicoDeTrabalho: new HistoricoDeTrabalho(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControleEmpregadosTestModule],
        declarations: [HistoricoDeTrabalhoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(HistoricoDeTrabalhoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HistoricoDeTrabalhoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.historicoDeTrabalho).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
