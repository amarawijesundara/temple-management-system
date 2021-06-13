import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMonkDetails } from '../monk-details.model';

@Component({
  selector: 'jhi-monk-details-detail',
  templateUrl: './monk-details-detail.component.html',
})
export class MonkDetailsDetailComponent implements OnInit {
  monkDetails: IMonkDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ monkDetails }) => {
      this.monkDetails = monkDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
