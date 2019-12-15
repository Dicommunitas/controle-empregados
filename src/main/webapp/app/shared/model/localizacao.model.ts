import { IPais } from 'app/shared/model/pais.model';

export interface ILocalizacao {
  id?: number;
  endereco?: string;
  cep?: string;
  cidade?: string;
  bairro?: string;
  pais?: IPais;
}

export class Localizacao implements ILocalizacao {
  constructor(
    public id?: number,
    public endereco?: string,
    public cep?: string,
    public cidade?: string,
    public bairro?: string,
    public pais?: IPais
  ) {}
}
