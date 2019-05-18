import { Moment } from 'moment';
import { IGoods } from 'app/shared/model/goods.model';

export interface IOrder {
  id?: number;
  orderCode?: string;
  goodsName?: string;
  quantity?: number;
  subtotalAmount?: number;
  price?: number;
  taxTotalAmount?: number;
  taxTate?: number;
  sumAmount?: number;
  createBy?: number;
  createTime?: Moment;
  updateby?: number;
  updateTime?: Moment;
  version?: number;
  status?: number;
  goods?: IGoods;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public orderCode?: string,
    public goodsName?: string,
    public quantity?: number,
    public subtotalAmount?: number,
    public price?: number,
    public taxTotalAmount?: number,
    public taxTate?: number,
    public sumAmount?: number,
    public createBy?: number,
    public createTime?: Moment,
    public updateby?: number,
    public updateTime?: Moment,
    public version?: number,
    public status?: number,
    public goods?: IGoods
  ) {}
}
