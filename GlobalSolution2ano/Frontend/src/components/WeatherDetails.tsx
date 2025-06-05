import { Card } from "@/components/ui/card";
import { WeatherData } from "@/types/weather";
import { Droplets, Eye, Gauge, Sun, Wind } from "lucide-react";

interface WeatherDetailsProps {
  data: WeatherData;
}

const WeatherDetails = ({ data }: WeatherDetailsProps) => {
  // Função para determinar o nível de risco do índice UV
  const getUVRiskLevel = (uvIndex: number) => {
    if (uvIndex >= 11) return { text: "Extremo", color: "text-purple-700" };
    if (uvIndex >= 8) return { text: "Muito Alto", color: "text-red-600" };
    if (uvIndex >= 6) return { text: "Alto", color: "text-orange-500" };
    if (uvIndex >= 3) return { text: "Moderado", color: "text-yellow-500" };
    return { text: "Baixo", color: "text-green-500" };
  };

  const uvRisk = getUVRiskLevel(data.uvIndex);

  return (
    <Card className="p-6 bg-white/90 backdrop-blur-sm border-blue-200 shadow-lg">
      <h3 className="text-xl font-bold text-gray-800 mb-4">Detalhes Meteorológicos</h3>
      
      <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
        {/* Umidade */}
        <div className="p-3 bg-blue-50 rounded-lg">
          <div className="flex items-center mb-2">
            <Droplets className="text-blue-500 mr-2" size={18} />
            <h4 className="text-sm font-semibold">Umidade</h4>
          </div>
          <p className="text-xl font-bold">{data.humidity}%</p>
          {data.humidity > 80 && (
            <p className="text-xs text-gray-600 mt-1">Umidade elevada</p>
          )}
        </div>
        
        {/* Vento */}
        <div className="p-3 bg-blue-50 rounded-lg">
          <div className="flex items-center mb-2">
            <Wind className="text-blue-500 mr-2" size={18} />
            <h4 className="text-sm font-semibold">Vento</h4>
          </div>
          <p className="text-xl font-bold">{data.windSpeed} km/h</p>
          {data.windSpeed > 50 && (
            <p className="text-xs text-red-600 mt-1">Vento forte</p>
          )}
        </div>
        
        {/* Índice UV */}
        <div className="p-3 bg-blue-50 rounded-lg">
          <div className="flex items-center mb-2">
            <Sun className="text-yellow-500 mr-2" size={18} />
            <h4 className="text-sm font-semibold">Índice UV</h4>
          </div>
          <p className={`text-xl font-bold ${uvRisk.color}`}>{data.uvIndex}</p>
          <p className={`text-xs ${uvRisk.color} mt-1`}>{uvRisk.text}</p>
        </div>
        
        {/* Visibilidade */}
        <div className="p-3 bg-blue-50 rounded-lg">
          <div className="flex items-center mb-2">
            <Eye className="text-blue-500 mr-2" size={18} />
            <h4 className="text-sm font-semibold">Visibilidade</h4>
          </div>
          <p className="text-xl font-bold">{data.visibility} km</p>
          {data.visibility < 5 && (
            <p className="text-xs text-amber-600 mt-1">Visibilidade reduzida</p>
          )}
        </div>
        
        {/* Pressão */}
        <div className="p-3 bg-blue-50 rounded-lg md:col-span-2">
          <div className="flex items-center mb-2">
            <Gauge className="text-blue-500 mr-2" size={18} />
            <h4 className="text-sm font-semibold">Pressão Atmosférica</h4>
          </div>
          <p className="text-xl font-bold">{data.pressure} hPa</p>
          {data.pressure < 1000 && (
            <p className="text-xs text-amber-600 mt-1">Pressão baixa - possibilidade de tempestades</p>
          )}
        </div>
        
        {/* Sensação Térmica */}
        <div className="p-3 bg-blue-50 rounded-lg md:col-span-2">
          <div className="flex items-center mb-2">
            <Sun className="text-orange-500 mr-2" size={18} />
            <h4 className="text-sm font-semibold">Sensação Térmica</h4>
          </div>
          <p className="text-xl font-bold">{data.feelsLike}°C</p>
          {Math.abs(data.temperature - data.feelsLike) > 3 && (
            <p className="text-xs text-gray-600 mt-1">
              {data.feelsLike > data.temperature 
                ? "Sensação de mais calor devido à umidade" 
                : "Sensação de mais frio devido ao vento"}
            </p>
          )}
        </div>
      </div>
    </Card>
  );
};

export default WeatherDetails;