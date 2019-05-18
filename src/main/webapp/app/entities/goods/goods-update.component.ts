import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IGoods, Goods } from 'app/shared/model/goods.model';
import { GoodsService } from './goods.service';
import { ITax } from 'app/shared/model/tax.model';
import { TaxService } from 'app/entities/tax';

@Component({
  selector: 'jhi-goods-update',
  templateUrl: './goods-update.component.html'
})
export class GoodsUpdateComponent implements OnInit {
  goods: IGoods;
  isSaving: boolean;

  taxes: ITax[];

  editForm = this.fb.group({
    id: [],
    goodsId: [null, [Validators.required]],
    name: [null, [Validators.minLength(0), Validators.maxLength(255)]],
    provinceCode: [null, [Validators.minLength(0), Validators.maxLength(6)]],
    rate: [],
    status: [],
    stack: [null, [Validators.required]],
    price: [null, [Validators.required]],
    tax: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected goodsService: GoodsService,
    protected taxService: TaxService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ goods }) => {
      this.updateForm(goods);
      this.goods = goods;
    });
    this.taxService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITax[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITax[]>) => response.body)
      )
      .subscribe((res: ITax[]) => (this.taxes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(goods: IGoods) {
    this.editForm.patchValue({
      id: goods.id,
      goodsId: goods.goodsId,
      name: goods.name,
      provinceCode: goods.provinceCode,
      rate: goods.rate,
      status: goods.status,
      stack: goods.stack,
      price: goods.price,
      tax: goods.tax
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const goods = this.createFromForm();
    if (goods.id !== undefined) {
      this.subscribeToSaveResponse(this.goodsService.update(goods));
    } else {
      this.subscribeToSaveResponse(this.goodsService.create(goods));
    }
  }

  private createFromForm(): IGoods {
    const entity = {
      ...new Goods(),
      id: this.editForm.get(['id']).value,
      goodsId: this.editForm.get(['goodsId']).value,
      name: this.editForm.get(['name']).value,
      provinceCode: this.editForm.get(['provinceCode']).value,
      rate: this.editForm.get(['rate']).value,
      status: this.editForm.get(['status']).value,
      stack: this.editForm.get(['stack']).value,
      price: this.editForm.get(['price']).value,
      tax: this.editForm.get(['tax']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGoods>>) {
    result.subscribe((res: HttpResponse<IGoods>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackTaxById(index: number, item: ITax) {
    return item.id;
  }
}
