import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { GuardianDetailsComponent } from './list/guardian-details.component';
import { GuardianDetailsDetailComponent } from './detail/guardian-details-detail.component';
import { GuardianDetailsUpdateComponent } from './update/guardian-details-update.component';
import { GuardianDetailsDeleteDialogComponent } from './delete/guardian-details-delete-dialog.component';
import { GuardianDetailsRoutingModule } from './route/guardian-details-routing.module';

@NgModule({
  imports: [SharedModule, GuardianDetailsRoutingModule],
  declarations: [
    GuardianDetailsComponent,
    GuardianDetailsDetailComponent,
    GuardianDetailsUpdateComponent,
    GuardianDetailsDeleteDialogComponent,
  ],
  entryComponents: [GuardianDetailsDeleteDialogComponent],
})
export class GuardianDetailsModule {}
