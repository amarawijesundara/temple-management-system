import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPersonalDetails, PersonalDetails } from '../personal-details.model';
import { PersonalDetailsService } from '../service/personal-details.service';
import { IMonkDetails } from 'app/entities/monk-details/monk-details.model';
import { MonkDetailsService } from 'app/entities/monk-details/service/monk-details.service';

@Component({
  selector: 'jhi-personal-details-update',
  templateUrl: './personal-details-update.component.html',
})
export class PersonalDetailsUpdateComponent implements OnInit {
  isSaving = false;

  monkDetailsCollection: IMonkDetails[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    gender: [],
    nic: [],
    nationality: [],
    ethnicity: [],
    passport: [],
    dateOfBirth: [],
    telephone: [],
    mobile: [],
    isBikshu: [],
    isAnagarika: [],
    isUpasaka: [],
    notes: [],
    createdDate: [],
    updatedDate: [],
    monkDetails: [],
  });

  constructor(
    protected personalDetailsService: PersonalDetailsService,
    protected monkDetailsService: MonkDetailsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personalDetails }) => {
      this.updateForm(personalDetails);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const personalDetails = this.createFromForm();
    if (personalDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.personalDetailsService.update(personalDetails));
    } else {
      this.subscribeToSaveResponse(this.personalDetailsService.create(personalDetails));
    }
  }

  trackMonkDetailsById(index: number, item: IMonkDetails): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonalDetails>>): void {
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

  protected updateForm(personalDetails: IPersonalDetails): void {
    this.editForm.patchValue({
      id: personalDetails.id,
      name: personalDetails.name,
      gender: personalDetails.gender,
      nic: personalDetails.nic,
      nationality: personalDetails.nationality,
      ethnicity: personalDetails.ethnicity,
      passport: personalDetails.passport,
      dateOfBirth: personalDetails.dateOfBirth,
      telephone: personalDetails.telephone,
      mobile: personalDetails.mobile,
      isBikshu: personalDetails.isBikshu,
      isAnagarika: personalDetails.isAnagarika,
      isUpasaka: personalDetails.isUpasaka,
      notes: personalDetails.notes,
      createdDate: personalDetails.createdDate,
      updatedDate: personalDetails.updatedDate,
      monkDetails: personalDetails.monkDetails,
    });

    this.monkDetailsCollection = this.monkDetailsService.addMonkDetailsToCollectionIfMissing(
      this.monkDetailsCollection,
      personalDetails.monkDetails
    );
  }

  protected loadRelationshipsOptions(): void {
    this.monkDetailsService
      .query({ filter: 'personaldetails-is-null' })
      .pipe(map((res: HttpResponse<IMonkDetails[]>) => res.body ?? []))
      .pipe(
        map((monkDetails: IMonkDetails[]) =>
          this.monkDetailsService.addMonkDetailsToCollectionIfMissing(monkDetails, this.editForm.get('monkDetails')!.value)
        )
      )
      .subscribe((monkDetails: IMonkDetails[]) => (this.monkDetailsCollection = monkDetails));
  }

  protected createFromForm(): IPersonalDetails {
    return {
      ...new PersonalDetails(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      nic: this.editForm.get(['nic'])!.value,
      nationality: this.editForm.get(['nationality'])!.value,
      ethnicity: this.editForm.get(['ethnicity'])!.value,
      passport: this.editForm.get(['passport'])!.value,
      dateOfBirth: this.editForm.get(['dateOfBirth'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      mobile: this.editForm.get(['mobile'])!.value,
      isBikshu: this.editForm.get(['isBikshu'])!.value,
      isAnagarika: this.editForm.get(['isAnagarika'])!.value,
      isUpasaka: this.editForm.get(['isUpasaka'])!.value,
      notes: this.editForm.get(['notes'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value,
      updatedDate: this.editForm.get(['updatedDate'])!.value,
      monkDetails: this.editForm.get(['monkDetails'])!.value,
    };
  }
}
