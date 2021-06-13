import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMonkDetails, getMonkDetailsIdentifier } from '../monk-details.model';

export type EntityResponseType = HttpResponse<IMonkDetails>;
export type EntityArrayResponseType = HttpResponse<IMonkDetails[]>;

@Injectable({ providedIn: 'root' })
export class MonkDetailsService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/monk-details');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(monkDetails: IMonkDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(monkDetails);
    return this.http
      .post<IMonkDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(monkDetails: IMonkDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(monkDetails);
    return this.http
      .put<IMonkDetails>(`${this.resourceUrl}/${getMonkDetailsIdentifier(monkDetails) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(monkDetails: IMonkDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(monkDetails);
    return this.http
      .patch<IMonkDetails>(`${this.resourceUrl}/${getMonkDetailsIdentifier(monkDetails) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMonkDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMonkDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMonkDetailsToCollectionIfMissing(
    monkDetailsCollection: IMonkDetails[],
    ...monkDetailsToCheck: (IMonkDetails | null | undefined)[]
  ): IMonkDetails[] {
    const monkDetails: IMonkDetails[] = monkDetailsToCheck.filter(isPresent);
    if (monkDetails.length > 0) {
      const monkDetailsCollectionIdentifiers = monkDetailsCollection.map(monkDetailsItem => getMonkDetailsIdentifier(monkDetailsItem)!);
      const monkDetailsToAdd = monkDetails.filter(monkDetailsItem => {
        const monkDetailsIdentifier = getMonkDetailsIdentifier(monkDetailsItem);
        if (monkDetailsIdentifier == null || monkDetailsCollectionIdentifiers.includes(monkDetailsIdentifier)) {
          return false;
        }
        monkDetailsCollectionIdentifiers.push(monkDetailsIdentifier);
        return true;
      });
      return [...monkDetailsToAdd, ...monkDetailsCollection];
    }
    return monkDetailsCollection;
  }

  protected convertDateFromClient(monkDetails: IMonkDetails): IMonkDetails {
    return Object.assign({}, monkDetails, {
      pawidiDate: monkDetails.pawidiDate?.isValid() ? monkDetails.pawidiDate.format(DATE_FORMAT) : undefined,
      upasampadaDate: monkDetails.upasampadaDate?.isValid() ? monkDetails.upasampadaDate.format(DATE_FORMAT) : undefined,
      createdDate: monkDetails.createdDate?.isValid() ? monkDetails.createdDate.format(DATE_FORMAT) : undefined,
      updatedDate: monkDetails.updatedDate?.isValid() ? monkDetails.updatedDate.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.pawidiDate = res.body.pawidiDate ? dayjs(res.body.pawidiDate) : undefined;
      res.body.upasampadaDate = res.body.upasampadaDate ? dayjs(res.body.upasampadaDate) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.updatedDate = res.body.updatedDate ? dayjs(res.body.updatedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((monkDetails: IMonkDetails) => {
        monkDetails.pawidiDate = monkDetails.pawidiDate ? dayjs(monkDetails.pawidiDate) : undefined;
        monkDetails.upasampadaDate = monkDetails.upasampadaDate ? dayjs(monkDetails.upasampadaDate) : undefined;
        monkDetails.createdDate = monkDetails.createdDate ? dayjs(monkDetails.createdDate) : undefined;
        monkDetails.updatedDate = monkDetails.updatedDate ? dayjs(monkDetails.updatedDate) : undefined;
      });
    }
    return res;
  }
}
