import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EmergencyContactDetailsComponent } from '../list/emergency-contact-details.component';
import { EmergencyContactDetailsDetailComponent } from '../detail/emergency-contact-details-detail.component';
import { EmergencyContactDetailsUpdateComponent } from '../update/emergency-contact-details-update.component';
import { EmergencyContactDetailsRoutingResolveService } from './emergency-contact-details-routing-resolve.service';

const emergencyContactDetailsRoute: Routes = [
  {
    path: '',
    component: EmergencyContactDetailsComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmergencyContactDetailsDetailComponent,
    resolve: {
      emergencyContactDetails: EmergencyContactDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmergencyContactDetailsUpdateComponent,
    resolve: {
      emergencyContactDetails: EmergencyContactDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmergencyContactDetailsUpdateComponent,
    resolve: {
      emergencyContactDetails: EmergencyContactDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(emergencyContactDetailsRoute)],
  exports: [RouterModule],
})
export class EmergencyContactDetailsRoutingModule {}
