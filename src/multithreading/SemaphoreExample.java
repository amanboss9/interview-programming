package multithreading;


class SemaphoreExample {

    private int usedPermits = 0; // permits given out
    private int maxCount;  // max permits to give out

    SemaphoreExample(int count) {
        this.maxCount = count;
    }

    synchronized void acquire() throws InterruptedException {
        while(usedPermits == maxCount)
            this.wait();

        usedPermits++;
        this.notify();
    }

    synchronized void release() throws InterruptedException {
        while(usedPermits == 0)
            this.wait();

        usedPermits--;
        this.notify();
    }
}


class SemaphoreDemo{
    public static void main(String[] args) throws InterruptedException {
        SemaphoreExample semaphore = new SemaphoreExample(1);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 5; i++) {
                        semaphore.acquire();
                        System.out.println("Foo " + i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 5; i++) {
                        semaphore.release();
                        System.out.println("Bar " + i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}