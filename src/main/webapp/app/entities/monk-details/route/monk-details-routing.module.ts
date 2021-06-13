import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MonkDetailsComponent } from '../list/monk-details.component';
import { MonkDetailsDetailComponent } from '../detail/monk-details-detail.component';
import { MonkDetailsUpdateComponent } from '../update/monk-details-update.component';
import { MonkDetailsRoutingResolveService } from './monk-details-routing-resolve.service';

const monkDetailsRoute: Routes = [
  {
    path: '',
    component: MonkDetailsComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MonkDetailsDetailComponent,
    resolve: {
      monkDetails: MonkDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MonkDetailsUpdateComponent,
    resolve: {
      monkDetails: MonkDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MonkDetailsUpdateComponent,
    resolve: {
      monkDetails: MonkDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(monkDetailsRoute)],
  exports: [RouterModule],
})
export class MonkDetailsRoutingModule {}
