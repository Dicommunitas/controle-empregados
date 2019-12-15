import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ControleEmpregadosTestModule } from '../../../test.module';
import { TrabalhoDetailComponent } from 'app/entities/trabalho/trabalho-detail.component';
import { Trabalho } from 'app/shared/model/trabalho.model';

describe('Component Tests', () => {
  describe('Trabalho Management Detail Component', () => {
    let comp: TrabalhoDetailComponent;
    let fixture: ComponentFixture<TrabalhoDetailComponent>;
    const route = ({ data: of({ trabalho: new Trabalho(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControleEmpregadosTestModule],
        declarations: [TrabalhoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TrabalhoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TrabalhoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.trabalho).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
