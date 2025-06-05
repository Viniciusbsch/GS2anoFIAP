import { RateLimitService } from './rateLimitService';

const API_KEY = '42a7c4d709869780252c869757625319';
const BASE_URL = 'https://api.openweathermap.org/data/2.5';

export interface OpenWeatherResponse {
  name: string;
  main: {
    temp: number;
    feels_like: number;
    humidity: number;
  };
  weather: Array<{
    main: string;
    description: string;
  }>;
  wind: {
    speed: number;
  };
  coord: {
    lat: number;
    lon: number;
  };
}

export interface OpenWeatherOneCallResponse {
  current: {
    temp: number;
    feels_like: number;
    humidity: number;
    wind_speed: number;
    uvi: number; // Índice UV
    visibility: number; // Visibilidade em metros
    pressure: number; // Pressão atmosférica
    weather: Array<{
      main: string;
      description: string;
      icon: string;
    }>;
  };
  daily: Array<{
    dt: number;
    temp: {
      min: number;
      max: number;
      day: number;
    };
    weather: Array<{
      main: string;
      description: string;
      icon: string;
    }>;
    pop: number; // Probabilidade de precipitação
    rain?: number; // Volume de chuva em mm
  }>;
  alerts?: Array<{
    sender_name: string;
    event: string;
    start: number;
    end: number;
    description: string;
  }>;
}

export const getCurrentWeather = async (
  cityName: string
): Promise<OpenWeatherResponse> => {
  // Verificar se ainda podemos fazer chamadas
  if (!RateLimitService.canMakeRequest()) {
    throw new Error(
      `Limite diário de chamadas excedido. Restam ${RateLimitService.getRemainingCalls()} chamadas hoje.`
    );
  }

  console.log(
    `Fazendo chamada à API. Restam ${RateLimitService.getRemainingCalls()} chamadas hoje.`
  );

  const response = await fetch(
    `${BASE_URL}/weather?q=${encodeURIComponent(
      cityName
    )},BR&appid=${API_KEY}&units=metric&lang=pt_br`
  );

  // Incrementar contador apenas se a requisição foi bem-sucedida
  if (response.ok) {
    RateLimitService.incrementCounter();
    console.log(
      `Chamada realizada com sucesso. ${RateLimitService.getCurrentCount()}/${RateLimitService.getMaxCalls()} chamadas utilizadas hoje.`
    );
  }

  if (!response.ok) {
    if (response.status === 401) {
      throw new Error('Chave da API inválida. Verifique sua configuração.');
    }
    throw new Error(`Erro ao buscar dados: ${response.status}`);
  }

  return response.json();
};

export const getDetailedWeather = async (
  lat: number,
  lon: number
): Promise<OpenWeatherOneCallResponse> => {
  // Verificar se ainda podemos fazer chamadas
  if (!RateLimitService.canMakeRequest()) {
    throw new Error(
      `Limite diário de chamadas excedido. Restam ${RateLimitService.getRemainingCalls()} chamadas hoje.`
    );
  }

  console.log(
    `Fazendo chamada detalhada à API. Restam ${RateLimitService.getRemainingCalls()} chamadas hoje.`
  );

  const response = await fetch(
    `https://api.openweathermap.org/data/3.0/onecall?lat=${lat}&lon=${lon}&appid=${API_KEY}&units=metric&lang=pt_br&exclude=minutely,hourly`
  );

  // Incrementar contador apenas se a requisição foi bem-sucedida
  if (response.ok) {
    RateLimitService.incrementCounter();
    console.log(
      `Chamada detalhada realizada com sucesso. ${RateLimitService.getCurrentCount()}/${RateLimitService.getMaxCalls()} chamadas utilizadas hoje.`
    );
  }

  if (!response.ok) {
    if (response.status === 401) {
      throw new Error('Chave da API inválida. Verifique sua configuração.');
    }
    throw new Error(`Erro ao buscar dados detalhados: ${response.status}`);
  }

  return response.json();
};
