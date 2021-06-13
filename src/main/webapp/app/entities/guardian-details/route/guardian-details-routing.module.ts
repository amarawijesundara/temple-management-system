import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GuardianDetailsComponent } from '../list/guardian-details.component';
import { GuardianDetailsDetailComponent } from '../detail/guardian-details-detail.component';
import { GuardianDetailsUpdateComponent } from '../update/guardian-details-update.component';
import { GuardianDetailsRoutingResolveService } from './guardian-details-routing-resolve.service';

const guardianDetailsRoute: Routes = [
  {
    path: '',
    component: GuardianDetailsComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GuardianDetailsDetailComponent,
    resolve: {
      guardianDetails: GuardianDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GuardianDetailsUpdateComponent,
    resolve: {
      guardianDetails: GuardianDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GuardianDetailsUpdateComponent,
    resolve: {
      guardianDetails: GuardianDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(guardianDetailsRoute)],
  exports: [RouterModule],
})
export class GuardianDetailsRoutingModule {}
