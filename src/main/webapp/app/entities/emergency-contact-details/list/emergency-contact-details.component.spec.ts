import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { EmergencyContactDetailsService } from '../service/emergency-contact-details.service';

import { EmergencyContactDetailsComponent } from './emergency-contact-details.component';

describe('Component Tests', () => {
  describe('EmergencyContactDetails Management Component', () => {
    let comp: EmergencyContactDetailsComponent;
    let fixture: ComponentFixture<EmergencyContactDetailsComponent>;
    let service: EmergencyContactDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EmergencyContactDetailsComponent],
      })
        .overrideTemplate(EmergencyContactDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmergencyContactDetailsComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(EmergencyContactDetailsService);

      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.emergencyContactDetails?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
