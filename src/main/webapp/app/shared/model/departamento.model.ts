import { ILocalizacao } from 'app/shared/model/localizacao.model';
import { IEmpregado } from 'app/shared/model/empregado.model';

export interface IDepartamento {
  id?: number;
  nome?: string;
  localizacao?: ILocalizacao;
  empregados?: IEmpregado[];
}

export class Departamento implements IDepartamento {
  constructor(public id?: number, public nome?: string, public localizacao?: ILocalizacao, public empregados?: IEmpregado[]) {}
}
