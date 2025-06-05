import { useState, useEffect } from 'react';
import { toast } from '@/hooks/use-toast';
import SearchBar from '@/components/SearchBar';
import CurrentWeather from '@/components/CurrentWeather';
import WeatherAlerts from '@/components/WeatherAlerts';
import ForecastCards from '@/components/ForecastCards';
import RateLimitDisplay from '@/components/RateLimitDisplay';
import WeatherDetails from '@/components/WeatherDetails';
import LoginButton from '@/components/LoginButton';
import RegisterButton from '@/components/RegisterButton';
import { WeatherData } from '@/types/weather';
import {
  getCurrentWeather,
  getDetailedWeather,
} from '@/services/weatherService';
import { mapWeatherData } from '@/utils/weatherMapper';
import { checkEmail } from '@/services/userService';

const Index = () => {
  const [weatherData, setWeatherData] = useState<WeatherData | null>(null);
  const [loading, setLoading] = useState(false);
  const [userEmail, setUserEmail] = useState<string | null>(null);
  const [userArea, setUserArea] = useState<string | null>(null);

  useEffect(() => {
    // Get user email from localStorage or your auth system
    const email = localStorage.getItem('userEmail');
    if (email) {
      setUserEmail(email);
      fetchUserWeather(email);
    }
  }, []);

  const fetchUserWeather = async (email: string) => {
    setLoading(true);
    try {
      // First, get user data with their area
      const userData = await checkEmail(email);
      console.log('Dados do usuário:', userData);
      if (!userData.areaRisco) {
        throw new Error('Usuário não possui área de risco associada');
      }

      setUserArea(userData.areaRisco.nome);

      // Then fetch weather data using the area name
      const currentWeather = await getCurrentWeather(userData.areaRisco.nome);
      console.log('Dados atuais recebidos:', currentWeather);

      let detailedWeather;
      try {
        detailedWeather = await getDetailedWeather(
          currentWeather.coord.lat,
          currentWeather.coord.lon
        );
        console.log('Dados detalhados recebidos:', detailedWeather);
      } catch (detailedError) {
        console.log(
          'Erro ao buscar dados detalhados (usando apenas dados básicos):',
          detailedError
        );
      }

      const mappedData = mapWeatherData(currentWeather, detailedWeather);
      console.log('Dados mapeados:', mappedData);

      setWeatherData(mappedData);
      setLoading(false);

      toast({
        title: 'Dados atualizados',
        description: `Informações meteorológicas de ${currentWeather.name} carregadas com sucesso.`,
      });
    } catch (error) {
      console.error('Erro ao buscar dados meteorológicos:', error);
      setLoading(false);

      let errorMessage = 'Não foi possível carregar os dados meteorológicos.';
      if (error instanceof Error) {
        if (error.message.includes('Limite diário')) {
          errorMessage = error.message;
        } else if (error.message.includes('404')) {
          errorMessage =
            'Cidade não encontrada. Verifique o nome e tente novamente.';
        } else if (
          error.message.includes('401') ||
          error.message.includes('Chave da API')
        ) {
          errorMessage = 'Erro de autenticação com a API.';
        } else if (error.message.includes('não possui área de risco')) {
          errorMessage = error.message;
        }
      }

      toast({
        title: 'Erro',
        description: errorMessage,
        variant: 'destructive',
      });
    }
  };

  const handleCitySearch = async (cityName: string) => {
    setLoading(true);
    console.log('Buscando dados da API para:', cityName);

    try {
      const currentWeather = await getCurrentWeather(cityName);
      console.log('Dados atuais recebidos:', currentWeather);

      let detailedWeather;
      try {
        detailedWeather = await getDetailedWeather(
          currentWeather.coord.lat,
          currentWeather.coord.lon
        );
        console.log('Dados detalhados recebidos:', detailedWeather);
      } catch (detailedError) {
        console.log(
          'Erro ao buscar dados detalhados (usando apenas dados básicos):',
          detailedError
        );
      }

      const mappedData = mapWeatherData(currentWeather, detailedWeather);
      console.log('Dados mapeados:', mappedData);

      setWeatherData(mappedData);
      setLoading(false);

      toast({
        title: 'Dados atualizados',
        description: `Informações meteorológicas de ${currentWeather.name} carregadas com sucesso.`,
      });
    } catch (error) {
      console.error('Erro ao buscar dados meteorológicos:', error);
      setLoading(false);

      let errorMessage = 'Não foi possível carregar os dados meteorológicos.';
      if (error instanceof Error) {
        if (error.message.includes('Limite diário')) {
          errorMessage = error.message;
        } else if (error.message.includes('404')) {
          errorMessage =
            'Cidade não encontrada. Verifique o nome e tente novamente.';
        } else if (
          error.message.includes('401') ||
          error.message.includes('Chave da API')
        ) {
          errorMessage = 'Erro de autenticação com a API.';
        }
      }

      toast({
        title: 'Erro',
        description: errorMessage,
        variant: 'destructive',
      });
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-b from-blue-50 to-white">
      <header className="bg-white shadow-sm">
        <div className="container mx-auto px-4 py-4 flex justify-between items-center">
          <div className="flex items-center gap-4">
            <h1 className="text-2xl font-bold text-gray-800">
              Alerta Climático Brasil
            </h1>
            {userArea && (
              <span className="text-sm text-gray-600">
                Você está em {userArea}
              </span>
            )}
          </div>
          <div className="flex items-center space-x-4">
            <RateLimitDisplay />
            <LoginButton />
            {!userEmail && <RegisterButton />}
          </div>
        </div>
      </header>

      <main className="container mx-auto px-4 py-8">
        {/* Search Section - Only show when not logged in */}
        {!userEmail && (
          <div className="mb-8">
            <SearchBar onSearch={handleCitySearch} loading={loading} />
          </div>
        )}

        {/* Weather Data */}
        {weatherData && (
          <div className="space-y-6">
            {/* Top Row - Current Weather and Forecast with equal heights */}
            <div className="grid gap-6 lg:grid-cols-3">
              {/* Current Weather - Takes 2 columns */}
              <div className="lg:col-span-2 flex">
                <CurrentWeather data={weatherData} />
              </div>

              {/* Forecast Cards */}
              <div className="flex">
                <ForecastCards forecast={weatherData.forecast} />
              </div>
            </div>

            {/* Weather Details - Full width */}
            <div>
              <WeatherDetails data={weatherData} />
            </div>

            {/* Alerts - Full width */}
            {weatherData.alerts.length > 0 && (
              <div>
                <WeatherAlerts alerts={weatherData.alerts} />
              </div>
            )}
          </div>
        )}

        {/* Welcome Message */}
        {!weatherData && !loading && (
          <div className="text-center py-16">
            <div className="w-24 h-24 bg-gradient-to-br from-blue-400 to-sky-500 rounded-full flex items-center justify-center mx-auto mb-6">
              <span className="text-white text-4xl">🌤️</span>
            </div>
            <h2 className="text-3xl font-bold text-gray-800 mb-4">
              Bem-vindo ao Alerta Climático Brasil
            </h2>
            <p className="text-lg text-gray-600 max-w-2xl mx-auto">
              {userEmail
                ? 'Carregando informações da sua área de risco...'
                : 'Faça login para ver as condições meteorológicas da sua área de risco ou digite o nome de uma cidade brasileira acima.'}
            </p>
          </div>
        )}
      </main>
    </div>
  );
};

export default Index;
