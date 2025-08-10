package findPrimes;

import java.util.ArrayList;
import java.util.List;

public class PrimeVirtualThread {

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

    public static List<Integer> findPrimesVirtualThread(int start, long end) throws InterruptedException {
        List<Integer> primes = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        
        int chunkSize = 1000;
        List<Thread> threads = new ArrayList<>();
        
        for (int i = start; i <= end; i += chunkSize) {
            int chunkStart = i;
            long chunkEnd = Math.min((long)i + chunkSize - 1, end);
            
            Thread thread = Thread.startVirtualThread(() -> {
                for (int j = chunkStart; j <= chunkEnd; j++) {
                    if (isPrime(j)) {
                        primes.add(j);
                    }
                }
            });
            threads.add(thread);
        }
        
        for (Thread thread : threads) {
            thread.join();
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("Virtual thread execution time: " + (endTime - startTime) + " ms");
        System.out.println("Number of threads created: " + threads.size());
        System.out.println("Found " + primes.size() + " primes");
        return primes;
    }
}