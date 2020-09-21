package multithreading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

interface ReadWriteLockImpl{
    void acquireReadLock() throws InterruptedException;
    void acquireWriteLock() throws InterruptedException;
    void releaseReadLock();
    void releaseWriteLock();
}

public class ReadWriteLock implements ReadWriteLockImpl{

    private int writeCapacity = 1;
    private int readers = 0;

    ReadWriteLock(){
    }

    @Override
    public synchronized void acquireReadLock() throws InterruptedException {
        while(writeCapacity == 0)
            wait();
        readers++;
    }

    @Override
    public synchronized void acquireWriteLock() throws InterruptedException {
        while(writeCapacity == 0 || readers != 0)
            wait();
        writeCapacity = 0;
    }

    @Override
    public synchronized void releaseReadLock() {
        readers--;
        notify();
    }

    @Override
    public synchronized void releaseWriteLock() {
        writeCapacity = 1;
        notify();
    }
}


class ReadWriteLockDemo{
    public static void main(String[] args) throws InterruptedException {

        ReadWriteLock readWriteLock = new ReadWriteLock();

        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    System.out.println("Attempting to acquire write lock in thread_1: " + System.currentTimeMillis());
                    readWriteLock.acquireWriteLock();
                    System.out.println("write lock acquired thread_1: " + +System.currentTimeMillis());

                    // Simulates write lock being held indefinitely
                    for (; ; ) {
                        Thread.sleep(500);
                    }

                } catch (InterruptedException ie) {

                }
            }
        });

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    System.out.println("Attempting to acquire write lock in thread_2: " + System.currentTimeMillis());
                    readWriteLock.acquireWriteLock();
                    System.out.println("write lock acquired thread_2: " + System.currentTimeMillis());

                } catch (InterruptedException ie) {

                }
            }
        });

        Thread tReader1 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    readWriteLock.acquireReadLock();
                    System.out.println("Read lock acquired: " + System.currentTimeMillis());

                } catch (InterruptedException ie) {

                }
            }
        });

        Thread tReader2 = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("Read lock about to release: " + System.currentTimeMillis());
                readWriteLock.releaseReadLock();
                System.out.println("Read lock released: " + System.currentTimeMillis());
            }
        });

        tReader1.start();
        t1.start();
        Thread.sleep(3000);
        tReader2.start();
        Thread.sleep(1000);
        t2.start();
        tReader1.join();
        tReader2.join();
        t2.join();
    }
}