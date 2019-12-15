import { ITrabalho } from 'app/shared/model/trabalho.model';

export interface ITarefa {
  id?: number;
  titulo?: string;
  descricao?: string;
  trabalhos?: ITrabalho[];
}

export class Tarefa implements ITarefa {
  constructor(public id?: number, public titulo?: string, public descricao?: string, public trabalhos?: ITrabalho[]) {}
}
