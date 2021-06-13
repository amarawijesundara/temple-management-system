import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMonkDetails } from '../monk-details.model';
import { MonkDetailsService } from '../service/monk-details.service';

@Component({
  templateUrl: './monk-details-delete-dialog.component.html',
})
export class MonkDetailsDeleteDialogComponent {
  monkDetails?: IMonkDetails;

  constructor(protected monkDetailsService: MonkDetailsService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.monkDetailsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
