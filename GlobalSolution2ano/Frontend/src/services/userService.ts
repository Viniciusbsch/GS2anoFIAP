import { UsuarioDTO } from '@/types/user';
import { API_URL } from '@/config';

export async function registerUser(
  userData: Partial<UsuarioDTO>
): Promise<UsuarioDTO> {
  const response = await fetch(`${API_URL}/users/register`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(userData),
  });

  if (!response.ok) {
    throw new Error('Failed to register user');
  }

  return response.json();
}

export async function checkEmail(email: string): Promise<UsuarioDTO> {
  const response = await fetch(`${API_URL}/users/check-email/${email}`);
  if (!response.ok) {
    throw new Error('Email not found');
  }
  return response.json();
}
