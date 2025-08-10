package virtual;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 가상 스레드(Virtual Thread 생성 한계 테스트) 클래스
 *
 * @author chan
 */
public class VirtualThreadLimitTest {

    // TODO: AtomicCounter는 무엇인가?
    private static final AtomicInteger threadCount = new AtomicInteger(0);
    private static final AtomicInteger activeCount = new AtomicInteger(0);

    public static void main(String[] args) {

        // JVM 정보 출력
        printSystemInfo();

        int targetThreads = 1_000_000; // 목표: 100만개!
        boolean userVirtualThread = true;

        System.out.println("\n" + (userVirtualThread ? "Virtual" : "Platform") + " Thread 생성 테스트 시작!");

        System.out.println("목표: " + String.format("%,d", targetThreads) + "개 \n");

        // 모든 스레드가 동시에 살아있도록 CountDownLatch 사용
        // TODO: CountDownLatch 는 무엇일까?
        CountDownLatch latch = new CountDownLatch(1);

        long startTime = System.currentTimeMillis(); // 시작 시간
        long startMemory = getUsedMemory();

        try {
            for (int i = 0; i < targetThreads; i++) {

                final int threadId = i;

                // 가상쓰레드 생성
                if (userVirtualThread) {

                    Thread.startVirtualThread(() -> {

                        runTask(threadId, latch);
                    });

                    // 일반 스레드 생성
                } else {

                    new Thread(() -> {
                        runTask(threadId, latch);
                    }).start();
                }

                // TODO: 이건 어떤 코드일까?
                threadCount.incrementAndGet();

                // 진행 상황 출력 (10만 개마다)
                if (i > 0 && i % 100_000 == 0) {

                    long currentMemory = getUsedMemory();

                    // TODO: 이건 어떤 의미인가?
                    long memoryPerThread = (currentMemory - startMemory) / i;

                    System.out.printf("%,d 개 생성 | 활성: %,d | 메모리: %,d MB | 스레드당 : %b bytes \n"
                            , i, activeCount.get(), currentMemory / 1024 / 1024, memoryPerThread);
                }
            }

            long creationTime = System.currentTimeMillis() - startTime;
            long finalMemory = getUsedMemory();
            long totalMemoryUsed = finalMemory - startMemory;

            // 결과 출력
            System.out.println("\n ======= 테스트 완료 =======");
            System.out.printf("* 생성된 스레드: %,d 개\n", threadCount.get());
            System.out.printf("* 생성 시간: %,d ms\n", creationTime);
            System.out.printf("* 총 메모리 사용: %,d MB\n", totalMemoryUsed / 1024 / 1024);
            System.out.printf("* 스레드당 평균 메모리: %,d bytes\n", totalMemoryUsed / threadCount.get());
            System.out.printf("!!! 초당 생성 속도: %,d 개/초 \n", (threadCount.get() * 1000L) / creationTime);

            // 3초 대기 후 모든 스레드 해제
            Thread.sleep(3000);

            // TODO: 이건 어떤 의미일까? latch는 무엇일까? count 라는 뜻은 --count 일까?
            latch.countDown();

            // 스레드들이 종료될 시간을 줌
            Thread.sleep(2000);
            System.out.println("\n 모든 스레드 종료 완료");

            // 최대 메모리 초과
        } catch (OutOfMemoryError e) {
            System.err.println("\n OutOfMemoryError 발생!!");
            System.err.printf("최대 생성 가능: %,d 개\n", threadCount.get());
        } catch (Exception e) {
            System.err.print("\n 에러 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void runTask(int id, CountDownLatch latch) {

        // TODO: 이건 무슨 의미인가?
        activeCount.incrementAndGet();

        try {
            // 모든 스레드가 동시에 살아있도록 대기
            // TODO: latch.await은 어떤 기능을 담당하는가?
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // TODO: activeCount가 뭐였지?
            // -> AtomicInteger의 기능을 제공한다.
            // -> AtomicInteger는 무엇인가?
            activeCount.decrementAndGet();
        }
    }

    private static void printSystemInfo() {

        // TODO: 현재 OS에서 돌아가는 기능인가?
        Runtime runtime = Runtime.getRuntime();
        System.out.println("============== 시스템 정보 ==============");
        System.out.printf("Java 버전: %s\n", System.getProperty("java.version"));
        System.out.printf("최대 힙 메모리: %,d MB\n", runtime.maxMemory() / 1024 / 1024);
        System.out.printf("CPU 코어: %d 개\n", runtime.availableProcessors());
        System.out.printf("OS: %s\n", System.getProperty("os.name"));
    }

    private static long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
