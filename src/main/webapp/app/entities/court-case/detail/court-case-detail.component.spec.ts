import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CourtCaseDetailComponent } from './court-case-detail.component';

describe('CourtCase Management Detail Component', () => {
  let comp: CourtCaseDetailComponent;
  let fixture: ComponentFixture<CourtCaseDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CourtCaseDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ courtCase: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CourtCaseDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CourtCaseDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load courtCase on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.courtCase).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
