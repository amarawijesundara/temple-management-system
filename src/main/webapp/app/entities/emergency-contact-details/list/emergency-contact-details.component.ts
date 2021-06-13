import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmergencyContactDetails } from '../emergency-contact-details.model';
import { EmergencyContactDetailsService } from '../service/emergency-contact-details.service';
import { EmergencyContactDetailsDeleteDialogComponent } from '../delete/emergency-contact-details-delete-dialog.component';

@Component({
  selector: 'jhi-emergency-contact-details',
  templateUrl: './emergency-contact-details.component.html',
})
export class EmergencyContactDetailsComponent implements OnInit {
  emergencyContactDetails?: IEmergencyContactDetails[];
  isLoading = false;

  constructor(protected emergencyContactDetailsService: EmergencyContactDetailsService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.emergencyContactDetailsService.query().subscribe(
      (res: HttpResponse<IEmergencyContactDetails[]>) => {
        this.isLoading = false;
        this.emergencyContactDetails = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IEmergencyContactDetails): number {
    return item.id!;
  }

  delete(emergencyContactDetails: IEmergencyContactDetails): void {
    const modalRef = this.modalService.open(EmergencyContactDetailsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.emergencyContactDetails = emergencyContactDetails;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
