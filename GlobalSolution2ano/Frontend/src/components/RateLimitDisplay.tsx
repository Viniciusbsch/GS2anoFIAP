
import { useState, useEffect } from 'react';
import { RateLimitService } from '@/services/rateLimitService';

const RateLimitDisplay = () => {
  const [rateLimitInfo, setRateLimitInfo] = useState({
    current: 0,
    max: 0,
    remaining: 0
  });

  const updateRateLimitInfo = () => {
    setRateLimitInfo({
      current: RateLimitService.getCurrentCount(),
      max: RateLimitService.getMaxCalls(),
      remaining: RateLimitService.getRemainingCalls()
    });
  };

  useEffect(() => {
    updateRateLimitInfo();
    
    // Atualizar a cada 30 segundos
    const interval = setInterval(updateRateLimitInfo, 30000);
    
    return () => clearInterval(interval);
  }, []);

  // Remover este useEffect que está causando o loop infinito
  // useEffect(() => {
  //   updateRateLimitInfo();
  // });

  const getStatusColor = () => {
    const percentage = (rateLimitInfo.current / rateLimitInfo.max) * 100;
    if (percentage >= 90) return 'text-red-600';
    if (percentage >= 70) return 'text-yellow-600';
    return 'text-green-600';
  };

  return (
    <div className="flex items-center space-x-2 text-sm">
      <div className="flex items-center space-x-1">
        <span className="text-gray-600">API:</span>
        <span className={getStatusColor()}>
          {rateLimitInfo.current}/{rateLimitInfo.max}
        </span>
      </div>
      <div className="w-16 h-2 bg-gray-200 rounded-full overflow-hidden">
        <div 
          className={`h-full transition-all duration-300 ${
            rateLimitInfo.current / rateLimitInfo.max >= 0.9 
              ? 'bg-red-500' 
              : rateLimitInfo.current / rateLimitInfo.max >= 0.7 
                ? 'bg-yellow-500' 
                : 'bg-green-500'
          }`}
          style={{ 
            width: `${Math.min((rateLimitInfo.current / rateLimitInfo.max) * 100, 100)}%` 
          }}
        />
      </div>
    </div>
  );
};

export default RateLimitDisplay;
