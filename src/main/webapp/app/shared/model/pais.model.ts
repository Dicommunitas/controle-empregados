import { IRegiao } from 'app/shared/model/regiao.model';

export interface IPais {
  id?: number;
  nome?: string;
  regiao?: IRegiao;
}

export class Pais implements IPais {
  constructor(public id?: number, public nome?: string, public regiao?: IRegiao) {}
}
