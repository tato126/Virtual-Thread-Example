import findPrimes.PrimeSingleThread;
import findPrimes.PrimeVirtualThread;
import findPrimes.PrimePlatformThread;

import java.util.List;

public class Main {

    private static final int start = 1;
    private static final int end = 1000000000;
    private static final String SEPARATOR = "=".repeat(50);

    public static void main(String[] args) {

//    TODO: 싱글 스레드는 몇 개까지 생성가능할까?
//          폴더에 파일은 몇 개까지 생성할 수 있을까?
//          각 쓰레드마다 메모리를 얼마나 사용할까?
//          다양한 작업 분할 크기에 따른 성능 변화
//          소켓 통신에서의 Virtual Thread 활용
//          Virtual Thread의 내부 구조 및 동작 원리

        long singleThreadTime = 0;
        long virtualThreadTime = 0;
        long platformThreadTime = 0;

        try {
            System.out.println("== 소수 찾기 성능 비교 ==");
            System.out.println("범위: 1~1000000000");
            System.out.println("CPU 코어 수: " + Runtime.getRuntime().availableProcessors());
            System.out.println(SEPARATOR);

            // 1. 단일 쓰레드 방식
            System.out.println("1. 단일 쓰레드 방식");
            long startTime1 = System.currentTimeMillis();
            List<Integer> primes1 = PrimeSingleThread.findPrimesSingleThread(start, end);
            singleThreadTime = System.currentTimeMillis() - startTime1;
            System.out.println();

            // 2. 가상 쓰레드 방식
            // TODO: 가상 쓰레드는 왜 버츄얼 쓰레드와 소수의 개수가 다르게 나올까?

            System.out.println("2. 가상 쓰레드 방식");
            long startTime2 = System.currentTimeMillis();
            List<Integer> primes2 = PrimeVirtualThread.findPrimesVirtualThread(start, end);
            virtualThreadTime = System.currentTimeMillis() - startTime2;
            System.out.println();

            // 3. 플랫폼 쓰레드 방식
            System.out.println("3. 멀티 쓰레드 방식");
            long startTime3 = System.currentTimeMillis();
            List<Integer> primes3 = PrimePlatformThread.findPrimesPlatformThread(start, end);
            platformThreadTime = System.currentTimeMillis() - startTime3;
            System.out.println();

            // 결과 출력
            System.out.println(SEPARATOR);
            System.out.println("📊 성능 비교 결과");
            System.out.println(SEPARATOR);

            System.out.println("실행 시간:");
            System.out.println("  • 단일 쓰레드: " + singleThreadTime + "ms");
            System.out.println("  • 가상 쓰레드: " + virtualThreadTime + "ms");
            System.out.println("  • 플랫폼 쓰레드: " + platformThreadTime + "ms");
            System.out.println();

            // 성능 향상 비율 계산
            double virtualSpeedup = (double) singleThreadTime / virtualThreadTime;
            double platformSpeedup = (double) singleThreadTime / platformThreadTime;

            System.out.println("단일 쓰레드 대비 성능 향상:");
            System.out.printf("  • 가상 쓰레드: %.2fx 빠름%n", virtualSpeedup);
            System.out.printf("  • 플랫폼 쓰레드: %.2fx 빠름%n", platformSpeedup);
            System.out.println();

            // 가장 빠른 방식 찾기
            String fastest;
            if (singleThreadTime <= virtualThreadTime && singleThreadTime <= platformThreadTime) {
                fastest = "단일 쓰레드";
            } else if (virtualThreadTime <= platformThreadTime) {
                fastest = "가상 쓰레드";
            } else {
                fastest = "플랫폼 쓰레드";
            }
            System.out.println("🏆 가장 빠른 방식: " + fastest);
            System.out.println(SEPARATOR);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}