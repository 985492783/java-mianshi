package com.zhanghong.pv;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author 985492783@qq.com
 * @date 2023/2/22 23:27
 */
public class PV {
    
    /**
     * 生产者消费者模型
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) {
        int n = 10;
        //空的个数
        final Semaphore empty = new Semaphore(n, false);
        //事件个数
        final Semaphore full = new Semaphore(0, false);
        final Semaphore mutex = new Semaphore(1, false);
        final Queue<Integer> queue = new LinkedList<Integer>();
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for(int i =0;i<20;i++) {
            threadPool.execute(new Product(empty, mutex, full, queue));
            threadPool.execute(new Consumer(empty, mutex, full, queue));
        }
    }
}
