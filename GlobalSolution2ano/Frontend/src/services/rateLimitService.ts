
interface RateLimitData {
  count: number;
  date: string;
}

const RATE_LIMIT_KEY = 'weather_api_rate_limit';
const MAX_CALLS_PER_DAY = 950; // Deixamos uma margem de segurança (50 chamadas a menos que o limite)

export class RateLimitService {
  private static getTodayString(): string {
    return new Date().toISOString().split('T')[0]; // YYYY-MM-DD
  }

  private static getRateLimitData(): RateLimitData {
    const stored = localStorage.getItem(RATE_LIMIT_KEY);
    if (!stored) {
      return { count: 0, date: this.getTodayString() };
    }

    try {
      const data: RateLimitData = JSON.parse(stored);
      
      // Se mudou de dia, resetar o contador
      if (data.date !== this.getTodayString()) {
        return { count: 0, date: this.getTodayString() };
      }

      return data;
    } catch {
      return { count: 0, date: this.getTodayString() };
    }
  }

  private static saveRateLimitData(data: RateLimitData): void {
    localStorage.setItem(RATE_LIMIT_KEY, JSON.stringify(data));
  }

  public static canMakeRequest(): boolean {
    const data = this.getRateLimitData();
    return data.count < MAX_CALLS_PER_DAY;
  }

  public static incrementCounter(): void {
    const data = this.getRateLimitData();
    data.count += 1;
    this.saveRateLimitData(data);
  }

  public static getRemainingCalls(): number {
    const data = this.getRateLimitData();
    return Math.max(0, MAX_CALLS_PER_DAY - data.count);
  }

  public static getCurrentCount(): number {
    const data = this.getRateLimitData();
    return data.count;
  }

  public static getMaxCalls(): number {
    return MAX_CALLS_PER_DAY;
  }
}
