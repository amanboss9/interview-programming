package multithreading;

import java.util.ArrayList;

class TokenBucketFilter {
    private int MAX_TOKENS;
    private long lastRequestedTime;

    private long tokens = 0;

    private TokenBucketFilter(int maxNumberOfTokens){
        lastRequestedTime = System.currentTimeMillis();
        MAX_TOKENS = maxNumberOfTokens;
    }

    // Entire method is critical
    private synchronized void getToken() throws InterruptedException {

        tokens+=(System.currentTimeMillis() - lastRequestedTime)/1000;

        // We don't want tokens exceeding MAX_TOKENS.
        if(tokens > MAX_TOKENS)
            tokens = MAX_TOKENS;

        if(tokens == 0)
            Thread.sleep(1000);
        else
            tokens--;

        lastRequestedTime = System.currentTimeMillis();

        System.out.println(String.format("Granting %s token at: %s", Thread.currentThread().getName(), System.currentTimeMillis()/1000));
    }

    static void testTokenBucketFilter() throws InterruptedException {
        ArrayList<Thread> threadList = new ArrayList<>();

        TokenBucketFilter tokenBucketFilter = new TokenBucketFilter(1);

        for(int i=1;i<=10;i++){

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        tokenBucketFilter.getToken();
                    } catch (InterruptedException e){
                        e.printStackTrace();
                        System.out.println("Some problem.");
                    }
                }
            });

            thread.setName("Thread_"+i);
            threadList.add(thread);
        }


        for(Thread t : threadList)
            t.start();

        for(Thread t: threadList)
            t.join();
    }


}

class TokenBucketDemo {
    public static void main( String args[] ) throws InterruptedException {
        TokenBucketFilter.testTokenBucketFilter();
    }
}
