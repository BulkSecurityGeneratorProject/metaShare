export interface IResumeHead {
  id?: number;
  name?: string;
  mobile?: string;
  email?: string;
  age?: number;
  photo?: string;
  residence?: string;
}

export class ResumeHead implements IResumeHead {
  constructor(
    public id?: number,
    public name?: string,
    public mobile?: string,
    public email?: string,
    public age?: number,
    public photo?: string,
    public residence?: string
  ) {}
}
