import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IOrder, Order } from 'app/shared/model/order.model';
import { OrderService } from './order.service';
import { IGoods } from 'app/shared/model/goods.model';
import { GoodsService } from 'app/entities/goods';

@Component({
  selector: 'jhi-order-update',
  templateUrl: './order-update.component.html'
})
export class OrderUpdateComponent implements OnInit {
  order: IOrder;
  isSaving: boolean;

  goods: IGoods[];

  editForm = this.fb.group({
    id: [],
    orderCode: [],
    goodsName: [null, [Validators.required]],
    quantity: [null, [Validators.required]],
    subtotalAmount: [null, [Validators.required]],
    price: [null, [Validators.required]],
    taxTotalAmount: [null, [Validators.required]],
    taxTate: [null, [Validators.required]],
    sumAmount: [null, [Validators.required]],
    createBy: [],
    createTime: [],
    updateby: [],
    updateTime: [],
    version: [],
    status: [],
    goods: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected orderService: OrderService,
    protected goodsService: GoodsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ order }) => {
      this.updateForm(order);
      this.order = order;
    });
    this.goodsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IGoods[]>) => mayBeOk.ok),
        map((response: HttpResponse<IGoods[]>) => response.body)
      )
      .subscribe((res: IGoods[]) => (this.goods = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(order: IOrder) {
    this.editForm.patchValue({
      id: order.id,
      orderCode: order.orderCode,
      goodsName: order.goodsName,
      quantity: order.quantity,
      subtotalAmount: order.subtotalAmount,
      price: order.price,
      taxTotalAmount: order.taxTotalAmount,
      taxTate: order.taxTate,
      sumAmount: order.sumAmount,
      createBy: order.createBy,
      createTime: order.createTime != null ? order.createTime.format(DATE_TIME_FORMAT) : null,
      updateby: order.updateby,
      updateTime: order.updateTime != null ? order.updateTime.format(DATE_TIME_FORMAT) : null,
      version: order.version,
      status: order.status,
      goods: order.goods
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const order = this.createFromForm();
    if (order.id !== undefined) {
      this.subscribeToSaveResponse(this.orderService.update(order));
    } else {
      this.subscribeToSaveResponse(this.orderService.create(order));
    }
  }

  private createFromForm(): IOrder {
    const entity = {
      ...new Order(),
      id: this.editForm.get(['id']).value,
      orderCode: this.editForm.get(['orderCode']).value,
      goodsName: this.editForm.get(['goodsName']).value,
      quantity: this.editForm.get(['quantity']).value,
      subtotalAmount: this.editForm.get(['subtotalAmount']).value,
      price: this.editForm.get(['price']).value,
      taxTotalAmount: this.editForm.get(['taxTotalAmount']).value,
      taxTate: this.editForm.get(['taxTate']).value,
      sumAmount: this.editForm.get(['sumAmount']).value,
      createBy: this.editForm.get(['createBy']).value,
      createTime:
        this.editForm.get(['createTime']).value != null ? moment(this.editForm.get(['createTime']).value, DATE_TIME_FORMAT) : undefined,
      updateby: this.editForm.get(['updateby']).value,
      updateTime:
        this.editForm.get(['updateTime']).value != null ? moment(this.editForm.get(['updateTime']).value, DATE_TIME_FORMAT) : undefined,
      version: this.editForm.get(['version']).value,
      status: this.editForm.get(['status']).value,
      goods: this.editForm.get(['goods']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrder>>) {
    result.subscribe((res: HttpResponse<IOrder>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackGoodsById(index: number, item: IGoods) {
    return item.id;
  }
}
