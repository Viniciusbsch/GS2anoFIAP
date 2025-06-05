
import { Card } from "@/components/ui/card";
import { ForecastDay } from "@/types/weather";
import { Cloud, CloudRain, Droplets, Sun } from "lucide-react";

interface ForecastCardsProps {
  forecast: ForecastDay[];
}

const ForecastCards = ({ forecast }: ForecastCardsProps) => {
  const getWeatherIcon = (condition: string) => {
    switch (condition.toLowerCase()) {
      case 'chuva':
      case 'chuvisco':
        return <CloudRain className="text-blue-500" size={24} />;
      case 'nublado':
      case 'nuvens':
        return <Cloud className="text-gray-500" size={24} />;
      default:
        return <Sun className="text-yellow-500" size={24} />;
    }
  };

  return (
    <Card className="p-6 bg-white/90 backdrop-blur-sm border-blue-200 shadow-lg w-full h-full flex flex-col">
      <h3 className="text-xl font-bold text-gray-800 mb-4">Previsão para 5 dias</h3>

      <div className="space-y-3 flex-grow flex flex-col justify-between">
        {forecast.map((day, index) => (
          <div key={index} className="p-3 bg-blue-50 rounded-lg flex-1 flex items-center">
            <div className="flex justify-between items-center w-full">
              <div className="flex items-center">
                {getWeatherIcon(day.condition)}
                <div className="ml-3">
                  <p className="font-semibold">{day.dayOfWeek}</p>
                  <p className="text-sm text-gray-600">{day.date}</p>
                </div>
              </div>

              <div className="text-right">
                <p className="font-bold">{day.maxTemp}° <span className="text-gray-500 font-normal">{day.minTemp}°</span></p>

                {day.precipitationChance > 0 && (
                  <div className="flex items-center justify-end mt-1">
                    <Droplets className="text-blue-500 mr-1" size={14} />
                    <span className={`text-xs ${day.precipitationChance > 50 ? 'text-blue-600 font-semibold' : 'text-gray-600'}`}>
                      {day.precipitationChance}%
                    </span>
                    {day.rainVolume && (
                      <span className="text-xs text-blue-600 ml-1">
                        ({day.rainVolume}mm)
                      </span>
                    )}
                  </div>
                )}
              </div>
            </div>
          </div>
        ))}
      </div>
    </Card>
  );
};

export default ForecastCards;
