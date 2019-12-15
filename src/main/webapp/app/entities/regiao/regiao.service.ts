import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRegiao } from 'app/shared/model/regiao.model';

type EntityResponseType = HttpResponse<IRegiao>;
type EntityArrayResponseType = HttpResponse<IRegiao[]>;

@Injectable({ providedIn: 'root' })
export class RegiaoService {
  public resourceUrl = SERVER_API_URL + 'api/regiaos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/regiaos';

  constructor(protected http: HttpClient) {}

  create(regiao: IRegiao): Observable<EntityResponseType> {
    return this.http.post<IRegiao>(this.resourceUrl, regiao, { observe: 'response' });
  }

  update(regiao: IRegiao): Observable<EntityResponseType> {
    return this.http.put<IRegiao>(this.resourceUrl, regiao, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRegiao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRegiao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRegiao[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
