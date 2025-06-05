import { useState, useEffect, useRef } from 'react';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { CloudSun } from 'lucide-react';
import { getAreas } from '@/services/areaService';
import { AreaRisco } from '@/types/area';
import { toast } from '@/hooks/use-toast';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
} from '@/components/ui/dropdown-menu';

interface SearchBarProps {
  onSearch: (city: string) => void;
  loading: boolean;
}

const SearchBar = ({ onSearch, loading }: SearchBarProps) => {
  const [cityName, setCityName] = useState('');
  const [areas, setAreas] = useState<AreaRisco[]>([]);
  const [isOpen, setIsOpen] = useState(false);
  const [filteredAreas, setFilteredAreas] = useState<AreaRisco[]>([]);
  const inputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    const fetchAreas = async () => {
      try {
        const areasData = await getAreas();
        setAreas(areasData);
      } catch (error) {
        console.error('Erro ao carregar áreas:', error);
        toast({
          title: 'Erro',
          description: 'Não foi possível carregar a lista de cidades.',
          variant: 'destructive',
        });
      }
    };

    fetchAreas();
  }, []);

  useEffect(() => {
    if (cityName.trim()) {
      const filtered = areas.filter((area) =>
        area.nome.toLowerCase().includes(cityName.toLowerCase())
      );
      setFilteredAreas(filtered);
      setIsOpen(true);
    } else {
      setFilteredAreas([]);
      setIsOpen(false);
    }
  }, [cityName, areas]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (cityName.trim()) {
      onSearch(cityName.trim());
      setIsOpen(false);
    }
  };

  const handleCitySelect = (city: string) => {
    setCityName(city);
    setIsOpen(false);
    if (inputRef.current) {
      inputRef.current.focus();
    }
  };

  return (
    <div className="max-w-2xl mx-auto">
      <form onSubmit={handleSubmit} className="flex gap-3">
        <div className="flex-1 relative">
          <div className="relative">
            <Input
              ref={inputRef}
              type="text"
              placeholder="Digite o nome da cidade (ex: São Paulo, Rio de Janeiro...)"
              value={cityName}
              onChange={(e) => setCityName(e.target.value)}
              className="pl-12 h-12 text-lg border-blue-200 focus:border-blue-400 focus:ring-blue-400"
            />
            <CloudSun
              className="absolute left-4 top-1/2 transform -translate-y-1/2 text-blue-400"
              size={20}
            />
          </div>
          {isOpen && filteredAreas.length > 0 && (
            <div className="absolute w-full mt-1 bg-white rounded-md shadow-lg border border-gray-200 max-h-[300px] overflow-y-auto z-50">
              {filteredAreas.map((area) => (
                <div
                  key={area.id}
                  onClick={() => handleCitySelect(area.nome)}
                  className="px-4 py-2 hover:bg-blue-50 cursor-pointer text-sm"
                >
                  {area.nome}
                </div>
              ))}
            </div>
          )}
          {isOpen && filteredAreas.length === 0 && cityName.trim() && (
            <div className="absolute w-full mt-1 bg-white rounded-md shadow-lg border border-gray-200 p-2 text-sm text-gray-500">
              Nenhuma cidade encontrada
            </div>
          )}
        </div>
        <Button
          type="submit"
          disabled={loading || !cityName.trim()}
          className="h-12 px-8 bg-gradient-to-r from-blue-500 to-sky-600 hover:from-blue-600 hover:to-sky-700 text-white font-medium"
        >
          {loading ? (
            <div className="flex items-center space-x-2">
              <div className="w-4 h-4 border-2 border-white border-t-transparent rounded-full animate-spin"></div>
              <span>Buscando...</span>
            </div>
          ) : (
            'Buscar'
          )}
        </Button>
      </form>
    </div>
  );
};

export default SearchBar;
