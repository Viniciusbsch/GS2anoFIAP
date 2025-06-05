import { AreaRiscoDTO } from '@/types/user';
import { API_URL } from '@/config';

export async function getAreas(): Promise<AreaRiscoDTO[]> {
  const response = await fetch(`${API_URL}/areas`);
  if (!response.ok) {
    throw new Error('Failed to fetch areas');
  }
  return response.json();
}
