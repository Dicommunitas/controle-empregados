export interface IRegiao {
  id?: number;
  nome?: string;
}

export class Regiao implements IRegiao {
  constructor(public id?: number, public nome?: string) {}
}
