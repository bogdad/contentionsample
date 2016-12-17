import com.google.common.util.concurrent.*;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by vshakhov on 2016-12-17.
 */
public class Contention {
    public static void main(String[] args) {

        final AtomicInteger atomicMax = new AtomicInteger(0);
        final MaxHolder syncMax = new MaxHolder();

        int nThreads = Runtime.getRuntime().availableProcessors();

        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(1000);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(nThreads, nThreads, 0, TimeUnit.SECONDS,
                queue, (r, e) -> {
            try {
                queue.put(r);
            } catch (InterruptedException e1) {
                Thread.currentThread().interrupt();
            }
        });
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(executor);

        Random r = new Random();
        while (true) {
            ListenableFuture<Integer> computation = executorService.submit(() -> r.nextInt());
            Futures.transform(computation, (Integer i) ->  {
                if (i % 2 == 0) {
                    atomicMax(atomicMax, i);
                } else {
                    syncMax(syncMax, i);
                }
                if (i % 100000 == 0) {
                    System.out.println("syncMax " + syncMax.max);
                    System.out.println("atomicMax " + atomicMax.get());
                }
                return String.valueOf(i);
            });
        }

    }

    static void atomicMax(AtomicInteger max, Integer cur) {
        while(true) {
            int curMax = max.get();
            if (cur < curMax)
                return;
            if (max.compareAndSet(curMax, cur))
                return;
        }
    }

    final static Lock lock = new ReentrantLock();
    static void syncMax(MaxHolder max, Integer cur) {
        lock.lock();
        try {
            if (max.max < cur) {
                max.max = cur;
            }
        } finally {
            lock.unlock();
        }
    }
    static class MaxHolder {
        public int max;
    }
}
