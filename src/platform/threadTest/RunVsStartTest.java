package platform.threadTest;

public class RunVsStartTest {
    public static void main(String[] args) {
        
        System.out.println("메인 스레드: " + Thread.currentThread().getName());
        System.out.println("=====================================\n");
        
        // 테스트용 Runnable
        Runnable task = () -> {
            System.out.println("실행 중인 스레드: " + Thread.currentThread().getName());
            System.out.println("스레드 ID: " + Thread.currentThread().getId());
        };
        
        // 1. run() 호출 - 새 스레드 생성 X
        System.out.println("1. run() 메서드 호출:");
        Thread thread1 = new Thread(task, "Thread-1");
        thread1.run();  // 주의: 새 스레드 생성 안됨!
        System.out.println();
        
        // 2. start() 호출 - 새 스레드 생성 O
        System.out.println("2. start() 메서드 호출:");
        Thread thread2 = new Thread(task, "Thread-2");
        thread2.start();  // 실제 새 스레드 생성!
        
        // 잠시 대기 (thread2 실행 보장)
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n=====================================");
        System.out.println("총 활성 스레드 수: " + Thread.activeCount());
    }
}