import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { MetaShareSharedLibsModule, MetaShareSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [MetaShareSharedLibsModule, MetaShareSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [MetaShareSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MetaShareSharedModule {
  static forRoot() {
    return {
      ngModule: MetaShareSharedModule
    };
  }
}
