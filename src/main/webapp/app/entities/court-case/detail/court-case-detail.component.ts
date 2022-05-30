import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICourtCase } from '../court-case.model';

@Component({
  selector: 'jhi-court-case-detail',
  templateUrl: './court-case-detail.component.html',
})
export class CourtCaseDetailComponent implements OnInit {
  courtCase: ICourtCase | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ courtCase }) => {
      this.courtCase = courtCase;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
