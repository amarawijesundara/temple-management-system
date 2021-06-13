jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AddressService } from '../service/address.service';
import { IAddress, Address } from '../address.model';
import { IPersonalDetails } from 'app/entities/personal-details/personal-details.model';
import { PersonalDetailsService } from 'app/entities/personal-details/service/personal-details.service';

import { AddressUpdateComponent } from './address-update.component';

describe('Component Tests', () => {
  describe('Address Management Update Component', () => {
    let comp: AddressUpdateComponent;
    let fixture: ComponentFixture<AddressUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let addressService: AddressService;
    let personalDetailsService: PersonalDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AddressUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AddressUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AddressUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      addressService = TestBed.inject(AddressService);
      personalDetailsService = TestBed.inject(PersonalDetailsService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call PersonalDetails query and add missing value', () => {
        const address: IAddress = { id: 456 };
        const personalDetails: IPersonalDetails = { id: 57042 };
        address.personalDetails = personalDetails;

        const personalDetailsCollection: IPersonalDetails[] = [{ id: 39121 }];
        spyOn(personalDetailsService, 'query').and.returnValue(of(new HttpResponse({ body: personalDetailsCollection })));
        const additionalPersonalDetails = [personalDetails];
        const expectedCollection: IPersonalDetails[] = [...additionalPersonalDetails, ...personalDetailsCollection];
        spyOn(personalDetailsService, 'addPersonalDetailsToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ address });
        comp.ngOnInit();

        expect(personalDetailsService.query).toHaveBeenCalled();
        expect(personalDetailsService.addPersonalDetailsToCollectionIfMissing).toHaveBeenCalledWith(
          personalDetailsCollection,
          ...additionalPersonalDetails
        );
        expect(comp.personalDetailsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const address: IAddress = { id: 456 };
        const personalDetails: IPersonalDetails = { id: 2218 };
        address.personalDetails = personalDetails;

        activatedRoute.data = of({ address });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(address));
        expect(comp.personalDetailsSharedCollection).toContain(personalDetails);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const address = { id: 123 };
        spyOn(addressService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ address });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: address }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(addressService.update).toHaveBeenCalledWith(address);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const address = new Address();
        spyOn(addressService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ address });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: address }));
        saveSubject.complete();

        // THEN
        expect(addressService.create).toHaveBeenCalledWith(address);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const address = { id: 123 };
        spyOn(addressService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ address });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(addressService.update).toHaveBeenCalledWith(address);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackPersonalDetailsById', () => {
        it('Should return tracked PersonalDetails primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackPersonalDetailsById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
