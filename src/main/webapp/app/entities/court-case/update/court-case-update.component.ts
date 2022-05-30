import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICourtCase, CourtCase } from '../court-case.model';
import { CourtCaseService } from '../service/court-case.service';

@Component({
  selector: 'jhi-court-case-update',
  templateUrl: './court-case-update.component.html',
})
export class CourtCaseUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    address: [],
    contactNo: [],
    emailAddress: [],
    salary: [],
    gstOnSalary: [],
    lastModified: [],
  });

  constructor(protected courtCaseService: CourtCaseService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ courtCase }) => {
      this.updateForm(courtCase);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const courtCase = this.createFromForm();
    if (courtCase.id !== undefined) {
      this.subscribeToSaveResponse(this.courtCaseService.update(courtCase));
    } else {
      this.subscribeToSaveResponse(this.courtCaseService.create(courtCase));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICourtCase>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(courtCase: ICourtCase): void {
    this.editForm.patchValue({
      id: courtCase.id,
      firstName: courtCase.firstName,
      lastName: courtCase.lastName,
      address: courtCase.address,
      contactNo: courtCase.contactNo,
      emailAddress: courtCase.emailAddress,
      salary: courtCase.salary,
      gstOnSalary: courtCase.gstOnSalary,
      lastModified: courtCase.lastModified,
    });
  }

  protected createFromForm(): ICourtCase {
    return {
      ...new CourtCase(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      address: this.editForm.get(['address'])!.value,
      contactNo: this.editForm.get(['contactNo'])!.value,
      emailAddress: this.editForm.get(['emailAddress'])!.value,
      salary: this.editForm.get(['salary'])!.value,
      gstOnSalary: this.editForm.get(['gstOnSalary'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
    };
  }
}
