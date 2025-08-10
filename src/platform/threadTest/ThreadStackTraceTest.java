package platform.threadTest;

public class ThreadStackTraceTest {
    public static void main(String[] args) throws InterruptedException {
        
        System.out.println("=== OS 스레드 vs 일반 메서드 호출 비교 ===\n");
        
        // 테스트용 작업
        Runnable task = new Runnable() {
            @Override
            public void run() {
                printStackTrace();
                doWork();
            }
            
            void doWork() {
                System.out.println("  작업 실행 중...");
                System.out.println("  OS 스레드 ID: " + Thread.currentThread().getId());
                System.out.println("  Java 스레드 이름: " + Thread.currentThread().getName());
            }
            
            void printStackTrace() {
                System.out.println("  호출 스택:");
                StackTraceElement[] stack = Thread.currentThread().getStackTrace();
                for (int i = 0; i < Math.min(5, stack.length); i++) {
                    System.out.println("    " + stack[i]);
                }
            }
        };
        
        // 1. run() 호출 - 일반 메서드처럼 동작
        System.out.println("1. thread.run() 호출 (일반 메서드처럼):");
        Thread thread1 = new Thread(task);
        thread1.run();  // main 스레드에서 직접 실행
        
        System.out.println("\n----------------------------------\n");
        
        // 2. start() 호출 - 실제 OS 스레드 생성
        System.out.println("2. thread.start() 호출 (OS 스레드 생성):");
        Thread thread2 = new Thread(task);
        thread2.start();  // 새로운 OS 스레드 생성 및 실행
        
        thread2.join(); // thread2 완료 대기
        
        System.out.println("\n----------------------------------\n");
        
        // 3. 일반 메서드 호출과 비교
        System.out.println("3. 일반 메서드 호출 (비교용):");
        normalMethod();
    }
    
    static void normalMethod() {
        System.out.println("  일반 메서드 실행");
        System.out.println("  OS 스레드 ID: " + Thread.currentThread().getId());
        System.out.println("  Java 스레드 이름: " + Thread.currentThread().getName());
    }
}