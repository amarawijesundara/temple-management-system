jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IGuardianDetails, GuardianDetails } from '../guardian-details.model';
import { GuardianDetailsService } from '../service/guardian-details.service';

import { GuardianDetailsRoutingResolveService } from './guardian-details-routing-resolve.service';

describe('Service Tests', () => {
  describe('GuardianDetails routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: GuardianDetailsRoutingResolveService;
    let service: GuardianDetailsService;
    let resultGuardianDetails: IGuardianDetails | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(GuardianDetailsRoutingResolveService);
      service = TestBed.inject(GuardianDetailsService);
      resultGuardianDetails = undefined;
    });

    describe('resolve', () => {
      it('should return IGuardianDetails returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultGuardianDetails = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultGuardianDetails).toEqual({ id: 123 });
      });

      it('should return new IGuardianDetails if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultGuardianDetails = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultGuardianDetails).toEqual(new GuardianDetails());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultGuardianDetails = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultGuardianDetails).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
