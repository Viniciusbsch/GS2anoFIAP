import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { LoginButton } from './LoginButton';
import { RegisterButton } from './RegisterButton';
import { useAuth } from '@/hooks/useAuth';
import { Search } from 'lucide-react';

export function Header() {
  const { user } = useAuth();

  return (
    <header className="border-b">
      <div className="container flex h-16 items-center justify-between px-4">
        <div className="flex items-center gap-4">
          <h1 className="text-xl font-bold">Sistema de Alertas</h1>
          {user?.areaRisco && (
            <div className="ml-4 text-sm text-muted-foreground">
              Área: {user.areaRisco.nome} (Nível {user.areaRisco.nivelRisco})
            </div>
          )}
        </div>
        <div className="flex items-center gap-4">
          {!user && (
            <>
              <LoginButton />
              <RegisterButton />
            </>
          )}
          {user && (
            <Button variant="outline" onClick={() => {}}>
              Sair
            </Button>
          )}
        </div>
      </div>
    </header>
  );
}
