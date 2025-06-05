
import { Card } from "@/components/ui/card";
import { WeatherData } from "@/types/weather";
import { Thermometer, CloudRain, Cloud } from "lucide-react";

interface CurrentWeatherProps {
  data: WeatherData;
}

const CurrentWeather = ({ data }: CurrentWeatherProps) => {
  const getWeatherIcon = (condition: string) => {
    switch (condition.toLowerCase()) {
      case "ensolarado":
        return "☀️";
      case "nublado":
        return "☁️";
      case "chuva leve":
        return "🌦️";
      case "parcialmente nublado":
        return "⛅";
      default:
        return "🌤️";
    }
  };

  const getTemperatureColor = (temp: number) => {
    if (temp >= 35) return "text-red-600";
    if (temp >= 28) return "text-orange-500";
    if (temp >= 20) return "text-green-600";
    return "text-blue-600";
  };

  return (
    <Card className="p-6 bg-white/90 backdrop-blur-sm border-blue-200 shadow-lg w-full h-full flex flex-col">
      <div className="flex items-center justify-between mb-6">
        <div>
          <h2 className="text-2xl font-bold text-gray-800">{data.city}</h2>
          <p className="text-gray-600">Condições atuais</p>
        </div>
        <div className="text-6xl">
          {getWeatherIcon(data.condition)}
        </div>
      </div>

      <div className="grid grid-cols-2 lg:grid-cols-4 gap-6 flex-grow">
        {/* Temperature */}
        <div className="text-center flex flex-col justify-center">
          <div className={`text-4xl font-bold ${getTemperatureColor(data.temperature)} mb-2`}>
            {data.temperature}°C
          </div>
          <p className="text-sm text-gray-600">Temperatura</p>
        </div>

        {/* Feels Like */}
        <div className="text-center flex flex-col justify-center">
          <div className="flex items-center justify-center mb-2">
            <Thermometer className="text-orange-500 mr-2" size={20} />
            <span className="text-2xl font-semibold text-gray-700">{data.feelsLike}°C</span>
          </div>
          <p className="text-sm text-gray-600">Sensação térmica</p>
        </div>

        {/* Humidity */}
        <div className="text-center flex flex-col justify-center">
          <div className="flex items-center justify-center mb-2">
            <CloudRain className="text-blue-500 mr-2" size={20} />
            <span className="text-2xl font-semibold text-gray-700">{data.humidity}%</span>
          </div>
          <p className="text-sm text-gray-600">Umidade</p>
        </div>

        {/* Wind Speed */}
        <div className="text-center flex flex-col justify-center">
          <div className="flex items-center justify-center mb-2">
            <Cloud className="text-gray-500 mr-2" size={20} />
            <span className="text-2xl font-semibold text-gray-700">{data.windSpeed} km/h</span>
          </div>
          <p className="text-sm text-gray-600">Vento</p>
        </div>
      </div>

      <div className="mt-6 pt-6 border-t border-gray-200">
        <div className="flex items-center justify-center">
          <div className="bg-gradient-to-r from-blue-50 to-sky-50 px-4 py-2 rounded-full">
            <span className="text-lg font-medium text-gray-700">{data.condition}</span>
          </div>
        </div>
      </div>
    </Card>
  );
};

export default CurrentWeather;
