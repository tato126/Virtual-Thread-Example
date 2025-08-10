package platform.threadCount;

/**
 *
 * @author chan
 */
public class ThreadCountTest {

    private static int threadCount = 0; // 현재 생성된 쓰레드 전역 카운터

    public static void main(String[] args) {

        // JVM 시스템 정보 수집
        System.out.println("JVM 정보:");
        // 왜 1024씩 두번 나눌까? , 1024가 의미하는 것은 무엇일까?
        // /1024/1024 -> KB -> MB 변환
        System.out.println("최대 힙 메모리: " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "MB");
        System.out.println("사용 가능한 프로세서: " + Runtime.getRuntime().availableProcessors() + "개\n");

        // TODO: 사용 가능한 프로세서 == 코어?

        // 쓰레드 생성 로직
        // 이건 어떤 로직일까?
        try {
            // 한계까지 쓰레드 생성
            while (true) {

                // 쓰레드 생성 블록
                Thread thread = new Thread(() -> {

                    // 새로 생성한 쓰레드는 대기하다가 현재 쓰레드를 깨운다.?
                    try {
                        // 쓰레드 영구 잚듬
                        // 약 292년
                        // 목적: 쓰레드가 종료되지 않고 메모리를 계속 사용하게 함

                        // TODO: Thread.sleep이란 무엇인가? -> SleepEx 예제
                        Thread.sleep(Long.MAX_VALUE);

                    } catch (InterruptedException e) {
                        // 인터럽트 발생 시 현재 쓰레드의 인터럽트 상태 복원
                        // 자바의 모범 사례
                        Thread.currentThread().interrupt();
                    }
                });

                // TODO: start가 아니라 run인 것이 좋지 않을까?
                //       run은 실제로 쓰레드를 사용하는 것이 아닌것인가?
                thread.start();
                threadCount++;

                // 현재
                if (threadCount % 100 == 0) {
                    long userMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024;
                    System.out.println("쓰레드 수: " + threadCount + " | 사용 메모리: " + userMemory + "MB");
                }
            }

        } catch (OutOfMemoryError e) { // 메모리 초과 에러시

            // TODO: err.print는 무엇을 의미하는걸까?
            System.err.println("\n[OutOfMemoryError] 최대 쓰레드: " + threadCount);
            System.err.println("에러 메시지: " + e.getMessage());

        } catch (Exception e) { // 그 외의 에러 발생 시
            System.err.println("\n[Error] 최대 쓰레드: " + threadCount);
            System.err.println("에러 타입: " + e.getClass().getSimpleName());
        }
    }
}
