package com.zhanghong.pv;

import java.util.Queue;
import java.util.concurrent.Semaphore;

/**
 * @author 985492783@qq.com
 * @date 2023/2/22 23:28
 */
public class Consumer extends Thread{
    
    private static int I = 1;
    
    private final int i = I++;
    private Semaphore empty;
    
    private Semaphore mutex;
    
    private Semaphore full;
    
    private Queue<Integer> queue;
    
    public Consumer(Semaphore empty, Semaphore mutex, Semaphore full, Queue<Integer> queue) {
        this.empty = empty;
        this.mutex = mutex;
        this.full = full;
        this.queue = queue;
    }
    @Override
    public void run() {
        while (true) {
            try {
                full.acquire();
                mutex.acquire();
                consumer();
                empty.release();
                mutex.release();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        
    }
    
    private void consumer() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("consumer-"+i+" get: "+queue.poll());
    }
}
