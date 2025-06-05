
import { Card } from "@/components/ui/card";
import { WeatherAlert } from "@/types/weather";
import { AlertTriangle, Bell } from "lucide-react";

interface WeatherAlertsProps {
  alerts: WeatherAlert[];
}

const WeatherAlerts = ({ alerts }: WeatherAlertsProps) => {
  const getSeverityColor = (severity: string) => {
    switch (severity) {
      case "extreme":
        return "bg-red-50 border-red-300 text-red-800";
      case "high":
        return "bg-orange-50 border-orange-300 text-orange-800";
      case "medium":
        return "bg-yellow-50 border-yellow-300 text-yellow-800";
      default:
        return "bg-blue-50 border-blue-300 text-blue-800";
    }
  };

  const getSeverityIcon = (severity: string) => {
    switch (severity) {
      case "extreme":
      case "high":
        return <AlertTriangle className="text-red-500" size={24} />;
      default:
        return <Bell className="text-orange-500" size={24} />;
    }
  };

  if (alerts.length === 0) return null;

  return (
    <div className="space-y-4">
      <h3 className="text-xl font-bold text-gray-800 flex items-center">
        <AlertTriangle className="text-red-500 mr-2" size={24} />
        Alertas Meteorológicos
      </h3>
      
      {alerts.map((alert) => (
        <Card 
          key={alert.id || alert.title} 
          className={`p-6 ${getSeverityColor(alert.severity || 'low')} border-l-4 shadow-lg animate-pulse`}
        >
          <div className="flex items-start space-x-4">
            <div className="flex-shrink-0">
              {getSeverityIcon(alert.severity || 'low')}
            </div>
            
            <div className="flex-1">
              <h4 className="text-lg font-bold mb-2">{alert.title}</h4>
              <p className="mb-4">{alert.description}</p>
              
              {alert.recommendations && alert.recommendations.length > 0 && (
                <div className="bg-white/70 rounded-lg p-4">
                  <h5 className="font-semibold mb-2">Recomendações:</h5>
                  <ul className="space-y-1">
                    {alert.recommendations.map((rec, index) => (
                      <li key={index} className="flex items-start">
                        <span className="text-green-600 mr-2">•</span>
                        <span className="text-sm">{rec}</span>
                      </li>
                    ))}
                  </ul>
                </div>
              )}
              
              {alert.startTime && alert.endTime && (
                <div className="mt-3 text-sm text-gray-600">
                  <p>Válido de: {alert.startTime}</p>
                  <p>Até: {alert.endTime}</p>
                </div>
              )}
              
              {alert.source && (
                <div className="mt-2 text-xs text-gray-500">
                  <p>Fonte: {alert.source}</p>
                </div>
              )}
            </div>
          </div>
        </Card>
      ))}
    </div>
  );
};

export default WeatherAlerts;
