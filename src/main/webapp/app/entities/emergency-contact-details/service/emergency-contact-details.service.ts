import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmergencyContactDetails, getEmergencyContactDetailsIdentifier } from '../emergency-contact-details.model';

export type EntityResponseType = HttpResponse<IEmergencyContactDetails>;
export type EntityArrayResponseType = HttpResponse<IEmergencyContactDetails[]>;

@Injectable({ providedIn: 'root' })
export class EmergencyContactDetailsService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/emergency-contact-details');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(emergencyContactDetails: IEmergencyContactDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emergencyContactDetails);
    return this.http
      .post<IEmergencyContactDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(emergencyContactDetails: IEmergencyContactDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emergencyContactDetails);
    return this.http
      .put<IEmergencyContactDetails>(
        `${this.resourceUrl}/${getEmergencyContactDetailsIdentifier(emergencyContactDetails) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(emergencyContactDetails: IEmergencyContactDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emergencyContactDetails);
    return this.http
      .patch<IEmergencyContactDetails>(
        `${this.resourceUrl}/${getEmergencyContactDetailsIdentifier(emergencyContactDetails) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmergencyContactDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmergencyContactDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEmergencyContactDetailsToCollectionIfMissing(
    emergencyContactDetailsCollection: IEmergencyContactDetails[],
    ...emergencyContactDetailsToCheck: (IEmergencyContactDetails | null | undefined)[]
  ): IEmergencyContactDetails[] {
    const emergencyContactDetails: IEmergencyContactDetails[] = emergencyContactDetailsToCheck.filter(isPresent);
    if (emergencyContactDetails.length > 0) {
      const emergencyContactDetailsCollectionIdentifiers = emergencyContactDetailsCollection.map(
        emergencyContactDetailsItem => getEmergencyContactDetailsIdentifier(emergencyContactDetailsItem)!
      );
      const emergencyContactDetailsToAdd = emergencyContactDetails.filter(emergencyContactDetailsItem => {
        const emergencyContactDetailsIdentifier = getEmergencyContactDetailsIdentifier(emergencyContactDetailsItem);
        if (
          emergencyContactDetailsIdentifier == null ||
          emergencyContactDetailsCollectionIdentifiers.includes(emergencyContactDetailsIdentifier)
        ) {
          return false;
        }
        emergencyContactDetailsCollectionIdentifiers.push(emergencyContactDetailsIdentifier);
        return true;
      });
      return [...emergencyContactDetailsToAdd, ...emergencyContactDetailsCollection];
    }
    return emergencyContactDetailsCollection;
  }

  protected convertDateFromClient(emergencyContactDetails: IEmergencyContactDetails): IEmergencyContactDetails {
    return Object.assign({}, emergencyContactDetails, {
      createdDate: emergencyContactDetails.createdDate?.isValid() ? emergencyContactDetails.createdDate.format(DATE_FORMAT) : undefined,
      updatedDate: emergencyContactDetails.updatedDate?.isValid() ? emergencyContactDetails.updatedDate.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((emergencyContactDetails: IEmergencyContactDetails) => {
        emergencyContactDetails.createdDate = emergencyContactDetails.createdDate ? dayjs(emergencyContactDetails.createdDate) : undefined;
        emergencyContactDetails.updatedDate = emergencyContactDetails.updatedDate ? dayjs(emergencyContactDetails.updatedDate) : undefined;
      });
    }
    return res;
  }
}
