import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { EmergencyContactDetailsComponent } from './list/emergency-contact-details.component';
import { EmergencyContactDetailsDetailComponent } from './detail/emergency-contact-details-detail.component';
import { EmergencyContactDetailsUpdateComponent } from './update/emergency-contact-details-update.component';
import { EmergencyContactDetailsDeleteDialogComponent } from './delete/emergency-contact-details-delete-dialog.component';
import { EmergencyContactDetailsRoutingModule } from './route/emergency-contact-details-routing.module';

@NgModule({
  imports: [SharedModule, EmergencyContactDetailsRoutingModule],
  declarations: [
    EmergencyContactDetailsComponent,
    EmergencyContactDetailsDetailComponent,
    EmergencyContactDetailsUpdateComponent,
    EmergencyContactDetailsDeleteDialogComponent,
  ],
  entryComponents: [EmergencyContactDetailsDeleteDialogComponent],
})
export class EmergencyContactDetailsModule {}
