import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MonkDetailsDetailComponent } from './monk-details-detail.component';

describe('Component Tests', () => {
  describe('MonkDetails Management Detail Component', () => {
    let comp: MonkDetailsDetailComponent;
    let fixture: ComponentFixture<MonkDetailsDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [MonkDetailsDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ monkDetails: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(MonkDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MonkDetailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load monkDetails on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.monkDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
