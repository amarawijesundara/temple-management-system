jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { GuardianDetailsService } from '../service/guardian-details.service';
import { IGuardianDetails, GuardianDetails } from '../guardian-details.model';
import { IPersonalDetails } from 'app/entities/personal-details/personal-details.model';
import { PersonalDetailsService } from 'app/entities/personal-details/service/personal-details.service';

import { GuardianDetailsUpdateComponent } from './guardian-details-update.component';

describe('Component Tests', () => {
  describe('GuardianDetails Management Update Component', () => {
    let comp: GuardianDetailsUpdateComponent;
    let fixture: ComponentFixture<GuardianDetailsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let guardianDetailsService: GuardianDetailsService;
    let personalDetailsService: PersonalDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [GuardianDetailsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(GuardianDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GuardianDetailsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      guardianDetailsService = TestBed.inject(GuardianDetailsService);
      personalDetailsService = TestBed.inject(PersonalDetailsService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call PersonalDetails query and add missing value', () => {
        const guardianDetails: IGuardianDetails = { id: 456 };
        const personalDetails: IPersonalDetails = { id: 59488 };
        guardianDetails.personalDetails = personalDetails;

        const personalDetailsCollection: IPersonalDetails[] = [{ id: 22847 }];
        spyOn(personalDetailsService, 'query').and.returnValue(of(new HttpResponse({ body: personalDetailsCollection })));
        const additionalPersonalDetails = [personalDetails];
        const expectedCollection: IPersonalDetails[] = [...additionalPersonalDetails, ...personalDetailsCollection];
        spyOn(personalDetailsService, 'addPersonalDetailsToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ guardianDetails });
        comp.ngOnInit();

        expect(personalDetailsService.query).toHaveBeenCalled();
        expect(personalDetailsService.addPersonalDetailsToCollectionIfMissing).toHaveBeenCalledWith(
          personalDetailsCollection,
          ...additionalPersonalDetails
        );
        expect(comp.personalDetailsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const guardianDetails: IGuardianDetails = { id: 456 };
        const personalDetails: IPersonalDetails = { id: 94677 };
        guardianDetails.personalDetails = personalDetails;

        activatedRoute.data = of({ guardianDetails });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(guardianDetails));
        expect(comp.personalDetailsSharedCollection).toContain(personalDetails);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const guardianDetails = { id: 123 };
        spyOn(guardianDetailsService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ guardianDetails });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: guardianDetails }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(guardianDetailsService.update).toHaveBeenCalledWith(guardianDetails);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const guardianDetails = new GuardianDetails();
        spyOn(guardianDetailsService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ guardianDetails });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: guardianDetails }));
        saveSubject.complete();

        // THEN
        expect(guardianDetailsService.create).toHaveBeenCalledWith(guardianDetails);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const guardianDetails = { id: 123 };
        spyOn(guardianDetailsService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ guardianDetails });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(guardianDetailsService.update).toHaveBeenCalledWith(guardianDetails);
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
