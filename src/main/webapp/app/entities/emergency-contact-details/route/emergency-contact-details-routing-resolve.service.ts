import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEmergencyContactDetails, EmergencyContactDetails } from '../emergency-contact-details.model';
import { EmergencyContactDetailsService } from '../service/emergency-contact-details.service';

@Injectable({ providedIn: 'root' })
export class EmergencyContactDetailsRoutingResolveService implements Resolve<IEmergencyContactDetails> {
  constructor(protected service: EmergencyContactDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmergencyContactDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((emergencyContactDetails: HttpResponse<EmergencyContactDetails>) => {
          if (emergencyContactDetails.body) {
            return of(emergencyContactDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EmergencyContactDetails());
  }
}
