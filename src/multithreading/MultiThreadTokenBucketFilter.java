package multithreading;

import java.util.ArrayList;

class MultiThreadTokenBucketFilter {
    private int MAX_TOKENS;
    private int tokens;
    private final int ONE_SECOND = 1000;

    MultiThreadTokenBucketFilter(int maxNumberOfTokens){
        MAX_TOKENS = maxNumberOfTokens;
    }

    void initialise(){
        Thread t = new Thread(() -> startDaemon());
        t.setDaemon(true);
        t.start();
    }

    private void startDaemon(){

        while(true){
            synchronized (this){
                if(tokens < MAX_TOKENS)
                    tokens++;
                this.notify();
            }
            try{
                Thread.sleep(ONE_SECOND);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    void getToken() throws InterruptedException {
        synchronized (this){
            while(tokens == 0)
                this.wait();

            tokens--;

            System.out.println(String.format("Granting %s token at: %s", Thread.currentThread().getName(), System.currentTimeMillis()/1000));

        }
    }


}

class TokenBucketFilterFactory{

    TokenBucketFilterFactory(){}

    // Before returning the tokenBucketFilter we are going to initialize the daemon thread.
    // Its anti-pattern to start thread in a constructor.
    MultiThreadTokenBucketFilter makeTokenBucketFilter(int capacity){
        MultiThreadTokenBucketFilter tokenBucketFilter = new MultiThreadTokenBucketFilter(capacity);
        tokenBucketFilter.initialise();
        return tokenBucketFilter;
    }
}

class MultiThreadTokenBucketDemo{
    public static void main(String[] args) throws InterruptedException {
        TokenBucketFilterFactory tokenBucketFactory =  new TokenBucketFilterFactory();

        MultiThreadTokenBucketFilter tokenBucketFilter = tokenBucketFactory.makeTokenBucketFilter(1);

        ArrayList<Thread> threadList = new ArrayList<>();

        for(int i=1;i<=10;i++){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        tokenBucketFilter.getToken();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        // Some thing wrong!!
                    }
                }
            });
            t.setName("Thread_"+i);
            threadList.add(t);
        }


        for(Thread t: threadList)
            t.start();

        for(Thread t: threadList)
            t.join();

    }
}
