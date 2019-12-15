import { Moment } from 'moment';
import { ITrabalho } from 'app/shared/model/trabalho.model';
import { IDepartamento } from 'app/shared/model/departamento.model';
import { IEmpregado } from 'app/shared/model/empregado.model';
import { Lingua } from 'app/shared/model/enumerations/lingua.model';

export interface IHistoricoDeTrabalho {
  id?: number;
  dataInicial?: Moment;
  dataFinal?: Moment;
  lingua?: Lingua;
  trabalho?: ITrabalho;
  departamento?: IDepartamento;
  empregado?: IEmpregado;
}

export class HistoricoDeTrabalho implements IHistoricoDeTrabalho {
  constructor(
    public id?: number,
    public dataInicial?: Moment,
    public dataFinal?: Moment,
    public lingua?: Lingua,
    public trabalho?: ITrabalho,
    public departamento?: IDepartamento,
    public empregado?: IEmpregado
  ) {}
}
