import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmpregado } from 'app/shared/model/empregado.model';

type EntityResponseType = HttpResponse<IEmpregado>;
type EntityArrayResponseType = HttpResponse<IEmpregado[]>;

@Injectable({ providedIn: 'root' })
export class EmpregadoService {
  public resourceUrl = SERVER_API_URL + 'api/empregados';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/empregados';

  constructor(protected http: HttpClient) {}

  create(empregado: IEmpregado): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(empregado);
    return this.http
      .post<IEmpregado>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(empregado: IEmpregado): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(empregado);
    return this.http
      .put<IEmpregado>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmpregado>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmpregado[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmpregado[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(empregado: IEmpregado): IEmpregado {
    const copy: IEmpregado = Object.assign({}, empregado, {
      dataContratacao: empregado.dataContratacao != null && empregado.dataContratacao.isValid() ? empregado.dataContratacao.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataContratacao = res.body.dataContratacao != null ? moment(res.body.dataContratacao) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((empregado: IEmpregado) => {
        empregado.dataContratacao = empregado.dataContratacao != null ? moment(empregado.dataContratacao) : null;
      });
    }
    return res;
  }
}
