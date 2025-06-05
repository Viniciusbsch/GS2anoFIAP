export interface AreaRiscoDTO {
  id: number;
  nome: string;
  nivelRisco: number;
}

export interface UsuarioDTO {
  id: number;
  nome: string;
  email: string;
  telefone: string;
  latitude: number;
  longitude: number;
  notifEmail: boolean;
  notifSms: boolean;
  areaRisco?: AreaRiscoDTO;
}
