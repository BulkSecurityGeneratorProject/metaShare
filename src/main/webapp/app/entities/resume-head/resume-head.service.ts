import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IResumeHead } from 'app/shared/model/resume-head.model';

type EntityResponseType = HttpResponse<IResumeHead>;
type EntityArrayResponseType = HttpResponse<IResumeHead[]>;

@Injectable({ providedIn: 'root' })
export class ResumeHeadService {
  public resourceUrl = SERVER_API_URL + 'api/resume-heads';

  constructor(protected http: HttpClient) {}

  create(resumeHead: IResumeHead): Observable<EntityResponseType> {
    return this.http.post<IResumeHead>(this.resourceUrl, resumeHead, { observe: 'response' });
  }

  update(resumeHead: IResumeHead): Observable<EntityResponseType> {
    return this.http.put<IResumeHead>(this.resourceUrl, resumeHead, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IResumeHead>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResumeHead[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
