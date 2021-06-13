import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EmergencyContactDetailsDetailComponent } from './emergency-contact-details-detail.component';

describe('Component Tests', () => {
  describe('EmergencyContactDetails Management Detail Component', () => {
    let comp: EmergencyContactDetailsDetailComponent;
    let fixture: ComponentFixture<EmergencyContactDetailsDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [EmergencyContactDetailsDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ emergencyContactDetails: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(EmergencyContactDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmergencyContactDetailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load emergencyContactDetails on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.emergencyContactDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
