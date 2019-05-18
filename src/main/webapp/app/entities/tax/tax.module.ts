import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MetaShareSharedModule } from 'app/shared';
import {
  TaxComponent,
  TaxDetailComponent,
  TaxUpdateComponent,
  TaxDeletePopupComponent,
  TaxDeleteDialogComponent,
  taxRoute,
  taxPopupRoute
} from './';

const ENTITY_STATES = [...taxRoute, ...taxPopupRoute];

@NgModule({
  imports: [MetaShareSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [TaxComponent, TaxDetailComponent, TaxUpdateComponent, TaxDeleteDialogComponent, TaxDeletePopupComponent],
  entryComponents: [TaxComponent, TaxUpdateComponent, TaxDeleteDialogComponent, TaxDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MetaShareTaxModule {}
