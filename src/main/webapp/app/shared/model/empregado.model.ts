import { Moment } from 'moment';
import { ITrabalho } from 'app/shared/model/trabalho.model';
import { IEmpregado } from 'app/shared/model/empregado.model';
import { IDepartamento } from 'app/shared/model/departamento.model';

export interface IEmpregado {
  id?: number;
  primeiroNome?: string;
  sobrenome?: string;
  email?: string;
  telefone?: string;
  dataContratacao?: Moment;
  salario?: number;
  comissao?: number;
  trabalhos?: ITrabalho[];
  gerente?: IEmpregado;
  departamento?: IDepartamento;
}

export class Empregado implements IEmpregado {
  constructor(
    public id?: number,
    public primeiroNome?: string,
    public sobrenome?: string,
    public email?: string,
    public telefone?: string,
    public dataContratacao?: Moment,
    public salario?: number,
    public comissao?: number,
    public trabalhos?: ITrabalho[],
    public gerente?: IEmpregado,
    public departamento?: IDepartamento
  ) {}
}
