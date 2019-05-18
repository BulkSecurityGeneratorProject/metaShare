import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MetaShareSharedModule } from 'app/shared';
import {
  ResumeHeadComponent,
  ResumeHeadDetailComponent,
  ResumeHeadUpdateComponent,
  ResumeHeadDeletePopupComponent,
  ResumeHeadDeleteDialogComponent,
  resumeHeadRoute,
  resumeHeadPopupRoute
} from './';

const ENTITY_STATES = [...resumeHeadRoute, ...resumeHeadPopupRoute];

@NgModule({
  imports: [MetaShareSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ResumeHeadComponent,
    ResumeHeadDetailComponent,
    ResumeHeadUpdateComponent,
    ResumeHeadDeleteDialogComponent,
    ResumeHeadDeletePopupComponent
  ],
  entryComponents: [ResumeHeadComponent, ResumeHeadUpdateComponent, ResumeHeadDeleteDialogComponent, ResumeHeadDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MetaShareResumeHeadModule {}
