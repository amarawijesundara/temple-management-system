jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EmergencyContactDetailsService } from '../service/emergency-contact-details.service';
import { IEmergencyContactDetails, EmergencyContactDetails } from '../emergency-contact-details.model';
import { IPersonalDetails } from 'app/entities/personal-details/personal-details.model';
import { PersonalDetailsService } from 'app/entities/personal-details/service/personal-details.service';

import { EmergencyContactDetailsUpdateComponent } from './emergency-contact-details-update.component';

describe('Component Tests', () => {
  describe('EmergencyContactDetails Management Update Component', () => {
    let comp: EmergencyContactDetailsUpdateComponent;
    let fixture: ComponentFixture<EmergencyContactDetailsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let emergencyContactDetailsService: EmergencyContactDetailsService;
    let personalDetailsService: PersonalDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EmergencyContactDetailsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EmergencyContactDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmergencyContactDetailsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      emergencyContactDetailsService = TestBed.inject(EmergencyContactDetailsService);
      personalDetailsService = TestBed.inject(PersonalDetailsService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call PersonalDetails query and add missing value', () => {
        const emergencyContactDetails: IEmergencyContactDetails = { id: 456 };
        const personalDetails: IPersonalDetails = { id: 6909 };
        emergencyContactDetails.personalDetails = personalDetails;

        const personalDetailsCollection: IPersonalDetails[] = [{ id: 2742 }];
        spyOn(personalDetailsService, 'query').and.returnValue(of(new HttpResponse({ body: personalDetailsCollection })));
        const additionalPersonalDetails = [personalDetails];
        const expectedCollection: IPersonalDetails[] = [...additionalPersonalDetails, ...personalDetailsCollection];
        spyOn(personalDetailsService, 'addPersonalDetailsToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ emergencyContactDetails });
        comp.ngOnInit();

        expect(personalDetailsService.query).toHaveBeenCalled();
        expect(personalDetailsService.addPersonalDetailsToCollectionIfMissing).toHaveBeenCalledWith(
          personalDetailsCollection,
          ...additionalPersonalDetails
        );
        expect(comp.personalDetailsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const emergencyContactDetails: IEmergencyContactDetails = { id: 456 };
        const personalDetails: IPersonalDetails = { id: 26845 };
        emergencyContactDetails.personalDetails = personalDetails;

        activatedRoute.data = of({ emergencyContactDetails });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(emergencyContactDetails));
        expect(comp.personalDetailsSharedCollection).toContain(personalDetails);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const emergencyContactDetails = { id: 123 };
        spyOn(emergencyContactDetailsService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ emergencyContactDetails });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: emergencyContactDetails }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(emergencyContactDetailsService.update).toHaveBeenCalledWith(emergencyContactDetails);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const emergencyContactDetails = new EmergencyContactDetails();
        spyOn(emergencyContactDetailsService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ emergencyContactDetails });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: emergencyContactDetails }));
        saveSubject.complete();

        // THEN
        expect(emergencyContactDetailsService.create).toHaveBeenCalledWith(emergencyContactDetails);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const emergencyContactDetails = { id: 123 };
        spyOn(emergencyContactDetailsService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ emergencyContactDetails });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(emergencyContactDetailsService.update).toHaveBeenCalledWith(emergencyContactDetails);
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
