/**
 * Created by vshakhov on 2016-12-18.
 */
import com.google.common.util.concurrent.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by vshakhov on 2016-12-17.
 */
public class ContentionLess {
    public static void main(String[] args) {

        final AtomicInteger atomicMax = new AtomicInteger(0);
        final MaxHolder syncMax = new MaxHolder();

        int nThreads = Runtime.getRuntime().availableProcessors();

        final Random r = new Random();
        List<Thread> threads = IntStream.range(0, nThreads)
                .mapToObj( iThread -> new Thread(() -> {
                    while (true) {
                        int i = r.nextInt();
                        if (i % 2 == 0) {
                            atomicMax(atomicMax, i);
                        } else {
                            syncMax(syncMax, i);
                        }
                        if (i % 100000 == 0) {
                            System.out.println("syncMax " + syncMax.max);
                            System.out.println("atomicMax " + atomicMax.get());
                        }
                    }
                },"pusher " + iThread)).collect(Collectors.toList());

        threads.forEach(Thread::start);
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
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

