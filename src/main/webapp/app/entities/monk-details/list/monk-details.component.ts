import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMonkDetails } from '../monk-details.model';
import { MonkDetailsService } from '../service/monk-details.service';
import { MonkDetailsDeleteDialogComponent } from '../delete/monk-details-delete-dialog.component';

@Component({
  selector: 'jhi-monk-details',
  templateUrl: './monk-details.component.html',
})
export class MonkDetailsComponent implements OnInit {
  monkDetails?: IMonkDetails[];
  isLoading = false;

  constructor(protected monkDetailsService: MonkDetailsService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.monkDetailsService.query().subscribe(
      (res: HttpResponse<IMonkDetails[]>) => {
        this.isLoading = false;
        this.monkDetails = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IMonkDetails): number {
    return item.id!;
  }

  delete(monkDetails: IMonkDetails): void {
    const modalRef = this.modalService.open(MonkDetailsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.monkDetails = monkDetails;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
