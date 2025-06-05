export interface AreaRisco {
  id: number;
  nome: string;
  nivelRisco: number;
  historicoAlteracoes?: string;
  latitude?: number;
  longitude?: number;
  dataCriacao: string;
  dataAtualizacao: string;
}
