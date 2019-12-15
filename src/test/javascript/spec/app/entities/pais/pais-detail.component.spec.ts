import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ControleEmpregadosTestModule } from '../../../test.module';
import { PaisDetailComponent } from 'app/entities/pais/pais-detail.component';
import { Pais } from 'app/shared/model/pais.model';

describe('Component Tests', () => {
  describe('Pais Management Detail Component', () => {
    let comp: PaisDetailComponent;
    let fixture: ComponentFixture<PaisDetailComponent>;
    const route = ({ data: of({ pais: new Pais(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControleEmpregadosTestModule],
        declarations: [PaisDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PaisDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaisDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pais).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
