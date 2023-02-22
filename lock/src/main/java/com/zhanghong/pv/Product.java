package com.zhanghong.pv;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @author 985492783@qq.com
 * @date 2023/2/22 23:28
 */
public class Product implements Runnable{
    private static int I = 1;
    
    private final int i = I++;
    private Semaphore empty;
    
    private Semaphore mutex;
    
    private Semaphore full;
    
    private Queue<Integer> queue;
    private static Random random = new Random();
    
    public Product(Semaphore empty, Semaphore mutex, Semaphore full, Queue<Integer> queue) {
        this.empty = empty;
        this.mutex = mutex;
        this.full = full;
        this.queue = queue;
    }
    
    @Override
    public void run() {
        while(true) {
            try {
                //防止粘滞
                Thread.sleep(100);
                empty.acquire();
                mutex.acquire();
                product();
                full.release();
                mutex.release();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        
    }
    
    private void product() {
        int ran = random.nextInt(10000000);
        System.out.println("product-"+ i + " create: " + ran);
        this.queue.offer(ran);
    }
}
