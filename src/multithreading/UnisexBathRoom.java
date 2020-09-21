package multithreading;


// A bathroom is being designed for the use of both males and females in an office
// but requires the following constraints to be maintained:
//  - There cannot be men and women in the bathroom at the same time.
//  - There should never be more than three employees in the bathroom simultaneously.
//  - The solution should avoid deadlocks. For now, though, don’t worry about starvation.

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class UnisexBathRoom {
    private Queue<Boolean> overAll;

    private static final int MAX_ALLOWED = 3;

    UnisexBathRoom(){
        overAll = new LinkedList<>();
    }

    private void useBathroom(String name) throws InterruptedException {
        System.out.println(name + " using bathroom. Current employees in bathroom = " + overAll.size() + " " + System.currentTimeMillis());
        Thread.sleep(5000);
    }

    void acquireBathRoom(boolean isWomen) throws InterruptedException {
        synchronized(this){
            while(overAll.size() >= MAX_ALLOWED || (overAll.size() > 0 && overAll.peek() && !isWomen
                    || overAll.size() > 0 && !overAll.peek() && isWomen))
                this.wait();

            if(!isWomen)
                overAll.add(false);
            else
                overAll.add(true);

            this.notifyAll();
        }

        useBathroom(Thread.currentThread().getName());
        releaseBathRoom();
    }

    private synchronized void releaseBathRoom() throws InterruptedException {
        while(overAll.isEmpty())
            this.wait();

        overAll.poll();
        System.out.println();
        System.out.println(Thread.currentThread().getName() + " Done using bathroom. Current employees in bathroom = " + overAll.size() +" "+ System.currentTimeMillis());
        System.out.println();

        this.notifyAll();
    }
}


class UnisexBathRoomDemo{
    public static void main(String[] args) throws InterruptedException {

        UnisexBathRoom unisexBathRoom = new UnisexBathRoom();

        ArrayList<Thread> tList = new ArrayList<>();
        boolean[] line = new boolean[]{false, true, true, false, true, true, false, true, false, true};

        for(int i=0;i<10;i++){
            boolean isWomen = line[i];
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        unisexBathRoom.acquireBathRoom(isWomen);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });
            t.setName("Thread_"+i+(!isWomen?"_Men":"_Women"));
            tList.add(t);
        }

        for(Thread t : tList){
            t.start();
            Thread.sleep(500);
        }

        for(Thread t : tList){
            t.join();
        }

    }
}
