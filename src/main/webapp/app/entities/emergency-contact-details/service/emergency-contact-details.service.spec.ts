import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ContactType } from 'app/entities/enumerations/contact-type.model';
import { IEmergencyContactDetails, EmergencyContactDetails } from '../emergency-contact-details.model';

import { EmergencyContactDetailsService } from './emergency-contact-details.service';

describe('Service Tests', () => {
  describe('EmergencyContactDetails Service', () => {
    let service: EmergencyContactDetailsService;
    let httpMock: HttpTestingController;
    let elemDefault: IEmergencyContactDetails;
    let expectedResult: IEmergencyContactDetails | IEmergencyContactDetails[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(EmergencyContactDetailsService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        address: 'AAAAAAA',
        telephone: 0,
        email: 'AAAAAAA',
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

      it('should create a EmergencyContactDetails', () => {
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

        service.create(new EmergencyContactDetails()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EmergencyContactDetails', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            address: 'BBBBBB',
            telephone: 1,
            email: 'BBBBBB',
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

      it('should partial update a EmergencyContactDetails', () => {
        const patchObject = Object.assign(
          {
            address: 'BBBBBB',
            email: 'BBBBBB',
            updatedDate: currentDate.format(DATE_FORMAT),
          },
          new EmergencyContactDetails()
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

      it('should return a list of EmergencyContactDetails', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            address: 'BBBBBB',
            telephone: 1,
            email: 'BBBBBB',
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

      it('should delete a EmergencyContactDetails', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addEmergencyContactDetailsToCollectionIfMissing', () => {
        it('should add a EmergencyContactDetails to an empty array', () => {
          const emergencyContactDetails: IEmergencyContactDetails = { id: 123 };
          expectedResult = service.addEmergencyContactDetailsToCollectionIfMissing([], emergencyContactDetails);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(emergencyContactDetails);
        });

        it('should not add a EmergencyContactDetails to an array that contains it', () => {
          const emergencyContactDetails: IEmergencyContactDetails = { id: 123 };
          const emergencyContactDetailsCollection: IEmergencyContactDetails[] = [
            {
              ...emergencyContactDetails,
            },
            { id: 456 },
          ];
          expectedResult = service.addEmergencyContactDetailsToCollectionIfMissing(
            emergencyContactDetailsCollection,
            emergencyContactDetails
          );
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a EmergencyContactDetails to an array that doesn't contain it", () => {
          const emergencyContactDetails: IEmergencyContactDetails = { id: 123 };
          const emergencyContactDetailsCollection: IEmergencyContactDetails[] = [{ id: 456 }];
          expectedResult = service.addEmergencyContactDetailsToCollectionIfMissing(
            emergencyContactDetailsCollection,
            emergencyContactDetails
          );
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(emergencyContactDetails);
        });

        it('should add only unique EmergencyContactDetails to an array', () => {
          const emergencyContactDetailsArray: IEmergencyContactDetails[] = [{ id: 123 }, { id: 456 }, { id: 11132 }];
          const emergencyContactDetailsCollection: IEmergencyContactDetails[] = [{ id: 123 }];
          expectedResult = service.addEmergencyContactDetailsToCollectionIfMissing(
            emergencyContactDetailsCollection,
            ...emergencyContactDetailsArray
          );
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const emergencyContactDetails: IEmergencyContactDetails = { id: 123 };
          const emergencyContactDetails2: IEmergencyContactDetails = { id: 456 };
          expectedResult = service.addEmergencyContactDetailsToCollectionIfMissing([], emergencyContactDetails, emergencyContactDetails2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(emergencyContactDetails);
          expect(expectedResult).toContain(emergencyContactDetails2);
        });

        it('should accept null and undefined values', () => {
          const emergencyContactDetails: IEmergencyContactDetails = { id: 123 };
          expectedResult = service.addEmergencyContactDetailsToCollectionIfMissing([], null, emergencyContactDetails, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(emergencyContactDetails);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
