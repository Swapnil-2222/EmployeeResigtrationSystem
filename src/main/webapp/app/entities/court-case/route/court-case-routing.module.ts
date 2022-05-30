import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CourtCaseComponent } from '../list/court-case.component';
import { CourtCaseDetailComponent } from '../detail/court-case-detail.component';
import { CourtCaseUpdateComponent } from '../update/court-case-update.component';
import { CourtCaseRoutingResolveService } from './court-case-routing-resolve.service';

const courtCaseRoute: Routes = [
  {
    path: '',
    component: CourtCaseComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CourtCaseDetailComponent,
    resolve: {
      courtCase: CourtCaseRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CourtCaseUpdateComponent,
    resolve: {
      courtCase: CourtCaseRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CourtCaseUpdateComponent,
    resolve: {
      courtCase: CourtCaseRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(courtCaseRoute)],
  exports: [RouterModule],
})
export class CourtCaseRoutingModule {}
