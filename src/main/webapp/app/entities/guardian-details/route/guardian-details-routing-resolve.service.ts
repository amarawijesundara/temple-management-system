import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGuardianDetails, GuardianDetails } from '../guardian-details.model';
import { GuardianDetailsService } from '../service/guardian-details.service';

@Injectable({ providedIn: 'root' })
export class GuardianDetailsRoutingResolveService implements Resolve<IGuardianDetails> {
  constructor(protected service: GuardianDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGuardianDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((guardianDetails: HttpResponse<GuardianDetails>) => {
          if (guardianDetails.body) {
            return of(guardianDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GuardianDetails());
  }
}
