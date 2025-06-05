import { useState, useEffect } from 'react';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from '@/components/ui/dialog';
import { toast } from '@/hooks/use-toast';
import { registerUser } from '@/services/userService';
import { AreaRiscoDTO } from '@/types/user';
import { getAreas } from '@/services/areaService';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import { Label } from '@/components/ui/label';

const RegisterButton = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [formData, setFormData] = useState({
    nome: '',
    email: '',
    telefone: '',
    areaRisco: {
      id: '',
    },
  });
  const [areas, setAreas] = useState<AreaRiscoDTO[]>([]);

  useEffect(() => {
    const fetchAreas = async () => {
      try {
        const areasData = await getAreas();
        setAreas(areasData);
      } catch (error) {
        console.error('Error fetching areas:', error);
        toast({
          title: 'Erro',
          description: 'Não foi possível carregar as áreas de risco',
          variant: 'destructive',
        });
      }
    };

    if (isOpen) {
      fetchAreas();
    }
  }, [isOpen]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (
      !formData.nome ||
      !formData.email ||
      !formData.telefone ||
      !formData.areaRisco.id
    ) {
      toast({
        title: 'Erro',
        description: 'Por favor, preencha todos os campos',
        variant: 'destructive',
      });
      return;
    }

    setIsLoading(true);
    try {
      const userData = {
        nome: formData.nome,
        email: formData.email,
        telefone: formData.telefone,
        areaRisco: {
          id: parseInt(formData.areaRisco.id),
        },
      };

      await registerUser(userData);
      toast({
        title: 'Sucesso',
        description: 'Usuário cadastrado com sucesso!',
      });
      setIsOpen(false);
      setFormData({
        nome: '',
        email: '',
        telefone: '',
        areaRisco: {
          id: '',
        },
      });
    } catch (error) {
      toast({
        title: 'Erro',
        description: 'Não foi possível cadastrar o usuário',
        variant: 'destructive',
      });
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <Dialog open={isOpen} onOpenChange={setIsOpen}>
      <DialogTrigger asChild>
        <Button variant="outline">Registrar</Button>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Registrar</DialogTitle>
        </DialogHeader>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="space-y-2">
            <Label htmlFor="nome">Nome</Label>
            <Input
              id="nome"
              name="nome"
              placeholder="Digite seu nome"
              value={formData.nome}
              onChange={handleChange}
            />
          </div>
          <div className="space-y-2">
            <Label htmlFor="email">Email</Label>
            <Input
              id="email"
              name="email"
              type="email"
              placeholder="Digite seu email"
              value={formData.email}
              onChange={handleChange}
            />
          </div>
          <div className="space-y-2">
            <Label htmlFor="telefone">Telefone</Label>
            <Input
              id="telefone"
              name="telefone"
              placeholder="Digite seu telefone"
              value={formData.telefone}
              onChange={handleChange}
            />
          </div>
          <div className="space-y-2">
            <Label htmlFor="area">Área de Risco</Label>
            <Select
              value={formData.areaRisco.id}
              onValueChange={(value) =>
                setFormData((prev) => ({
                  ...prev,
                  areaRisco: {
                    ...prev.areaRisco,
                    id: value,
                  },
                }))
              }
            >
              <SelectTrigger>
                <SelectValue placeholder="Selecione uma área" />
              </SelectTrigger>
              <SelectContent>
                {areas.map((area) => (
                  <SelectItem key={area.id} value={area.id.toString()}>
                    {area.nome} (Nível {area.nivelRisco})
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>
          <Button type="submit" className="w-full" disabled={isLoading}>
            {isLoading ? 'Cadastrando...' : 'Registrar'}
          </Button>
        </form>
      </DialogContent>
    </Dialog>
  );
};

export default RegisterButton;
