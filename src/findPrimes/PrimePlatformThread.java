package findPrimes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrimePlatformThread {

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

    public static List<Integer> findPrimesPlatformThread(int start, int end) throws InterruptedException {
        List<Integer> primes = Collections.synchronizedList(new ArrayList<>());
        long startTime = System.currentTimeMillis();
        
        int numThreads = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[numThreads];
        int chunkSize = (end - start + 1) / numThreads;
        
        for (int i = 0; i < numThreads; i++) {
            int threadStart = start + i * chunkSize;
            int threadEnd = (i == numThreads - 1) ? end : threadStart + chunkSize - 1;
            
            threads[i] = new Thread(() -> {
                for (int j = threadStart; j <= threadEnd; j++) {
                    if (isPrime(j)) {
                        primes.add(j);
                    }
                }
            });
            threads[i].start();
        }
        
        for (Thread thread : threads) {
            thread.join();
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("Platform thread execution time: " + (endTime - startTime) + " ms");
        System.out.println("Number of threads used: " + numThreads);
        System.out.println("Found " + primes.size() + " primes");
        return primes;
    }
}