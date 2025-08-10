package findPrimes;

import java.util.ArrayList;
import java.util.List;

public class PrimeSingleThread {

    private static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        if (n <= 3) {
            return true;
        }
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }
        
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }

    public static List<Integer> findPrimesSingleThread(int start, long end) {
        List<Integer> primes = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        
        for (int i = start; i <= end; i++) {
            if (isPrime(i)) {
                primes.add(i);
            }
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("Single thread execution time: " + (endTime - startTime) + " ms");
        System.out.println("Found " + primes.size() + " primes");
        return primes;
    }
}