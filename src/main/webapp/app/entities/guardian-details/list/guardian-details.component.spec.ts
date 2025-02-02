import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { GuardianDetailsService } from '../service/guardian-details.service';

import { GuardianDetailsComponent } from './guardian-details.component';

describe('Component Tests', () => {
  describe('GuardianDetails Management Component', () => {
    let comp: GuardianDetailsComponent;
    let fixture: ComponentFixture<GuardianDetailsComponent>;
    let service: GuardianDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [GuardianDetailsComponent],
      })
        .overrideTemplate(GuardianDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GuardianDetailsComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(GuardianDetailsService);

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
      expect(comp.guardianDetails?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
