import { ITax } from 'app/shared/model/tax.model';

export interface IGoods {
  id?: number;
  goodsId?: number;
  name?: string;
  provinceCode?: string;
  rate?: number;
  status?: number;
  stack?: number;
  price?: number;
  tax?: ITax;
}

export class Goods implements IGoods {
  constructor(
    public id?: number,
    public goodsId?: number,
    public name?: string,
    public provinceCode?: string,
    public rate?: number,
    public status?: number,
    public stack?: number,
    public price?: number,
    public tax?: ITax
  ) {}
}
