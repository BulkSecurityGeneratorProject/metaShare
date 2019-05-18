import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'resume-head',
        loadChildren: './resume-head/resume-head.module#MetaShareResumeHeadModule'
      },
      {
        path: 'resume-head',
        loadChildren: './resume-head/resume-head.module#MetaShareResumeHeadModule'
      },
      {
        path: 'tax',
        loadChildren: './tax/tax.module#MetaShareTaxModule'
      },
      {
        path: 'goods',
        loadChildren: './goods/goods.module#MetaShareGoodsModule'
      },
      {
        path: 'order',
        loadChildren: './order/order.module#MetaShareOrderModule'
      },
      {
        path: 'resume-head',
        loadChildren: './resume-head/resume-head.module#MetaShareResumeHeadModule'
      },
      {
        path: 'tax',
        loadChildren: './tax/tax.module#MetaShareTaxModule'
      },
      {
        path: 'goods',
        loadChildren: './goods/goods.module#MetaShareGoodsModule'
      },
      {
        path: 'order',
        loadChildren: './order/order.module#MetaShareOrderModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MetaShareEntityModule {}
