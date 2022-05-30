import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICourtCase, getCourtCaseIdentifier } from '../court-case.model';

export type EntityResponseType = HttpResponse<ICourtCase>;
export type EntityArrayResponseType = HttpResponse<ICourtCase[]>;

@Injectable({ providedIn: 'root' })
export class CourtCaseService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/court-cases');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(courtCase: ICourtCase): Observable<EntityResponseType> {
    return this.http.post<ICourtCase>(this.resourceUrl, courtCase, { observe: 'response' });
  }

  update(courtCase: ICourtCase): Observable<EntityResponseType> {
    return this.http.put<ICourtCase>(`${this.resourceUrl}/${getCourtCaseIdentifier(courtCase) as number}`, courtCase, {
      observe: 'response',
    });
  }

  partialUpdate(courtCase: ICourtCase): Observable<EntityResponseType> {
    return this.http.patch<ICourtCase>(`${this.resourceUrl}/${getCourtCaseIdentifier(courtCase) as number}`, courtCase, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICourtCase>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICourtCase[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCourtCaseToCollectionIfMissing(
    courtCaseCollection: ICourtCase[],
    ...courtCasesToCheck: (ICourtCase | null | undefined)[]
  ): ICourtCase[] {
    const courtCases: ICourtCase[] = courtCasesToCheck.filter(isPresent);
    if (courtCases.length > 0) {
      const courtCaseCollectionIdentifiers = courtCaseCollection.map(courtCaseItem => getCourtCaseIdentifier(courtCaseItem)!);
      const courtCasesToAdd = courtCases.filter(courtCaseItem => {
        const courtCaseIdentifier = getCourtCaseIdentifier(courtCaseItem);
        if (courtCaseIdentifier == null || courtCaseCollectionIdentifiers.includes(courtCaseIdentifier)) {
          return false;
        }
        courtCaseCollectionIdentifiers.push(courtCaseIdentifier);
        return true;
      });
      return [...courtCasesToAdd, ...courtCaseCollection];
    }
    return courtCaseCollection;
  }
}
