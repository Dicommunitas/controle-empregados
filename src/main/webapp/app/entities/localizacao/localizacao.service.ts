import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILocalizacao } from 'app/shared/model/localizacao.model';

type EntityResponseType = HttpResponse<ILocalizacao>;
type EntityArrayResponseType = HttpResponse<ILocalizacao[]>;

@Injectable({ providedIn: 'root' })
export class LocalizacaoService {
  public resourceUrl = SERVER_API_URL + 'api/localizacaos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/localizacaos';

  constructor(protected http: HttpClient) {}

  create(localizacao: ILocalizacao): Observable<EntityResponseType> {
    return this.http.post<ILocalizacao>(this.resourceUrl, localizacao, { observe: 'response' });
  }

  update(localizacao: ILocalizacao): Observable<EntityResponseType> {
    return this.http.put<ILocalizacao>(this.resourceUrl, localizacao, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILocalizacao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILocalizacao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILocalizacao[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
