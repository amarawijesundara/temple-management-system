import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGuardianDetails, getGuardianDetailsIdentifier } from '../guardian-details.model';

export type EntityResponseType = HttpResponse<IGuardianDetails>;
export type EntityArrayResponseType = HttpResponse<IGuardianDetails[]>;

@Injectable({ providedIn: 'root' })
export class GuardianDetailsService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/guardian-details');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(guardianDetails: IGuardianDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(guardianDetails);
    return this.http
      .post<IGuardianDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(guardianDetails: IGuardianDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(guardianDetails);
    return this.http
      .put<IGuardianDetails>(`${this.resourceUrl}/${getGuardianDetailsIdentifier(guardianDetails) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(guardianDetails: IGuardianDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(guardianDetails);
    return this.http
      .patch<IGuardianDetails>(`${this.resourceUrl}/${getGuardianDetailsIdentifier(guardianDetails) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IGuardianDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGuardianDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addGuardianDetailsToCollectionIfMissing(
    guardianDetailsCollection: IGuardianDetails[],
    ...guardianDetailsToCheck: (IGuardianDetails | null | undefined)[]
  ): IGuardianDetails[] {
    const guardianDetails: IGuardianDetails[] = guardianDetailsToCheck.filter(isPresent);
    if (guardianDetails.length > 0) {
      const guardianDetailsCollectionIdentifiers = guardianDetailsCollection.map(
        guardianDetailsItem => getGuardianDetailsIdentifier(guardianDetailsItem)!
      );
      const guardianDetailsToAdd = guardianDetails.filter(guardianDetailsItem => {
        const guardianDetailsIdentifier = getGuardianDetailsIdentifier(guardianDetailsItem);
        if (guardianDetailsIdentifier == null || guardianDetailsCollectionIdentifiers.includes(guardianDetailsIdentifier)) {
          return false;
        }
        guardianDetailsCollectionIdentifiers.push(guardianDetailsIdentifier);
        return true;
      });
      return [...guardianDetailsToAdd, ...guardianDetailsCollection];
    }
    return guardianDetailsCollection;
  }

  protected convertDateFromClient(guardianDetails: IGuardianDetails): IGuardianDetails {
    return Object.assign({}, guardianDetails, {
      createdDate: guardianDetails.createdDate?.isValid() ? guardianDetails.createdDate.format(DATE_FORMAT) : undefined,
      updatedDate: guardianDetails.updatedDate?.isValid() ? guardianDetails.updatedDate.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.updatedDate = res.body.updatedDate ? dayjs(res.body.updatedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((guardianDetails: IGuardianDetails) => {
        guardianDetails.createdDate = guardianDetails.createdDate ? dayjs(guardianDetails.createdDate) : undefined;
        guardianDetails.updatedDate = guardianDetails.updatedDate ? dayjs(guardianDetails.updatedDate) : undefined;
      });
    }
    return res;
  }
}
