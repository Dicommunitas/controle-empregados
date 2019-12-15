import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IHistoricoDeTrabalho } from 'app/shared/model/historico-de-trabalho.model';

type EntityResponseType = HttpResponse<IHistoricoDeTrabalho>;
type EntityArrayResponseType = HttpResponse<IHistoricoDeTrabalho[]>;

@Injectable({ providedIn: 'root' })
export class HistoricoDeTrabalhoService {
  public resourceUrl = SERVER_API_URL + 'api/historico-de-trabalhos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/historico-de-trabalhos';

  constructor(protected http: HttpClient) {}

  create(historicoDeTrabalho: IHistoricoDeTrabalho): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(historicoDeTrabalho);
    return this.http
      .post<IHistoricoDeTrabalho>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(historicoDeTrabalho: IHistoricoDeTrabalho): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(historicoDeTrabalho);
    return this.http
      .put<IHistoricoDeTrabalho>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHistoricoDeTrabalho>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHistoricoDeTrabalho[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHistoricoDeTrabalho[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(historicoDeTrabalho: IHistoricoDeTrabalho): IHistoricoDeTrabalho {
    const copy: IHistoricoDeTrabalho = Object.assign({}, historicoDeTrabalho, {
      dataInicial:
        historicoDeTrabalho.dataInicial != null && historicoDeTrabalho.dataInicial.isValid()
          ? historicoDeTrabalho.dataInicial.toJSON()
          : null,
      dataFinal:
        historicoDeTrabalho.dataFinal != null && historicoDeTrabalho.dataFinal.isValid() ? historicoDeTrabalho.dataFinal.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataInicial = res.body.dataInicial != null ? moment(res.body.dataInicial) : null;
      res.body.dataFinal = res.body.dataFinal != null ? moment(res.body.dataFinal) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((historicoDeTrabalho: IHistoricoDeTrabalho) => {
        historicoDeTrabalho.dataInicial = historicoDeTrabalho.dataInicial != null ? moment(historicoDeTrabalho.dataInicial) : null;
        historicoDeTrabalho.dataFinal = historicoDeTrabalho.dataFinal != null ? moment(historicoDeTrabalho.dataFinal) : null;
      });
    }
    return res;
  }
}
