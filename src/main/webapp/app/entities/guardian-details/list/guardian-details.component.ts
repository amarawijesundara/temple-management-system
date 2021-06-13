import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGuardianDetails } from '../guardian-details.model';
import { GuardianDetailsService } from '../service/guardian-details.service';
import { GuardianDetailsDeleteDialogComponent } from '../delete/guardian-details-delete-dialog.component';

@Component({
  selector: 'jhi-guardian-details',
  templateUrl: './guardian-details.component.html',
})
export class GuardianDetailsComponent implements OnInit {
  guardianDetails?: IGuardianDetails[];
  isLoading = false;

  constructor(protected guardianDetailsService: GuardianDetailsService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.guardianDetailsService.query().subscribe(
      (res: HttpResponse<IGuardianDetails[]>) => {
        this.isLoading = false;
        this.guardianDetails = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IGuardianDetails): number {
    return item.id!;
  }

  delete(guardianDetails: IGuardianDetails): void {
    const modalRef = this.modalService.open(GuardianDetailsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.guardianDetails = guardianDetails;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
