import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { EmpregadoService } from 'app/entities/empregado/empregado.service';
import { IEmpregado, Empregado } from 'app/shared/model/empregado.model';

describe('Service Tests', () => {
  describe('Empregado Service', () => {
    let injector: TestBed;
    let service: EmpregadoService;
    let httpMock: HttpTestingController;
    let elemDefault: IEmpregado;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(EmpregadoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Empregado(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataContratacao: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Empregado', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataContratacao: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataContratacao: currentDate
          },
          returnedFromService
        );
        service
          .create(new Empregado(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Empregado', () => {
        const returnedFromService = Object.assign(
          {
            primeiroNome: 'BBBBBB',
            sobrenome: 'BBBBBB',
            email: 'BBBBBB',
            telefone: 'BBBBBB',
            dataContratacao: currentDate.format(DATE_TIME_FORMAT),
            salario: 1,
            comissao: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataContratacao: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Empregado', () => {
        const returnedFromService = Object.assign(
          {
            primeiroNome: 'BBBBBB',
            sobrenome: 'BBBBBB',
            email: 'BBBBBB',
            telefone: 'BBBBBB',
            dataContratacao: currentDate.format(DATE_TIME_FORMAT),
            salario: 1,
            comissao: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataContratacao: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Empregado', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
