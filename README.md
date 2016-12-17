# contentionsample

This just does some heavily contended things

![flamegraph](https://cdn.rawgit.com/bogdad/contentionsample/master/flamegraph.svg)
```bash
 vshakhov@ubuntu:~$ pidstat -w -I -t -p 2486 5
 Linux 4.8.0-22-generic (ubuntu) 	12/17/2016 	_x86_64_	(2 CPU)

 09:48:32 PM   UID      TGID       TID   cswch/s nvcswch/s  Command
 09:48:37 PM  1000      2486         -      0.00      0.00  java
 09:48:37 PM  1000         -      2486      0.00      0.00  |__java
 09:48:37 PM  1000         -      2488  12951.40     73.40  |__java
 "main" #1 prio=5 os_prio=0 tid=0x00007f36a000a000 nid=0x9b8 (2488) waiting on condition [0x00007f36a7d40000]
    java.lang.Thread.State: WAITING (parking)
 	at sun.misc.Unsafe.park(Native Method)
 	- parking to wait for  <0x00000000b3a08760> (a java.util.concurrent.locks.ReentrantLock$NonfairSync)
 	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
 	at java.util.concurrent.locks.AbstractQueuedSynchronizer.parkAndCheckInterrupt(AbstractQueuedSynchronizer.java:836)
 	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireQueued(AbstractQueuedSynchronizer.java:870)
 	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(AbstractQueuedSynchronizer.java:1199)
 	at java.util.concurrent.locks.ReentrantLock$NonfairSync.lock(ReentrantLock.java:209)
 	at java.util.concurrent.locks.ReentrantLock.lock(ReentrantLock.java:285)
 	at java.util.concurrent.ArrayBlockingQueue.offer(ArrayBlockingQueue.java:327)
 	at java.util.concurrent.ThreadPoolExecutor.execute(ThreadPoolExecutor.java:1361)
 	at com.google.common.util.concurrent.MoreExecutors$ListeningDecorator.execute(MoreExecutors.java:556)
 	at java.util.concurrent.AbstractExecutorService.submit(AbstractExecutorService.java:134)
 	at com.google.common.util.concurrent.AbstractListeningExecutorService.submit(AbstractListeningExecutorService.java:61)
 	at Contention.main(Contention.java:35)
 09:48:37 PM  1000         -      2492     57.40      0.00  |__java
 09:48:37 PM  1000         -      2493     62.80      0.00  |__java
 09:48:37 PM  1000         -      2494     91.40      1.60  |__java
 09:48:37 PM  1000         -      2495      0.00      0.00  |__java
 09:48:37 PM  1000         -      2496      0.00      0.00  |__java
 09:48:37 PM  1000         -      2497      0.00      0.00  |__java
 09:48:37 PM  1000         -      2498      0.20      0.00  |__java
 09:48:37 PM  1000         -      2499      0.20      0.00  |__java
 09:48:37 PM  1000         -      2500      0.00      0.00  |__java
 09:48:37 PM  1000         -      2501     20.00      0.00  |__java
 09:48:37 PM  1000         -      2502   8482.00    224.20  |__java
 "pool-1-thread-1" #9 prio=5 os_prio=0 tid=0x00007f36a012f000 nid=0x9c6 (2502) waiting on condition [0x00007f368cc0c000]
    java.lang.Thread.State: WAITING (parking)
 	at sun.misc.Unsafe.park(Native Method)
 	- parking to wait for  <0x00000000b3a9afb0> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
 	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
 	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2039)
 	at java.util.concurrent.ArrayBlockingQueue.take(ArrayBlockingQueue.java:403)
 	at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1067)
 	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1127)
 	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
 	at java.lang.Thread.run(Thread.java:745)
 09:48:37 PM  1000         -      2503   8748.20     28.00  |__java
 pool-1-thread-2" #10 prio=5 os_prio=0 tid=0x00007f36a013f000 nid=0x9c7 (2503) waiting on condition [0x00007f368cb0b000]
    java.lang.Thread.State: WAITING (parking)
 	at sun.misc.Unsafe.park(Native Method)
 	- parking to wait for  <0x00000000b3a9afb0> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
 	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
 	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2039)
 	at java.util.concurrent.ArrayBlockingQueue.take(ArrayBlockingQueue.java:403)
 	at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1067)
 	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1127)
 	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
 	at java.lang.Thread.run(Thread.java:745)

 09:48:37 PM  1000         -      2566      0.00      0.00  |__java
 ```
