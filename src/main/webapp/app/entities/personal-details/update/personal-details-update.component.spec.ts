jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PersonalDetailsService } from '../service/personal-details.service';
import { IPersonalDetails, PersonalDetails } from '../personal-details.model';
import { IMonkDetails } from 'app/entities/monk-details/monk-details.model';
import { MonkDetailsService } from 'app/entities/monk-details/service/monk-details.service';

import { PersonalDetailsUpdateComponent } from './personal-details-update.component';

describe('Component Tests', () => {
  describe('PersonalDetails Management Update Component', () => {
    let comp: PersonalDetailsUpdateComponent;
    let fixture: ComponentFixture<PersonalDetailsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let personalDetailsService: PersonalDetailsService;
    let monkDetailsService: MonkDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PersonalDetailsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(PersonalDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PersonalDetailsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      personalDetailsService = TestBed.inject(PersonalDetailsService);
      monkDetailsService = TestBed.inject(MonkDetailsService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call monkDetails query and add missing value', () => {
        const personalDetails: IPersonalDetails = { id: 456 };
        const monkDetails: IMonkDetails = { id: 46294 };
        personalDetails.monkDetails = monkDetails;

        const monkDetailsCollection: IMonkDetails[] = [{ id: 67909 }];
        spyOn(monkDetailsService, 'query').and.returnValue(of(new HttpResponse({ body: monkDetailsCollection })));
        const expectedCollection: IMonkDetails[] = [monkDetails, ...monkDetailsCollection];
        spyOn(monkDetailsService, 'addMonkDetailsToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ personalDetails });
        comp.ngOnInit();

        expect(monkDetailsService.query).toHaveBeenCalled();
        expect(monkDetailsService.addMonkDetailsToCollectionIfMissing).toHaveBeenCalledWith(monkDetailsCollection, monkDetails);
        expect(comp.monkDetailsCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const personalDetails: IPersonalDetails = { id: 456 };
        const monkDetails: IMonkDetails = { id: 33713 };
        personalDetails.monkDetails = monkDetails;

        activatedRoute.data = of({ personalDetails });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(personalDetails));
        expect(comp.monkDetailsCollection).toContain(monkDetails);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const personalDetails = { id: 123 };
        spyOn(personalDetailsService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ personalDetails });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: personalDetails }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(personalDetailsService.update).toHaveBeenCalledWith(personalDetails);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const personalDetails = new PersonalDetails();
        spyOn(personalDetailsService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ personalDetails });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: personalDetails }));
        saveSubject.complete();

        // THEN
        expect(personalDetailsService.create).toHaveBeenCalledWith(personalDetails);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const personalDetails = { id: 123 };
        spyOn(personalDetailsService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ personalDetails });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(personalDetailsService.update).toHaveBeenCalledWith(personalDetails);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackMonkDetailsById', () => {
        it('Should return tracked MonkDetails primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackMonkDetailsById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
