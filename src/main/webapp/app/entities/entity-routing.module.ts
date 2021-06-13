import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'personal-details',
        data: { pageTitle: 'templeMsApp.personalDetails.home.title' },
        loadChildren: () => import('./personal-details/personal-details.module').then(m => m.PersonalDetailsModule),
      },
      {
        path: 'address',
        data: { pageTitle: 'templeMsApp.address.home.title' },
        loadChildren: () => import('./address/address.module').then(m => m.AddressModule),
      },
      {
        path: 'emergency-contact-details',
        data: { pageTitle: 'templeMsApp.emergencyContactDetails.home.title' },
        loadChildren: () =>
          import('./emergency-contact-details/emergency-contact-details.module').then(m => m.EmergencyContactDetailsModule),
      },
      {
        path: 'guardian-details',
        data: { pageTitle: 'templeMsApp.guardianDetails.home.title' },
        loadChildren: () => import('./guardian-details/guardian-details.module').then(m => m.GuardianDetailsModule),
      },
      {
        path: 'monk-details',
        data: { pageTitle: 'templeMsApp.monkDetails.home.title' },
        loadChildren: () => import('./monk-details/monk-details.module').then(m => m.MonkDetailsModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
