import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmergencyContactDetails } from '../emergency-contact-details.model';

@Component({
  selector: 'jhi-emergency-contact-details-detail',
  templateUrl: './emergency-contact-details-detail.component.html',
})
export class EmergencyContactDetailsDetailComponent implements OnInit {
  emergencyContactDetails: IEmergencyContactDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emergencyContactDetails }) => {
      this.emergencyContactDetails = emergencyContactDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
