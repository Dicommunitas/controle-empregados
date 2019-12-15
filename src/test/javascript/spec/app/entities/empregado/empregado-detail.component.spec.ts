import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ControleEmpregadosTestModule } from '../../../test.module';
import { EmpregadoDetailComponent } from 'app/entities/empregado/empregado-detail.component';
import { Empregado } from 'app/shared/model/empregado.model';

describe('Component Tests', () => {
  describe('Empregado Management Detail Component', () => {
    let comp: EmpregadoDetailComponent;
    let fixture: ComponentFixture<EmpregadoDetailComponent>;
    const route = ({ data: of({ empregado: new Empregado(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControleEmpregadosTestModule],
        declarations: [EmpregadoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EmpregadoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmpregadoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.empregado).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
