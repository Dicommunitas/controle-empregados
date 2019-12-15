import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITarefa } from 'app/shared/model/tarefa.model';

type EntityResponseType = HttpResponse<ITarefa>;
type EntityArrayResponseType = HttpResponse<ITarefa[]>;

@Injectable({ providedIn: 'root' })
export class TarefaService {
  public resourceUrl = SERVER_API_URL + 'api/tarefas';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/tarefas';

  constructor(protected http: HttpClient) {}

  create(tarefa: ITarefa): Observable<EntityResponseType> {
    return this.http.post<ITarefa>(this.resourceUrl, tarefa, { observe: 'response' });
  }

  update(tarefa: ITarefa): Observable<EntityResponseType> {
    return this.http.put<ITarefa>(this.resourceUrl, tarefa, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITarefa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITarefa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITarefa[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
