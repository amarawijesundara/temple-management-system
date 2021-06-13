jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { MonkDetailsService } from '../service/monk-details.service';
import { IMonkDetails, MonkDetails } from '../monk-details.model';

import { MonkDetailsUpdateComponent } from './monk-details-update.component';

describe('Component Tests', () => {
  describe('MonkDetails Management Update Component', () => {
    let comp: MonkDetailsUpdateComponent;
    let fixture: ComponentFixture<MonkDetailsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let monkDetailsService: MonkDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [MonkDetailsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(MonkDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MonkDetailsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      monkDetailsService = TestBed.inject(MonkDetailsService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const monkDetails: IMonkDetails = { id: 456 };

        activatedRoute.data = of({ monkDetails });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(monkDetails));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const monkDetails = { id: 123 };
        spyOn(monkDetailsService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ monkDetails });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: monkDetails }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(monkDetailsService.update).toHaveBeenCalledWith(monkDetails);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const monkDetails = new MonkDetails();
        spyOn(monkDetailsService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ monkDetails });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: monkDetails }));
        saveSubject.complete();

        // THEN
        expect(monkDetailsService.create).toHaveBeenCalledWith(monkDetails);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const monkDetails = { id: 123 };
        spyOn(monkDetailsService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ monkDetails });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(monkDetailsService.update).toHaveBeenCalledWith(monkDetails);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
