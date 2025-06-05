import { useState } from 'react';
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
import { checkEmail } from '@/services/userService';

const LoginButton = () => {
  const [email, setEmail] = useState('');
  const [isOpen, setIsOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  const handleLogin = async () => {
    if (!email) {
      toast({
        title: 'Erro',
        description: 'Por favor, insira um email.',
        variant: 'destructive',
      });
      return;
    }

    setIsLoading(true);
    try {
      const user = await checkEmail(email);
      localStorage.setItem('isLogged', 'true');
      localStorage.setItem('userEmail', user.email);
      localStorage.setItem('userName', user.nome);

      toast({
        title: 'Login realizado',
        description: `Bem-vindo(a), ${user.nome}!`,
      });

      setIsOpen(false);
      window.location.reload();
    } catch (error) {
      toast({
        title: 'Erro',
        description:
          'Email não encontrado. Por favor, verifique e tente novamente.',
        variant: 'destructive',
      });
    } finally {
      setIsLoading(false);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem('isLogged');
    localStorage.removeItem('userEmail');
    localStorage.removeItem('userName');
    window.location.reload();
  };

  const isLogged = localStorage.getItem('isLogged') === 'true';
  const userName = localStorage.getItem('userName');

  return (
    <div className="flex items-center gap-2">
      {isLogged ? (
        <>
          <span className="text-sm text-gray-600">Olá, {userName}</span>
          <Button variant="outline" onClick={handleLogout}>
            Sair
          </Button>
        </>
      ) : (
        <Dialog open={isOpen} onOpenChange={setIsOpen}>
          <DialogTrigger asChild>
            <Button variant="outline">Entrar</Button>
          </DialogTrigger>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>Login</DialogTitle>
            </DialogHeader>
            <div className="space-y-4 py-4">
              <div className="space-y-2">
                <label htmlFor="email" className="text-sm font-medium">
                  Email
                </label>
                <Input
                  id="email"
                  type="email"
                  placeholder="seu@email.com"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                />
              </div>
              <Button
                className="w-full"
                onClick={handleLogin}
                disabled={isLoading}
              >
                {isLoading ? 'Entrando...' : 'Entrar'}
              </Button>
            </div>
          </DialogContent>
        </Dialog>
      )}
    </div>
  );
};

export default LoginButton;
