import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAddress, Address } from '../address.model';
import { AddressService } from '../service/address.service';
import { IPersonalDetails } from 'app/entities/personal-details/personal-details.model';
import { PersonalDetailsService } from 'app/entities/personal-details/service/personal-details.service';

@Component({
  selector: 'jhi-address-update',
  templateUrl: './address-update.component.html',
})
export class AddressUpdateComponent implements OnInit {
  isSaving = false;

  personalDetailsSharedCollection: IPersonalDetails[] = [];

  editForm = this.fb.group({
    id: [],
    type: [],
    address1: [],
    address2: [],
    postCode: [],
    personalDetails: [],
  });

  constructor(
    protected addressService: AddressService,
    protected personalDetailsService: PersonalDetailsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ address }) => {
      this.updateForm(address);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const address = this.createFromForm();
    if (address.id !== undefined) {
      this.subscribeToSaveResponse(this.addressService.update(address));
    } else {
      this.subscribeToSaveResponse(this.addressService.create(address));
    }
  }

  trackPersonalDetailsById(index: number, item: IPersonalDetails): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddress>>): void {
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

  protected updateForm(address: IAddress): void {
    this.editForm.patchValue({
      id: address.id,
      type: address.type,
      address1: address.address1,
      address2: address.address2,
      postCode: address.postCode,
      personalDetails: address.personalDetails,
    });

    this.personalDetailsSharedCollection = this.personalDetailsService.addPersonalDetailsToCollectionIfMissing(
      this.personalDetailsSharedCollection,
      address.personalDetails
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

  protected createFromForm(): IAddress {
    return {
      ...new Address(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value,
      address1: this.editForm.get(['address1'])!.value,
      address2: this.editForm.get(['address2'])!.value,
      postCode: this.editForm.get(['postCode'])!.value,
      personalDetails: this.editForm.get(['personalDetails'])!.value,
    };
  }
}
