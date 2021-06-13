import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmergencyContactDetails } from '../emergency-contact-details.model';
import { EmergencyContactDetailsService } from '../service/emergency-contact-details.service';

@Component({
  templateUrl: './emergency-contact-details-delete-dialog.component.html',
})
export class EmergencyContactDetailsDeleteDialogComponent {
  emergencyContactDetails?: IEmergencyContactDetails;

  constructor(protected emergencyContactDetailsService: EmergencyContactDetailsService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.emergencyContactDetailsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
