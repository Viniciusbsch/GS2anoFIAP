
export interface WeatherAlert {
  id?: string;
  title: string;
  description: string;
  severity?: "low" | "medium" | "high" | "extreme";
  recommendations?: string[];
  startTime?: string;
  endTime?: string;
  source?: string;
}

export interface ForecastDay {
  date: string;
  dayOfWeek: string;
  minTemp: number;
  maxTemp: number;
  condition: string;
  precipitationChance: number;  // Probabilidade de chuva (0-100%)
  rainVolume?: number;          // Volume de chuva em mm
}

export interface WeatherData {
  city: string;
  temperature: number;
  feelsLike: number;
  condition: string;
  humidity: number;
  windSpeed: number;
  uvIndex: number;
  visibility: number;      // Visibilidade em km
  pressure: number;        // Pressão atmosférica em hPa
  alerts: WeatherAlert[];
  forecast: ForecastDay[];
}
