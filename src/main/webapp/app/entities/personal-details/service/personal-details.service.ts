import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPersonalDetails, getPersonalDetailsIdentifier } from '../personal-details.model';

export type EntityResponseType = HttpResponse<IPersonalDetails>;
export type EntityArrayResponseType = HttpResponse<IPersonalDetails[]>;

@Injectable({ providedIn: 'root' })
export class PersonalDetailsService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/personal-details');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(personalDetails: IPersonalDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personalDetails);
    return this.http
      .post<IPersonalDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(personalDetails: IPersonalDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personalDetails);
    return this.http
      .put<IPersonalDetails>(`${this.resourceUrl}/${getPersonalDetailsIdentifier(personalDetails) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(personalDetails: IPersonalDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personalDetails);
    return this.http
      .patch<IPersonalDetails>(`${this.resourceUrl}/${getPersonalDetailsIdentifier(personalDetails) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPersonalDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPersonalDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPersonalDetailsToCollectionIfMissing(
    personalDetailsCollection: IPersonalDetails[],
    ...personalDetailsToCheck: (IPersonalDetails | null | undefined)[]
  ): IPersonalDetails[] {
    const personalDetails: IPersonalDetails[] = personalDetailsToCheck.filter(isPresent);
    if (personalDetails.length > 0) {
      const personalDetailsCollectionIdentifiers = personalDetailsCollection.map(
        personalDetailsItem => getPersonalDetailsIdentifier(personalDetailsItem)!
      );
      const personalDetailsToAdd = personalDetails.filter(personalDetailsItem => {
        const personalDetailsIdentifier = getPersonalDetailsIdentifier(personalDetailsItem);
        if (personalDetailsIdentifier == null || personalDetailsCollectionIdentifiers.includes(personalDetailsIdentifier)) {
          return false;
        }
        personalDetailsCollectionIdentifiers.push(personalDetailsIdentifier);
        return true;
      });
      return [...personalDetailsToAdd, ...personalDetailsCollection];
    }
    return personalDetailsCollection;
  }

  protected convertDateFromClient(personalDetails: IPersonalDetails): IPersonalDetails {
    return Object.assign({}, personalDetails, {
      dateOfBirth: personalDetails.dateOfBirth?.isValid() ? personalDetails.dateOfBirth.format(DATE_FORMAT) : undefined,
      createdDate: personalDetails.createdDate?.isValid() ? personalDetails.createdDate.format(DATE_FORMAT) : undefined,
      updatedDate: personalDetails.updatedDate?.isValid() ? personalDetails.updatedDate.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateOfBirth = res.body.dateOfBirth ? dayjs(res.body.dateOfBirth) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.updatedDate = res.body.updatedDate ? dayjs(res.body.updatedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((personalDetails: IPersonalDetails) => {
        personalDetails.dateOfBirth = personalDetails.dateOfBirth ? dayjs(personalDetails.dateOfBirth) : undefined;
        personalDetails.createdDate = personalDetails.createdDate ? dayjs(personalDetails.createdDate) : undefined;
        personalDetails.updatedDate = personalDetails.updatedDate ? dayjs(personalDetails.updatedDate) : undefined;
      });
    }
    return res;
  }
}
