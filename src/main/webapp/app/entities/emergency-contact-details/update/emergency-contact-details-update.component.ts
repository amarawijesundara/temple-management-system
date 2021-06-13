import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEmergencyContactDetails, EmergencyContactDetails } from '../emergency-contact-details.model';
import { EmergencyContactDetailsService } from '../service/emergency-contact-details.service';
import { IPersonalDetails } from 'app/entities/personal-details/personal-details.model';
import { PersonalDetailsService } from 'app/entities/personal-details/service/personal-details.service';

@Component({
  selector: 'jhi-emergency-contact-details-update',
  templateUrl: './emergency-contact-details-update.component.html',
})
export class EmergencyContactDetailsUpdateComponent implements OnInit {
  isSaving = false;

  personalDetailsSharedCollection: IPersonalDetails[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    address: [],
    telephone: [],
    email: [],
    contactType: [],
    createdDate: [],
    updatedDate: [],
    personalDetails: [],
  });

  constructor(
    protected emergencyContactDetailsService: EmergencyContactDetailsService,
    protected personalDetailsService: PersonalDetailsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emergencyContactDetails }) => {
      this.updateForm(emergencyContactDetails);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const emergencyContactDetails = this.createFromForm();
    if (emergencyContactDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.emergencyContactDetailsService.update(emergencyContactDetails));
    } else {
      this.subscribeToSaveResponse(this.emergencyContactDetailsService.create(emergencyContactDetails));
    }
  }

  trackPersonalDetailsById(index: number, item: IPersonalDetails): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmergencyContactDetails>>): void {
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

  protected updateForm(emergencyContactDetails: IEmergencyContactDetails): void {
    this.editForm.patchValue({
      id: emergencyContactDetails.id,
      name: emergencyContactDetails.name,
      address: emergencyContactDetails.address,
      telephone: emergencyContactDetails.telephone,
      email: emergencyContactDetails.email,
      contactType: emergencyContactDetails.contactType,
      createdDate: emergencyContactDetails.createdDate,
      updatedDate: emergencyContactDetails.updatedDate,
      personalDetails: emergencyContactDetails.personalDetails,
    });

    this.personalDetailsSharedCollection = this.personalDetailsService.addPersonalDetailsToCollectionIfMissing(
      this.personalDetailsSharedCollection,
      emergencyContactDetails.personalDetails
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

  protected createFromForm(): IEmergencyContactDetails {
    return {
      ...new EmergencyContactDetails(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      address: this.editForm.get(['address'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      email: this.editForm.get(['email'])!.value,
      contactType: this.editForm.get(['contactType'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value,
      updatedDate: this.editForm.get(['updatedDate'])!.value,
      personalDetails: this.editForm.get(['personalDetails'])!.value,
    };
  }
}
