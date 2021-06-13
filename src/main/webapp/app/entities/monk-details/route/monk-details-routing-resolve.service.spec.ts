jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IMonkDetails, MonkDetails } from '../monk-details.model';
import { MonkDetailsService } from '../service/monk-details.service';

import { MonkDetailsRoutingResolveService } from './monk-details-routing-resolve.service';

describe('Service Tests', () => {
  describe('MonkDetails routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: MonkDetailsRoutingResolveService;
    let service: MonkDetailsService;
    let resultMonkDetails: IMonkDetails | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(MonkDetailsRoutingResolveService);
      service = TestBed.inject(MonkDetailsService);
      resultMonkDetails = undefined;
    });

    describe('resolve', () => {
      it('should return IMonkDetails returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMonkDetails = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultMonkDetails).toEqual({ id: 123 });
      });

      it('should return new IMonkDetails if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMonkDetails = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultMonkDetails).toEqual(new MonkDetails());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMonkDetails = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultMonkDetails).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
