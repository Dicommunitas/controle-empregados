import { ITarefa } from 'app/shared/model/tarefa.model';
import { IEmpregado } from 'app/shared/model/empregado.model';

export interface ITrabalho {
  id?: number;
  titulo?: string;
  salarioMinimo?: number;
  salarioMaximo?: number;
  tarefas?: ITarefa[];
  empregado?: IEmpregado;
}

export class Trabalho implements ITrabalho {
  constructor(
    public id?: number,
    public titulo?: string,
    public salarioMinimo?: number,
    public salarioMaximo?: number,
    public tarefas?: ITarefa[],
    public empregado?: IEmpregado
  ) {}
}
