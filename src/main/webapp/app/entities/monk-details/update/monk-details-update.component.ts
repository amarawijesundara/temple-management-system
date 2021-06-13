import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IMonkDetails, MonkDetails } from '../monk-details.model';
import { MonkDetailsService } from '../service/monk-details.service';

@Component({
  selector: 'jhi-monk-details-update',
  templateUrl: './monk-details-update.component.html',
})
export class MonkDetailsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nameEnglish: [],
    nameSinhala: [],
    pawidiNo: [],
    samaneraNo: [],
    upasampadaNo: [],
    pawidiDate: [],
    upasampadaDate: [],
    createdDate: [],
    updatedDate: [],
  });

  constructor(protected monkDetailsService: MonkDetailsService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ monkDetails }) => {
      this.updateForm(monkDetails);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const monkDetails = this.createFromForm();
    if (monkDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.monkDetailsService.update(monkDetails));
    } else {
      this.subscribeToSaveResponse(this.monkDetailsService.create(monkDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMonkDetails>>): void {
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

  protected updateForm(monkDetails: IMonkDetails): void {
    this.editForm.patchValue({
      id: monkDetails.id,
      nameEnglish: monkDetails.nameEnglish,
      nameSinhala: monkDetails.nameSinhala,
      pawidiNo: monkDetails.pawidiNo,
      samaneraNo: monkDetails.samaneraNo,
      upasampadaNo: monkDetails.upasampadaNo,
      pawidiDate: monkDetails.pawidiDate,
      upasampadaDate: monkDetails.upasampadaDate,
      createdDate: monkDetails.createdDate,
      updatedDate: monkDetails.updatedDate,
    });
  }

  protected createFromForm(): IMonkDetails {
    return {
      ...new MonkDetails(),
      id: this.editForm.get(['id'])!.value,
      nameEnglish: this.editForm.get(['nameEnglish'])!.value,
      nameSinhala: this.editForm.get(['nameSinhala'])!.value,
      pawidiNo: this.editForm.get(['pawidiNo'])!.value,
      samaneraNo: this.editForm.get(['samaneraNo'])!.value,
      upasampadaNo: this.editForm.get(['upasampadaNo'])!.value,
      pawidiDate: this.editForm.get(['pawidiDate'])!.value,
      upasampadaDate: this.editForm.get(['upasampadaDate'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value,
      updatedDate: this.editForm.get(['updatedDate'])!.value,
    };
  }
}
