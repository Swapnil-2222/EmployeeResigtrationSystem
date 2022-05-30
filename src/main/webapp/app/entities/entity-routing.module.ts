import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'court-case',
        data: { pageTitle: 'employeeResigtrationSystemApp.courtCase.home.title' },
        loadChildren: () => import('./court-case/court-case.module').then(m => m.CourtCaseModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
