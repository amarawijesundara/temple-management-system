jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IEmergencyContactDetails, EmergencyContactDetails } from '../emergency-contact-details.model';
import { EmergencyContactDetailsService } from '../service/emergency-contact-details.service';

import { EmergencyContactDetailsRoutingResolveService } from './emergency-contact-details-routing-resolve.service';

describe('Service Tests', () => {
  describe('EmergencyContactDetails routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: EmergencyContactDetailsRoutingResolveService;
    let service: EmergencyContactDetailsService;
    let resultEmergencyContactDetails: IEmergencyContactDetails | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(EmergencyContactDetailsRoutingResolveService);
      service = TestBed.inject(EmergencyContactDetailsService);
      resultEmergencyContactDetails = undefined;
    });

    describe('resolve', () => {
      it('should return IEmergencyContactDetails returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEmergencyContactDetails = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEmergencyContactDetails).toEqual({ id: 123 });
      });

      it('should return new IEmergencyContactDetails if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEmergencyContactDetails = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultEmergencyContactDetails).toEqual(new EmergencyContactDetails());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEmergencyContactDetails = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEmergencyContactDetails).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
