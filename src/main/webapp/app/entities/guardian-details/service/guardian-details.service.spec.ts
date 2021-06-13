import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ContactType } from 'app/entities/enumerations/contact-type.model';
import { IGuardianDetails, GuardianDetails } from '../guardian-details.model';

import { GuardianDetailsService } from './guardian-details.service';

describe('Service Tests', () => {
  describe('GuardianDetails Service', () => {
    let service: GuardianDetailsService;
    let httpMock: HttpTestingController;
    let elemDefault: IGuardianDetails;
    let expectedResult: IGuardianDetails | IGuardianDetails[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(GuardianDetailsService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        address: 'AAAAAAA',
        contactType: ContactType.FATHER,
        createdDate: currentDate,
        updatedDate: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            createdDate: currentDate.format(DATE_FORMAT),
            updatedDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a GuardianDetails', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdDate: currentDate.format(DATE_FORMAT),
            updatedDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdDate: currentDate,
            updatedDate: currentDate,
          },
          returnedFromService
        );

        service.create(new GuardianDetails()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a GuardianDetails', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            address: 'BBBBBB',
            contactType: 'BBBBBB',
            createdDate: currentDate.format(DATE_FORMAT),
            updatedDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdDate: currentDate,
            updatedDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a GuardianDetails', () => {
        const patchObject = Object.assign(
          {
            address: 'BBBBBB',
            updatedDate: currentDate.format(DATE_FORMAT),
          },
          new GuardianDetails()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            createdDate: currentDate,
            updatedDate: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of GuardianDetails', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            address: 'BBBBBB',
            contactType: 'BBBBBB',
            createdDate: currentDate.format(DATE_FORMAT),
            updatedDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdDate: currentDate,
            updatedDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a GuardianDetails', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addGuardianDetailsToCollectionIfMissing', () => {
        it('should add a GuardianDetails to an empty array', () => {
          const guardianDetails: IGuardianDetails = { id: 123 };
          expectedResult = service.addGuardianDetailsToCollectionIfMissing([], guardianDetails);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(guardianDetails);
        });

        it('should not add a GuardianDetails to an array that contains it', () => {
          const guardianDetails: IGuardianDetails = { id: 123 };
          const guardianDetailsCollection: IGuardianDetails[] = [
            {
              ...guardianDetails,
            },
            { id: 456 },
          ];
          expectedResult = service.addGuardianDetailsToCollectionIfMissing(guardianDetailsCollection, guardianDetails);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a GuardianDetails to an array that doesn't contain it", () => {
          const guardianDetails: IGuardianDetails = { id: 123 };
          const guardianDetailsCollection: IGuardianDetails[] = [{ id: 456 }];
          expectedResult = service.addGuardianDetailsToCollectionIfMissing(guardianDetailsCollection, guardianDetails);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(guardianDetails);
        });

        it('should add only unique GuardianDetails to an array', () => {
          const guardianDetailsArray: IGuardianDetails[] = [{ id: 123 }, { id: 456 }, { id: 33514 }];
          const guardianDetailsCollection: IGuardianDetails[] = [{ id: 123 }];
          expectedResult = service.addGuardianDetailsToCollectionIfMissing(guardianDetailsCollection, ...guardianDetailsArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const guardianDetails: IGuardianDetails = { id: 123 };
          const guardianDetails2: IGuardianDetails = { id: 456 };
          expectedResult = service.addGuardianDetailsToCollectionIfMissing([], guardianDetails, guardianDetails2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(guardianDetails);
          expect(expectedResult).toContain(guardianDetails2);
        });

        it('should accept null and undefined values', () => {
          const guardianDetails: IGuardianDetails = { id: 123 };
          expectedResult = service.addGuardianDetailsToCollectionIfMissing([], null, guardianDetails, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(guardianDetails);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
