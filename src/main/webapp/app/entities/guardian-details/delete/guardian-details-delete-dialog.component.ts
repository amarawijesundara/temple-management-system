import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IGuardianDetails } from '../guardian-details.model';
import { GuardianDetailsService } from '../service/guardian-details.service';

@Component({
  templateUrl: './guardian-details-delete-dialog.component.html',
})
export class GuardianDetailsDeleteDialogComponent {
  guardianDetails?: IGuardianDetails;

  constructor(protected guardianDetailsService: GuardianDetailsService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.guardianDetailsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
