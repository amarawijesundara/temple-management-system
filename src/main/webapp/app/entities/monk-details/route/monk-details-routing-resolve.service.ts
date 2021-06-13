import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMonkDetails, MonkDetails } from '../monk-details.model';
import { MonkDetailsService } from '../service/monk-details.service';

@Injectable({ providedIn: 'root' })
export class MonkDetailsRoutingResolveService implements Resolve<IMonkDetails> {
  constructor(protected service: MonkDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMonkDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((monkDetails: HttpResponse<MonkDetails>) => {
          if (monkDetails.body) {
            return of(monkDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MonkDetails());
  }
}
