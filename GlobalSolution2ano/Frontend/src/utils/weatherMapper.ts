
import { WeatherData, WeatherAlert, ForecastDay } from '@/types/weather';
import { OpenWeatherResponse, OpenWeatherOneCallResponse } from '@/services/weatherService';

// Função para capitalizar a primeira letra de cada palavra
const capitalizeWords = (text: string): string => {
  return text.split(' ').map(word => 
    word.charAt(0).toUpperCase() + word.slice(1).toLowerCase()
  ).join(' ');
};

// A API OpenWeatherMap já retorna a descrição em português quando configurada corretamente
// Esta função apenas formata a capitalização para melhor apresentação
const translateCondition = (condition: string, description?: string): string => {
  // Se temos uma descrição, usamos ela formatada
  if (description && description.trim()) {
    return capitalizeWords(description.trim());
  }
  
  // Fallback para traduções básicas se não houver descrição
  const basicTranslations: { [key: string]: string } = {
    'clear': 'Céu Limpo',
    'clouds': 'Nublado',
    'rain': 'Chuva',
    'drizzle': 'Garoa',
    'thunderstorm': 'Tempestade',
    'snow': 'Neve',
    'mist': 'Neblina',
    'smoke': 'Fumaça',
    'haze': 'Névoa',
    'dust': 'Poeira',
    'fog': 'Nevoeiro',
    'sand': 'Areia',
    'ash': 'Cinzas Vulcânicas',
    'squall': 'Rajadas de Vento',
    'tornado': 'Tornado'
  };
  
  const lowerCondition = condition.toLowerCase();
  return basicTranslations[lowerCondition] || capitalizeWords(condition);
};

const getSeverityFromEvent = (event: string): "low" | "medium" | "high" | "extreme" => {
  const eventLower = event.toLowerCase();
  
  if (eventLower.includes('extreme') || eventLower.includes('severe')) {
    return 'extreme';
  }
  if (eventLower.includes('warning') || eventLower.includes('alto')) {
    return 'high';
  }
  if (eventLower.includes('watch') || eventLower.includes('moderado')) {
    return 'medium';
  }
  return 'low';
};

const formatDate = (timestamp: number): string => {
  const date = new Date(timestamp * 1000);
  const today = new Date();
  const tomorrow = new Date(today);
  tomorrow.setDate(tomorrow.getDate() + 1);
  
  if (date.toDateString() === today.toDateString()) {
    return 'Hoje';
  }
  if (date.toDateString() === tomorrow.toDateString()) {
    return 'Amanhã';
  }
  
  return date.toLocaleDateString('pt-BR', { weekday: 'long' });
};

export const mapWeatherData = (
  currentWeather: OpenWeatherResponse,
  detailedWeather?: OpenWeatherOneCallResponse
): WeatherData => {
  // Mapear alertas se disponíveis
  const alerts: WeatherAlert[] = [];
  if (detailedWeather?.alerts) {
    detailedWeather.alerts.forEach(alert => {
      const severity = getSeverityFromEvent(alert.event);
      alerts.push({
        id: `${alert.start}-${alert.end}`,
        title: alert.event,
        description: alert.description,
        severity: severity,
        source: alert.sender_name,
        startTime: new Date(alert.start * 1000).toLocaleString(),
        endTime: new Date(alert.end * 1000).toLocaleString()
      });
    });
  }

  // Mapear previsão para os próximos dias
  const forecast: ForecastDay[] = [];
  if (detailedWeather?.daily) {
    // Pegar apenas os próximos 5 dias
    detailedWeather.daily.slice(0, 5).forEach(day => {
      const date = new Date(day.dt * 1000);
      forecast.push({
        date: date.toLocaleDateString('pt-BR'),
        dayOfWeek: date.toLocaleDateString('pt-BR', { weekday: 'short' }),
        minTemp: Math.round(day.temp.min),
        maxTemp: Math.round(day.temp.max),
        condition: translateCondition(day.weather[0].main, day.weather[0].description),
        precipitationChance: Math.round(day.pop * 100), // Converter para porcentagem
        rainVolume: day.rain
      });
    });
  }

  return {
    city: currentWeather.name,
    temperature: Math.round(currentWeather.main.temp),
    feelsLike: Math.round(currentWeather.main.feels_like),
    condition: translateCondition(currentWeather.weather[0].main, currentWeather.weather[0].description),
    humidity: currentWeather.main.humidity,
    windSpeed: Math.round(currentWeather.wind.speed * 3.6), // Converter m/s para km/h
    uvIndex: detailedWeather?.current?.uvi || 0,
    visibility: detailedWeather?.current?.visibility ? Math.round(detailedWeather.current.visibility / 1000) : 10, // Converter para km
    pressure: detailedWeather?.current?.pressure || currentWeather.main.pressure,
    alerts,
    forecast
  };
};
