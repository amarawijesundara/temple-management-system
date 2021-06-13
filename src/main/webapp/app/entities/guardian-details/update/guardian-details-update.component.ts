import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IGuardianDetails, GuardianDetails } from '../guardian-details.model';
import { GuardianDetailsService } from '../service/guardian-details.service';
import { IPersonalDetails } from 'app/entities/personal-details/personal-details.model';
import { PersonalDetailsService } from 'app/entities/personal-details/service/personal-details.service';

@Component({
  selector: 'jhi-guardian-details-update',
  templateUrl: './guardian-details-update.component.html',
})
export class GuardianDetailsUpdateComponent implements OnInit {
  isSaving = false;

  personalDetailsSharedCollection: IPersonalDetails[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    address: [],
    contactType: [],
    createdDate: [],
    updatedDate: [],
    personalDetails: [],
  });

  constructor(
    protected guardianDetailsService: GuardianDetailsService,
    protected personalDetailsService: PersonalDetailsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ guardianDetails }) => {
      this.updateForm(guardianDetails);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const guardianDetails = this.createFromForm();
    if (guardianDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.guardianDetailsService.update(guardianDetails));
    } else {
      this.subscribeToSaveResponse(this.guardianDetailsService.create(guardianDetails));
    }
  }

  trackPersonalDetailsById(index: number, item: IPersonalDetails): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGuardianDetails>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(guardianDetails: IGuardianDetails): void {
    this.editForm.patchValue({
      id: guardianDetails.id,
      name: guardianDetails.name,
      address: guardianDetails.address,
      contactType: guardianDetails.contactType,
      createdDate: guardianDetails.createdDate,
      updatedDate: guardianDetails.updatedDate,
      personalDetails: guardianDetails.personalDetails,
    });

    this.personalDetailsSharedCollection = this.personalDetailsService.addPersonalDetailsToCollectionIfMissing(
      this.personalDetailsSharedCollection,
      guardianDetails.personalDetails
    );
  }

  protected loadRelationshipsOptions(): void {
    this.personalDetailsService
      .query()
      .pipe(map((res: HttpResponse<IPersonalDetails[]>) => res.body ?? []))
      .pipe(
        map((personalDetails: IPersonalDetails[]) =>
          this.personalDetailsService.addPersonalDetailsToCollectionIfMissing(personalDetails, this.editForm.get('personalDetails')!.value)
        )
      )
      .subscribe((personalDetails: IPersonalDetails[]) => (this.personalDetailsSharedCollection = personalDetails));
  }

  protected createFromForm(): IGuardianDetails {
    return {
      ...new GuardianDetails(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      address: this.editForm.get(['address'])!.value,
      contactType: this.editForm.get(['contactType'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value,
      updatedDate: this.editForm.get(['updatedDate'])!.value,
      personalDetails: this.editForm.get(['personalDetails'])!.value,
    };
  }
}
