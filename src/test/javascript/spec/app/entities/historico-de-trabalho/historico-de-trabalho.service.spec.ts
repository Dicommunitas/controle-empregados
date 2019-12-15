import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { HistoricoDeTrabalhoService } from 'app/entities/historico-de-trabalho/historico-de-trabalho.service';
import { IHistoricoDeTrabalho, HistoricoDeTrabalho } from 'app/shared/model/historico-de-trabalho.model';
import { Lingua } from 'app/shared/model/enumerations/lingua.model';

describe('Service Tests', () => {
  describe('HistoricoDeTrabalho Service', () => {
    let injector: TestBed;
    let service: HistoricoDeTrabalhoService;
    let httpMock: HttpTestingController;
    let elemDefault: IHistoricoDeTrabalho;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(HistoricoDeTrabalhoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new HistoricoDeTrabalho(0, currentDate, currentDate, Lingua.PORTUGUESE);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataInicial: currentDate.format(DATE_TIME_FORMAT),
            dataFinal: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a HistoricoDeTrabalho', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataInicial: currentDate.format(DATE_TIME_FORMAT),
            dataFinal: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataInicial: currentDate,
            dataFinal: currentDate
          },
          returnedFromService
        );
        service
          .create(new HistoricoDeTrabalho(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a HistoricoDeTrabalho', () => {
        const returnedFromService = Object.assign(
          {
            dataInicial: currentDate.format(DATE_TIME_FORMAT),
            dataFinal: currentDate.format(DATE_TIME_FORMAT),
            lingua: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataInicial: currentDate,
            dataFinal: currentDate
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

      it('should return a list of HistoricoDeTrabalho', () => {
        const returnedFromService = Object.assign(
          {
            dataInicial: currentDate.format(DATE_TIME_FORMAT),
            dataFinal: currentDate.format(DATE_TIME_FORMAT),
            lingua: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataInicial: currentDate,
            dataFinal: currentDate
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

      it('should delete a HistoricoDeTrabalho', () => {
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
