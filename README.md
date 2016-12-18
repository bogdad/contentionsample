# First sample, Contention

This just does some heavily contended things

## Flamegraph

![flamegraph](https://cdn.rawgit.com/bogdad/contentionsample/master/flamegraph.svg)

## Pidstat with jstack

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

## SystemTap futexes.stp

```bash
vshakhov@ubuntu:~$ sudo stap futexes.stp
^Cjava[3308] lock 0x7f481c0b5854 contended 1 times, 113 avg us
java[3308] lock 0x7f481c0b8028 contended 1 times, 94 avg us
java[3308] lock 0x7f481cdee114 contended 158 times, 34624 avg us
java[3333] lock 0x7f63d812fc70 contended 4037 times, 47 avg us
java[3333] lock 0x7f63d800b1e8 contended 22574 times, 18 avg us
java[3333] lock 0x7f63d8021f50 contended 12 times, 45 avg us
java[3333] lock 0x7f63d800b210 contended 13706 times, 47 avg us
java[3333] lock 0x7f63d8140634 contended 92765 times, 111 avg us
java[3333] lock 0x7f63d8140630 contended 2313 times, 33 avg us
java[3333] lock 0x7f63d812fc74 contended 95261 times, 118 avg us
java[3333] lock 0x7f63d8021f28 contended 57 times, 8 avg us
java[3333] lock 0x7f63d813fb54 contended 287 times, 825 avg us
java[3333] lock 0x7f63d8140608 contended 5401 times, 14 avg us
java[3333] lock 0x7f63d8021f54 contended 713 times, 26504 avg us
java[3333] lock 0x7f63d80b8328 contended 1 times, 4 avg us
java[3333] lock 0x7f63d800ad54 contended 251 times, 905 avg us
java[3333] lock 0x7f63d813fb28 contended 5 times, 9 avg us
java[3333] lock 0x7f63d8074850 contended 9 times, 26 avg us
java[3333] lock 0x7f63d8074828 contended 369 times, 17 avg us
java[3333] lock 0x7f63d8020328 contended 14 times, 13 avg us
java[3333] lock 0x7f63d812fc48 contended 8805 times, 14 avg us
java[3333] lock 0x7f63d800ad28 contended 7 times, 17 avg us
java[3333] lock 0x7f63d8074854 contended 603 times, 158 avg us
java[3333] lock 0x7f63d800b214 contended 111924 times, 110 avg us
java[3333] lock 0x7f63d8020350 contended 13 times, 24 avg us
java[3333] lock 0x7f63d812f854 contended 275 times, 900 avg us
java[3333] lock 0x7f63d812f850 contended 28 times, 29 avg us
java[3333] lock 0x7f63d813fb50 contended 23 times, 28 avg us
java[3333] lock 0x7f63d813f754 contended 3 times, 93 avg us
java[3333] lock 0x7f63d800ad50 contended 1 times, 26 avg us
java[3333] lock 0x7f63d812f828 contended 6 times, 18 avg us
java[3333] lock 0x7f63d812f454 contended 1 times, 442 avg us
java[3333] lock 0x7f63d813fd28 contended 1 times, 23 avg us
java[3333] lock 0x7f63d800af54 contended 1 times, 26 avg us
java[3333] lock 0x7f63d8020354 contended 762 times, 24803 avg us
java[3333] lock 0x7f63d813fd54 contended 4 times, 58 avg us
java[3333] lock 0x7f63d812fa54 contended 2 times, 85 avg us
WARNING: Number of errors: 0, skipped probes: 52
```

# Second sample, less contention

## Flamegraph

![flamegraph](https://cdn.rawgit.com/bogdad/contentionsample/master/flamegraphless.svg)


## Pidstat with jstack

```bash
vshakhov@ubuntu:~$ pidstat -w -I -t -p 4357  5
Linux 4.8.0-22-generic (ubuntu) 	12/18/2016 	_x86_64_	(2 CPU)

03:11:34 PM   UID      TGID       TID   cswch/s nvcswch/s  Command
03:11:39 PM  1000      4357         -      0.00      0.00  java
03:11:39 PM  1000         -      4357      0.00      0.00  |__java
03:11:39 PM  1000         -      4359      0.00      0.00  |__java
03:11:39 PM  1000         -      4363     29.20      4.80  |__java
03:11:39 PM  1000         -      4364     29.00      4.80  |__java
03:11:39 PM  1000         -      4365     49.20      0.80  |__java
03:11:39 PM  1000         -      4366      0.00      0.00  |__java
03:11:39 PM  1000         -      4367      0.00      0.00  |__java
03:11:39 PM  1000         -      4368      0.00      0.00  |__java
03:11:39 PM  1000         -      4369      0.20      0.00  |__java
03:11:39 PM  1000         -      4370      0.20      0.00  |__java
03:11:39 PM  1000         -      4371      0.00      0.00  |__java
03:11:39 PM  1000         -      4372     20.00      0.00  |__java
03:11:39 PM  1000         -      4373    465.00    330.80  |__java
"pusher 0" #8 prio=5 os_prio=0 tid=0x00007fe28810f800 nid=0x1115 runnable [0x00007fe2761f0000]
   java.lang.Thread.State: RUNNABLE
	at java.lang.Integer.valueOf(Integer.java:832)
	at ContentionLess.lambda$null$0(ContentionLess.java:34)
	at ContentionLess$$Lambda$6/558638686.run(Unknown Source)
	at java.lang.Thread.run(Thread.java:745)
03:11:39 PM  1000         -      4374    475.00    363.60  |__java
"pusher 1" #9 prio=5 os_prio=0 tid=0x00007fe288113000 nid=0x1116 waiting on condition [0x00007fe2760ef000]
   java.lang.Thread.State: WAITING (parking)
	at sun.misc.Unsafe.park(Native Method)
	- parking to wait for  <0x00000000b3a09fd0> (a java.util.concurrent.locks.ReentrantLock$NonfairSync)
	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.parkAndCheckInterrupt(AbstractQueuedSynchronizer.java:836)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireQueued(AbstractQueuedSynchronizer.java:870)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(AbstractQueuedSynchronizer.java:1199)
	at java.util.concurrent.locks.ReentrantLock$NonfairSync.lock(ReentrantLock.java:209)
	at java.util.concurrent.locks.ReentrantLock.lock(ReentrantLock.java:285)
	at ContentionLess.syncMax(ContentionLess.java:67)
	at ContentionLess.lambda$null$0(ContentionLess.java:36)
	at ContentionLess$$Lambda$6/558638686.run(Unknown Source)
	at java.lang.Thread.run(Thread.java:745)
03:11:39 PM  1000         -      4392      0.00      0.00  |__java
03:11:39 PM  1000         -      4393      0.00      0.00  |__java
03:11:39 PM  1000         -      4397      0.00      0.00  |__java
```

## SystemTap futexes.stp
```bash
vshakhov@ubuntu:~$ sudo stap ./futexes.stp
[sudo] password for vshakhov:
^Cjava[4334] lock 0x7fc4b8d2eaf4 contended 192 times, 5534 avg us
java[4334] lock 0x7fc4b8d2eac8 contended 1 times, 33 avg us
java[4357] lock 0x7fe288112654 contended 261 times, 1422 avg us
java[4357] lock 0x7fe288074854 contended 449 times, 189 avg us
java[4357] lock 0x7fe288112b04 contended 12089 times, 37 avg us
java[4357] lock 0x7fe288020350 contended 5 times, 31 avg us
java[4357] lock 0x7fe288020328 contended 90 times, 5 avg us
java[4357] lock 0x7fe288112228 contended 5 times, 4 avg us
java[4357] lock 0x7fe288114630 contended 3258 times, 31 avg us
java[4357] lock 0x7fe288112ad8 contended 5996 times, 9 avg us
java[4357] lock 0x7fe288113e28 contended 3 times, 10 avg us
java[4357] lock 0x7fe288020354 contended 582 times, 29835 avg us
java[4357] lock 0x7fe288114608 contended 4208 times, 13 avg us
java[4357] lock 0x7fe288021f28 contended 11 times, 18 avg us
java[4357] lock 0x7fe288112254 contended 38 times, 374 avg us
java[4357] lock 0x7fe288112b00 contended 4495 times, 31 avg us
java[4357] lock 0x7fe288114254 contended 263 times, 1513 avg us
java[4357] lock 0x7fe288113e54 contended 20 times, 419 avg us
java[4357] lock 0x7fe288114228 contended 8 times, 428 avg us
java[4357] lock 0x7fe288114634 contended 9446 times, 37 avg us
java[4357] lock 0x7fe288074828 contended 232 times, 4 avg us
java[4357] lock 0x7fe288112650 contended 30 times, 26 avg us
java[4357] lock 0x7fe288112628 contended 3 times, 11 avg us
java[4357] lock 0x7fe288114250 contended 35 times, 26 avg us
java[4357] lock 0x7fe288021f54 contended 598 times, 29033 avg us
java[4357] lock 0x7fe288021f50 contended 14 times, 25 avg us
```