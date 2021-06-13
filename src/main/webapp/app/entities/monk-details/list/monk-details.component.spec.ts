import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { MonkDetailsService } from '../service/monk-details.service';

import { MonkDetailsComponent } from './monk-details.component';

describe('Component Tests', () => {
  describe('MonkDetails Management Component', () => {
    let comp: MonkDetailsComponent;
    let fixture: ComponentFixture<MonkDetailsComponent>;
    let service: MonkDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [MonkDetailsComponent],
      })
        .overrideTemplate(MonkDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MonkDetailsComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(MonkDetailsService);

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
      expect(comp.monkDetails?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
