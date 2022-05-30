import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CourtCaseComponent } from './list/court-case.component';
import { CourtCaseDetailComponent } from './detail/court-case-detail.component';
import { CourtCaseUpdateComponent } from './update/court-case-update.component';
import { CourtCaseDeleteDialogComponent } from './delete/court-case-delete-dialog.component';
import { CourtCaseRoutingModule } from './route/court-case-routing.module';

@NgModule({
  imports: [SharedModule, CourtCaseRoutingModule],
  declarations: [CourtCaseComponent, CourtCaseDetailComponent, CourtCaseUpdateComponent, CourtCaseDeleteDialogComponent],
  entryComponents: [CourtCaseDeleteDialogComponent],
})
export class CourtCaseModule {}
