package zad1;

public class ThreadWriteNumber implements Runnable{
    private int i;
    private final Thread[] tabSync;

    public ThreadWriteNumber(int i, Thread[] tabSync) {
        this.i = i;
        this.tabSync = tabSync;
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[10];

        for(int i = 0 ; i < 10; ++i) {
            threads[i] = new Thread(new ThreadWriteNumber(i, threads));
        }

        for(int i = 0 ; i < 10; ++i) {
            threads[i].start();
        }
    }

    @Override
    public void run() {

        if(i == 9) {
            System.out.println("Hello from task " + i);
            return;
        }

        try {
            tabSync[i+1].join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Hello from task " + i);


    }
}
