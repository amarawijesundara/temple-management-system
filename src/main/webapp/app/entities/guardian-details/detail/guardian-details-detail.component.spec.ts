import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GuardianDetailsDetailComponent } from './guardian-details-detail.component';

describe('Component Tests', () => {
  describe('GuardianDetails Management Detail Component', () => {
    let comp: GuardianDetailsDetailComponent;
    let fixture: ComponentFixture<GuardianDetailsDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [GuardianDetailsDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ guardianDetails: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(GuardianDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GuardianDetailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load guardianDetails on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.guardianDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
