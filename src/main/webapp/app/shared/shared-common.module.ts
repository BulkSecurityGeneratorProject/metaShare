import { NgModule } from '@angular/core';

import { MetaShareSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [MetaShareSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [MetaShareSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class MetaShareSharedCommonModule {}
