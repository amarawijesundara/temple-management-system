import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { MonkDetailsComponent } from './list/monk-details.component';
import { MonkDetailsDetailComponent } from './detail/monk-details-detail.component';
import { MonkDetailsUpdateComponent } from './update/monk-details-update.component';
import { MonkDetailsDeleteDialogComponent } from './delete/monk-details-delete-dialog.component';
import { MonkDetailsRoutingModule } from './route/monk-details-routing.module';

@NgModule({
  imports: [SharedModule, MonkDetailsRoutingModule],
  declarations: [MonkDetailsComponent, MonkDetailsDetailComponent, MonkDetailsUpdateComponent, MonkDetailsDeleteDialogComponent],
  entryComponents: [MonkDetailsDeleteDialogComponent],
})
export class MonkDetailsModule {}
