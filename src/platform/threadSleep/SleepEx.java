package platform.threadSleep;

/**
 * Thread.sleep() 예제 클래스
 * sleep()은 현재 쓰레드를 일정시간(millsSecond) 동안 중단한다.
 *
 * @author chan
 */
public class SleepEx {
    public static void main(String[] args) {

        // TODO: run과 start의 차이는 무엇일까?
        // run: 호출만
        // start: 호출 후 동작까지
        Runnable runnable = new ThreadSleep();
        Runnable runnable2 = new ThreadSleep2();

        // 왜 상속방식은 바로 start를 호출하지 못할까?
        // Thread로 감싸야한다. -> 주입을 통해 구현하는 것이니까 strategy 패턴인가?
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable2);


        t1.start();
        t2.start();

        // 작업을 하다가 쓰레드1을 2초 중단한다.
        // try-catch
        try {
            t1.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("쓰레드 종료");
    }
}

class ThreadSleep implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 300; i++) {
            System.out.print("-");
        }
        System.out.println("<<th1 종료>>");
    }
}

class ThreadSleep2 implements Runnable {

    @Override
    public void run() {

        for (int i = 0; i < 300; i++) {
            System.out.print("|");
        }

        System.out.println("<<th2 종료>>");
    }
}
