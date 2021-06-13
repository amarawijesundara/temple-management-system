import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IMonkDetails, MonkDetails } from '../monk-details.model';

import { MonkDetailsService } from './monk-details.service';

describe('Service Tests', () => {
  describe('MonkDetails Service', () => {
    let service: MonkDetailsService;
    let httpMock: HttpTestingController;
    let elemDefault: IMonkDetails;
    let expectedResult: IMonkDetails | IMonkDetails[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(MonkDetailsService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        nameEnglish: 'AAAAAAA',
        nameSinhala: 'AAAAAAA',
        pawidiNo: 'AAAAAAA',
        samaneraNo: 'AAAAAAA',
        upasampadaNo: 'AAAAAAA',
        pawidiDate: currentDate,
        upasampadaDate: currentDate,
        createdDate: currentDate,
        updatedDate: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            pawidiDate: currentDate.format(DATE_FORMAT),
            upasampadaDate: currentDate.format(DATE_FORMAT),
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

      it('should create a MonkDetails', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            pawidiDate: currentDate.format(DATE_FORMAT),
            upasampadaDate: currentDate.format(DATE_FORMAT),
            createdDate: currentDate.format(DATE_FORMAT),
            updatedDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            pawidiDate: currentDate,
            upasampadaDate: currentDate,
            createdDate: currentDate,
            updatedDate: currentDate,
          },
          returnedFromService
        );

        service.create(new MonkDetails()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MonkDetails', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nameEnglish: 'BBBBBB',
            nameSinhala: 'BBBBBB',
            pawidiNo: 'BBBBBB',
            samaneraNo: 'BBBBBB',
            upasampadaNo: 'BBBBBB',
            pawidiDate: currentDate.format(DATE_FORMAT),
            upasampadaDate: currentDate.format(DATE_FORMAT),
            createdDate: currentDate.format(DATE_FORMAT),
            updatedDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            pawidiDate: currentDate,
            upasampadaDate: currentDate,
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

      it('should partial update a MonkDetails', () => {
        const patchObject = Object.assign(
          {
            pawidiNo: 'BBBBBB',
            samaneraNo: 'BBBBBB',
            pawidiDate: currentDate.format(DATE_FORMAT),
            upasampadaDate: currentDate.format(DATE_FORMAT),
            updatedDate: currentDate.format(DATE_FORMAT),
          },
          new MonkDetails()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            pawidiDate: currentDate,
            upasampadaDate: currentDate,
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

      it('should return a list of MonkDetails', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nameEnglish: 'BBBBBB',
            nameSinhala: 'BBBBBB',
            pawidiNo: 'BBBBBB',
            samaneraNo: 'BBBBBB',
            upasampadaNo: 'BBBBBB',
            pawidiDate: currentDate.format(DATE_FORMAT),
            upasampadaDate: currentDate.format(DATE_FORMAT),
            createdDate: currentDate.format(DATE_FORMAT),
            updatedDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            pawidiDate: currentDate,
            upasampadaDate: currentDate,
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

      it('should delete a MonkDetails', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addMonkDetailsToCollectionIfMissing', () => {
        it('should add a MonkDetails to an empty array', () => {
          const monkDetails: IMonkDetails = { id: 123 };
          expectedResult = service.addMonkDetailsToCollectionIfMissing([], monkDetails);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(monkDetails);
        });

        it('should not add a MonkDetails to an array that contains it', () => {
          const monkDetails: IMonkDetails = { id: 123 };
          const monkDetailsCollection: IMonkDetails[] = [
            {
              ...monkDetails,
            },
            { id: 456 },
          ];
          expectedResult = service.addMonkDetailsToCollectionIfMissing(monkDetailsCollection, monkDetails);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a MonkDetails to an array that doesn't contain it", () => {
          const monkDetails: IMonkDetails = { id: 123 };
          const monkDetailsCollection: IMonkDetails[] = [{ id: 456 }];
          expectedResult = service.addMonkDetailsToCollectionIfMissing(monkDetailsCollection, monkDetails);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(monkDetails);
        });

        it('should add only unique MonkDetails to an array', () => {
          const monkDetailsArray: IMonkDetails[] = [{ id: 123 }, { id: 456 }, { id: 36152 }];
          const monkDetailsCollection: IMonkDetails[] = [{ id: 123 }];
          expectedResult = service.addMonkDetailsToCollectionIfMissing(monkDetailsCollection, ...monkDetailsArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const monkDetails: IMonkDetails = { id: 123 };
          const monkDetails2: IMonkDetails = { id: 456 };
          expectedResult = service.addMonkDetailsToCollectionIfMissing([], monkDetails, monkDetails2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(monkDetails);
          expect(expectedResult).toContain(monkDetails2);
        });

        it('should accept null and undefined values', () => {
          const monkDetails: IMonkDetails = { id: 123 };
          expectedResult = service.addMonkDetailsToCollectionIfMissing([], null, monkDetails, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(monkDetails);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
