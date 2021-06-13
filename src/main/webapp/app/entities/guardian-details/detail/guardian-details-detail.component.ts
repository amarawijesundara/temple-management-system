import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGuardianDetails } from '../guardian-details.model';

@Component({
  selector: 'jhi-guardian-details-detail',
  templateUrl: './guardian-details-detail.component.html',
})
export class GuardianDetailsDetailComponent implements OnInit {
  guardianDetails: IGuardianDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ guardianDetails }) => {
      this.guardianDetails = guardianDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
