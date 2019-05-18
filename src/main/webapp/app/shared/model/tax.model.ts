export interface ITax {
  id?: number;
  provinceCode?: string;
  name?: number;
  rate?: number;
}

export class Tax implements ITax {
  constructor(public id?: number, public provinceCode?: string, public name?: number, public rate?: number) {}
}
